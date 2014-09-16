package com.moviles.happer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.moviles.mundo.HApper;
import com.moviles.mundo.Pregunta;

public class PreguntasActivity extends Activity 
{
	/**
	 * Atributo que modela el cuadro de texto para ingresar el enunciado de la pregunta
	 */
	private TextView enun;
	
	/**
	 * Atributo que modela el cuadro de texto para ingresar la opcion 4 de la pregunta
	 */
	private Pregunta pregunta;
	
	/**
	 * Boton de la opcion 1
	 */
	private Button opc1;
	
	/**
	 * Boton de la opcion 2
	 */
	private Button opc2;
	
	/**
	 * Boton de la opcion 3
	 */
	private Button opc3;

	/**
	 * Boton de la opcion 4
	 */
	private Button opc4;
	
	/**
	 * Atributo que modela la instancia del mundo
	 */
	private HApper instancia;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preguntas);
		instancia = HApper.darInstancia(getApplicationContext());
		pregunta = instancia.darSiguientePregunta();
		enun = (TextView) findViewById(R.id.lblTitulo);
		enun.setText(pregunta.getEnunciado());
		opc1 = (Button) findViewById(R.id.btnOpcion1);
		opc1.setText(pregunta.getOpcion1());
		opc2 = (Button) findViewById(R.id.btnOpcion2);
		opc2.setText(pregunta.getOpcion2());
		opc3 = (Button) findViewById(R.id.btnOpcion3);
		opc3.setText(pregunta.getOpcion3());
		opc4 = (Button) findViewById(R.id.btnOpcion4);
		opc4.setText(pregunta.getOpcion4());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.preguntas, menu);
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
	 * Maneja la accion del boton de la opcion 1
	 * @param v
	 */
	public void opcion1(View v)
	{
		if(Character.getNumericValue(pregunta.getCorrecta()) == 0)
		{
			showDialog("Respuesta correcta", "Muy bien! has acertado en la respuesta");
		}
		else
		{
			showDialog("Respuesta incorrecta", "No has respondido correctamente.");
		}
	}
	/**
	 * Maneja la accion del boton de la opcion 2
	 * @param v
	 */
	public void opcion2(View v)
	{
		if(Character.getNumericValue(pregunta.getCorrecta()) == 1)
		{
			showDialog("Respuesta correcta", "Muy bien! has acertado en la respuesta");
		}
		else
		{
			showDialog("Respuesta incorrecta", "No has respondido correctamente.");
		}
	}
	/**
	 * Maneja la accion del boton de la opcion 3
	 * @param v
	 */
	public void opcion3(View v)
	{
		if(Character.getNumericValue(pregunta.getCorrecta()) == 2)
		{
			showDialog("Respuesta correcta", "Muy bien! has acertado en la respuesta.");
		}
		else
		{
			showDialog("Respuesta incorrecta", "No has respondido correctamente.");
		}
	}
	/**
	 * Maneja la accion del boton de la opcion 1
	 * @param v
	 */
	public void opcion4(View v)
	{
		if(Character.getNumericValue(pregunta.getCorrecta()) == 3)
		{
			showDialog("Respuesta correcta", "Muy bien! has acertado en la respuesta");
		}
		else
		{
			showDialog("Respuesta incorrecta", "No has respondido correctamente.");
		}
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
			public void onClick(DialogInterface dialog,int id) 
			{
				Intent intent = new Intent(getApplicationContext(), PreguntasActivity.class);
				startActivity(intent);
			}
		});
		AlertDialog dialog= alertDialog.create();
		dialog.show();	
	}
	
}
