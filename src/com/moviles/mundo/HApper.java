package com.moviles.mundo;

public class HApper 
{
	//esrfafsvasfv con cambios
	/**
	 * Instancia del mundo
	 */
	private HApper instancia;
	
	/**
	 * Constructor del mundo que inicializa 
	 */
	public HApper() 
	{
		super();
	}
	
	public HApper darInstancia()
	{
		if(instancia == null)
			instancia = new HApper();
		return instancia;
	}
}
