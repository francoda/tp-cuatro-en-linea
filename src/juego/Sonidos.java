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
    	reproducir("resources/Terminar.wav");
    }
    
    public void Iniciar() {
    	reproducir("resources/Iniciar.wav");
    }
    
    public void Jugar() {
    	reproducir("resources/Jugar.wav");
    }
    
    public void Soltar() {
    	reproducir("resources/Soltar.wav");
    }
    
    public void Empate() {
    	reproducir("resources/Empate.wav");
    }
    
    public void Random() {
    	reproducir("resources/Random.wav");
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
