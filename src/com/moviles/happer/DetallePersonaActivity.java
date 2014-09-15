package com.moviles.happer;

import com.moviles.mundo.HApper;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

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
	
	/**
	 *Atributo que modela el genero de la persona 
	 */
	private TextView genero;
	
	/**
	 *Atributo que modela la fecha de nacimiento de la persona 
	 */
	private TextView fechaNacimiento;
	
	/**
	 *Atributo que modela la relacion de la persona con el usuario 
	 */
	private TextView rel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle_persona);
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
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
