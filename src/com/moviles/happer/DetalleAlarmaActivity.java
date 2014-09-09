package com.moviles.happer;

import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.moviles.mundo.Alarma;
import com.moviles.mundo.HApper;

public class DetalleAlarmaActivity extends Activity 
{
	/**
	 * Atributo que modela la instancia del mundo
	 */
	private HApper instancia;
	
	/**
	 * Atributo que modela el nombre de la alarma
	 */
	private String nombreAlarma;
	
	
	/**
	 * Atributo que modela el cuadro de texto para ingresar el nombre de la alarma
	 */
	private EditText nomb;
	
	/**
	 * Atributo que modela el cuadro de texto para ingresar la descripción de la alarma
	 */
	private EditText desc;
	
	/**
	 * Atributo que modela el campo que muestra la fecha de creacion de la alarma
	 */
	private TextView fechaCreacion;
	
	/**
	 * Atributo que modela el DatePicker para ingresar la fecha de lanzamiento de la alarma
	 */
	private DatePicker fecha;
	
	/**
	 * 	Atributo que modela el TimePicker para ingresar la hora de lanzamiento de la alarma
	 */
	private TimePicker hora;
	
	@SuppressLint("SimpleDateFormat") 
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle_alarma);
		instancia = HApper.darInstancia();
		
		Intent intent = getIntent();
		nombreAlarma = intent.getStringExtra("nombreAlarma");
		Alarma a = instancia.darAlarma(nombreAlarma);
		if(a == null)
		{	
			Toast.makeText(getApplicationContext(), "Ocurrió un problema, la alarma no se encuentra", Toast.LENGTH_LONG).show();
			finish( );
		}
		else
		{
			nomb = (EditText) findViewById(R.id.txtNombreAlarma);
			nomb.setText(nombreAlarma);
			desc = (EditText) findViewById(R.id.txtDescripcion);
			desc.setText(a.getDescripcion());
			fechaCreacion = (TextView) findViewById(R.id.lblFechaCreacion);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
			fechaCreacion.setText(sdf.format(a.getFechaCreacion()));
			fecha = (DatePicker) findViewById(R.id.dpAlarma);
			fecha.updateDate(a.getFechaLanzamiento().getYear(), a.getFechaLanzamiento().getMonth(), a.getFechaLanzamiento().getDate());
			hora = (TimePicker) findViewById(R.id.tpAlarma);
			hora.setCurrentHour(a.getFechaLanzamiento().getHours());
			hora.setCurrentMinute(a.getFechaLanzamiento().getMinutes());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalle_alarma, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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
	public void aceptarDetalleAlarma(View v)
	{
		finish();
	}
	public void modificarAlarma(View v)
	{
		showDialog("Modificar Alarma","se modificará la alarma");
	}
}
