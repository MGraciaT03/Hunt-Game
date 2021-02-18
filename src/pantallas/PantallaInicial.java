package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.SwingUtilities;

import main.PanelJuego;

/**
 * @author Mario Gracia Torres
 * @version 1.0
 * @since 1.0
 * @see Esta clase implementa la interfaz Pantalla,y muestra el titulo del juego
 */

// Clase pantalla Inicial que se encarga de mostrar el titulo del juego
public class PantallaInicial implements Pantalla {
	Color colorLetraInicio = Color.WHITE;
	PanelJuego panelDeJuego;
	private BufferedImage fondo;
	private Image fondoEscalado;
	final Font fuenteTitulo= new Font("", Font.BOLD, 50);
	final Font fuenteInicio = new Font("", Font.BOLD, 30);
	final Font fuenteClick = new Font("", Font.BOLD, 23);

	//Constructor de la pantallaInicial
	public PantallaInicial(PanelJuego panel) {
	 panelDeJuego = panel;

	}
//Metodo que se encarga de implementar los hilos
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
	//Metodo que inicializa la pantalla,instanciandola de la clase PanelJuego
	@Override
	public void inicializarPantalla(PanelJuego panel) {
		this.panelDeJuego = panel;
		try {
			fondo = ImageIO.read(new File("imagenes/titulo.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("PROBLEMAS AL CARGAR LAS IMÁGENES. FIN DEL PROGRAMA");
			System.exit(1);
		}
		redimensionar();
	}
	//Metodo que se le pasa Graphics y pintara por pantalla el titulo del juego y los mensajes como alumnos e indicacion
	@Override
	public void pintarPantalla(Graphics g) {
		inicializarPantalla(panelDeJuego);
		rellenarFondo(g);
		g.setFont(fuenteTitulo);
		g.setColor(colorLetraInicio);
		g.drawString("HUNT GAME", panelDeJuego.getWidth() / 2 - 160, panelDeJuego.getHeight() / 2 - 10);
		g.setFont(fuenteInicio);
		g.drawString("Realizado por  Mario Gracia", panelDeJuego.getWidth() / 2 - 200, panelDeJuego.getHeight() / 2 + 50);
		g.setFont(fuenteClick);
		g.drawString("Click Izquierdo: Empezar Juego", panelDeJuego.getWidth() /  2 - 350, panelDeJuego.getHeight() / 2 + 150);
		g.drawString("Click Derecho: Salir", panelDeJuego.getWidth() / 2 + 100, panelDeJuego.getHeight() / 2 + 150);
	
	}
	//Ejecuta los componentes del frame y cambia de color a los mensajes de la pantalla
	@Override
	public void ejecutarFrame() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		colorLetraInicio = colorLetraInicio == Color.WHITE ? Color.DARK_GRAY: Color.WHITE ;
	}
//Cuando el usuario pulsa el raton, saltara a la pantalla del titulo,la clase PanelJuego actualizara la pantalla
	@Override
	public void pulsarRaton(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
		ReproducirSonido("sonidos/pulsarBoton.wav");
		panelDeJuego.setPantalla(new PantallaJuego(panelDeJuego));
		}else{
			if (SwingUtilities.isRightMouseButton(e)) {
			ReproducirSonido("sonidos/pulsarBoton.wav");
			System.exit(1);
			}
		}
	}

	@Override
	public void moverRaton(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void redimensionar() {
		fondoEscalado = fondo.getScaledInstance(panelDeJuego.getWidth(), panelDeJuego.getHeight(),
				BufferedImage.SCALE_SMOOTH);
	}
	//Añade el fondo de la ventana a la pantalla
	private void rellenarFondo(Graphics g) {
		g.drawImage(fondoEscalado, 0, 0, null);
	}
	//Recibe una ruta de un fichero de audio,lo abrira y lo reproducira
	public void ReproducirSonido(String nombreSonido){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(nombreSonido).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
			clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            System.out.println("Error al reproducir el sonido.");
        }
    }

}
