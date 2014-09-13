package com.moviles.happer;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.moviles.mundo.Alarma;
import com.moviles.mundo.HApper;

public class AlarmasActivity extends ActionBarActivity 
{
	/**
	 * Atributo que modela la instancia del mundo
	 */
	private HApper instancia;

	/**
	 * Lista que despliega las alarmas
	 */
	private ListView listaAlarmas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarmas);
		instancia = HApper.darInstancia(getApplicationContext()); 
		
		listaAlarmas = (ListView) findViewById(R.id.listaAlarmas);
		ArrayList<Alarma> alarmas = new ArrayList<Alarma>(instancia.darAlarmas().values());
		
		ArrayAdapter<Alarma> adapter = new ArrayAdapter<Alarma>(this, android.R.layout.simple_list_item_1, android.R.id.text1, alarmas);
		listaAlarmas.setAdapter(adapter);
		if(alarmas.size() == 0)
		{
			TextView tituloLista = (TextView) findViewById(R.id.txtTituloListaAlarmas);
			tituloLista.setText("No tiene alarmas, por favor agregue una nueva...");
		}
		listaAlarmas.setOnItemClickListener(new OnItemClickListener() 
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				@SuppressWarnings("unchecked")
				ArrayAdapter<Alarma> ad = (ArrayAdapter<Alarma>) parent.getAdapter();
				Alarma al = ad.getItem(position);
				
				Intent intent = new Intent(getApplicationContext(), DetalleAlarmaActivity.class);
				intent.putExtra("idAlarma", al.getId());
				startActivity(intent);
				System.out.println(position +": "+al.getId() + "-" + al.getNombre());
			}
		});
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
		
		ArrayList<Alarma> alarmas = new ArrayList<Alarma>(instancia.darAlarmas().values());
		ArrayAdapter<Alarma> adapter = new ArrayAdapter<Alarma>(this, android.R.layout.simple_list_item_1, android.R.id.text1, alarmas);
		listaAlarmas.setAdapter(adapter);
		TextView tituloLista = (TextView) findViewById(R.id.txtTituloListaAlarmas);
		if(alarmas.size() > 0)
			tituloLista.setText("Alarmas Agregadas");
		else
			tituloLista.setText("No tiene alarmas, por favor agregue una nueva...");
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
	
	/**
	 * Metodo encargado de desplegar la vista para agregar una nueva alarma 
	 * @param v View
	 */
	public void agregarAlarmas(View v) 
	{
		Intent intent = new Intent(getApplicationContext(), AgregarAlarmaActivity.class);
		startActivity(intent);
	}
}
