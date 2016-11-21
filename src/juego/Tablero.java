package juego;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.stage.WindowEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;


/**
 * Representación gráfica del Tablero del Juego Cuatro en Línea.
 * 
 */
public class Tablero {

	private static final int ALTO_FILA = 80;
	private static final int ANCHO_COLUMNA = 80;
	private static final int ALTURA_BOTON = 40;
	private static final double LADO = Math.min(ALTO_FILA - 1, ANCHO_COLUMNA - 1) ;
	
	private CuatroEnLinea juego;
	private GridPane grilla;
	private Stage escenario;

	/**
	 * post: asocia el Tablero a 'nuevoJuego' y lo inicializa a partir de su estado. 
	 * 
	 * @param nuevoJuego
	 */
	public Tablero(CuatroEnLinea nuevoJuego) {
		
		juego = nuevoJuego;
		escenario = new Stage();
		grilla = new GridPane();
		
	}
	
	/**
	 * post: muestra el Tablero en pantalla.
	 */
	public void mostrar() {
		
		dibujarTurnoActual();
		dibujarBotones();
		
		double ancho = juego.contarColumnas() * ANCHO_COLUMNA;
		double alto = (juego.contarFilas() * ALTO_FILA) + ALTURA_BOTON + LADO/3;
		
		Scene escena = new Scene(grilla, ancho, alto);

		escenario.setScene(escena);
		escenario.setResizable(false);
		escenario.setTitle(Aplicacion.TITULO);
		
		dibujar();

		escenario.show();
	}
	
	/*
	 * post: actualiza el Tablero a partir del turno actual
	 */
	public void dibujarTurnoActual() {
		Label etiquetaTurnoActual = new Label(" Turno actual:");

		grilla.add(etiquetaTurnoActual, 0, 0);
		
		grilla.add(dibujarCasilleroTurnoActual(juego.turnoActual()), 1, 0);
	}
	
	private Rectangle dibujarCasilleroTurnoActual(Casillero casillero) {
		
		Rectangle dibujoCasillero = new Rectangle(LADO/3, LADO/3, obtenerPintura(casillero));
		
		dibujoCasillero.setStroke(new Color(0.5, 0.5, 0.5, 1.0));
		dibujoCasillero.setScaleX(0.5);
		dibujoCasillero.setScaleY(0.5);
		return dibujoCasillero;
	}
	
	/**
	 * post: agrega los botones para soltar una ficha en cada columna del Tablero.
	 */
	private void dibujarBotones() {
		
		for (int columna = 1; columna <= juego.contarColumnas(); columna++) {

			Button botonSoltarFicha = new Button("soltar");
			botonSoltarFicha.setMinHeight(ALTURA_BOTON);

			botonSoltarFicha.setOnAction(new SoltarFicha(this, juego, columna));
			botonSoltarFicha.setMinWidth(ANCHO_COLUMNA);
			grilla.add(botonSoltarFicha, columna - 1, 1);
		}
	}
		
	/**
	 * post: actualiza el Tablero a partir del estado del juego asociado.
	 */
	public void dibujar() {
		
		dibujarTurnoActual();

		for (int fila = 1; fila <= juego.contarFilas(); fila++) {

			for (int columna = 1; columna <= juego.contarColumnas(); columna++) {

				Casillero casillero = juego.obtenerCasillero(fila, columna);
				
				Rectangle dibujoCasillero = dibujarCasillero(casillero);
				
				grilla.add(dibujoCasillero, columna - 1, fila + 1 );
			}
		}
	}

	/**
	 * post: dibuja y devuelve el casillero dado.
	 * 
	 * @param casillero
	 * @return representación gráfica del Casillero.
	 */
	private Rectangle dibujarCasillero(Casillero casillero) {
		
		Rectangle dibujoCasillero = new Rectangle(LADO, LADO, obtenerPintura(casillero));
		
		dibujoCasillero.setStroke(new Color(0.5, 0.5, 0.5, 1.0));
		dibujoCasillero.setScaleX(0.95);
		dibujoCasillero.setScaleY(0.95);
		return dibujoCasillero;
	}

	/**
	 * post: determina la pintura a utilizar para 'casillero'.

	 * @param casillero
	 * @return pintura a utilizar para identificar el Casillero.
	 */
	private Paint obtenerPintura(Casillero casillero) {

		Paint pintura;

		switch (casillero) {
		
			case AMARILLO:
				pintura = Color.rgb(208, 187, 158);
				break;
				
			case ROJO:
				pintura = Color.rgb(241, 220, 198);
				break;
				
			default:
				pintura = Color.rgb(246, 255, 240);
		}

		return pintura;
	}

	/**
	 * pre : el juego asociado terminó.
	 * post: muestra un mensaje indicando el resultado del juego.
	 */
	public void mostrarResultado() {

		Stage dialogo = new Stage();
		
		BorderPane panelGanador = new BorderPane();
		panelGanador.setPadding(new Insets(10.0));
		Text textoResultado;
		Font fuente = new Font(40.0);
		
		if (juego.hayGanador() && juego.obtenerGanador().equals("Mariano")) {
		
			textoResultado = new Text("Ganó Mariano, se puso contento y nos probó a todos."  + System.lineSeparator() + "Por ende, ¡ganamos todos!");
			Aplicacion.sonidos.Random();

			//detiene sonido al cerrar la ventana
			dialogo.setOnCloseRequest(new EventHandler<WindowEvent>() {
	        		public void handle(WindowEvent we) {
	            			Aplicacion.sonidos.pararActual();
	            		}
	        	});

			
		} else if (juego.hayGanador()) {
			
				textoResultado = new Text("Ganó el jugador " + juego.obtenerGanador());
				Aplicacion.sonidos.Terminar();
				
		} else {
			
			textoResultado = new Text("Empataron");
			Aplicacion.sonidos.Empate();
		}
		
		textoResultado.setFont(fuente);
		panelGanador.setCenter(textoResultado);
		
		Scene escenaGanador = new Scene(panelGanador);
		
		dialogo.setScene(escenaGanador);
		dialogo.initOwner(escenario);
		dialogo.initModality(Modality.WINDOW_MODAL);
		dialogo.setResizable(false);
		
		dialogo.showAndWait();
	}
}
