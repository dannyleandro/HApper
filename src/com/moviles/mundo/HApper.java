package com.moviles.mundo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

public class HApper 
{
	/**
	 * Instancia del mundo
	 */
	private static HApper instancia;

	/**
	 * Estructura de datos con las alarmas
	 */
	private Hashtable<Integer, Alarma> alarmas;

	/**
	 * Estructura de datos con las personas
	 */
	private Hashtable<Integer, Persona> personas;

	/**
	 * Estructura de datos con las preguntas
	 */
	private ArrayList<Pregunta> preguntas;

	/**
	 * Clase encargada de manejar el botón de pánico
	 */
	private BotonPanico bP;

	/**
	 * Instancia del manejador de la base de datos
	 */
	private SQLiteHelper sqliteHelper;

	/**
	 * Manejador de la ubicación
	 */	
	private GPSTracker gps;

	/**
	 * maneja el geocodificador
	 */
	private Geocoder geocoder;

	
	
	
	/**
	 * Devuelve la instancia del mundo
	 * @return instancia de HApper
	 */
	public static HApper darInstancia(Context context) 
	{
		if(instancia == null)
			instancia = new HApper(context);

		return instancia;
	}

	/**
	 * Constructor del mundo que inicializa 
	 * @param lm 
	 */
	public HApper(Context context) 
	{
		super();
		System.out.println("Empieza!!!!!!!!!!!!!!!!!!!!!!!");
		sqliteHelper = new SQLiteHelper(context);
		alarmas = sqliteHelper.getAllAlarmas();
		personas = sqliteHelper.getAllPersona();
		preguntas = new ArrayList<Pregunta>();
		bP = new BotonPanico("Hijo", "3138176004","Ha ocurrido una emergencia!");
		try 
		{			  
			BufferedReader br = new BufferedReader(new InputStreamReader(context.getResources().getAssets().open("preguntas.txt")));
			String linea = br.readLine();
			while( !linea.equals("**Fin preguntas**") )
			{
				String enunc = linea.split(";")[0];
				String opc1 = linea.split(";")[1];
				String opc2 = linea.split(";")[2];
				String opc3 = linea.split(";")[3];
				String opc4 = linea.split(";")[4];
				char resp = linea.split(";")[5].charAt(0);
				agregarPregunta(enunc, opc1, opc2, opc3, opc4, resp);
				linea = br.readLine();
			}
			br.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		geocoder = new Geocoder(context, Locale.getDefault());
		gps = new GPSTracker(context);
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (mWifi.isConnected()) 
		{
			conectarServidor(context);
		}
		
	}

	/**
	 * Devuelve la lista de las alarmas
	 * @return Hashtable de las alarmas
	 */
	public Hashtable<Integer, Alarma> darAlarmas()
	{
		return alarmas;
	}

	/**
	 * Devuelve la lista de las personas
	 * @return Hashtable de las personas
	 */
	public Hashtable<Integer, Persona> darPersonas()
	{
		return personas;
	}

	/**
	 * Devuelve una alarma dado su id
	 * @param id int con el id de la alarma a buscar
	 * @return Alarma que corresponde con el id pasado en el parametro
	 */
	public Alarma darAlarma(int id)
	{
		return alarmas.get(id);
	}

	/**
	 * Devuelve una alarma dado su id
	 * @param id int con el id de la alarma a buscar
	 * @return Persona que corresponde con el id pasado en el parametro
	 */
	public Persona darPersona(int id)
	{
		return personas.get(id);
	}

	/**
	 * Metodo encargado de agregar la nueva alarma
	 * @param nomb Nombre de la alarma
	 * @param desc Descripcion de la alarma
	 * @param fecha Fecha de lanzamiento de la alarma incluyendo su hora
	 * @param imgUri Uri de la imagen asociada a la alarma
	 */
	public int agregarAlarma(String nomb, String desc, Date fecha, Uri imgUri)
	{
		Date feCre = new Date();
		long id = sqliteHelper.addAlarma(nomb, desc, fecha.getTime(), feCre.getTime(), imgUri.toString());
		if(id >= 0)
			alarmas.put((int) id, new Alarma((int) id, nomb, desc, fecha, feCre, imgUri));
		return (int) id;
	}

	/**
	 * Metodo encargado de agregar una nueva persona
	 * @param nomb Nombre de la persona
	 * @param feNac Fecha de nacimiento de la persona
	 * @param esMale Genero de la persona, true si es masculino
	 * @param rel relacion con el usuario
	 * @return int id de la persona
	 */
	@SuppressWarnings("deprecation")
	public int agregarPersona(String nomb, Date feNac, boolean esMale, String rel)
	{
		agregarPregunta("¿En qué día cumple años " + nomb +  "?", "" + (feNac.getDate() + 1), "" + (feNac.getDate() - 1), "" + (feNac.getDate() - 2), "" + feNac.getDate(), '3');
		agregarPregunta("¿En qué año cumple " + nomb +  "?", "" + (feNac.getYear() + 1900), "" + (feNac.getYear() + 1899), "" + (feNac.getYear() +1901), "" + (feNac.getYear()+1902), '0');
		agregarPregunta("¿En qué mes cumple " + nomb +  "?", "" + (feNac.getMonth() + 1), "" + (feNac.getMonth() + 2), "" + (feNac.getMonth()), "" + (feNac.getMonth() + 3), '2');
		agregarPregunta("¿Qué relación tiene con " + nomb +  "?", "Amigo", rel, "Desconocido", "Conocido", '1');

		long id = sqliteHelper.addPersona(nomb, feNac.getTime(), (esMale ? 1 : 0) , rel);
		if(id >= 0)
			personas.put((int) id, new Persona((int) id, nomb, feNac, esMale, rel));
		return (int) id;
	}

	/**
	 * Devuelve de manera aleatoria una pregunta
	 * @return
	 */
	public Pregunta darSiguientePregunta()
	{
		Random rand = new Random();
		int randomNum = rand.nextInt(preguntas.size());
		return preguntas.get(randomNum);
	}

	/**
	 * Agrega una nueva pregunta
	 * @param enun
	 * @param opc1
	 * @param opc2
	 * @param opc3
	 * @param opc4
	 * @param resp
	 */
	public void agregarPregunta(String enun, String opc1, String opc2, String opc3, String opc4, char resp)
	{
		preguntas.add(new Pregunta(enun, opc1, opc2, opc3, opc4, resp));
	}

	/**
	 * Metodo encargado de modificar una alarma 
	 * @param id de la alarma que se va a modificar
	 * @param desc Descripcion nueva de la alarma
	 * @param nomb Nombre nuevo de la alarma
	 * @param fechaLan nueva fecha de lanzamiento
	 * @param imgUri Uri de la imagen asociada a la alarma
	 */
	public void modificarAlarma(int id, String nomb, String desc, Date fechaLan, Uri imgUri) 
	{
		Alarma al = alarmas.get(id);
		al.setNombre(nomb);
		al.setDescripcion(desc);
		al.setFechaLanzamiento(fechaLan);
		al.setImagenUri(imgUri);
		sqliteHelper.updateAlarma(id, nomb, desc, fechaLan.getTime(), imgUri.toString());
	}

	/**
	 * Metodo encargado de modificar una persona
	 * @param id de la persona a modificar
	 * @param nomb Nombre de la persona
	 * @param feNac Fecha de nacimiento de la persona
	 * @param esMale Genero de la persona, true si es masculino
	 * @param rel relacion con el usuario
	 */
	public void modificarPersona(int id, String nomb, Date feNac, boolean esMale, String rel) 
	{
		Persona p = personas.get(id);
		p.setNombre(nomb);
		p.setFechaNacimiento(feNac);
		p.setGenero(esMale);
		p.setRelacion(rel);
		sqliteHelper.updatePersona(id, nomb, feNac.getTime(), (esMale ? 1 : 0), rel);
	}

	/**
	 * Metodo que elimina una alarma
	 * @param id de la alarma a eliminar
	 */
	public void eliminarAlarma(int id) 
	{
		sqliteHelper.deleteAlarma(id);
		alarmas.remove(id);
	}

	/**
	 * Metodo que elimina una persona
	 * @param id de la persona a eliminar
	 */
	public void eliminarPersona(int id) 
	{
		sqliteHelper.deletePersona(id);
		personas.remove(id);
	}

	/**
	 * Metodo encargado de modificar las propiedades del bot�n de p�nico
	 * @param nNombre nuevo nombre
	 * @param nTelefono nuevo telefono
	 * @param nMensaje nuevo mensaje
	 */
	public void cambiarInfoBP(String nNombre, String nTelefono, String nMensaje)
	{
		bP.setNombreContacto(nNombre);
		bP.setTelefonoContacto(nTelefono);
		bP.setMensajeAEnviar(nMensaje);
	}

	public String darNombreContactoBP()
	{
		return bP.getNombreContacto();
	}

	public String darTelefonoContactoBP()
	{
		return bP.getTelefonoContacto();
	}

	public String darMensajeAEnviarBP()
	{
		return bP.getMensajeAEnviar();
	}

	public void conectarServidor(final Context context)
	{
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context); 
		String restoredText = prefs.getString("prefIdUsuario", "");
		System.out.println(restoredText);
		if (restoredText.equals("")) 
		{
			//hay que crear un nuevo usuario en el servidor
			crearNuevoUsuario(context);
		}
		Thread thread = new Thread(new Runnable()
		{
			@Override
			public void run() 
			{
				try 
				{
					SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
					URL url = new URL("http://happer.herokuapp.com/usuarios/" + prefs.getString("prefIdUsuario", "-1"));
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.setRequestProperty("Accept", "application/json");

					if (conn.getResponseCode() != 200) 
					{
						System.out.println("Failed : HTTP error code : " + conn.getResponseCode());	
						crearNuevoUsuario(context);
					}
					else
					{
						BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

						System.out.println("Output from Server .... \n");
						if(br.readLine() == null) 
						{
							crearNuevoUsuario(context);
						}
						System.out.println("Obtuvo datos del servidor");
						//Toast.makeText(getApplicationContext(), "Obtuvo datos del servidor.", Toast.LENGTH_LONG).show();
					}
					conn.disconnect();
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				//Enviar posiciones!
				ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				while (mWifi.isConnected()) 
				{
					try 
					{
						URL url = new URL("http://happer.herokuapp.com/posiciones/");
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						conn.setDoOutput(true);
						conn.setRequestMethod("POST");
						conn.setRequestProperty("Content-Type", "application/json");

						SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
						String input = "{\"fechaCreacion\":\"" + new Date().toString() + "\",\"usuario\":\"" + prefs.getString("prefIdUsuario", "-1")  + "\",\"descripcion\":\"" + cambioUbicacion() + "\",\"latitud\":" + gps.getLatitude() + ",\"longitud\":" + gps.getLongitude() +"}";
						System.out.println(input);
						OutputStream os = conn.getOutputStream();
						os.write(input.getBytes());
						os.flush();

						if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
							throw new RuntimeException("Failed : HTTP error code : "
									+ conn.getResponseCode());
						}

						BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

						SharedPreferences.Editor prefsEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
						String output;
						System.out.println("Output from Server .... \n");
						while ((output = br.readLine()) != null) 
						{
							String [] resp = output.split("\"");
							prefsEditor.putString("prefUltimaPosID", resp[resp.length-2]);
							prefsEditor.commit();
						}
						conn.disconnect();
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
					//duerme 10 seg
					try 
					{
						System.out.println("Espera 10 seg para volver a enviar");
						Thread.sleep(100000);
					} 
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();
	}

	private void crearNuevoUsuario(Context context)
	{
		try 
		{
			URL url = new URL("http://happer.herokuapp.com/usuarios/");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context); 
			String input = "{\"nombre\":\"" + prefs.getString("prefNombreUsuario", "Anónimo") + "\",\"nombreContacto\":\"" + prefs.getString("prefNombresContacto", "ContactoAnónimo")  + "\",\"numeroContacto\":" + prefs.getString("prefCelularContacto", "5555") +"}";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			SharedPreferences.Editor prefsEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) 
			{
				String [] resp = output.split("\"");
				prefsEditor.putString("prefIdUsuario", resp[resp.length-2]);
				prefsEditor.commit();
				System.out.println(output);	
			}
			conn.disconnect();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
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