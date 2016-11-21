package juego;

import sun.audio.*;
import java.io.*;


public class Sonidos extends Thread {
        
	private InputStream in;
        AudioStream as = null;
        
        /**
         * Depende el parametro que recibe el constructor suena una cosa u otra.
         * 
         * @param pTipoSonido
         *            - jugar - iniciar - soltar - terminar - empate -
         * @throws IOException
         */
        
        public Sonidos(String pTipoSonido) throws IOException {
                
        	if(pTipoSonido == null){
                        return;
                }
                
                if (pTipoSonido.equals("iniciar")) {

                        in = new FileInputStream("src/sonidos/Iniciar.wav");
                        
                } else if (pTipoSonido.equals("jugar")) {

                        in = new FileInputStream("src/sonidos/Jugar.wav");
                        
                } else if (pTipoSonido.equals("soltar")) {

                        in = new FileInputStream("src/sonidos/Soltar.wav");
                        
                } else if (pTipoSonido.equals("terminar")) {

                        in = new FileInputStream("src/sonidos/Terminar.wav");
                        
                } else if (pTipoSonido.equals("empate")){
                        
                	in = new FileInputStream("src/sonidos/Empate.wav");
                	
                } else if (pTipoSonido.equals("random")){
                    
            	in = new FileInputStream("src/sonidos/Random.wav");
                }
                
                as = new AudioStream(in);
        }
        
        public void run() {
                AudioPlayer.player.start(as);
        }
       
        
        public void pararActual() {
                AudioPlayer.player.stop(as);
        }
        
        public void sonidoTerminar() {
                try {
                        new Sonidos("terminar").start();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
        
        public void sonidoIniciar() {
                try {
                        new Sonidos("iniciar").start();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
        
        public void sonidoJugar() {
                try {
                        new Sonidos("jugar").start();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
        
        public void sonidoSoltar() {
                try {
                        new Sonidos("soltar").start();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
        
        public void sonidoEmpate() {
            try {
                    new Sonidos("empate").start();
            } catch (IOException e) {
                    e.printStackTrace();
            }
        }
        
        public void sonidoRandom() {
            try {
                    new Sonidos("random").start();
            } catch (IOException e) {
                    e.printStackTrace();
            }
        }
}
