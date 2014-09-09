package com.moviles.mundo;

public class HApper 
{
	/**
	 * Instancia del mundo
	 */
	private static HApper instancia;
	
	/**
	 * Constructor del mundo que inicializa 
	 */
	public HApper() 
	{
		super();
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
	
	public String [] darAlarmas() 
	{
		String [] listaAlarmas = {"Está","Vivo","Esto!!!"};
		
		return listaAlarmas;
	}
}
