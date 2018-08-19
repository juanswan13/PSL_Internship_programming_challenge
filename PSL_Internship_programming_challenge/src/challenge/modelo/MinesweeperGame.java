package challenge.modelo;

/**
 * Esta entidad representa la tabla de juego y contiene todas las celdas.
 * @author Juan C Swan
 */
public class MinesweeperGame {
	/**
	 * <p> Número de celdas que el usuario ha marcado. </p>
	*/
	private int minas;	
	
	/**
	 * <p> Número de celdas que el usuario ha marcado. </p>
	*/
	private int banderas;
	
	
	/**
	 * <p> Número de celdas que contienen mina y el usuario ha marcado. </p>
	*/
	private int banderasCorrectas;

	
	/**
	 * <p>Esta es una variable interna de control, es un número que varia entre 0 y 1 .
	 * Define el estado actual del juego.</p>
	 * 0 = Juego Activo.  <br>
	 * 1 = Juego Finalizado.
	*/
	private int estadoJuego;
	
	/**
	 * <p> Matriz de NxM que contiene las celdas del juego. </p>
	*/
	private Cell[][] celdas;
	
	/**
	 * <p> Entidad que representa la tabla de juego y contiene todas las celdas. Aquí se inicializan los atributos de la clase,
	 *  se llena el tablero de celdas, se agregan las minas al tablero y se carga toda la información de las celdas (se inicializa el juego).</p>
	 * @param alto : número de filas que tendrá la matriz del campo de juego
	 * @param ancho : número de columnas que tendrá la matriz del campo de juego
	 * @param numMinas : número de minas que se van a incluir en las celdas.
	 * @post El campo de juego es inicializado.
	 */
	public MinesweeperGame(int alto, int ancho, int numMinas) {
		this.celdas = new Cell[alto][ancho];
		this.minas = numMinas;
		inicializarCampoVacio();
		insertarMinas();
		for (int i = 0; i < celdas.length; i++) {
			for (int j = 0; j < celdas[0].length; j++) {
				llenarMinasAdyacentes(i,j,0);
			}
		}
		
	}
	
	/**
	 * Inicializa el campo de celdas sin minas <br>
	 */
	private void inicializarCampoVacio() {
		for (int i = 0; i < celdas.length; i++) {
			for (int j = 0; j < celdas[0].length; j++) {
				Cell celda = new Cell(false,0);
				celdas[i][j] = celda;
			}
		}
	}
	
	/**
	 * Inserta las minas de forma aleatoria en la matriz de celdas <br>
	 * @pre la clase se ha inicializado: minas ya tiene el valor númerico de cuantas celdas llevan minas
	 * y la matriz celdas ya se encuentra inicializada.
	 * @post Las minas son insertadas al campo de juego de forma aleatoria.
	 */
	private void insertarMinas() {
		int i = 0;
		while(i<this.getMinas()) {		
			int x = (int) (Math.random() * this.celdas.length);
			int y = (int) (Math.random() * this.celdas[0].length);
			
			if(celdas[x][y].isEsMina() == false) {
				Cell bomb = new Cell(true,0);
				celdas[x][y] = bomb;
				
				i++;
			}
		}
	}
	
	/**
	 * Inserta el valor de minasAdyacentes a las celdas <br>
	 * @pre La matriz de celdas ya se encuentra inicializada.<br>
	 * Las minas ya han sido ingresadas.
	 * @param x : coordenada x de la matriz de celdas
	 * @param y : coordenada y de la matriz de celdas
	 * @param llamado : Inidica desde dónde fue llamado el metodo <br>
	 * &nbsp;&nbsp;&nbsp; 0 : Llamado desde un metodo diferente. <br>
	 * &nbsp;&nbsp;&nbsp; 1 : Llamado desde el mismo metodo (llamado recursivo)
	 * @post A las celdas del mapa se les agrega el número de minas adyacentes.
	 */
	private void llenarMinasAdyacentes(int x , int y, int llamado) {
		if((x>=0 && y >=0) && (x<celdas.length && y<celdas[0].length) && (celdas[x][y].isEsMina()) && llamado == 0) {	
			llenarMinasAdyacentes(x,y+1, 1);
			llenarMinasAdyacentes(x-1,y+1, 1);
			llenarMinasAdyacentes(x+1,y+1, 1);
			llenarMinasAdyacentes(x-1,y, 1);
			llenarMinasAdyacentes(x+1,y, 1);
			llenarMinasAdyacentes(x,y-1, 1);
			llenarMinasAdyacentes(x-1,y-1, 1);
			llenarMinasAdyacentes(x+1,y-1, 1);		
		}else if((x>=0 && y >=0) && (x<celdas.length && y<celdas[0].length) && llamado == 1){
			celdas[x][y].setMinasAdyacentes(celdas[x][y].getMinasAdyacentes()+1);
		}
	}
	
	/**
	 * Desmarcar las celdas correctas cuando el usuario digita una jugada. -metodo recursivo-<br>
	 * @pre La matriz de celdas ya se encuentra inicializada.<br>
	 * Las minas ya han sido ingresadas.
	 * las celdas ya cuentan con el atributo minasAdyacentes
	 * @param x : coordenada x de la matriz de celdas
	 * @param y : coordenada y de la matriz de celdas
	 * @post Se cambia el estado de las celdas a visible siguiendo el patron del juego buscaminas: <br>
	 * Si el usuario elige una celda sin minas adyancentes cambia a visible ella y sus adyacentes, si sus adyacentes no tienen minas adyacentes se repite el proceso.
	 */
	public void destaparMapa(int x , int y) {
		if((x>=0 && y >=0) && (x<celdas.length && y<celdas[0].length) && (!celdas[x][y].isEsMina()) && celdas[x][y].getEstado() != 3) {
			celdas[x][y].setEstado(3);
			if(celdas[x][y].getMinasAdyacentes()==0) {
				destaparMapa(x,y+1);
				destaparMapa(x-1,y+1);
				destaparMapa(x+1,y+1);
				destaparMapa(x-1,y);
				destaparMapa(x+1,y);
				destaparMapa(x,y-1);
				destaparMapa(x-1,y-1);
				destaparMapa(x+1,y-1);
			}
		}
	}
		
	/**
	 * <p><b>getMinas: </b>Getter de minas</p> 
	 * @return (int) Número que representa el valor del atributo minas: cantidad de minas presentes en el juego.
	*/
	public int getMinas() {
		return minas;
	}
	
	/**
	 * <p><b>getBanderas: </b>Getter de banderas</p> 
	 * @return (int) Número que representa el valor del atributo banderas: cantidad de celdas marcadas por el usuario.
	*/
	public int getBanderas() {
		return banderas;
	}
	
	/**
	 * <p><b>setBanderas: </b>setter de banderas, modifica el valor del atributo</p> 
	 * @param banderas : nuevo valor para el atributo de banderas
	 * @post El atributo banderas ha sido modificado, resultado de que el usuario haya agregado o eliminado una celda marcada.
	*/
	public void setBanderas(int banderas) {
		this.banderas = banderas;
	}
	
	/**
	 * <p><b>getBanderasCorrectas: </b>Getter de banderasCorrectas</p> 
	 * @return (int) Número que representa el valor del atributo banderasCorrectas: cantidad de celdas que contienen una mina y han sido marcadas por el usuario.
	*/
	public int getBanderasCorrectas() {
		return banderasCorrectas;
	}

	/**
	 * <p><b>setBanderasCorrectas: </b>setter de banderasCorrectas, modifica el valor del atributo</p> 
	 * @param banderasCorrectas : nuevo valor para el atributo de banderasCorrectas
	 * @post El atributo banderas ha sido modificado, resultado de que el usuario haya marcado o desmarcado alguna celda que contenia una mina.
	*/
	public void setBanderasCorrectas(int banderasCorrectas) {
		this.banderasCorrectas = banderasCorrectas;
	}

	/**
	 * <p><b>getEstadoJuego: </b>Getter de estadoJuego</p> 
	 * @return (int) Número que representa el valor del atributo estadoJuego: <br>
	 * 0 = Juego Activo.  <br>
	 * 1 = Juego Finalizado.
	*/
	public int getEstadoJuego() {
		return estadoJuego;
	}
	
	/**
	 * <p><b>setEstadoJuego: </b>setter de estadoJuego, modifica el valor del atributo</p> 
	 * @param estadoJuego : nuevo valor para el atributo de estadoJuego
	 * @post El atributo estadoJuego ha sido modificado, resultado de que el juego haya finalizado.
	*/
	public void setEstadoJuego(int estadoJuego) {
		this.estadoJuego = estadoJuego;
	}

	/**
	 * <p><b>getCeldas: </b>Getter de celdas</p> 
	 * @return Matriz de NxM que contiene las celdas del mapa de juego.
	*/
	public Cell[][] getCeldas() {
		return celdas;
	}
}
