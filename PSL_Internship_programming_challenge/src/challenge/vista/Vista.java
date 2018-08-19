package challenge.vista;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Vista {
	
	/**
	 * <p> Texto que contiene la ultima jugada ingresada por el usuario. </p>
	 * Formato de jugada: <br>
	 * X Y U <br>
	 * X Y M <br>
	 * donde X y Y representan numeros con las coordenadas x,y de la matriz del campo de juego. U y M representan la
	 * accion a ejecutar, sea hacer visible la celda o marcarla con bandera.   
	*/
	private String jugada;
	
	/**
	 * <p> Texto que contiene la configuracion de la matriz del campo de juego (x,y) y la cantidad de minas que se
	 * insertaran en el campo. </p>
	 * Formato del texto: <br>
	 * X Y Z <br>
	 * donde X y Y representan numeros con las coordenadas x,y de la matriz del campo de juego. y Z representa la cantidad
	 * de minas que se van a incluir en el juego
	*/
	private String  initialUserInput;
	
	/**
	 * <p> Texto que contiene el comando dado por el usuario al finalizar el juego, permite crear un nuevo juego
	 * o cerrar la aplicacion</p>
	 * Formato del texto: <br>
	 * y <br>
	 * n <br>
	 * Donde 'y' reinicia la aplicacion creando un nuevo juego y 'n' cierra el programa.
	*/
	private String command;
	
	/**
	 * <p> Hace referencia al lector que obtiene las entradas de texto digitadas por el usuario en la consola </p>
	*/
	private BufferedReader lectorUserInputs;
	
	
	/**
	 * <p> Constructor de la clase Vista, inicializa el lector con el que se adquiere la informacion que el usuario va a ingresar, 
	 * da la bienvenida al juego y le pide al usuario la configuracion inicial del juego. </p>
	*/
	public Vista() {
		lectorUserInputs = new BufferedReader(new InputStreamReader(System.in));			
		System.out.println("¡ Bienvenido al juego Minesweeper !");
		System.out.println("----------- ----------- -----------");
		pedirConfigInicial();
	}
	
	/**
	 * <p><b>pedirConfigInicial: </b> Indica al usuario que debe ingresar la configuracion del juego. Se indica el formato 
	 * correcto que se debe utilizar y llama al metodo <b> waitForInitialInput()</b> para esperar a que el usuario ingrese dicha configuracion.
	 * Si este metodo lanza la {@link IOException}, se indica al usuario que hubo un error leyendo la informacion y se indica de nuevo que ingrese
	 * la configuracion del juego indicandole el formato que debe utilizar. </p>
	 * Formato del texto: <br>
	 * X Y Z <br>
	 * donde X y Y representan numeros con las coordenadas x,y de la matriz del campo de juego. y Z representa la cantidad
	 * de minas que se van a incluir en el juego
	*/
	public void pedirConfigInicial() {
		try {
			System.out.println("Para empezar digite los parametros del juego: 'X Y Z' donde X y Y representan"
					+ "las dimensiones (x,y) del mapa de juego"+"\n"+"y Z representa el numero de minas que deseas incluir.");
			waitForInitialInput();
		} catch (IOException e) {
			System.out.println("ERROR LEYENDO LA ENTRADA DE DATOS.");
			pedirConfigInicial();
			
		}
	}
	
	/**
	 * <p><b>waitForJugada: </b> Espera a que el usuario ingrese las configuraciones iniciales del juego, 
	 * valida que dicha entrada tenga un formato valido comprobando que sea una entrada de digitos separados por espacio y que dichos tres digitos sean numeros
	 * posteriormente comprueba que el numero de minas sea menor que el numero de celdas del mapa de juego. </p>
	 * Formato del texto: <br>
	 * X Y Z <br>
	 * donde X y Y representan numeros con las coordenadas x,y de la matriz del campo de juego. y Z representa la cantidad
	 * de minas que se van a incluir en el juego 
	 * @throws IOException Error leyendo la informacion ingresada por el usuario.
	*/
	public void waitForInitialInput() throws IOException {
		boolean entradaValida = false;
		while(!entradaValida) {			
			System.out.println("----------- ----------- ----------- ----------- ----------- -----------");
			System.out.println("Solo una entrada con el formato correcto (X Y Z); donde X, Y y Z son numeros, sera valida.");
			String linea = lectorUserInputs.readLine();
			String entrada[] =linea.split(" ");
			if(entrada.length == 3) {
				if(this.isNumber(entrada[0]) &&  this.isNumber(entrada[1]) && this.isNumber(entrada[2])) {
					int numCeldas = Integer.parseInt(entrada[0]) * Integer.parseInt(entrada[1]);
					int minas = Integer.parseInt(entrada[2]);
					if(minas<numCeldas) {
						entradaValida = true;
						this.setInitialUserInput(linea);							
					}
				}
			}
		}
	}
	
	/**
	 * <p><b>printMapa: </b> Imprime el mapa del juego.</p> 
	 * @param mapa : Texto que contiene el estado del mapa de juego.
	*/
	public void printMapa(String mapa) {
		System.out.println("~~~~~~~~ MINESWEEPER MAP ~~~~~~~~");
		System.out.println(mapa);
	}
	
	/**
	 * <p><b>pedirJugada: </b> Indica al usuario que debe realizar una jugada y le muestra el formato correcto que debe utilizar. Luego espera a que el usuario digite su jugada,
	 *  en caso de que el metodo <b>waitForJugada() </b>lance la {@link IOException}</b>, se informa al usuario que hubo un error leyendo los datos y se 
	 *  vuelve a indicar que debe sumunistrar una jugada y se le indica el formato correcto que debe ingresar. </p>
	*/
	public void pedirJugada() {
		System.out.println("Ingresa tu jugada. Formato de la jugada:" + "\n"
				+ "X Y U \n" + 
				"X Y M\n" + 
				"donde X y Y representan numeros con las coordenadas x,y de la matriz del campo de juego. U y M representan la \n" + 
				"accion a ejecutar. U: hacer visible la celda. M: marcar la celda.   ");
		try {
			waitForJugada();
		} catch (IOException e) {
			System.out.println("ERROR LEYENDO LA ENTRADA DE DATOS.");
			pedirJugada();
		}
	}
	
	/**
	 * <p><b>waitForJugada: </b> Espera a que el usuario ingrese una jugada, valida dicha jugada comprobando que tenga un formato valido
	 * siguiendo el formato que se ha establecido para las jugadas y modifica el valor del atributo jugada.</p>
	 * Formato de jugada: <br>
	 * X Y U <br>
	 * X Y M <br>
	 * donde X y Y representan numeros con las coordenadas x,y de la matriz del campo de juego. U y M representan la
	 * accion a ejecutar, sea hacer visible la celda o marcarla con bandera.   
	 * @throws IOException Error leyendo la informacion ingresada por el usuario.
	*/
	public void waitForJugada() throws IOException {
		boolean entradaValida = false;
		while(!entradaValida) {			
			System.out.println("----------- ----------- ----------- ");
			System.out.println("Solo se aceptan jugadas validas");
			String linea = lectorUserInputs.readLine();
			String entrada[] =linea.split(" ");
			if(entrada.length == 3) {
				if(this.isNumber(entrada[0]) &&  this.isNumber(entrada[1]) && (entrada[2].equalsIgnoreCase("U")||entrada[2].equalsIgnoreCase("M"))) {
					int xMapa = Integer.parseInt(this.getInitialUserInput().split(" ")[0]);
					int yMapa = Integer.parseInt(this.getInitialUserInput().split(" ")[1]);
					int xEntry = Integer.parseInt(entrada[0]);
					int yEntry = Integer.parseInt(entrada[1]);
					if((xEntry<=xMapa) && (yEntry <= yMapa) && (xEntry > 0) && (yEntry > 0)) {
						entradaValida = true;
						this.setJugada(linea);							
					}
				}
			}
		}
	}
	
	/**
	 * <p><b>preguntarSalir: </b> Cuando el juego termina se pregunta al usuario si desea salir del juego y se almacena su respuesta en el atributo command.</p>
	*/
	public void preguntarSalir() {
		System.out.println("¿ Desea volver a jugar ? y/n");
		String linea;
		try {
			linea = lectorUserInputs.readLine();
			this.setCommand(linea);
		} catch (IOException e) {
			System.out.println("ERROR LEYENDO LA ENTRADA DE DATOS.");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * <p><b>printFinDelJuego: </b> Cuando el juego termina imprime el resultado del juego.</p> 
	 * @param resultado : Texto que contiene el resultado del juego (Juego Ganado o Juego Perdido).
	*/
	public void printFinDelJuego(String resultado) {
		System.out.println("~~~~~~ EL JUEGO HA TERMINADO ~~~~~~");
		System.out.println(resultado);
	}
	
	/**
	 * <p><b>printMs: </b>Imprime el texto recibido por parametro como salida para el usuario.</p> 
	 * @param text : Texto que se quiere imprimir.
	*/
	public void printMs(String text) {
		System.out.println("-: " + text);
	}
	
	/**
	 * <p><b>isNumber: </b>Indica si el String pasado por parametro contiene un numero</p> 
	 * @param entry : Texto del cual se quiere saber si contiene un numero.
	 * @return Verdadero o Falso:  <br>
	 * True = El parametro es un numero.  <br>
	 * False = El parametro no es un numero valido.
	*/
	public boolean isNumber(String entry) {
		boolean isNumber = false;
		try {
            Integer.parseInt(entry);
            isNumber = true;
        } catch (NumberFormatException excepcion) {
        	isNumber = false;
        }
		return isNumber;
	}
	
	/**
	 * <p><b>getEstado: </b>Getter de jugada, retorna el valor que tenga el atributo jugada. (ultima jugada del usuario) </p> 
	 * @return <b>(String) jugada: </b> texto con la ultima jugada del usuario. <br>
	*/
	public String getJugada() {
		return jugada;
	}

	/**
	 * <p><b>setJugada: </b>setter de jugada, modifica el valor del atributo.</p> 
	 * @param jugada : nuevo valor para el atributo jugada.
	 * @post el atributo jugada ha sido modificado.
	*/
	public void setJugada(String jugada) {
		this.jugada = jugada;
	}
	
	/**
	 * <p><b>getInitialUserInput: </b>Getter de initialUserInput, retorna el valor que tenga el atributo initialUserInput. (Configuracion del juego) </p> 
	 * @return <b>(String) initialUserInput: </b> texto con la configuracion del juego. <br>
	*/
	public String getInitialUserInput() {
		return initialUserInput;
	}

	/**
	 * <p><b>setInitialUserInput: </b>setter de initialUserInput, modifica el valor del atributo.</p> 
	 * @param initialUserInput : nuevo valor para el atributo initialUserInput.
	 * @post el atributo initialUserInput ha sido modificado.
	*/
	public void setInitialUserInput(String initialUserInput) {
		this.initialUserInput = initialUserInput;
	}

	/**
	 * <p><b>getCommand: </b>Getter de command, retorna el valor que tenga el atributo command. (Al final del juego indica si se quiere jugar de nuevo o salir del juego). </p> 
	 * @return <b>(String) command: </b> texto que indica si se quiere jugar de nuevo o salir del juego. <br>
	*/
	public String getCommand() {
		return command;
	}

	/**
	 * <p><b>setCommand: </b>setter de command, modifica el valor del atributo.</p> 
	 * @param command : nuevo valor para el atributo command.
	 * @post el atributo command ha sido modificado.
	*/
	public void setCommand(String command) {
		this.command = command;
	}
}
