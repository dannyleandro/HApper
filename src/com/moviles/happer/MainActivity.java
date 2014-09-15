package com.moviles.happer;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.moviles.mundo.HApper;


public class MainActivity extends ActionBarActivity {
	
	private HApper instancia;

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instancia = HApper.darInstancia(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
     * Muestra la actividad de alarmas 
     * @param v View
     */
    public void verAlarmas(View v) 
    {
    	Intent intent = new Intent(getApplicationContext(), AlarmasActivity.class);
		startActivity(intent);
	}
    
    /**
     * Muestra la actividad del juego 
     * @param v View vista gen�rica de carga de la actividad
     */
    public void verJuego(View v) 
    {
    	Intent intent = new Intent(getApplicationContext(), JuegoActivity.class);
		startActivity(intent);
	}
    
    /**
     * Muestra la actividad de los Ajustes
     * @param v View vista gen�rica de carga de la actividad
     */
    public void verAjustes(View v) 
    {
    	Intent intent = new Intent(getApplicationContext(), AjustesActivity.class);
		startActivity(intent);
	}
    
    public void activarBotonPanico(View v)
    {
     	new AlertDialog.Builder(this)
        .setTitle("Emergencia!!!")
        .setMessage("Se enviara un mensaje autom�tico a: " +instancia.darNombreContactoBP()+  ". Esta de acuerdo con esto?")
        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { 
                enviarMensajeSMS(instancia.darTelefonoContactoBP(), instancia.darMensajeAEnviarBP());
            }
         })
        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { 
                // do nothing
            }
         })
        .setIcon(android.R.drawable.ic_dialog_alert)
         .show();
    }
    
    protected void enviarMensajeSMS(String phoneNo, String message) 
    {
        try {
           SmsManager smsManager = SmsManager.getDefault();
           smsManager.sendTextMessage(phoneNo, null, message, null, null);
           Toast.makeText(getApplicationContext(), "SMS sent.",
           Toast.LENGTH_LONG).show();
        } 
        catch (Exception e) 
        {
        	Writer writer = new StringWriter();
        	PrintWriter printWriter = new PrintWriter(writer);
        	e.printStackTrace(printWriter);
        	String s = writer.toString();
 
        	showDialog("Error", s);
        
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
			public void onClick(DialogInterface dialog,int id) {
				
			}
		});
		AlertDialog dialog= alertDialog.create();
		dialog.show();	
	}
}