package com.moviles.mundo;

import java.util.ArrayList;
import java.util.Date;

public class HApper 
{
	/**
	 * Instancia del mundo
	 */
	private static HApper instancia;
	
	private ArrayList<Alarma> alarmas;
	
	/**
	 * Constructor del mundo que inicializa 
	 */
	public HApper() 
	{
		super();
		alarmas = new ArrayList<Alarma>();
	}
	
	/**
	 * Devuelve la instancia del mundo
	 * @return instancia de HApper
	 */
	public static HApper darInstancia() 
	{
		if(instancia == null)
			instancia = new HApper();
		return instancia;
	}
	
	/**
	 * Devuelve la lista de los nombres de las alarmas
	 * @return arreglo de String con los nombres de las alarmas
	 */
	public String [] darAlarmas() 
	{
		String [] listaAlarmas = new String[alarmas.size()];
		for (int i = 0; i < alarmas.size(); i++) 
		{
			listaAlarmas[i] = alarmas.get(i).getNombre();
		}
		return listaAlarmas;
	}
	
	/**
	 * Metodo encargado de agregar la nueva alarma
	 * @param nomb Nombre de la alarma
	 * @param desc Descripcion de la alarma
	 * @param fecha Fecha de lanzamiento de la alarma incluyendo su hora
	 */
	public void agregarAlarma(String nomb, String desc, Date fecha)
	{
		alarmas.add(new Alarma(nomb, desc, fecha));
	}
}
