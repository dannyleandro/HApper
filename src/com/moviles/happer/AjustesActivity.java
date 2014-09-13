package com.moviles.happer;

import com.moviles.mundo.HApper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class AjustesActivity extends ActionBarActivity
{
	/**
	 * Atributo que modela la instancia del mundo
	 */
	private HApper instancia;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ajustes);
		instancia = HApper.darInstancia(getApplicationContext());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
				getMenuInflater().inflate(R.menu.ajustes, menu);
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
	 * Metodo encargado de desplegar la vista para realizar los ajustes de una alarma
	 * @param v View
	 */
	public void verAjustesAlarmas(View v) 
	{
		Intent intent = new Intent(getApplicationContext(), AjustesAlarmasActivity.class);
		startActivity(intent);
	}
}
