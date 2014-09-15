package com.moviles.mundo;

import java.util.Date;

public class Persona 
{
	/**
	 * identificacion de la persona en BD
	 */
	private int id;
	
	/**
	 * Nombre de la persona
	 */
	private String nombre;
	
	/**
	 * Fecha de nacimiento de la persona
	 */
	private Date fechaNacimiento;
	
	/**
	 * Genero de la persona, true si es masculino y false si es femenino
	 */
	private boolean masculino;
	
	/**
	 * Relacion de la persona con el usuario
	 */
	private String relacion;
	
	public Persona(int ident, String nomb, Date feNac, boolean esMale, String rel) 
	{
		id = ident;
		nombre = nomb;
		fechaNacimiento = feNac;
		masculino = esMale;
		relacion = rel;
	}
	
	public int getId() 
	{
		return id;
	}
	
	public String getNombre() 
	{
		return nombre;
	}
	
	public Date getFechaNacimiento() 
	{
		return fechaNacimiento;
	}
	
	public boolean isMasculino() 
	{
		return masculino;
	}
	
	public String getRelacion() 
	{
		return relacion;
	}
	
	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}
	
	public void setFechaNacimiento(Date fechaNacimiento) 
	{
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public void setGenero(boolean isMasculino) 
	{
		this.masculino = isMasculino;
	}
	
	public void setRelacion(String relacion) 
	{
		this.relacion = relacion;
	}
	
	
}
