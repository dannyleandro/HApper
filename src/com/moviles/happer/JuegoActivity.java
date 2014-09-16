package com.moviles.happer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class JuegoActivity extends ActionBarActivity 
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_juego);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{

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
	 * Muestra la actividad de las preguntas
	 * @param v
	 */
	public void verPreguntas(View v)
	{
		Intent intent = new Intent(getApplicationContext(), PreguntasActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Muestra la actividad de agregar una pregunta
	 * @param v
	 */
	public void verAgregarPregunta(View v)
	{
		Intent intent = new Intent(getApplicationContext(), AgregarPreguntaActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Muestra la actividad de las personas
	 * @param v
	 */
	public void verPersonas(View v)
	{
		Intent intent = new Intent(getApplicationContext(), PersonasActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Muestra la actividad de los lugares
	 * @param v
	 */
	public void verLugares (View v)
	{
		Toast.makeText(getApplicationContext(), "Aplicacion en construccion, disculpe las molestias", Toast.LENGTH_LONG).show();
	}
	
	/**
	 * Muestra la actividad de las objetos
	 * @param v
	 */
	public void verObjetos(View v)
	{
		Toast.makeText(getApplicationContext(), "Aplicacion en construccion, disculpe las molestias", Toast.LENGTH_LONG).show();
	}
}
