package com.moviles.mundo;

import java.util.Date;
import java.util.Hashtable;

import android.content.Context;

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
	
	private BotonPanico bP;
	/**
	 * Instancia del manejador de la base de datos
	 */
<<<<<<< .merge_file_AFramz
	private SQLiteHelper sqliteHelper;
=======
	public HApper() 
	{
		super();
		alarmas = new ArrayList<Alarma>();
		bP = new BotonPanico("Danny", "5556","Ha ocurrido una emergencia");
	}
>>>>>>> .merge_file_lqFsON
	
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
	 */
	public HApper(Context context) 
	{
		super();
		sqliteHelper = new SQLiteHelper(context);
		alarmas = sqliteHelper.getAllAlarmas();
	}
	
	/**
	 * Devuelve la lista de los nombres de las alarmas
	 * @return arreglo de String con los nombres de las alarmas
	 */
	public Hashtable<Integer, Alarma> darAlarmas()
	{
		return alarmas;
	}
	
	/**
	 * Devuelve una alarma dado su nombre
	 * @param nomb String con el nombre de la alarma a buscar
	 * @return Alarma que corresponde con el nombre pasado en el parametro
	 */
	public Alarma darAlarma(int id)
	{
		return alarmas.get(id);
	}
	
	/**
	 * Metodo encargado de agregar la nueva alarma
	 * @param nomb Nombre de la alarma
	 * @param desc Descripcion de la alarma
	 * @param fecha Fecha de lanzamiento de la alarma incluyendo su hora
	 */
	public void agregarAlarma(String nomb, String desc, Date fecha)
	{
		Date feCre = new Date();
		long id = sqliteHelper.addAlarma(nomb, desc, fecha.getTime(), feCre.getTime());
		if(id >= 0)
			alarmas.put((int) id, new Alarma((int) id, nomb, desc, fecha, feCre));
	}

	/**
	 * Metodo encargado de modificar una alarma 
	 * @param id de la alarma que se va a modificar
	 * @param desc Descripcion nueva de la alarma
	 * @param nomb Nombre nuevo de la alarma
	 * @param fechaLan nueva fecha de lanzamiento
	 */
	public void modificarAlarma(int id, String nomb, String desc, Date fechaLan) 
	{
		Alarma al = alarmas.get(id);
		al.setNombre(nomb);
		al.setDescripcion(desc);
		al.setFechaLanzamiento(fechaLan);
		sqliteHelper.updateAlarma(id, nomb, desc, fechaLan.getTime());
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
	 * Metodo encargado de modificar las propiedades del botón de pánico
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
}
