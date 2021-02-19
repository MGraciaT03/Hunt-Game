package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

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
 * @see Esta clase implementa la interfaz Pantalla,y muestra al usuario que ha
 *      ganado
 */

// Clase pantalla Victoria que se encarga de mostrar al jugador que ha ganado la
// partida,implementa la interfaz Pantalla
public class PantallaVictoria implements Pantalla {
	// Atributos de PantallaVictoria
	Color colorLetraFinal = Color.WHITE;
	PanelJuego panelDeJuego;
	final Font fuenteFinal = new Font("", Font.BOLD, 30);
	private BufferedImage fondo;
	private Image fondoEscalado;
	private int puntos;
	private double tiempo;
	private DecimalFormat formato = new DecimalFormat("#.##");

	// Constructor,incicializa la pantalla pasandole como instancia la clase
	// PanelJuego
	// La puntuacion a mostrar son los pajaros que se han abatido
	// Y finalmente se le pase el tiempo logrado
	public PantallaVictoria(PanelJuego panel, int puntuacion, double tiempoLogrado) {
		inicializarPantalla(panel);
		this.puntos = puntuacion ;
		tiempo = tiempoLogrado;
	}

	// Metodo Run
	@Override
	public void run() {
	}

	// Para inicializar la pantalla,carga la imagen e instancia la clase PanelJuego
	@Override
	public void inicializarPantalla(PanelJuego panel) {
		this.panelDeJuego = panel;
		try {
			fondo = ImageIO.read(new File("imagenes/victoria.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("PROBLEMAS AL CARGAR LAS IMÁGENES. FIN DEL PROGRAMA");
			System.exit(1);
		}
		redimensionar();
	}

	// usando Graphics,pintara por la pantalla los atributos pasados
	// y teniendo una forma de posicionarlos en la pantalla
	@Override
	public void pintarPantalla(Graphics g) {
		rellenarFondo(g);
		g.setFont(fuenteFinal);
		g.setColor(colorLetraFinal);
		g.drawString("!ENHORABUENA! HAS GANADO", panelDeJuego.getWidth() / 2 - 210,
				(panelDeJuego.getHeight() / 2) - 30);
		g.drawString("PUNTUACIÓN: " + puntos, panelDeJuego.getWidth() / 2 - 130, (panelDeJuego.getHeight() / 2) + 30);
		g.drawString("Tu tiempo: " + formato.format(tiempo), panelDeJuego.getWidth() / 2 - 120,
				(panelDeJuego.getHeight() / 2) + 70);
		g.drawString("Click Izquierdo: Volver al inicio", panelDeJuego.getWidth() / 2 - 180, (panelDeJuego.getHeight() / 2) + 120);
	}

	// Ejecuta los componentes del frame
	@Override
	public void ejecutarFrame() {
		try {
			Thread.sleep(25);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	// Cuando el usuario pulsa el raton, saltara a otra pantalla,la clase PanelJuego
	// actualizara la pantalla

	@Override
	public void pulsarRaton(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			ReproducirSonido("sonidos/pulsarBoton.wav");
			panelDeJuego.setPantalla(new PantallaInicial(panelDeJuego));
		}
	}

	@Override
	public void moverRaton(MouseEvent e) {
	}

	// Redimendiona el fondo cada vez que redimendionemos la ventana
	@Override
	public void redimensionar() {
		fondoEscalado = fondo.getScaledInstance(panelDeJuego.getWidth(), panelDeJuego.getHeight(),
				BufferedImage.SCALE_SMOOTH);
	}

	// Añade el fondo de la ventana a la pantalla
	private void rellenarFondo(Graphics g) {
		g.drawImage(fondoEscalado, 0, 0, null);
	}

	// Recibe una ruta de un fichero de audio,lo abrira y lo reproducira
	public void ReproducirSonido(String nombreSonido) {
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