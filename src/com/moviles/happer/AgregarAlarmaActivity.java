package com.moviles.happer;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.moviles.mundo.AlarmReciever;
import com.moviles.mundo.HApper;

public class AgregarAlarmaActivity extends ActionBarActivity 
{
	/**
	 * Atributo que modela el cuadro de texto para ingresar el nombre de la alarma
	 */
	private EditText nomb;

	/**
	 * Atributo que modela el cuadro de texto para ingresar la descripción de la alarma
	 */
	private EditText desc;

	/**
	 * Atributo que modela la imagen de la alarma
	 */
	private ImageView imagen;

	/**
	 * Uri de la imagen seleccionada
	 */
	private Uri imgUri;

	/**
	 * Atributo que modela el DatePicker para ingresar la fecha de lanzamiento de la alarma
	 */
	private DatePicker fecha;

	/**
	 * 	Atributo que modela el TimePicker para ingresar la hora de lanzamiento de la alarma
	 */
	private TimePicker hora;

	/**
	 * Atributo que modela la instancia del mundo
	 */
	private HApper instancia;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agregar_alarma);

		instancia = HApper.darInstancia(getApplicationContext());

		nomb = (EditText) findViewById(R.id.txtNombreAlarma);
		desc = (EditText) findViewById(R.id.txtDescripcion);
		imagen = (ImageView) findViewById(R.id.imgAlarma);
		imagen.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				editarImagen();
			}
		});
		fecha = (DatePicker) findViewById(R.id.dpAlarma);
		hora = (TimePicker) findViewById(R.id.tpAlarma);
		imgUri = Uri.parse("");	
	}

	private void editarImagen()
	{
		// Determine Uri of camera image to save.
		final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "HApper" + File.separator);
		root.mkdirs();
		final String fname = "img_"+ System.currentTimeMillis() + ".jpg";
		final File sdImageMainDirectory = new File(root, fname);
		imgUri = Uri.fromFile(sdImageMainDirectory);

		// Camera.
		final List<Intent> cameraIntents = new ArrayList<Intent>();
		final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		final PackageManager packageManager = getPackageManager();
		final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
		for(ResolveInfo res : listCam) 
		{
			final String packageName = res.activityInfo.packageName;
			final Intent intent = new Intent(captureIntent);
			intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
			intent.setPackage(packageName);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
			cameraIntents.add(intent);
		}

		// Filesystem.
		final Intent galleryIntent = new Intent();
		galleryIntent.setType("image/*");
		galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

		// Chooser of filesystem options.
		final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

		// Add the camera options.
		chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));

		startActivityForResult(chooserIntent, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(resultCode == RESULT_OK && requestCode == 0)
		{
			final boolean isCamera;
			if(data == null)
				isCamera = true;
			else
			{
				final String action = data.getAction();
				if(action == null)
					isCamera = false;
				else
					isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			}
			if(!isCamera)
				imgUri = data == null ? null : data.getData();
			try 
			{
				ExifInterface exif = new ExifInterface(imgUri.getPath());
				int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
				switch (orientation) 
				{
				case ExifInterface.ORIENTATION_ROTATE_90:
					orientation = 90;
					break; 

				case ExifInterface.ORIENTATION_ROTATE_180:
					orientation = 180;
					break;

				case ExifInterface.ORIENTATION_ROTATE_270:
					orientation = 270;
					break;
				}

				Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
				Bitmap imageScaled = Bitmap.createScaledBitmap(bitmap, 250, 250 * bitmap.getHeight() / bitmap.getWidth(), false);
				if (orientation != 0 ) 
				{
					Matrix matrix = new Matrix();
					matrix.postRotate(orientation);
					Bitmap rotatedScaledImage = Bitmap.createBitmap(imageScaled, 0, 0, imageScaled.getWidth(), imageScaled.getHeight(),	matrix, true);
					imagen.setImageBitmap(rotatedScaledImage);
				}
				else 
					imagen.setImageBitmap(imageScaled);
				Toast.makeText(this, "Imagen agregada", Toast.LENGTH_SHORT).show();
			} 
			catch (Exception e) 
			{
				Toast.makeText(this, "Error al agregar la imagen", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			} 
		}
	}

	public static int getOrientation(Context context, Uri photoUri) 
	{
		/* it's on the external media. */
		Cursor cursor = context.getContentResolver().query(photoUri, new String[] { MediaStore.Images.ImageColumns.ORIENTATION }, null, null, null);

		if (cursor == null || cursor.getCount() != 1) 
			return -1;

		cursor.moveToFirst();
		return cursor.getInt(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.agregar_alarma, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent i = new Intent(this, SettingsActivity.class);
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Maneja el evento de agregar una nueva alarma
	 * @param v View
	 */
	@SuppressWarnings("deprecation")
	public void agregarAlarma(View v) 
	{
		if(!nomb.getText().toString().equals("") && !desc.getText().toString().equals(""))
		{
			Date fechaLanzamiento = new Date(fecha.getYear()-1900, fecha.getMonth(), fecha.getDayOfMonth(), hora.getCurrentHour(), hora.getCurrentMinute());

			int id = instancia.agregarAlarma(nomb.getText().toString(), desc.getText().toString(), fechaLanzamiento, imgUri);

			Intent intentAlarm = new Intent(getApplicationContext(), AlarmReciever.class);
			intentAlarm.putExtra("idAlarma", id);

			AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
			alarmManager.set(AlarmManager.RTC_WAKEUP, fechaLanzamiento.getTime(), PendingIntent.getBroadcast(getApplicationContext(), 1,  intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));

			Toast.makeText(getApplicationContext(), "La alarma se ha agregado correctamente", Toast.LENGTH_LONG).show();

			finish( );
		}
		else
		{
			showDialog("Valores vacíos", "Debe ingresar valores válidos para adicionar la alarma");
		}

	}

	/**
	 * Maneja el evento de cancelar la nueva alarma
	 * @param v View
	 */
	public void cancelarAgregarAlarma(View v)
	{
		finish( );
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
}
