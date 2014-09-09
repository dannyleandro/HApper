package com.moviles.happer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.moviles.mundo.HApper;

public class AlarmasActivity extends ActionBarActivity 
{
	private HApper instancia;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarmas);
		instancia = HApper.darInstancia();
		
		ListView listaAlarmas = (ListView) findViewById(R.id.listaAlarmas);
		String [] alarmas = instancia.darAlarmas();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, alarmas);
		listaAlarmas.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alarmas, menu);
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
	
	public void agregarAlarmas(View v) 
	{
		Intent intent = new Intent(getApplicationContext(), AgregarAlarmaActivity.class);
		startActivity(intent);
	}
}
