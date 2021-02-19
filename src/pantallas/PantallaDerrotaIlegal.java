package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
/**
* @author Mario Gracia Torres
* @version 1.0
* @since   1.0
* @see Esta clase implementa la interfaz Pantalla,y muestra al usuario que ha perdido
*/
import javax.swing.SwingUtilities;

import main.PanelJuego;
//Clase pantalla Victoria que se encarga de mostrar al jugador que ha perdido la partida,implementa la interfaz Pantalla
public class PantallaDerrotaIlegal implements Pantalla {
		//Atributos de PantallaDerrota
	Color colorLetraAviso = Color.RED;
	PanelJuego panelDeJuego;
	final Font fuenteGrande = new Font("", Font.BOLD, 35);
	final Font fuenteFinal = new Font("", Font.BOLD, 29);
	final Font fuenteAviso = new Font("", Font.BOLD, 22);
	private BufferedImage fondo;
	private Image fondoEscalado;
	private int puntos;
	private int idAnimal;
	private String advertencia = "";

	//Constructor,incicializa la pantalla pasandole como instancia la clase PanelJuego
	// Y La puntuacion a mostrar se le divide entre 5 como penalizacion
	public PantallaDerrotaIlegal(PanelJuego panel, int puntuacion,int id) {
		panelDeJuego = panel;
		inicializarPantalla(panel);
		this.puntos=puntuacion;
		idAnimal = id;
	}

	@Override
	public void run() {
	}
	//Para inicializar la pantalla de derrota,carga la imagen e instancia la clase PanelJuego
	@Override
	public void inicializarPantalla(PanelJuego panel) {
		this.panelDeJuego = panel;
		try {
			fondo = ImageIO.read(new File("imagenes/derrotaCiguena.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("PROBLEMAS AL CARGAR LAS IMÁGENES. FIN DEL PROGRAMA");
			System.exit(1);
		}
		redimensionar();
	}
	//usando Graphics,pintara por la pantalla los atributos pasados
	//y teniendo una forma de posicionarlos en la pantalla
	//Dependiendo del animal abatido como especie protegida,se le mostrara al jugador una frase diferente
	@Override
	public void pintarPantalla(Graphics g) {
		rellenarFondo(g);
		switch(idAnimal){
			case 1:
			 advertencia = "¡NO CAZES CIGÜEÑAS, ABATIR ESPECIES PROTEGIDAS ES UN DELITO!";
			break;
			case 6:
			advertencia  = "¡NO  CAZES AVES RAPACES, ES MUCHO PEOR QUE ABATIR CIGÜEÑAS CONDENANDOTE A PENA DE CARCEÑ!";
			break;
			case 10:
			advertencia  = "¡NO CAZES LOBOS, ABATIR ESPECIES PROTEGIDAS ES UN DELITO!";
			break;
			case 11:
			advertencia  = "¡¡NO CAZES OSOS, ABATIR ESPECIES PROTEGIDAS ES UN DELITO!";
			break;
			case 12:
			advertencia = "¡¡NI SE TE OCURRA CAZAR A UN LINCE, ABATIR UN LINCE ES PENA DE CARCEL!!";
			break;
		}
		g.setFont(fuenteGrande);
		g.setColor(colorLetraAviso);
		g.drawString("¡DERROTA!", panelDeJuego.getWidth() / 2 - 70, (panelDeJuego.getHeight()/2) - 100);
		g.setFont(fuenteFinal);
		g.drawString("PUNTUACIÓN: " + puntos, panelDeJuego.getWidth() / 2 - 75, (panelDeJuego.getHeight()/2) - 10);
		g.setFont(fuenteAviso);
		g.setColor(colorLetraAviso);
		g.drawString(advertencia, panelDeJuego.getWidth() / 2 - 380, (panelDeJuego.getHeight()/2) + 90);
		g.setFont(fuenteFinal);
		g.drawString("Click Izquierdo: Volver al inicio ", panelDeJuego.getWidth() / 2 - 150, (panelDeJuego.getHeight()/2) + 180);
	
	}
//Ejecuta los componentes del frame
	@Override
	public void ejecutarFrame() {
		try {
			Thread.sleep(25);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
//Cuando el usuario pulsa el raton, saltara a la pantalla del titulo,la clase PanelJuego actualizara la pantalla
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

	@Override
	public void redimensionar() {
		fondoEscalado = fondo.getScaledInstance(panelDeJuego.getWidth(), panelDeJuego.getHeight(),
				BufferedImage.SCALE_SMOOTH);
	}

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
