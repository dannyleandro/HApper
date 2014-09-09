package com.moviles.mundo;

import java.util.Date;

public class Alarma 
{
	/**
	 * nombre de la alarma
	 */
	private String nombre;
	
	/**
	 * fecha de en la que la alarma será anunciada
	 */
	private Date fechaLanzamiento;
	
	/**
	 * fecha en la que la alarma se creó
	 */
	private Date fechaCreacion;
	
	/**
	 * descripción de la alarma
	 */
	private String descripción;
	
	/**
	 * Constructor de la alarma
	 * @param nom nombre de la alarma
	 * @param desc descripción de la alarma
	 * @param fechaLan fecha en la que será lanzada la alarma
	 */
	public Alarma(String nom, String desc, Date fechaLan) 
	{
		nombre = nom;
		descripción = desc;
		fechaLan = fechaLanzamiento;
		fechaCreacion = new Date();
	}
	
	/**
	 * Devuelve la descripción de la alarma
	 * @return String con la descripción
	 */
	public String getDescripción() 
	{
		return descripción;
	}
	
	/**
	 * Devuelve la fecha de creación de la alarma 
	 * @return Date con la fecha en la que se creó la alarma
	 */
	public Date getFechaCreacion() 
	{
		return fechaCreacion;
	}
	
	/**
	 * Devuelve la fecha en la que será lanzada la alarma
	 * @return Date con la fecha en la que será lanzada la alarma
	 */
	public Date getFechaLanzamiento() 
	{
		return fechaLanzamiento;
	}
	
	/**
	 * Devuelve el nombre de la alarma
	 * @return String con el nombre de la alarma
	 */
	public String getNombre() 
	{
		return nombre;
	}
	
	/**
	 * Modifica la descripción de la alarma
	 * @param descripción String con la nueva descripción
	 */
	public void setDescripción(String descripción) 
	{
		this.descripción = descripción;
	}
	
	/**
	 * Modifica la fecha de lanzamiento de la alarma
	 * @param fechaLanzamiento Date con la nueva fecha de lanzamiento
	 */
	public void setFechaLanzamiento(Date fechaLanzamiento) 
	{
		this.fechaLanzamiento = fechaLanzamiento;
	}
	
	/**
	 * Modifica el nombre de la alarma
	 * @param nombre String con el nuevo nombre de la alarma
	 */
	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}
}
