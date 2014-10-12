package com.moviles.happer;

import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.moviles.mundo.Alarma;
import com.moviles.mundo.HApper;

public class DetalleAlarmaActivity extends Activity 
{
	/**
	 * Atributo que modela la instancia del mundo
	 */
	private HApper instancia;
	
	/**
	 * Atributo que modela el id de la alarma
	 */
	private int idAlarma;
	
	/**
	 * Atributo que modela el label para mostrar el nombre de la alarma
	 */
	private TextView nomb;
	
	/**
	 * Atributo que modela el label para mostrar la descripción de la alarma
	 */
	private TextView desc;
	
	/**
	 * Atributo que modela la imagen de la alarma
	 */
	private ImageView imagen;
	
	/**
	 * Atributo que modela el campo que muestra la fecha de creacion de la alarma
	 */
	private TextView fechaCreacion;
	
	/**
	 * Atributo que modela el campo que muestra la fecha de lanzamiento de la alarma
	 */
	private TextView fecha;
	
	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle_alarma);
		instancia = HApper.darInstancia(getApplicationContext());
		
		Intent intent = getIntent();
		idAlarma = intent.getIntExtra("idAlarma", -1);
		
		Alarma a = instancia.darAlarma(idAlarma);
		
		if(a == null)
		{	
			Toast.makeText(getApplicationContext(), "Ocurrió un problema, la alarma no se encuentra", Toast.LENGTH_LONG).show();
			finish( );
		}
		else
		{
			nomb = (TextView) findViewById(R.id.lblNombreAlarma);
			nomb.setText(a.getNombre());
			desc = (TextView) findViewById(R.id.lblDescripcion);
			desc.setText(a.getDescripcion());
			fechaCreacion = (TextView) findViewById(R.id.lblFechaCreacion);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
			fechaCreacion.setText(sdf.format(a.getFechaCreacion()));
			fecha = (TextView) findViewById(R.id.lblFechaLanzamiento);
			fecha.setText(sdf.format(a.getFechaLanzamiento()));
			imagen = (ImageView) findViewById(R.id.imgAlarma);
			inicializarImagen(a.getImagenUri());
		}
	}
	
	/**
	 * Metodo que construye la imagen a partir de la ruta
	 */
	public void inicializarImagen(Uri imgUri)
	{
		try 
		{
			ExifInterface exif = new ExifInterface(imgUri.getPath());
			int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) 
			{
			case ExifInterface.ORIENTATION_ROTATE_90:
				orientation = 90;
				break; 

			case ExifInterface.ORIENTATION_ROTATE_180:
				orientation = 180;
				break;

			case ExifInterface.ORIENTATION_ROTATE_270:
				orientation = 270;
				break;
			}
			Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
			Bitmap imageScaled = Bitmap.createScaledBitmap(bitmap, 250, 250 * bitmap.getHeight() / bitmap.getWidth(), false);
			if (orientation != 0 ) 
			{
				Matrix matrix = new Matrix();
				matrix.postRotate(orientation);
				Bitmap rotatedScaledImage = Bitmap.createBitmap(imageScaled, 0, 0, imageScaled.getWidth(), imageScaled.getHeight(),	matrix, true);
				imagen.setImageBitmap(rotatedScaledImage);
			}
			else 
				imagen.setImageBitmap(imageScaled);
		} 
		catch (Exception e) 
		{
			findViewById(R.id.txtImagen).setVisibility(View.GONE);
			imagen.setVisibility(View.GONE);
			e.printStackTrace();
			Toast.makeText(this, "La alarma no tiene imagen", Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalle_alarma, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent i = new Intent(this, SettingsActivity.class);
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Metodo encargado de manejar el botón aceptar en la vista de detalle de la alarma
	 * @param v View
	 */
	public void aceptarDetalleAlarma(View v)
	{
		Intent intent = new Intent(getApplicationContext(), AlarmasActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Metodo encargado de mostrar la ventana para modificar la alarma
	 * @param v View
	 */
	public void modificarAlarma(View v)
	{
		Intent intent = new Intent(getApplicationContext(), ModificarAlarmaActivity.class);
		intent.putExtra("idAlarma", idAlarma);
		startActivity(intent);
	}
	
	@Override
	public void onBackPressed() 
	{
	   Log.d("CDA", "onBackPressed Called");
	   Intent intent = new Intent(getApplicationContext(), AlarmasActivity.class);
		startActivity(intent);
	}
	
	public void eliminarAlarma(View v)
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("Eliminar Alarma");
		alertDialog.setCancelable(false);
		alertDialog.setMessage("¿Está seguro que desea eliminar la alarma?");
		alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
		alertDialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog,int id) 
			{
				instancia.eliminarAlarma(idAlarma);
				DetalleAlarmaActivity.this.finish();
			}
		});
		alertDialog.setNegativeButton(android.R.string.no, null);
		AlertDialog dialog = alertDialog.create();
		dialog.show();
	}
}
