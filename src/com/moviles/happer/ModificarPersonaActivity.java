package com.moviles.happer;

import java.text.SimpleDateFormat;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.moviles.mundo.HApper;
import com.moviles.mundo.Persona;

public class ModificarPersonaActivity extends Activity {
	/**
	 * Atributo que modela la instancia del mundo
	 */
	private HApper instancia;
	
	/**
	 * Atributo que modela el id de la persona
	 */
	private int idPersona;
	
	
	/**
	 * Atributo que modela el label para mostrar el nombre de la persona
	 */
	private TextView nomb;
	
	/**
	 * Atributo que modela el DatePicker para ingresar la fecha de nacimiento de la persona
	 */
	private TextView fechaNacimiento;
	
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
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modificar_persona);
		instancia = HApper.darInstancia(getApplicationContext());
		
		Intent intent = getIntent();
		idPersona = intent.getIntExtra("idPersona", -1);
		Persona p = instancia.darPersona(idPersona);
		if(p == null)
		{	
			Toast.makeText(getApplicationContext(), "Ocurrió un problema, la persona no se puede modificar", Toast.LENGTH_LONG).show();
			finish( );
		}
		
		else
		{
			nomb = (EditText) findViewById(R.id.txtNombrePersona);
			nomb.setText(p.getNombre());
			rel = (EditText) findViewById(R.id.txtRelacion);
			rel.setText(p.getRelacion());
			fechaNacimiento = (TextView) findViewById(R.id.lblFechaNacimiento);
			fecha = (DatePicker) findViewById(R.id.dpPersona);
			fecha.updateDate(p.getFechaNacimiento().getYear() + 1900, p.getFechaNacimiento().getDate(), p.getFechaNacimiento().getDate());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.modificar_persona, menu);
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
	 * Metodo encargado de mostrar un mensaje
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
	 * Metodo encargado de guardar los cambios que se modificaron de la persona
	 * @param v View
	 */
	public void establecerPersona(View v)
	{
		if(!nomb.getText().toString().equals("") && !rel.getText().toString().equals(""))
		{
			@SuppressWarnings("deprecation")
			Date fechaNacimiento = new Date(fecha.getYear()-1900, fecha.getMonth(), fecha.getDayOfMonth());
			instancia.modificarPersona(idPersona, nomb.getText().toString(),fechaNacimiento, false,rel.getText().toString());
			Toast.makeText(getApplicationContext(), "La persona se ha modificado correctamente", Toast.LENGTH_LONG).show();
			Intent intent = new Intent(getApplicationContext(), DetallePersonaActivity.class);
			intent.putExtra("idPersona", idPersona);
			startActivity(intent);
		}
		else
		{
			showDialog("Valores vacíos", "Debe ingresar valores válidos para modificar la persona");
		}
	}
	
	/**
	 * Metodo encargado de cancelar la modificacion de la persona
	 * @param v
	 */
	public void cancelarModificacionPersona(View v)
	{
		finish();
	}
}
