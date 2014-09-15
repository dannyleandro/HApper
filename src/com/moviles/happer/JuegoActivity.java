package com.moviles.happer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.moviles.mundo.HApper;

public class JuegoActivity extends ActionBarActivity 
{
	/**
	 * Atributo que modela la instancia del mundo
	 */
	private HApper instancia;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_juego);
		instancia = HApper.darInstancia(getApplicationContext()); 
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
		System.out.println(instancia);
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
	 * Muestra la actividad de configuracion
	 * @param v
	 */
	public void verConfiguraciones(View v)
	{
		Intent intent = new Intent(getApplicationContext(), ConfiguracionJuegoActivity.class);
		startActivity(intent);
	}
	
}
