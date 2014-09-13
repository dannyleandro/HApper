package com.moviles.mundo;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Santi
 *
 */
public class HApper 
{
	/**
	 * Instancia del mundo
	 */
	private static HApper instancia;
	
	private ArrayList<Alarma> alarmas;
	
	private BotonPanico bP;
	/**
	 * Constructor del mundo que inicializa 
	 */
	public HApper() 
	{
		super();
		alarmas = new ArrayList<Alarma>();
		bP = new BotonPanico("Danny", "3138176004","Ha ocurrido una emergencia");
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
	 * Devuelve una alarma dado su nombre
	 * @param nomb String con el nombre de la alarma a buscar
	 * @return Alarma que corresponde con el nombre pasado en el parametro
	 */
	public Alarma darAlarma(String nomb)
	{
		Alarma a = null;
		for (Alarma al : alarmas) 
		{
			if(al.getNombre().equals(nomb))
			{
				a = al;
				break;
			}
		}
		return a;
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

	public void modificarAlarma(String nombreAnt, String nombreNue, String desc, Date fechaLan) 
	{
		Alarma al = darAlarma(nombreAnt);
		al.setNombre(nombreNue);
		al.setDescripcion(desc);
		al.setFechaLanzamiento(fechaLan);
	}

	
	/**
	 * Metodo encargado de eliminar una alarma existente
	 * @param nomb String con el nombre de la alarma
	 */
	public void eliminarAlarma(String nomb) 
	{
		for (Alarma al : alarmas) 
		{
			if(al.getNombre().equals(nomb))
			{
				alarmas.remove(al);
				break;
			}
		}
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
