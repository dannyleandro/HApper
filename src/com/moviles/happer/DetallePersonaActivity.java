package com.moviles.happer;

import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.moviles.mundo.HApper;
import com.moviles.mundo.Persona;

public class DetallePersonaActivity extends Activity 
{
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
	 * Atributo que modela el label para mostrar la descripcionn de la persona
	 */
	private TextView desc;
	
//	/**
//	 *Atributo que modela el genero de la persona 
//	 */
//	private TextView genero;
//	
//	/**
//	 *Atributo que modela la fecha de nacimiento de la persona 
//	 */
//	private TextView fechaNacimiento;
//	
//	/**
//	 *Atributo que modela la relacion de la persona con el usuario 
//	 */
//	private TextView rel;
	
	/**
	 * Atributo que modela el campo que muestra la fecha de lanzamiento de la persona
	 */
	private TextView fecha;
	
	@SuppressLint("SimpleDateFormat") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle_persona);
		instancia = HApper.darInstancia(getApplicationContext());
		
		Intent intent = getIntent();
		idPersona = intent.getIntExtra("idPersona", -1);
		
		Persona p = instancia.darPersona(idPersona);
		
		if(p == null)
		{	
			Toast.makeText(getApplicationContext(), "Ocurrió un problema, la persona no se encuentra", Toast.LENGTH_LONG).show();
			finish( );
		}
		else
		{
			nomb = (TextView) findViewById(R.id.lblNombrePersona);
			nomb.setText(p.getNombre());
			desc = (TextView) findViewById(R.id.lblRelacion);
			desc.setText(p.getRelacion());
			//fechaNacimiento = (TextView) findViewById(R.id.lblFechaNacimiento);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
			fecha = (TextView) findViewById(R.id.lblFechaNacimiento);
			fecha.setText(sdf.format(p.getFechaNacimiento()));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalle_persona, menu);
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
	 * Metodo encargado de manejar el botón aceptar en la vista de detalle de la persona
	 * @param v View
	 */
	public void aceptarDetallePersona(View v)
	{
		Intent intent = new Intent(getApplicationContext(), PersonasActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Metodo encargado de mostrar la ventana para modificar la alarma
	 * @param v View
	 */
	public void modificarPersona(View v)
	{
		Intent intent = new Intent(getApplicationContext(), ModificarPersonaActivity.class);
		intent.putExtra("idPersona", idPersona);
		startActivity(intent);
	}
	
	@Override
	public void onBackPressed() 
	{
	   Log.d("CDA", "onBackPressed Called");
	   Intent intent = new Intent(getApplicationContext(), AlarmasActivity.class);
		startActivity(intent);
	}
	
	public void eliminarPersona(View v)
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("Eliminar Persona");
		alertDialog.setCancelable(false);
		alertDialog.setMessage("Est� seguro que desea eliminar la persona?");
		alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
		alertDialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog,int id) 
			{
				instancia.eliminarPersona(idPersona);
				DetallePersonaActivity.this.finish();
			}
		});
		alertDialog.setNegativeButton(android.R.string.no, null);
		AlertDialog dialog = alertDialog.create();
		dialog.show();
	}
}
