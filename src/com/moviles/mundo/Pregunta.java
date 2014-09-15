package com.moviles.mundo;

public class Pregunta 
{
	/**
	 * Atributo que modela el enunciado de la pregunta
	 */
	private String enunciado;
	
	/**
	 * Atributo que modela la opcion 1 de la pregunta
	 */
	private String opcion1;
	
	/**
	 * Atributo que modela la opcion 2 de la pregunta
	 */
	private String opcion2;
	
	/**
	 * Atributo que modela la opcion 3 de la pregunta
	 */
	private String opcion3;
	
	/**
	 * Atributo que modela la opcion 4 de la pregunta
	 */
	private String opcion4;
	
	/**
	 * Atributo que modela la respuesta correcta a la pregunta, tiene valores de 0 a 3
	 */
	private char correcta;
	
	/**
	 * Constructor de la pregunta
	 * @param enun Enunciado de la pregunta
	 * @param opc1 Opcion 1 de la pregunta
	 * @param opc2 Opcion 2 de la pregunta
	 * @param opc3 Opcion 3 de la pregunta
	 * @param opc4 Opcion 4 de la pregunta
	 * @param resp Respuesta correcta de la pregunta, índice de 0 a 3
	 */
	public Pregunta(String enun, String opc1, String opc2, String opc3, String opc4, char resp) 
	{
		enunciado = enun;
		opcion1 = opc1;
		opcion2 = opc2;
		opcion3 = opc3;
		opcion4 = opc4;
		correcta = resp;
	}
	
	/**
	 * Devuelve el enunciado
	 * @return String con el enunciado de la pregunta
	 */
	public String getEnunciado() 
	{
		return enunciado;
	}
	
	/**
	 * Devuelve la opcion 1 de la pregunta
	 * @return String con la opcion 1
	 */
	public String getOpcion1() 
	{
		return opcion1;
	}
	
	/**
	 * Devuelve la opcion 2 de la pregunta
	 * @return String con la opcion 2
	 */
	public String getOpcion2() 
	{
		return opcion2;
	}
	
	/**
	 * Devuelve la opcion 3 de la pregunta
	 * @return String con la opcion 3
	 */
	public String getOpcion3() 
	{
		return opcion3;
	}
	
	/**
	 * Devuelve la opcion 4 de la pregunta
	 * @return String con la opcion 4
	 */
	public String getOpcion4() 
	{
		return opcion4;
	}
	
	/**
	 * Devuelve el numero de la respuesta correcta
	 * @return char con el numero de la respuesta correcta, de 0 a 3
	 */
	public char getCorrecta() 
	{
		return correcta;
	}
	
	/**
	 * Modifica el enunciado
	 * @param enunciado Stringo con el nuevo enunciado
	 */
	public void setEnunciado(String enunciado) 
	{
		this.enunciado = enunciado;
	}
	
	/**
	 * Modifica la opcion1
	 * @param opcion1 String con la nueva opcion 1
	 */
	public void setOpcion1(String opcion1) 
	{
		this.opcion1 = opcion1;
	}
	
	/**
	 * Modifica la opcion2
	 * @param opcion2 String con la nueva opcion 2
	 */
	public void setOpcion2(String opcion2) 
	{
		this.opcion2 = opcion2;
	}
	
	/**
	 * Modifica la opcion3
	 * @param opcion3 String con la nueva opcion 3
	 */
	public void setOpcion3(String opcion3) 
	{
		this.opcion3 = opcion3;
	}
	
	/**
	 * Modifica la opcion4
	 * @param opcion4 String con la nueva opcion 4
	 */
	public void setOpcion4(String opcion4) 
	{
		this.opcion4 = opcion4;
	}
	
	/**
	 * Modifica la respuesta correcta
	 * @param correcta char con el numero de la respuesta correcta, debe ser de 0 a 3
	 */
	public void setCorrecta(char correcta) 
	{
		this.correcta = correcta;
	}
}
