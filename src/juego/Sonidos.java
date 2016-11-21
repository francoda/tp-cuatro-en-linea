package juego;

import sun.audio.*;
import java.io.*;

public class Sonidos {
        
	private AudioStream audio = null;
    
    public void reproducirActual() {
    	if (audio != null) {
    		AudioPlayer.player.start(audio);    		
    	}
    }
  
    public void pararActual() {
    	if (audio != null) {
            AudioPlayer.player.stop(audio);
    	}
    }
    
    public void Terminar() {
    	reproducir("src/sonidos/Terminar.wav");
    }
    
    public void Iniciar() {
    	reproducir("src/sonidos/Iniciar.wav");
    }
    
    public void Jugar() {
    	reproducir("src/sonidos/Jugar.wav");
    }
    
    public void Soltar() {
    	reproducir("src/sonidos/Soltar.wav");
    }
    
    public void Empate() {
    	reproducir("src/sonidos/Empate.wav");
    }
    
    public void Random() {
    	reproducir("src/sonidos/Random.wav");
    }
    
    private void reproducir(String archivo) {
    	try {
    		pararActual();
    		InputStream ArchivoStream = new FileInputStream(archivo);
    		audio = new AudioStream(ArchivoStream);
    		reproducirActual();
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
}
