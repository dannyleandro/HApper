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
	
	/**
	 * Estructura de datos con las personas
	 */
	private Hashtable<Integer, Persona> personas;
	
	/**
	 * 
	 */
	private BotonPanico bP;
	
	/**
	 * Instancia del manejador de la base de datos
	 */
	private SQLiteHelper sqliteHelper;
	
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
		personas = sqliteHelper.getAllPersona();
		bP = new BotonPanico("Danny", "5556","Ha ocurrido una emergencia");
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
	 */
	public int agregarAlarma(String nomb, String desc, Date fecha)
	{
		Date feCre = new Date();
		long id = sqliteHelper.addAlarma(nomb, desc, fecha.getTime(), feCre.getTime());
		if(id >= 0)
			alarmas.put((int) id, new Alarma((int) id, nomb, desc, fecha, feCre));
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
	public int agregarPersona(String nomb, Date feNac, boolean esMale, String rel)
	{
		long id = sqliteHelper.addPersona(nomb, feNac.getTime(), (esMale ? 1 : 0) , rel);
		if(id >= 0)
			personas.put((int) id, new Persona((int) id, nomb, feNac, esMale, rel));
		return (int) id;
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
	public void eliminarPerosna(int id) 
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
}
