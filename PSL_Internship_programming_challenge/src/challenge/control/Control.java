package challenge.control;

import challenge.modelo.MinesweeperGame;
import challenge.vista.Vista;

/**
 * Esta clase controla toda la ejecución del proyecto. Manejando el avance del juego
 * y la conexión entre la vista y el modelo.
 * @author Juan C Swan
 */
public class Control {
	/**
	 * <p> Conexión con la vista que hace interacción con el usuario. </p>
	*/
	private Vista vista;
	
	/**
	 * <p> Conexión con el modelo que controla la información de la aplicación. </p>
	*/
	private MinesweeperGame modelo;
	
	/**
	 * Constructor de la clase Control. <br>
	 * Ejecuta el metodo <b>ejecutarJuego</b> <br> 
	 */
	public Control() {
		ejecutarJuego();		
		vista.printMs("Cerrando App..");
	}
	
	/**
	 * <p><b> ejecutarJuego: </b> Se encarga de dar inicio al juego y crear la conexión con el modelo y la vista. Crea el modelo con 
	 * las configuraciones que el usuario entrega e inicia el juego. Una vez el juego termina, informa el resultado y consulta si se 
	 * desea seguir jugando; si esta respuesta es negativa termina su ejecución.</p>
	*/
	public void ejecutarJuego() {
		boolean salir = false;
		while(!salir) {
			vista = new Vista();
			String[] gameConfig = vista.getInitialUserInput().split(" ");
			int alto = Integer.parseInt(gameConfig[1]);
			int ancho = Integer.parseInt(gameConfig[0]);
			int numMinas = Integer.parseInt(gameConfig[2]);
			modelo = new MinesweeperGame(alto, ancho, numMinas);
			vista.printMapa(pintarMapa());
			boolean perdio = juegoEnCurso();
			if(!perdio) {
				vista.printFinDelJuego("Felicidades ! \n ¡Has ganado!");
			}else {
				vista.printFinDelJuego("Lo sentimos, has perdido :C");
			}
			vista.preguntarSalir();
			if(!vista.getCommand().equalsIgnoreCase("y")) {
				salir = true;
			}
		}
	}
	
	/**
	 * <p><b>juegoEnCurso: </b> Se encarga de controlar la evolución del juego. Pide al usuario una jugada y la procesa. Se detiene cuando el juego termina.</p>
	 * @return Valor verdadero o falso: <br>
	 * Verdadero : El usuario perdió el juego. <br>
	 * Falso : El usuario ganó el juego.
	*/
	public boolean juegoEnCurso() {
		boolean juegoContinua = true;
		boolean perdio = false;		
		while(juegoContinua) {
			vista.pedirJugada();
			String[] jugada = vista.getJugada().split(" ");
			int x = Integer.parseInt(jugada[1])-1;
			int y = Integer.parseInt(jugada[0])-1;			
			if(jugada[2].equalsIgnoreCase("M")) {
				ponerBandera(x, y);
			}else if(modelo.getCeldas()[x][y].isEsMina()) {
					perdio = true;
				}else if(modelo.getCeldas()[x][y].getEstado()!=3) {
						modelo.destaparMapa(x, y);
					}else {
						vista.printMs("La celda ya se encuentra desmarcada !");
					}
			
			//Controla si el juego ha terminado y el usuario perdió, si no ha perdido imprime el mapa en su estado actual y continua la siguiente ronda.
			if(perdio) {
				vista.printMapa(pintarMapaMostrandoMinas());
				juegoContinua = false;
			}else {				
				vista.printMapa(pintarMapa());
			}
			//Controla si el juego ha terminado y el usuario ganó.
			if((modelo.getBanderasCorrectas() == modelo.getMinas()) && (modelo.getBanderasCorrectas() == modelo.getBanderas())) {
				juegoContinua = false;
			}
		}
		return perdio;
	}
	
	/**
	 * <p><b>ponerBandera: </b> En la celda seleccionada por el usuario se posiciona una bandera, si esta celda ya se encontraba marcada entonces se retira la marca.</p>
	 * @param x : coordenada x de la matriz de celdas
	 * @param y : coordenada y de la matriz de celdas
	*/
	public void ponerBandera(int x, int y) {
		if(modelo.getCeldas()[x][y].getEstado()==2) {
			modelo.getCeldas()[x][y].setEstado(1);
			modelo.setBanderas(modelo.getBanderas()-1);
			if(modelo.getCeldas()[x][y].isEsMina()) {
				modelo.setBanderasCorrectas(modelo.getBanderasCorrectas()-1);
			}
		}else {
			modelo.getCeldas()[x][y].setEstado(2);
			modelo.setBanderas(modelo.getBanderas()+1);
			if(modelo.getCeldas()[x][y].isEsMina()) {
				modelo.setBanderasCorrectas(modelo.getBanderasCorrectas()+1);
			}
		}	
	}
	
	/**
	 * <p><b>pintarMapa: </b>  Pinta el estado actual del mapa de juego y las minas permanecen ocultas.</p>
	 * @return String que contiene el estado actual del mapa.
	*/
	public String pintarMapa() {
		String mapa = "";
		for (int i = 0; i < modelo.getCeldas().length; i++) {
			for (int j = 0; j <  modelo.getCeldas()[0].length; j++) {
				if(j == 0 && i == 0) {
					mapa ="  |   " + darEstadoCelda(i,j);				
				}else if(j==0 && i!=0) {
					mapa = mapa  + "  |   " +darEstadoCelda(i,j);
				}else {
					mapa = mapa + " " +darEstadoCelda(i,j);	
				}				
			}
			mapa = mapa + "   |"   + "\n";
		}
		return mapa;
	}
	
	/**
	 * <p><b>pintarMapaMostrandoMinas: </b>  Pinta el estado actual del mapa de juego pero muestra todas las minas que habian en el mapa. Este metodo se 
	 * utiliza cuando el juego ha terminado.</p>
	 * @return String que contiene el mapa pero deja visible las minas.
	*/
	public String pintarMapaMostrandoMinas() {
		String mapa = "";
		for (int i = 0; i < modelo.getCeldas().length; i++) {
			for (int j = 0; j <  modelo.getCeldas()[0].length; j++) {
				if(j == 0 && i == 0) {
					if(modelo.getCeldas()[i][j].isEsMina()) {
						mapa = "*";
					}else {
						mapa = darEstadoCelda(i, j);
					}
				}else if(j==0 && i!=0) {
					if(modelo.getCeldas()[i][j].isEsMina()) {
						mapa = mapa + "*";
					}else {
						mapa = mapa + darEstadoCelda(i, j);
					}
				}else {
					if(modelo.getCeldas()[i][j].isEsMina()) {
						mapa = mapa + " *";
					}else {
						mapa = mapa + " " +darEstadoCelda(i, j);
					}
				}				
			}
			mapa = mapa + "\n";
		}
		return mapa;
	}
	
	/**
	 * <p><b>darEstadoCelda: </b> Retorna el caracter de la celda que se imprime en el mapa de juego dependiendo de su estado actual: <br>
	 * 1 = Celda Oculta <br>
	 * 2 = Celda Marcada<br>
	 * 3 = Celda Visible</p>
	 * @param x : coordenada x de la matriz de celdas
	 * @param y : coordenada y de la matriz de celdas
	 * @return String que contiene el caracter de la celda dependiendo de su estado: <br>
	 * 1 : '.'<br>
	 * 2 : 'P'<br>
	 * 3 : '-' Si no tiene minas adyacentes de lo contrario imprime el # de minas adyacentes.
	*/
	public String darEstadoCelda(int x , int y) {
		String estado = "";
		switch (modelo.getCeldas()[x][y].getEstado()) {
		case 1:
			estado = ".";
			break;						
		case 2:
			estado = "P";
			break;
		case 3:
			if(modelo.getCeldas()[x][y].getMinasAdyacentes() == 0) {
				estado = "-";
			}else {
				estado = "" + modelo.getCeldas()[x][y].getMinasAdyacentes();
			}						
			break;
		}
		return estado;
	}

	
	public static void main(String[] args) {
		new Control();
	}
}
