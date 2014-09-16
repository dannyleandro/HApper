package com.moviles.happer;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.moviles.mundo.HApper;

public class AgregarPreguntaActivity extends Activity 
{
	/**
	 * Atributo que modela el cuadro de texto para ingresar el enunciado de la pregunta
	 */
	private EditText enun;
	
	/**
	 * Atributo que modela el cuadro de texto para ingresar la respuesta correcta de la alarma
	 */
	private EditText resp;
	
	/**
	 * Atributo que modela el cuadro de texto para ingresar la opcion 2 de la pregunta
	 */
	private EditText opc2;
	
	/**
	 * Atributo que modela el cuadro de texto para ingresar la opcion 3 de la pregunta
	 */
	private EditText opc3;
	
	/**
	 * Atributo que modela el cuadro de texto para ingresar la opcion 4 de la pregunta
	 */
	private EditText opc4;
		
	/**
	 * Atributo que modela la instancia del mundo
	 */
	private HApper instancia;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agregar_pregunta);
		
		instancia = HApper.darInstancia(getApplicationContext());
		enun = (EditText) findViewById(R.id.txtPregunta);
		resp = (EditText) findViewById(R.id.txtRespuestaCorrecta);
		opc2 = (EditText) findViewById(R.id.txtOpcion1);
		opc3 = (EditText) findViewById(R.id.txtOpcion2);
		opc4 = (EditText) findViewById(R.id.txtOpcion3);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.agregar_pregunta, menu);
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
	 * Agrega la pregunta
	 * @param v
	 */
	public void aceptar(View v)
	{
		if(!enun.getText().toString().equals("") && !resp.getText().toString().equals("") && !opc2.getText().toString().equals("") && !opc3.getText().toString().equals("") && !opc4.getText().toString().equals(""))
		{
			Random rand = new Random();
		    int randomNum = rand.nextInt(4);
		    switch (randomNum) 
		    {
				case 0:
					instancia.agregarPregunta(enun.getText().toString(), resp.getText().toString(), opc3.getText().toString(), opc4.getText().toString(), opc2.getText().toString(), '0');
					break;
				case 1:
					instancia.agregarPregunta(enun.getText().toString(), opc2.getText().toString(), resp.getText().toString(), opc4.getText().toString(), opc3.getText().toString(), '1');
					break;
				case 2:
					instancia.agregarPregunta(enun.getText().toString(), opc2.getText().toString(), opc3.getText().toString(), resp.getText().toString(), opc4.getText().toString(), '2');
					break;
				case 3:
					instancia.agregarPregunta(enun.getText().toString(), opc2.getText().toString(), opc3.getText().toString(), opc4.getText().toString(), resp.getText().toString(), '3');
					break;
				default:
					instancia.agregarPregunta(enun.getText().toString(), opc2.getText().toString(), opc3.getText().toString(), opc4.getText().toString(), resp.getText().toString(), '3');
					break;
			}
			Toast.makeText(getApplicationContext(), "La pregunta se agregó correctamente", Toast.LENGTH_LONG).show();
			finish( ); 
		}
		else
		{
			showDialog("Valores vacíos", "Debe ingresar valores válidos para adicionar la pregunta");
		}
	}
	
	/**
	 * Cancela la nueva pregunta
	 * @param v
	 */
	public void cancelar(View v)
	{
		finish();
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
