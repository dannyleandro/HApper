package com.moviles.happer;

import java.util.Date;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

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
		
		instancia = HApper.darInstancia();
		
		nomb = (EditText) findViewById(R.id.txtNombreAlarma);
		desc = (EditText) findViewById(R.id.txtDescripcion);
		fecha = (DatePicker) findViewById(R.id.dpAlarma);
		hora = (TimePicker) findViewById(R.id.tpAlarma);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
			return true;
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
			System.out.println(fecha.getDayOfMonth() + "/" + fecha.getMonth() + "/" + fecha.getYear());
			System.out.println(hora.getCurrentHour() + ":" + hora.getCurrentMinute());
			Date fechaLanzamiento = new Date(fecha.getYear()-1900, fecha.getMonth(), fecha.getDayOfMonth(), hora.getCurrentHour(), hora.getCurrentMinute());
			System.out.println(fecha.getYear()+" - 1900 = " + (fecha.getYear()-1900));
			instancia.agregarAlarma(nomb.getText().toString(), desc.getText().toString(), fechaLanzamiento);
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
