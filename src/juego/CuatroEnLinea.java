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
		int ultimaFilaVacia = tablero.length -1;
		columna-=1;
		while (ultimaFilaVacia > 0 &&tablero[ultimaFilaVacia][columna] != Casillero.VACIO)  {
			ultimaFilaVacia--;
		}

		if (ultimaFilaVacia >= 0 && tablero[0][columna]==Casillero.VACIO) {
			if (esPrimerJugador) {
				tablero[ultimaFilaVacia][columna] = Casillero.ROJO;
				
			} else {
				tablero[ultimaFilaVacia][columna] = Casillero.AMARILLO;
			}
			ultimoCasillero[0] = ultimaFilaVacia;
			ultimoCasillero[1] =columna;			
			esPrimerJugador = !esPrimerJugador;
		}

		if (ultimaFilaVacia >= 0) {
			if (esPrimerJugador) {
				tablero[ultimaFilaVacia][columna-1] = Casillero.ROJO;
			} else {
				tablero[ultimaFilaVacia][columna-1] = Casillero.AMARILLO;
			}
			esPrimerJugador = !esPrimerJugador;
		}
	}
	
	/**
	 * post: indica si el juego terminó porque uno de los jugadores
	 * 		 ganó o no existen casilleros vacíos.
	 */
	public boolean termino() {
		int i=0;
		boolean termino=false;
		
		//verifica la ultima fila por empate		
		while (i<tablero[0].length && tablero[0][i]!=Casillero.VACIO){			
			if (i==tablero[0].length-1){
				termino=!termino;
			}
			i++;
		}
		
		//termino = hayCuatroEnLinea();
		
		return termino;
	}

	/**
	 * post: indica si el juego terminó y tiene un ganador.
	 */
	public boolean hayGanador() {
		
		return false;
	}

	/**
	 * pre : el juego terminó.
	 * post: devuelve el nombre del jugador que ganó el juego.
	 */
	public String obtenerGanador() {
		
		String ganador="mariano tugnarelli";
		if (hayGanador()) {
			if (esPrimerJugador) {
				ganador = jugadorRojo;
			} else {
				ganador = jugadorAmarillo;
			}
		}
		
		return ganador;
	}
}