package challenge.control;

import challenge.modelo.MinesweeperGame;
import challenge.vista.Vista;

/**
 * Esta clase controla toda la ejecución del proyecto. Manejando el avance del juego
 * y la conexión entre la vista y el modelo.
 * @author Juan C Swan
 */
public class Control {
	
	private Vista vista;
	private MinesweeperGame modelo;
	
	/**
	 * Constructor de la clase Control. <br>
	 * Inicializa sus atributos y envia la señal a la vista para que consulte al usuario las medidas del juego. <br> 
	 */
	public Control() {
		ejecutarJuego();		
		vista.printMs("Cerrando App..");
	}
	
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
	
//SI GANA  RETORTA false SI PIERDE RETORNA true
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
	
	
	public String pintarMapa() {
		String mapa = "";
		for (int i = 0; i < modelo.getCeldas().length; i++) {
			for (int j = 0; j <  modelo.getCeldas()[0].length; j++) {
				if(j == 0 && i == 0) {
					mapa = darEstadoCelda(i,j);				
				}else if(j==0 && i!=0) {
					mapa = mapa + darEstadoCelda(i,j);
				}else {
					mapa = mapa + " " +darEstadoCelda(i,j);	
				}				
			}
			mapa = mapa + "\n";
		}
		return mapa;
	}
	
	
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
