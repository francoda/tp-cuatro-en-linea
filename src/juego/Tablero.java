package juego;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;

/**
 * Representaci�n gr�fica del Tablero del Juego Cuatro en L�nea.
 * 
 */
public class Tablero {

	private static final int ALTO_FILA = 80;
	private static final int ANCHO_COLUMNA = 80;
	private static final int ALTURA_BOTON = 40;
	private static final double RADIO = Math.min(ALTO_FILA - 1, ANCHO_COLUMNA - 1) / 2;
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
		grilla.setStyle("-fx-background-color: steelblue");

	}

	/**
	 * post: muestra el Tablero en pantalla.
	 */
	public void mostrar() {

		dibujarTurnoActual();
		dibujarBotones();

		double ancho = juego.contarColumnas() * ANCHO_COLUMNA - 10;
		double alto = (juego.contarFilas() * ALTO_FILA) + ALTURA_BOTON + RADIO;

		Scene escena = new Scene(grilla, ancho, alto, Color.rgb(240, 240, 40));
		escenario.setScene(escena);
		escenario.setResizable(false);
		escenario.setTitle(Aplicacion.TITULO);
		escenario.getIcons().add(new Image("file:resources/icon.png"));
		
		dibujar();

		escenario.show();
	}

	/*
	 * post: actualiza el Tablero a partir del turno actual
	 */
	public void dibujarTurnoActual() {
		Label etiquetaTurnoActual = new Label(" Turno actual: ");
		etiquetaTurnoActual.setTextFill(new Color(1, 1, 1, 1));

		grilla.add(etiquetaTurnoActual, 0, 0);

		grilla.add(dibujarCasilleroTurnoActual(juego.turnoActual()), 1, 0);
		
		GridPane.setHalignment(etiquetaTurnoActual, HPos.CENTER);
	}

	private Circle dibujarCasilleroTurnoActual(Casillero casillero) {

		Circle dibujoCasillero = new Circle(RADIO / 1.5,
				obtenerPintura(casillero));

		dibujoCasillero.setStroke(new Color(0, 0, 0, 0));
		dibujoCasillero.setScaleX(0.5);
		dibujoCasillero.setScaleY(0.5);
		return dibujoCasillero;
	}

	/**
	 * post: agrega los botones para soltar una ficha en cada columna del
	 * Tablero.
	 */
	private void dibujarBotones() {

		for (int columna = 1; columna <= juego.contarColumnas(); columna++) {

			Button botonSoltarFicha = new Button("", new ImageView(new Image("file:resources/flecha.png")));
			botonSoltarFicha.setMinHeight(ALTURA_BOTON);
			botonSoltarFicha.setStyle("-fx-background-color: transparent; " +
									 	"-fx-background-radius: 5em; " +
	                					"-fx-min-width: 40px; " +
						                "-fx-min-height: 40px; " +
						                "-fx-max-width: 40px; " +
						                "-fx-max-height: 40px;");
			botonSoltarFicha.addEventHandler(MouseEvent.MOUSE_ENTERED, 
			    new EventHandler<MouseEvent>() {
			        @Override public void handle(MouseEvent e) {
			        	botonSoltarFicha.setGraphic(dibujarCasilleroTurnoActual(juego.turnoActual()));
			        }
			});
			botonSoltarFicha.addEventHandler(MouseEvent.MOUSE_EXITED, 
			    new EventHandler<MouseEvent>() {
			        @Override public void handle(MouseEvent e) {
			        	botonSoltarFicha.setGraphic(new ImageView(new Image("file:resources/flecha.png")));
			        }
			});
			botonSoltarFicha.setOnAction(new SoltarFicha(this, juego, columna));
			botonSoltarFicha.setMinWidth(ANCHO_COLUMNA);
			grilla.add(botonSoltarFicha, columna - 1, 1);
			GridPane.setHalignment(botonSoltarFicha, HPos.CENTER);
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

				Circle dibujoCasillero = dibujarCasillero(casillero);

				grilla.add(dibujoCasillero, columna - 1, fila + 1);
				
				GridPane.setHalignment(dibujoCasillero, HPos.CENTER);
			}
		}
	}

	/**
	 * post: dibuja y devuelve el casillero dado.
	 * 
	 * @param casillero
	 * @return representaci�n gr�fica del Casillero.
	 */
	private Circle dibujarCasillero(Casillero casillero) {

		Circle dibujoCasillero = new Circle(RADIO,
				obtenerPintura(casillero));

		dibujoCasillero.setStroke(new Color(0, 0, 0, 0));
		dibujoCasillero.setScaleX(0.95);
		dibujoCasillero.setScaleY(0.95);
		return dibujoCasillero;
	}

	/**
	 * post: determina la pintura a utilizar para 'casillero'.
	 * 
	 * @param casillero
	 * @return pintura a utilizar para identificar el Casillero.
	 */
	private Paint obtenerPintura(Casillero casillero) {

		Paint pintura;

		switch (casillero) {

		case AMARILLO:
			pintura = Color.rgb(240, 240, 40);
			break;

		case ROJO:
			pintura = Color.rgb(120, 175, 255);
			break;

		default:
			pintura = Color.rgb(250, 250, 250);
		}

		return pintura;
	}

	/**
	 * pre : el juego asociado termin�. post: muestra un mensaje indicando el
	 * resultado del juego.
	 */
	public void mostrarResultado() {

		Stage dialogo = new Stage();

		BorderPane panelGanador = new BorderPane();
		panelGanador.setPadding(new Insets(10.0));
		Text textoResultado;
		Font fuente = new Font(40.0);

		if (juego.hayGanador() && juego.obtenerGanador().equals("Mariano")) {

			textoResultado = new Text(
					"Gan� Mariano, se puso contento y nos prob� a todos."
							+ System.lineSeparator()
							+ "Por ende, �ganamos todos!");
			Aplicacion.sonidos.Random();

			// detiene sonido al cerrar la ventana
			dialogo.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
					Aplicacion.sonidos.pararActual();
				}
			});

		} else if (juego.hayGanador()) {

			textoResultado = new Text("Gan� el jugador "
					+ juego.obtenerGanador());
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
