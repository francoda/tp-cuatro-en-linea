package juego;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class pruebasCuatroEnLinea {

	private CuatroEnLinea juego7x7;
	private CuatroEnLinea juego4x6;
	private CuatroEnLinea juego6x4;
	private CuatroEnLinea[] todosLosTableros = new CuatroEnLinea[3];

	@Before
	public void inicializacionDelJuego() {
		this.juego7x7 = new CuatroEnLinea(7, 7, "JugadorUno", "JugadorDos");
		this.juego4x6 = new CuatroEnLinea(4, 6, "JugadorUno", "JugadorDos");
		this.juego6x4 = new CuatroEnLinea(6, 4, "JugadorUno", "JugadorDos");
		todosLosTableros[0] = juego7x7;
		todosLosTableros[1] = juego4x6;
		todosLosTableros[2] = juego6x4;
	}

	@Test(expected = Error.class)
	public void InicializacionConLineasMenorACuatro() {
		// inicializacion
		juego7x7 = new CuatroEnLinea(1, 15, "JugadorUno", "JugadorDos");
	}

	@Test(expected = Error.class)
	public void InicializacionConColumnasMenorACuatro() {
		// inicializacion
		juego7x7 = new CuatroEnLinea(10, 2, "JugadorUno", "JugadorDos");
	}

	@Test(expected = Error.class)
	public void InicializacionSinNombrePrimerJugador() {
		// inicalizacion
		juego7x7 = new CuatroEnLinea(10, 15, "", "JugadorDos");
	}

	@Test(expected = Error.class)
	public void InicializacionSinNombreSegundoJugador() {
		// inicalizacion
		juego7x7 = new CuatroEnLinea(10, 15, "JugadorUno", "");
	}

	@Test
	public void InsercionCorrectaPrimeraFichaRoja() {
		// inicializacion

		// operacion
		juego7x7.soltarFicha(1);

		// evaluacion
		Assert.assertEquals(Casillero.CASILLERO1,
				juego7x7.obtenerCasillero(7, 1));
	}

	@Test
	public void InsercionPrimeraFichaAmarilla() {
		// inicializacion

		// operacion
		for (int i = 0; i < 2; i++) {
			juego7x7.soltarFicha(1);
		}

		// evaluacion
		Assert.assertEquals(Casillero.CASILLERO1,
				juego7x7.obtenerCasillero(7, 1));
	}

	@Test(expected = Exception.class)
	public void ConsultaCasilleroInexistenteEnLimiteBajo() {
		// inicializacion

		// operacion
		juego7x7.obtenerCasillero(0, 0);

		// evaluacion

	}

	@Test(expected = Exception.class)
	public void ConsultaCasilleroInexistenteEnLimiteAlto() {
		// inicializacion

		// operacion
		juego7x7.obtenerCasillero(10, 16);

		// evaluacion
	}

	@Test
	public void CuatroEnLineaHorizontalGanadorJugadorUno() {
		for(CuatroEnLinea juego : todosLosTableros) {
			// inicializacion
	
			// operacion
			for (int columna = 0; columna < 4; columna++) {
				for (int i = 0; i < 2; i++) {
					juego.soltarFicha(columna + 1);
				}
			}
			juego.soltarFicha(4);
	
			// evaluacion
			Assert.assertEquals(true, juego.hayGanador());
			Assert.assertEquals("JugadorUno", juego.obtenerGanador());
		}
	}

	@Test
	public void CuatroEnLineaVerticalGanadorJugadorUno() {
		for(CuatroEnLinea juego : todosLosTableros) {
			// inicializacion
	
			// operacion
			for (int i = 0; i <= 4; i++) {
				for (int columna = 0; columna < 2 || (i == 4 && columna == 0); columna++) {
					juego.soltarFicha(columna + 1);
				}
			}
	
			// evaluacion
			Assert.assertEquals(true, juego.hayGanador());
			Assert.assertEquals("JugadorUno", juego.obtenerGanador());
		}
	}

	@Test
	public void CuatroEnLineaDiagonalAsendenteInferiorIzquierda() {
		for(CuatroEnLinea juego : todosLosTableros) {
			// inicializacion
	
			// operacion
			for (int columna = 0; columna < 4; columna++) {
				for (int i = 0; i < 3 || (columna == 3 && i == 3); i++) {
					juego.soltarFicha(columna + 1);
				}
			}			
	
			// evaluacion
			Assert.assertEquals(true, juego.hayGanador());
			Assert.assertEquals("JugadorUno", juego.obtenerGanador());
		}
	}

//	@Test
//	public void CuatroEnLineaDiagonalAsendenteInferiorDerecha() {
//		for(CuatroEnLinea juego : todosLosTableros) {
//			// inicializacion
//	
//			// operacion
//				
//			for (int columna = 0; columna < 4; columna++) {
//				for (int i = 0; i < 3 || (columna == 3 && i == 3); i++) {
//					juego.soltarFicha(columna + 1);
//				}
//			}
//	
//			// evaluacion
//			Assert.assertEquals(true, juego.hayGanador());
//			Assert.assertEquals("JugadorUno", juego.obtenerGanador());
//		}
//	}

}
