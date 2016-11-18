package juego;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class pruebasCuatroEnLinea {

	private CuatroEnLinea juegoParaPruebas;
	
	@Before
	public void inicializacionDelJuego() {
		this.juegoParaPruebas = new CuatroEnLinea(10,15,"JugadorUno","JugadorDos");
	}
	
	@Test (expected = Error.class)
	public void pruebaInicializacionConLineasMenorACuatro() {
		//inicializacion
		juegoParaPruebas = new CuatroEnLinea(1,15,"JugadorUno","JugadorDos");
	}
	
	@Test (expected = Error.class)
	public void pruebaInicializacionConColumnasMenorACuatro() {
		//inicializacion
		juegoParaPruebas = new CuatroEnLinea(10,2,"JugadorUno","JugadorDos");
	}
	
	@Test (expected = Error.class)
	public void pruebaInicializacionSinNombrePrimerJugador(){
		//inicalizacion
		juegoParaPruebas = new CuatroEnLinea(10,15,"","JugadorDos");
	}
	
	@Test (expected = Error.class)
	public void pruebaInicializacionSinNombreSegundoJugador(){
		//inicalizacion
		juegoParaPruebas = new CuatroEnLinea(10,15,"JugadorUno","");
	}
	
	@Test
	public void pruebaInsercionCorrectaPrimeraFichaRoja(){
		//inicializacion
		
		//operacion
		juegoParaPruebas.soltarFicha(1);
		
		//evaluacion
		Assert.assertEquals(Casillero.ROJO, juegoParaPruebas.obtenerCasillero(10, 1));
	}
	
	@Test
	public void pruebaInsercionPrimeraFichaAmarilla(){
		//inicializacion
		
		//operacion
		juegoParaPruebas.soltarFicha(1);
		juegoParaPruebas.soltarFicha(1);
		
		//evaluacion
		Assert.assertEquals(Casillero.AMARILLO, juegoParaPruebas.obtenerCasillero(9, 1));
	}
	
	@Test (expected = Exception.class)
	public void pruebaConsultaCasilleroInexistenteEnLimiteBajo() {
		//inicializacion
		
		//operacion
		juegoParaPruebas.obtenerCasillero(0, 0);
		
		//evaluacion
		
	}
	
	@Test (expected = Exception.class)
	public void pruebaConsultaCasilleroInexistenteEnLimiteAlto() {
		//inicializacion
		
		//operacion
		juegoParaPruebas.obtenerCasillero(10, 16);
		
		//evaluacion
	}
	
	@Test
	public void pruebaCuatroEnLineaHorizontalGanadorJugadorUno() {
		//inicializacion
		
		//operacion
		juegoParaPruebas.soltarFicha(1);
		juegoParaPruebas.soltarFicha(1);
		juegoParaPruebas.soltarFicha(2);
		juegoParaPruebas.soltarFicha(2);
		juegoParaPruebas.soltarFicha(3);
		juegoParaPruebas.soltarFicha(3);
		juegoParaPruebas.soltarFicha(4);

		//evaluacion
		Assert.assertEquals(true, juegoParaPruebas.hayGanador());
		Assert.assertEquals("JugadorUno", juegoParaPruebas.obtenerGanador());
		
	}
	
	@Test
	public void pruebaCuatroEnLineaVerticalGanadorJugadorUno() {
		//inicializacion
		
		//operacion
		juegoParaPruebas.soltarFicha(1);
		juegoParaPruebas.soltarFicha(2);
		juegoParaPruebas.soltarFicha(1);
		juegoParaPruebas.soltarFicha(2);
		juegoParaPruebas.soltarFicha(1);
		juegoParaPruebas.soltarFicha(2);
		juegoParaPruebas.soltarFicha(1);

		//evaluacion
		Assert.assertEquals(true, juegoParaPruebas.hayGanador());
		Assert.assertEquals("JugadorUno", juegoParaPruebas.obtenerGanador());
		
	}
	
	@Test
	public void pruebaCuatroEnLineaDiagonalIzquierdaADerechaGanadorJugadorUno() {
		//inicializacion
		
		//operacion
		juegoParaPruebas.soltarFicha(1);
		juegoParaPruebas.soltarFicha(2);
		juegoParaPruebas.soltarFicha(2);
		juegoParaPruebas.soltarFicha(3);
		juegoParaPruebas.soltarFicha(3);
		juegoParaPruebas.soltarFicha(4);
		juegoParaPruebas.soltarFicha(3);
		juegoParaPruebas.soltarFicha(4);
		juegoParaPruebas.soltarFicha(4);
		juegoParaPruebas.soltarFicha(1);
		juegoParaPruebas.soltarFicha(4);

		//evaluacion
		Assert.assertEquals(true, juegoParaPruebas.hayGanador());
		Assert.assertEquals("JugadorUno", juegoParaPruebas.obtenerGanador());
		
	}

	
}
