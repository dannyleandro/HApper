package com.moviles.mundo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.util.Log;

public class WifiReceiver extends BroadcastReceiver
{
	/**
	 * Manejador de la ubicación
	 */	
	private GPSTracker gps;

	/**
	 * maneja el geocodificador
	 */
	private Geocoder geocoder;

	@Override
	public void onReceive(Context context, Intent intent) 
	{
		final Context cont = context;
		geocoder = new Geocoder(context, Locale.getDefault());
		gps = new GPSTracker(context);
		NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
		if(info != null) 
		{
			if(info.isConnected()) 
			{
				Thread thread = new Thread(new Runnable()
				{
					@Override
					public void run() 
					{
						try 
						{
							SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(cont);
							URL url = new URL("http://happer.herokuapp.com/posiciones/" + prefs.getString("prefUltimaPosID", "-1"));
							HttpURLConnection conn = (HttpURLConnection) url.openConnection();
							conn.setRequestMethod("GET");
							conn.setRequestProperty("Accept", "application/json");
							String mensaje = prefs.getString("prefNombreUsuario", "Anonimo") + " se encontraba en ";
							if (conn.getResponseCode() != 200) 
							{
								System.out.println("Failed : HTTP error code : " + conn.getResponseCode());
							}
							else
							{
								BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

								System.out.println("Output from Server .... \n");
								String output;
								while((output=br.readLine()) != null) 
								{
									String [] resp = output.split("\"");
									mensaje +=resp[11];
									System.out.println(output);
								}
								System.out.println("Obtuvo datos del servidor");
							}
							mensaje += " y ahora esta en " + cambioUbicacion();
							System.out.println("http://happer.herokuapp.com/enviarMensaje/" + mensaje.trim().replaceAll(" ", "").replaceAll("á", "a"));
							url = new URL("http://happer.herokuapp.com/enviarMensaje/" + mensaje.replaceAll(" ", "+"));
							conn = (HttpURLConnection) url.openConnection();
							conn.setRequestMethod("GET");
							conn.setRequestProperty("Accept", "application/json");
							if (conn.getResponseCode() != 200) 
							{
								System.out.println("Failed : HTTP error code : " + conn.getResponseCode());
							}
							conn.disconnect();
						}
						catch (Exception e) 
						{
							e.printStackTrace();
						}
					}
				});
				thread.start();
			}

		}
	}
	public String cambioUbicacion()
	{
		if(gps.canGetLocation())
		{
			// Get the current location from the input parameter list
			List<Address> addresses = null;
			try 
			{
				System.out.println("latitud:" + gps.getLatitude() + ", Longitud: " + gps.getLongitude());
				addresses = geocoder.getFromLocation(gps.getLatitude(), gps.getLongitude(), 1);
			} 
			catch (IOException e1) 
			{
				Log.e("LocationSampleActivity", "IO Exception in getFromLocation()");
				e1.printStackTrace();
				return ("IO Exception trying to get address");
			} 
			catch (IllegalArgumentException e2) 
			{
				// Error message to post in the log
				String errorString = "Illegal arguments " + Double.toString(gps.getLatitude()) + " , " + Double.toString(gps.getLongitude()) + " passed to address service";
				Log.e("LocationSampleActivity", errorString);
				e2.printStackTrace();
				return errorString;
			}
			if (addresses != null && addresses.size() > 0) 
			{
				Address address = addresses.get(0);

				String addressText = String.format("%s, %s, %s", address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "", address.getMaxAddressLineIndex() > 1 ? address.getAddressLine(1) : "", address.getCountryName());

				return addressText;
			} 
			System.out.println("latitud:" + gps.getLatitude() + ", Longitud: " + gps.getLongitude());
			return "latitud:" + gps.getLatitude() + ", Longitud: " + gps.getLongitude();
		}
		else
		{
			return null;
		}
	}
}