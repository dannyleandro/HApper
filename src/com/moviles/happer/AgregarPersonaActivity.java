package com.moviles.happer;

import java.util.Date;

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
import android.widget.Toast;

import com.moviles.mundo.AlarmReceiver;
import com.moviles.mundo.HApper;

public class AgregarPersonaActivity extends Activity {
	
	/**
	 * Atributo que modela la instancia del mundo
	 */
	private HApper instancia;
	
//	/**
//	 * Atributo que modela el id de la persona
//	 */
//	private int idPersona;
	
	/**
	 * Atributo que modela el label para mostrar el nombre de la persona
	 */
	private TextView nomb;
	
	/**
	 *Atributo que modela la fecha de nacimiento de la persona 
	 */
	private DatePicker fecha;
	
	/**
	 *Atributo que modela la relacion de la persona con el usuario 
	 */
	private TextView rel;
	
	/**
	 *Atributo que modela el genero 
	 */
	private boolean gen;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agregar_persona);
		
		instancia = HApper.darInstancia(getApplicationContext());
		
		nomb = (EditText) findViewById(R.id.txtNombrePersona);
		rel = (EditText) findViewById(R.id.txtRelacionPersona);
		fecha = (DatePicker) findViewById(R.id.dpPersona);
		gen = false;
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.agregar_persona, menu);
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
	 * Maneja el evento de cancelar creacion de la persona
	 * @param v View
	 */
	public void cancelarAgregarPersona(View v)
	{
		finish( );
	}
	
	/**
	 * Maneja el evento de agregar una nueva persona
	 * @param v View
	 */
	@SuppressWarnings("deprecation")
	public void agregarPersona(View v) 
	{
		if(!nomb.getText().toString().equals("") && !rel.getText().toString().equals(""))
		{
			Date fechaNacimiento = new Date(fecha.getYear()-1900, fecha.getMonth(), fecha.getDayOfMonth());
			
			int id = instancia.agregarPersona(nomb.getText().toString(), fechaNacimiento, gen,rel.getText().toString());
			
			Intent intentPerson = new Intent(getApplicationContext(), AlarmReceiver.class);
			intentPerson.putExtra("idPersona", id);
			
			Toast.makeText(getApplicationContext(), "La persona se ha agregado correctamente", Toast.LENGTH_LONG).show();
			
			finish( );
		}
		else
		{
			showDialog("Valores vacíos", "Debe ingresar valores válidos para adicionar la alarma");
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
}
