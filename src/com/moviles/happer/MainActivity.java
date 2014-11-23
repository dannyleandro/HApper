package com.moviles.happer;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.moviles.mundo.GPSTracker;
import com.moviles.mundo.HApper;

public class MainActivity extends ActionBarActivity implements SensorEventListener
{	
	private SensorManager mSensorManager; 

	private Sensor mAccelerometer; 

	private HApper instancia;

	private boolean pausa;

	private boolean caminata;

	private MediaRecorder mRecorder;
	Activity act;

	/**
	 * Manejador de la ubicación
	 */	
	private GPSTracker gps;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		instancia = HApper.darInstancia(getApplicationContext());
		act = this;
		pausa = false;
		caminata = false;
		gps = new GPSTracker(getApplicationContext());
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		mRecorder = new MediaRecorder();
		activarSonidoFuerte();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onResume() 
	{
		super.onResume();
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		pausa=false;
		if (mRecorder != null) 
		{
			mRecorder.start();
		}
	}

	@Override
	protected void onPause() 
	{
		super.onPause();
		mSensorManager.unregisterListener(this);
		pausa = true;
		if (mRecorder != null) 
		{
			mRecorder.stop();       
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		int id = item.getItemId();
		if (id == R.id.action_settings) 
		{
			Intent i = new Intent(this, SettingsActivity.class);
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Muestra la actividad de alarmas 
	 * @param v View
	 */
	public void verAlarmas(View v) 
	{
		Intent intent = new Intent(getApplicationContext(), AlarmasActivity.class);
		startActivity(intent);
	}

	/**
	 * Muestra la actividad de ayuda 
	 * @param v View
	 */
	public void verAyuda(View v) 
	{
		instancia.conectarServidor(getApplicationContext());
	}

	/**
	 * Muestra la actividad del juego 
	 * @param v View vista gen�rica de carga de la actividad
	 */
	public void verJuego(View v) 
	{
		Intent intent = new Intent(getApplicationContext(), JuegoActivity.class);
		startActivity(intent);
	}

	/**
	 * Muestra la actividad de ajustes 
	 * @param v View vista con los valores de los ajustes
	 */
	public void verAjustes(View v) 
	{
		Intent i = new Intent(this, SettingsActivity.class);
		startActivity(i);
	}

	public void iniciarCaminata(View v)
	{
		//inicial = getCurrentLocation
		Button b = (Button) findViewById(R.id.btnAyuda);
		if(caminata)
		{
			//Termina la caminata
			caminata = false;
			b.setText("Iniciar Caminata");
		}
		else
		{
			b.setText("Terminar Caminata");
			//iniciar caminata
			caminata = true;
			final Location inicial = gps.getLocation();
			Thread thread = new Thread(new Runnable()
			{
				@Override
				public void run() 
				{
					while(caminata)
					{
						try 
						{
							System.out.println("Espera 15 seg para saber la distancia");
							Thread.sleep(15000);
						} 
						catch (InterruptedException e) 
						{
							e.printStackTrace();
						}
						final float distance = inicial.distanceTo(gps.getLocation());
						System.out.println("Su distancia es:" + distance);
						SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
						int brecha = prefs.getInt("prefDistance", 50);
						if(distance > brecha)
						{
							act.runOnUiThread(new Runnable() 
							{
								@Override
								public void run() 
								{
									Toast.makeText(getApplicationContext(), "Su distancia es:" + distance, Toast.LENGTH_LONG).show();
									enviarMensajeSMS(instancia.darTelefonoContactoBP(), instancia.darMensajeAEnviarBP());
								}
							});
							caminata = false;
						}
					}
				}
			});
			thread.start();
			caminata = true;
		}
	}

	public void activarBotonPanico(View v)
	{
		new AlertDialog.Builder(this)
		.setTitle("Emergencia!!!")
		.setMessage("Se enviará un mensaje automatico a: " +instancia.darNombreContactoBP()+  ". Esta de acuerdo con esto?")
		.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) { 
				enviarMensajeSMS(instancia.darTelefonoContactoBP(), instancia.darMensajeAEnviarBP());
			}
		})
		.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) { 
				// do nothing
			}
		})
		.setIcon(android.R.drawable.ic_dialog_alert)
		.show();
	}

	protected void enviarMensajeSMS(String phoneNo, String message) 
	{
		try 
		{
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(phoneNo, null, message, null, null);
			Toast.makeText(getApplicationContext(), "SMS sent.",
					Toast.LENGTH_LONG).show();
		} 
		catch (Exception e) 
		{
			Writer writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			e.printStackTrace(printWriter);
			String s = writer.toString();

			showDialog("Error", s);

		}
	} 

	/**
	 * Método encargado de mostrar un mensaje
	 * @param title Titulo del mensaje a mostrar
	 * @param message Mensaje a mostrar
	 */
	private void showDialog(String title, String message) 
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle(title);
		alertDialog.setCancelable(false);
		alertDialog.setMessage(message);
		alertDialog.setPositiveButton("OK",new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog,int id) {

			}
		});
		AlertDialog dialog= alertDialog.create();
		dialog.show();	
	}

	/**
	 * Function to show settings alert dialog
	 * */
	public void showSettingsAlert()
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		// Setting Dialog Title
		alertDialog.setTitle("GPS is settings");

		// Setting Dialog Message
		alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

		// Setting Icon to Dialog
		//alertDialog.setIcon(R.drawable.delete);

		// On pressing Settings button
		alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) 
			{
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(intent);
			}
		});

		// on pressing cancel button
		alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		// Showing Alert Message
		AlertDialog dialog = alertDialog.create();
		dialog.show();
	}

	@Override
	public void onSensorChanged(SensorEvent event) 
	{
		if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER) 
		{
			//double gvt=SensorManager.STANDARD_GRAVITY;
			double xx = event.values[0];
			double yy = event.values[1];
			double zz = event.values[2];
			double aaa=Math.round(Math.sqrt(Math.pow(xx, 2) +Math.pow(yy, 2) +Math.pow(zz, 2)));
			boolean max = false;
			System.out.println(aaa);
			max = (aaa >= 20);
			if (max) 
			{
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
				alertDialog.setTitle("Se detectó una caída");
				alertDialog.setMessage("Se enviará un mensaje automatico a: " +instancia.darNombreContactoBP()+  ". Esta de acuerdo con esto?");
				alertDialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int which) 
					{ 
						enviarMensajeSMS(instancia.darTelefonoContactoBP(), instancia.darMensajeAEnviarBP());

					}
				});
				alertDialog.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int which) 
					{ 

					}
				});
				alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
				alertDialog.show();
				max=false;
				mSensorManager.unregisterListener(this);
				Thread thread = new Thread(new Runnable()
				{
					@Override
					public void run() 
					{
						try 
						{
							Thread.sleep(10000);
							iniciarListener();
						} 
						catch (InterruptedException e) 
						{
							e.printStackTrace();
						}

					}
				});
				thread.start();
			}
		}
	}

	private void iniciarListener()
	{
		if(!pausa)
			mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) 
	{

	}

	/**
	 * Metodo encargado de registrar audio de manera constante, al igual que de analizar el volumen del audio escuchado.
	 * Se calcula el nivel de decibeles que se escucha, y en caso de que sea demasiado alto (una persona gritando a una distancia de 1 mt)
	 * envia un msj de emergencia al cuidador 
	 */
	public void activarSonidoFuerte()
	{
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		mRecorder.setOutputFile("/dev/null");
		try 
		{
			mRecorder.prepare();
			mRecorder.start();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 

		Thread thread = new Thread(new Runnable()
		{
			double amplitud = 0;
			boolean noGritaron = true;
			@Override
			public void run() 
			{
				while(noGritaron)
				{
					if (mRecorder != null)
					{    
						amplitud =  (mRecorder.getMaxAmplitude());
					}

					System.out.println("Amplitud del sonido:"+amplitud);
					double amplitudEMA = 0;
					amplitudEMA = 0.6 * amplitud + (1.0 - 0.6)* amplitudEMA;

					double decibeles = 20 * Math.log10(amplitud/2700.0);

					System.out.println("decibeles:"+decibeles);
					if(decibeles >= 18)
					{
						System.out.println("Gritaron...");
						noGritaron = false;
						act.runOnUiThread(new Runnable() 
						{
							@Override
							public void run() 
							{
								enviarAlarmaSonido();
							}
						});
					}
					try 
					{
						Thread.sleep(500);
					} 
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();
	}

	public void detenerSonidoFuerte()
	{
		if (mRecorder != null) 
		{
			mRecorder.stop();       
			mRecorder.release();
		}
	}

	public void enviarAlarmaSonido()
	{
		detenerSonidoFuerte();
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("Se detectó una sonido demasiado fuerte");
		alertDialog.setMessage("Se enviará un mensaje automatico a: " +instancia.darNombreContactoBP()+  ". Esta de acuerdo con esto?");
		alertDialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{ 
				enviarMensajeSMS(instancia.darTelefonoContactoBP(), instancia.darMensajeAEnviarBP());
			}
		});
		alertDialog.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{ 

			}
		});
		alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
		alertDialog.show();
	}
}