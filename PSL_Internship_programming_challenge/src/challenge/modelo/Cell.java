package challenge.modelo;

/**
 * Esta entidad representa a una celda presente en el campo de juego.
 * @author Juan C Swan
 */
public class Cell {
	
	/**
	 * <p> Número de minas que se encuentran adyacentes a esta celda. </p>
	*/
	private int minasAdyacentes;
	
	/**
	 * <p>Define si la celda contiene o no una mina. </p>
	 * True = Contiene mina. <br> 
	 * False = No contiene mina. 
	*/
	private boolean esMina;
	
	/**
	 * <p>Esta es una variable interna de control, es un número que varia entre 1 y 3.
	 * Define el estado actual de la celda.</p>
	 * 1 = Celda Oculta.  <br>
	 * 2 = Celda Marcada. <br>
	 * 3 = Celda Visible.  
	*/
	private int estado;
	
	
	/**
	 * Constructor de clase <br>
	 * Se inicializa la celda. El estado inicial de la celda es oculta (1). 
	 * @param esMina Indica si la celda contiene una mina.
	 * @param minasAdyacentes indica cuántas minas se encuentran adyacentes a la celda.
	 */
	public Cell(boolean esMina, int minasAdyacentes) {
		this.minasAdyacentes = minasAdyacentes;
		this.esMina = esMina;
		this.estado = 1;
	}


	/**
	 * <p><b>getMinasAdyacentes: </b>Getter de minasAdyacentes</p> 
	 * @return (int) Número que representa el valor del atributo minasAdyacentes
	*/
	public int getMinasAdyacentes() {
		return minasAdyacentes;
	}


	/**
	 * <p><b>setMinasAdyacentes: </b>setter de minasAdyacentes, modifica el valor del atributo</p> 
	 * @param minasAdyacentes : nuevo valor para el atributo de minasAdyacentes
	 * @post el atributo minasAdyacentes ha sido modificado
	*/
	public void setMinasAdyacentes(int minasAdyacentes) {
		this.minasAdyacentes = minasAdyacentes;
	}


	/**
	 * <p><b>isEsMina: </b>Getter de esMina.</p> 
	 * @return <b>(boolean) esMina: </b> Verdadero si la celda contiene una mina, falso de lo contrario.
	*/
	public boolean isEsMina() {
		return esMina;
	}


	/**
	 * <p><b>setEsMina: </b>setter de esMina, modifica el valor del atributo.</p> 
	 * @param esMina : nuevo valor para el atributo esMina.
	 * @post el atributo esMina ha sido modificado.
	*/
	public void setEsMina(boolean esMina) {
		this.esMina = esMina;
	}


	/**
	 * <p><b>getEstado: </b>Getter de estado</p> 
	 * @return <b>(int) estado: </b> Número que representa el valor del atributo estado <br>
	 * 1 = Celda Oculta.  <br>
	 * 2 = Celda Marcada. <br>
	 * 3 = Celda Visible.  
	*/
	public int getEstado() {
		return estado;
	}


	/**
	 * <p><b>setEstado: </b>setter de estado, modifica el valor del atributo</p> 
	 * @param estado : nuevo valor para el atributo estado <br>
	 * 1 = Celda Oculta.  <br>
	 * 2 = Celda Marcada. <br>
	 * 3 = Celda Visible.
	 * @post el atributo estado es modificado según el avance del juego.
	*/
	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	
	
}
