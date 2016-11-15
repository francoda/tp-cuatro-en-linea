package juego;

/**
 * Juego Cuatro en Lí­nea
 * 
 * Reglas:
 * 
 * 		...
 *
 */
public class CuatroEnLinea {

	private Casillero[][] tablero;
	private boolean esPrimerJugador;
	private String jugadorRojo;
	private String jugadorAmarillo;
	private String ganador = null;
	private int[] ultimoCasillero = {0,0};
	
	/**
	 * pre : 'filas' y 'columnas' son mayores o iguales a 4.
	 * post: empieza el juego entre el jugador que tiene fichas rojas, identificado como 
	 * 		 'jugadorRojo' y el jugador que tiene fichas amarillas, identificado como
	 * 		 'jugadorAmarillo'. 
	 * 		 Todo el tablero está vacío.
	 * 
	 * @param filas : cantidad de filas que tiene el tablero.
	 * @param columnas : cantidad de columnas que tiene el tablero.
	 * @param jugadorRojo : nombre del jugador con fichas rojas.
	 * @param jugadorAmarillo : nombre del jugador con fichas amarillas.
	 */
	public CuatroEnLinea(int filas, int columnas, String jugadorRojo, String jugadorAmarillo) {

		if (filas < 4 || columnas < 4) {
			throw new Error("Medidas de tablero incorrectas");
		}
		
		tablero = new Casillero[filas][columnas];
		
		for (int i=0;i<filas;i++) {
			for (int j=0;j<columnas;j++) {
				tablero[i][j] = Casillero.VACIO;
			}
		}
		
		this.jugadorRojo = jugadorRojo;
		this.jugadorAmarillo = jugadorAmarillo;
	}

	/**
	 * post: devuelve la cantidad máxima de fichas que se pueden apilar en el tablero.
	 */
	public int contarFilas() {
		return tablero.length;
	}

	/**
	 * post: devuelve la cantidad máxima de fichas que se pueden alinear en el tablero.
	 */
	public int contarColumnas() {
		return tablero[0].length;
	}

	/**
	 * pre : fila está en el intervalo [1, contarFilas()],
	 * 		 columnas está en el intervalo [1, contarColumnas()].
	 * post: indica qué ocupa el casillero en la posición dada por fila y columna.
	 * 
	 * @param fila
	 * @param columna
	 */
	public Casillero obtenerCasillero(int fila, int columna) {
		return tablero[fila-1][columna-1];
	}
	
	/**
	 * pre : el juego no terminó, columna está en el intervalo [1, contarColumnas()]
	 * 		 y aún queda uxn Casillero.VACIO en la columna indicada. 
	 * post: deja caer una ficha en la columna indicada.
	 * 
	 * @param columna
	 */
	public void soltarFicha(int columna) {
		int ultimaFilaVacia = tablero.length - 1;
		columna--;
		//Busco desde abajo asta arriba la fila vacia
		while (ultimaFilaVacia > 0 &&tablero[ultimaFilaVacia][columna] != Casillero.VACIO)  {
			ultimaFilaVacia--;
		}
		//Cambio la casilla si esta vacia
		if (ultimaFilaVacia >= 0 && tablero[0][columna]==Casillero.VACIO) {
			if (esPrimerJugador) {
				tablero[ultimaFilaVacia][columna] = Casillero.ROJO;
			} else {
				tablero[ultimaFilaVacia][columna] = Casillero.AMARILLO;
			}
			//Cargo la ultima casilla y cambio de jugador
			ultimoCasillero[0] = ultimaFilaVacia;
			ultimoCasillero[1] = columna;			
			esPrimerJugador = !esPrimerJugador;
		}
	}
	
	/**
	 * post: indica si el juego terminó porque uno de los jugadores
	 * 		 ganó o no existen casilleros vacíos.
	 */
	public boolean termino() {
		int i=0;
		boolean termino = false;
		//verifica la ultima fila por empate		
		while (i<tablero[0].length && tablero[0][i]!=Casillero.VACIO){			
			if (i==tablero[0].length-1){
				termino = !termino;
			}
			i++;
		}
		//verifica si hay 4 en linea en Fila, Columna, DiagonalAsendente, DiagonalDesendente
		if (!termino && hayCuatroEnLinea(obtenerFila())){
			termino = !termino;
		} else if (!termino && hayCuatroEnLinea(obtenerColumna())) {
			termino = !termino;
		} else if (!termino && hayCuatroEnLinea(obtenerDiagonalAsendente())) {
			termino = !termino;
		} else if (!termino && hayCuatroEnLinea(obtenerDiagonalDesendente())) {
			termino = !termino;
		}
		
		return termino;
	}

	/**
	 * post: indica si se realizo un 4 en linea en los casilleros pasados.
	 */
	private boolean hayCuatroEnLinea(Casillero[] casilleros) {
		boolean hayCuatroEnLinea = false;
		Casillero ultimoEstado = Casillero.VACIO;
		int contador = 0;
		int i = 0;
		
		while (i < casilleros.length && !hayCuatroEnLinea) {
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
				ganador = jugadorRojo;
			} else {
				ganador = jugadorAmarillo;
			}
		}
		
		return hayCuatroEnLinea;
	}
	
	private Casillero[] obtenerFila() {
		return tablero[ultimoCasillero[0]];
	}
	
	private Casillero[] obtenerColumna() {
		Casillero[] casilleros = new Casillero[tablero.length];
		
		for (int i=0;i<tablero.length;i++) {
			casilleros[i] = tablero[i][ultimoCasillero[1]];
		}
		
		return casilleros;
	}
	
	private Casillero[] obtenerDiagonalDesendente() {
		Casillero[] casilleros = null;
		int aux = modulo(ultimoCasillero[0] - ultimoCasillero[1]);
		//Creo el array para devolver con la cantidad maxima posible de valores dependiendo del tamano del tablero
		if (tablero.length < tablero[0].length) {
			casilleros = new Casillero[tablero.length - aux];
		} else {
			casilleros = new Casillero[tablero[0].length - aux];
		}
		
		for (int i=0;i<casilleros.length;i++) {
			if (ultimoCasillero[0] - ultimoCasillero[1] > 0) {
				casilleros[i] = tablero[aux][i];				
			} else {
				casilleros[i] = tablero[i][aux];
			}
			aux++;
		}
		
		return casilleros;
	}
	
	private Casillero[] obtenerDiagonalAsendente() {
		Casillero[] casilleros = null;
		int aux = ultimoCasillero[0] + ultimoCasillero[1];
		int indexDiagonalIncio = 0;
		int indexDiagonalFinal = aux;
		//aux es la suma de las cordenadas de la la ultima ficha, es un valor que se repite en toda la diagonal
		if (aux > tablero.length) {
			indexDiagonalIncio = modulo(aux - tablero.length + 1);
		}
		if (aux > tablero[0].length-1) {
			indexDiagonalFinal = tablero[0].length-1;
		}
		casilleros = new Casillero[indexDiagonalFinal - indexDiagonalIncio + 1];			
		for (int i=0;i<casilleros.length;i++) {
			casilleros[i] = tablero[indexDiagonalFinal][indexDiagonalIncio];
			indexDiagonalIncio++;
			indexDiagonalFinal--;
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
	 * post: indica si el juego terminó y tiene un ganador.
	 */
	public boolean hayGanador() {
		return ganador == null;
	}

	/**
	 * pre : el juego terminó.
	 * post: devuelve el nombre del jugador que ganó el juego.
	 */
	public String obtenerGanador() {	
		return ganador;
	}
}