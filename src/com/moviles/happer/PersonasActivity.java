package com.moviles.happer;

import java.util.ArrayList;

import com.moviles.mundo.HApper;
import com.moviles.mundo.Persona;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class PersonasActivity extends Activity 
{
	/**
	 * Atributo que modela la instancia del mundo
	 */
	private HApper instancia;

	/**
	 * Lista que despliega las Personas
	 */
	private ListView listaPersonas;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personas);
		
		instancia = HApper.darInstancia(getApplicationContext()); 
		
		listaPersonas = (ListView) findViewById(R.id.listaPersonas);
		
		ArrayList<Persona> personas = new ArrayList<Persona>(instancia.darPersonas().values());
		
		ArrayAdapter<Persona> adapter = new ArrayAdapter<Persona>(this, android.R.layout.simple_list_item_1, android.R.id.text1, personas);
		listaPersonas.setAdapter(adapter);
		
		if(personas.size() == 0)
		{
			TextView tituloLista = (TextView) findViewById(R.id.txtTituloListaPersonas);
			tituloLista.setText("No se encontraron personas, por favor agregue una nueva...");
		}
		listaPersonas.setOnItemClickListener(new OnItemClickListener() 
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				@SuppressWarnings("unchecked")
				ArrayAdapter<Persona> ad = (ArrayAdapter<Persona>) parent.getAdapter();
				Persona p = ad.getItem(position);
				
				Intent intent = new Intent(getApplicationContext(), DetallePersonaActivity.class);
				intent.putExtra("idPersona", p.getId());
				startActivity(intent);
				System.out.println(position +": "+p.getId() + "-" + p.getNombre());
			}
		});
	}

	@Override
	protected void onResume() 
	{
		super.onResume();
		
		ArrayList<Persona> personas = new ArrayList<Persona>(instancia.darPersonas().values());
		ArrayAdapter<Persona> adapter = new ArrayAdapter<Persona>(this, android.R.layout.simple_list_item_1, android.R.id.text1, personas);
		listaPersonas.setAdapter(adapter);
		TextView tituloLista = (TextView) findViewById(R.id.txtTituloListaPersonas);
		if(personas.size() > 0)
			tituloLista.setText("Personas Agregadas");
		else
			tituloLista.setText("No tiene personas, por favor agregue una nueva...");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.personas, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
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
	 * Despliega la vista para agregar una persona
	 * @param v
	 */
	public void agregarPersona(View v)
	{
		Intent intent = new Intent(getApplicationContext(), AgregarAlarmaActivity.class);
		startActivity(intent);	
	}
}
