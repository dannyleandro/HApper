package com.moviles.happer;

import com.moviles.mundo.HApper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {
	
	private HApper instancia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        instancia = HApper.darInstancia();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
     * @param v View vista genérica de carga de la actividad
     */
    public void verJuego(View v) 
    {
    	Intent intent = new Intent(getApplicationContext(), JuegoActivity.class);
		startActivity(intent);
	}
    
    /**
     * Muestra la actividad de los Ajustes
     * @param v View vista genérica de carga de la actividad
     */
    public void verAjustes(View v) 
    {
    	Intent intent = new Intent(getApplicationContext(), AjustesActivity.class);
		startActivity(intent);
	}
    
    public void activarBotonPanico(View v)
    {
    	//instancia.cambiarInfoBP("Danny Hurtado","123456789","Ha ocurrido una emergencia");
    	//  
    	
    	new AlertDialog.Builder(this)
        .setTitle("Emergencia!!!")
        .setMessage("Se enviara un mensaje automático a: " +instancia.darNombreContactoBP()+  ". Esta de acuerdo con esto?")
        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { 
                // completar
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
}