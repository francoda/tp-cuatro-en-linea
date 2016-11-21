package juego;

/**
 * Juego Cuatro en Línea
 * 
 * Reglas:
 * 
 * ...
 *
 */
public class CuatroEnLinea {

	private Casillero[][] tablero;
	private boolean esPrimerJugador = true;
	private String jugadorRojo;
	private String jugadorAmarillo;
	private String nombreGanador = null;
	private boolean ganador = false;
	private int[] ultimoCasillero = { 0, 0 };

	/**
	 * pre : 'filas' y 'columnas' son mayores o iguales a 4. post: empieza el
	 * juego entre el jugador que tiene fichas rojas, identificado como
	 * 'jugadorRojo' y el jugador que tiene fichas amarillas, identificado como
	 * 'jugadorAmarillo'. Todo el tablero esta¡ vacio.
	 * 
	 * @param filas
	 *            : cantidad de filas que tiene el tablero.
	 * @param columnas
	 *            : cantidad de columnas que tiene el tablero.
	 * @param jugadorRojo
	 *            : nombre del jugador con fichas rojas.
	 * @param jugadorAmarillo
	 *            : nombre del jugador con fichas amarillas.
	 */
	public CuatroEnLinea(int filas, int columnas, String jugadorRojo,
			String jugadorAmarillo) {

		if (filas < 4 || columnas < 4) {
			throw new Error("Medidas de tablero incorrectas");
		}

		if (filas > 8 || columnas > 16) {
			throw new Error("Medidas de tablero incorrectas");
		}

		if (jugadorRojo.equals("") || jugadorAmarillo.equals("")) {
			throw new Error("Ambos jugadores deben tener un nombre");
		}

		tablero = new Casillero[filas][columnas];

		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {
				tablero[i][j] = Casillero.VACIO;
			}
		}

		this.jugadorRojo = jugadorRojo;
		this.jugadorAmarillo = jugadorAmarillo;
		
	}

	/**
	 * post: devuelve la cantidad maxima de fichas que se pueden apilar en el
	 * tablero.
	 */
	public int contarFilas() {
		return tablero.length;
	}

	/**
	 * post: devuelve la cantidad maxima de fichas que se pueden alinear en el
	 * tablero.
	 */
	public int contarColumnas() {
		return tablero[0].length;
	}

	/**
	 * pre : fila esta¡ en el intervalo [1, contarFilas()], columnas esta¡ en el
	 * intervalo [1, contarColumnas()]. post: indica que ocupa el casillero en
	 * la posicion dada por fila y columna.
	 * 
	 * @param fila
	 * @param columna
	 */
	public Casillero obtenerCasillero(int fila, int columna) {
		return tablero[fila - 1][columna - 1];
	}

	/**
	 * pre : el juego no termina, columna esta¡ en el intervalo [1,
	 * contarColumnas()] y aún queda uxn Casillero.VACIO en la columna indicada.
	 * post: deja caer una ficha en la columna indicada.
	 * 
	 * @param columna
	 */
	public void soltarFicha(int columna) {
		int ultimaFilaVacia = tablero.length - 1;
		columna--;

		// Cambia la primera ficha vacia en 'columna' si el juego aun no
		// termina
		if (!termino()) {

			// Busco desde abajo hasta arriba la fila vacia
			while (ultimaFilaVacia > 0
					&& tablero[ultimaFilaVacia][columna] != Casillero.VACIO) {
				ultimaFilaVacia--;
			}

			// Cambio el casillero si esta vacio
			if (ultimaFilaVacia >= 0 && tablero[0][columna] == Casillero.VACIO) {
				if (esPrimerJugador) {
					tablero[ultimaFilaVacia][columna] = Casillero.ROJO;
				} else {
					tablero[ultimaFilaVacia][columna] = Casillero.AMARILLO;
				}

				// Guardo el ultimo casillero y cambio de jugador
				ultimoCasillero[0] = ultimaFilaVacia;
				ultimoCasillero[1] = columna;

				// Si el juego no termina con la ficha colocada, cambio el
				// jugador
				if (!termino()) {
					esPrimerJugador = !esPrimerJugador;
				}
			}
		}
	}

	/**
	 * post: indica si el juego termina porque uno de los jugadores gana o no
	 * existen casilleros vacios.
	 */
	public boolean termino() {
		int i = 0;
		boolean finalizo = false;

		// verifica la ultima fila por empate
		while (i < tablero[0].length && tablero[0][i] != Casillero.VACIO) {
			if (i == tablero[0].length - 1) {
				finalizo = !finalizo;
			}
			i++;
		}

		// verifica si hay 4 en linea en Fila, Columna, DiagonalAsendente,
		// DiagonalDesendente
		if (!finalizo && hayCuatroEnLinea(obtenerFila())) {
			finalizo = !finalizo;
		} else if (!finalizo && hayCuatroEnLinea(obtenerColumna())) {
			finalizo = !finalizo;
		} else if (!finalizo && verificarDiagonales()) {
			finalizo = !finalizo;
		}

		return finalizo;
	}

	/**
	 * post: indica si se realizo un 4 en linea en los casilleros pasados.
	 */
	private boolean hayCuatroEnLinea(Casillero[] casilleros) {

		// Se inicializan variables
		boolean hayCuatroEnLinea = false;
		Casillero ultimoEstado = casilleros[0];
		int contador = 0;
		int i = 1;

		//
		while (i < casilleros.length && !hayCuatroEnLinea && casilleros.length > 3) {
			if (casilleros[i] == ultimoEstado) {
				contador++;
			} else {
				contador = 0;
			}
			if (contador == 3 && ultimoEstado != Casillero.VACIO) {
				hayCuatroEnLinea = !hayCuatroEnLinea;
			}
			ultimoEstado = casilleros[i];
			i++;
		}

		if (hayCuatroEnLinea) {
			if (esPrimerJugador) {
				nombreGanador = this.jugadorRojo;
			} else {
				nombreGanador = this.jugadorAmarillo;
			}
		}

		return hayCuatroEnLinea;
	}

	private Casillero[] obtenerFila() {
		return tablero[ultimoCasillero[0]];
	}

	private Casillero[] obtenerColumna() {
		Casillero[] casilleros = new Casillero[tablero.length];

		for (int i = 0; i < tablero.length; i++) {
			casilleros[i] = tablero[i][ultimoCasillero[1]];
		}

		return casilleros;
	}

	@Deprecated
	private Casillero[] obtenerDiagonalDescendente() {
		Casillero[] casilleros = null;
		int aux = modulo(ultimoCasillero[0] - ultimoCasillero[1]);
		// Creo el array para devolver con la cantidad mÃ¡xima posible de
		// valores dependiendo del tamaÃ±o del tablero
		try {
		if (tablero.length < tablero[0].length) {
			casilleros = new Casillero[tablero.length - aux];
		} else {
			casilleros = new Casillero[tablero[0].length - aux];
		}

		for (int i = 0; i < casilleros.length; i++) {
			if (ultimoCasillero[0] - ultimoCasillero[1] > 0) {
				casilleros[i] = tablero[aux][i];
			} else {
				casilleros[i] = tablero[i][aux];
			}
			aux++;
		}
		} catch (Exception e) {
			String error = e.getMessage();
			error += error + " ";
		}

		return casilleros;
	}

	@Deprecated
	private Casillero[] obtenerDiagonalAscendente() {
		Casillero[] casilleros = null;
		// aux es la suma de las coordenadas de la ultima ficha,
		// es un valor que se repite en toda la diagonal
		int aux = ultimoCasillero[0] + ultimoCasillero[1];
		int lengthCasilleros = aux + 1;
 		int indexDiagonalIncio = 0;
		int indexDiagonalFinal = aux;
		try {
		// aux es la suma de las coordenadas de la ultima ficha,
		// es un valor que se repite en toda la diagonal
		if (aux > tablero[0].length - 1) {
			indexDiagonalIncio += aux - (tablero[0].length - 1);
			indexDiagonalFinal = tablero[0].length - 1;
			lengthCasilleros -= aux + 1 - tablero[0].length;			
		}
		if (aux > tablero.length - 1) {
			lengthCasilleros -= aux + 1 - tablero.length;
		}
		
		casilleros = new Casillero[lengthCasilleros];
		for (int i = 0; i < casilleros.length; i++) {
			casilleros[i] = tablero[indexDiagonalIncio][indexDiagonalFinal];
			indexDiagonalIncio++;
			indexDiagonalFinal--;
		}
		} catch (Exception e) {
			String error = e.getMessage();
			error += error + "";
		}
		return casilleros;
	}

	private int modulo(int numero) {
		int modulo = numero;
		if (numero < 0) {
			modulo = -numero;
		}
		return modulo;
	}

	/**
	 * post: indica si el juego termina y tiene un ganador.
	 */
	public boolean hayGanador() {
		if (nombreGanador != null) {
			this.ganador = true;
		}
		return this.ganador;
	}

	/**
	 * pre : el juego termina.
	 * post: devuelve el nombre del jugador que gana el juego.
	 */
	public String obtenerGanador() {

		return nombreGanador;

	}

	private boolean verificarDiagonales() {

		boolean hayCuatroEnLinea = false;

		/*
		* Me aseguro no verificar el primer turno verificando que
		* ultimoCasillero no sea 0:0 o que tablero[tablero.length-1][0] sea
		* distinto de Casillero.VACIO
		*/

		if (this.ultimoCasillero[0] != 0 && this.ultimoCasillero[1] != 0
				|| tablero[tablero.length - 1][0] != Casillero.VACIO) {
			int fichasEnDiagonalDerecha = 1;

			// seteo posicionArrayFila y posicionArrayColumna para nueva diagonal.
			int posicionArrayFila = this.ultimoCasillero[0] - 1;
			int posicionArrayColumna = this.ultimoCasillero[1] + 1;

			// verifico diagonal ascendenteDerecha desde coordenada ultimoCasillero
			while (fichasEnDiagonalDerecha < 4
					&& posicionArrayFila >= 0
					&& posicionArrayColumna < tablero[0].length
					&& tablero[posicionArrayFila][posicionArrayColumna] == tablero[ultimoCasillero[0]][ultimoCasillero[1]]) {

				fichasEnDiagonalDerecha++;
				posicionArrayFila--;
				posicionArrayColumna++;

			}

			// reseteo posicionArrayFila y posicionArrayColumna para nueva diagonal.
			posicionArrayFila = this.ultimoCasillero[0] + 1;
			posicionArrayColumna = this.ultimoCasillero[1] - 1;

			// verifico diagonal descendenteIzquierda desde coordenada ultimoCasillero
			while (fichasEnDiagonalDerecha < 4
					&& posicionArrayFila < tablero.length
					&& posicionArrayColumna >= 0
					&& tablero[posicionArrayFila][posicionArrayColumna] == tablero[ultimoCasillero[0]][ultimoCasillero[1]]) {
				fichasEnDiagonalDerecha++;
				posicionArrayFila++;
				posicionArrayColumna--;
			}

			int fichasEnDiagonalIzquierda = 1;

			// reseteo posicionArrayFila y posicionArrayColumna para nueva diagonal
			posicionArrayFila = this.ultimoCasillero[0] - 1;
			posicionArrayColumna = this.ultimoCasillero[1] - 1;

			// verifico diagonal ascendenteIzquierda desde coordenada ultimoCasillero
			while (fichasEnDiagonalIzquierda < 4
					&& posicionArrayFila >= 0
					&& posicionArrayColumna >= 0
					&& tablero[posicionArrayFila][posicionArrayColumna] == tablero[ultimoCasillero[0]][ultimoCasillero[1]]) {
				fichasEnDiagonalIzquierda++;
				posicionArrayFila--;
				posicionArrayColumna--;
			}

			// reseteo posicionArrayFila y posicionArrayColumna para nueva diagonal.
			posicionArrayFila = this.ultimoCasillero[0] + 1;
			posicionArrayColumna = this.ultimoCasillero[1] + 1;

			// verifico diagonal descendenteDerecha desde coordenada
			// ultimoCasillero
			while (fichasEnDiagonalIzquierda < 4
					&& posicionArrayFila < tablero.length
					&& posicionArrayColumna < tablero[0].length
					&& tablero[posicionArrayFila][posicionArrayColumna] == tablero[ultimoCasillero[0]][ultimoCasillero[1]]) {
				fichasEnDiagonalIzquierda++;
				posicionArrayFila++;
				posicionArrayColumna++;
			}

			if (fichasEnDiagonalIzquierda == 4 || fichasEnDiagonalDerecha == 4) {
				hayCuatroEnLinea = true;
			}

			if (hayCuatroEnLinea) {
				if (esPrimerJugador) {
					nombreGanador = this.jugadorRojo;
				} else {
					nombreGanador = this.jugadorAmarillo;
				}
			}
		}

		return hayCuatroEnLinea;

	}
	
	public Casillero turnoActual (){
		Casillero jugadorActual;
		if (esPrimerJugador) {
			jugadorActual = Casillero.ROJO;
		} else {
			jugadorActual = Casillero.AMARILLO;
		}
		return jugadorActual;
	}
}