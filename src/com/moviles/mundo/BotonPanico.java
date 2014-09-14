package com.moviles.mundo;


/**
 * @author Santiago Arias Donato
 */

public class BotonPanico 
{
	/**
	 * Telefono de contacto  
	 */
	private String telefonoContacto;
	
	/**
	 * Nombre del contacto
	 */
	private String nombreContacto;
	
	/**
	 * Mensaje de texto a enviar
	 */
	private String mensajeAEnviar;
	
	/**
	 * Constructor del boton de panico
	 * @param nTelefonoContacto String con el telefono del contacto
	 * @param nMensajeAEnviar mensaje a encviar al contacto
	 * @param nNombreContacto nombre del contacto
	 */
	public BotonPanico( String nNombreContacto,String nTelefonoContacto, String nMensajeAEnviar) 
	{
		telefonoContacto = nTelefonoContacto;
		mensajeAEnviar = nMensajeAEnviar;
		nombreContacto = nNombreContacto;
	}
	
	/**
	 * Devuelve el telefono de contacto
	 * @return String con el numero de contacto
	 */
	public String getTelefonoContacto() 
	{
		return telefonoContacto;
	}
	
	/**
	 * Modifica el telefono de contacto
	 * @param nTelefono String con el nuevo telefono de contacto
	 */
	public void setTelefonoContacto(String nTelefono) 
	{
		telefonoContacto = nTelefono;
	}
	
	/**
	 * Devuelve el nombre del contacto
	 * @return String con el nombre de contacto
	 */
	public String getNombreContacto() 
	{
		return nombreContacto;
	}
	
	/**
	 * Modifica el nombre del contacto
	 * @param nombreContacto String con el nuevo nombre del contacto
	 */
	public void setNombreContacto(String nNombreContacto) 
	{
		nombreContacto = nNombreContacto;
	}
	
	/**
	 * Devuelve el mensaje a enviar
	 * @return String con el mensaje a enviar
	 */
	public String getMensajeAEnviar() 
	{
		return mensajeAEnviar;
	}
	
	/**
	 * Modifica el mensaje a enviar
	 * @param mensajeAEnviar String con el nuevo mensaje a enviar
	 */
	public void setMensajeAEnviar(String mensajeAEnviar) 
	{
		this.mensajeAEnviar = mensajeAEnviar;
	}

}
