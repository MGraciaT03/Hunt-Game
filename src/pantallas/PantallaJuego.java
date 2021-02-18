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
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.SwingUtilities;

import main.PanelJuego;
import main.Sprite;

/**
 * @author Mario Gracia Torres
 * @version 1.0
 * @since 1.0
 * @see Esta clase implementa la interfaz del juego y se encarga principalmente
 *      de crear los componentes de la pantalla,haciendo instancia a la clase
 *      PanelJuego
 */

// clase PantallaJuego,que implementa la interfaz Pantalla
public class PantallaJuego implements Pantalla {
	private static ArrayList<Sprite> animalesLista;
	private static Sprite[] animales;
	private static final int ANIMALES_INICIALES = 20;
	private static final int LADO_ANIMAL = 190;
	private static final int LADO_CURACION = 80;
	private Random rd = new Random();
	private static Sprite ciguena, pato, perdiz, faisan, paloma,ciervo,jabali,aguila,lobo,oso,zorro,lince, curacion;
	private int animalesVivos;
	private static Sprite escopeta;
	private Image imagenEscopeta, imagenPato, imagenPaloma, imagenCiguena, imagenFaisan, imagenPerdiz,imagenCiervo,imagenJabali,imagenAguila,imagenLobo,imagenOso,imagenZorro,imagenLince,imagenCuracion;
	private static final int ANCHO_ESCOPETA = 50;
	private static final int MAX_BALAS = 20;
	private double tiempoInicial = 0;
	private DecimalFormat formato = new DecimalFormat("#.##");
	final Font fuenteTiempo = new Font("", Font.BOLD, 30);
	final Font fuentePuntos = new Font("", Font.BOLD, 30);
	final Font fuenteVidas = new Font("", Font.BOLD, 30);
	private Color colorVidas = Color.RED;
	private BufferedImage fondo;
	private Image fondoEscalado;
	private PanelJuego panelDeJuego;
	private double tiempo;
	private int puntuacion, vidas;
	private int entrePajaro = 3;
	private int contadorBalas;
	private int entreCuracion = 27;

	// Constructor de la clase PantallaJuego
	//Carga las imagenes de los animales,las redimensiona y crea los Sprites de la escopeta y los animales
	//A cada Sprite se le asigna un id y una posicion diferente y velocidad  aleatoriamente
	public PantallaJuego(PanelJuego panel) {
		inicializarPantalla(panel);
		animalesLista = new ArrayList<>();
		try {
			fondo = ImageIO.read(new File("imagenes/terreno.png"));
			imagenEscopeta = ImageIO.read(new File("imagenes/mirilla.png"));
			imagenPato = ImageIO.read(new File("imagenes/pato.png"));
			imagenPaloma = ImageIO.read(new File("imagenes/paloma.png"));
			imagenPerdiz = ImageIO.read(new File("imagenes/perdiz.png"));
			imagenFaisan = ImageIO.read(new File("imagenes/faisan.png"));
			imagenCiguena = ImageIO.read(new File("imagenes/ciguena.png"));
			imagenCuracion = ImageIO.read(new File("imagenes/curacion.png"));
			imagenCiervo = ImageIO.read(new File("imagenes/ciervo.png"));
			imagenAguila = ImageIO.read(new File("imagenes/aguila.png"));
			imagenJabali= ImageIO.read(new File("imagenes/jabali.png"));
			imagenLobo = ImageIO.read(new File("imagenes/lobo.png"));
			imagenOso = ImageIO.read(new File("imagenes/oso.png"));
			imagenLince = ImageIO.read(new File("imagenes/lince.png"));
			imagenZorro = ImageIO.read(new File("imagenes/zorro.png"));
			imagenAguila = ImageIO.read(new File("imagenes/aguila.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("PROBLEMAS AL CARGAR LAS IMÁGENES. FIN DEL PROGRAMA");
			System.exit(1);
		}
		imagenPato = imagenPato.getScaledInstance(LADO_ANIMAL, LADO_ANIMAL, Image.SCALE_SMOOTH);
		imagenCiguena = imagenCiguena.getScaledInstance(LADO_ANIMAL, LADO_ANIMAL, Image.SCALE_SMOOTH);
		imagenPaloma = imagenPaloma.getScaledInstance(LADO_ANIMAL, LADO_ANIMAL, Image.SCALE_SMOOTH);
		imagenPerdiz = imagenPerdiz.getScaledInstance(LADO_ANIMAL, LADO_ANIMAL, Image.SCALE_SMOOTH);
		imagenFaisan = imagenFaisan.getScaledInstance(260, LADO_ANIMAL, Image.SCALE_SMOOTH);
		imagenAguila = imagenAguila.getScaledInstance(260, LADO_ANIMAL, Image.SCALE_SMOOTH);
		imagenLobo = imagenLobo.getScaledInstance( 150, 120, Image.SCALE_SMOOTH);
		imagenOso = imagenOso.getScaledInstance(LADO_ANIMAL, LADO_ANIMAL, Image.SCALE_SMOOTH);
		imagenJabali = imagenJabali.getScaledInstance(280, LADO_ANIMAL, Image.SCALE_SMOOTH);
		imagenLince = imagenLince.getScaledInstance(280, LADO_ANIMAL, Image.SCALE_SMOOTH);
		imagenCiervo = imagenCiervo.getScaledInstance(LADO_ANIMAL, LADO_ANIMAL, Image.SCALE_SMOOTH);
		imagenCuracion = imagenCuracion.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		imagenZorro = imagenZorro.getScaledInstance( 150, 120, Image.SCALE_SMOOTH);


		escopeta = new Sprite(0, -ANCHO_ESCOPETA - 60, -ANCHO_ESCOPETA - 10, ANCHO_ESCOPETA, ANCHO_ESCOPETA, 0, 0,imagenEscopeta, true);
		ciguena = new Sprite(1, panelDeJuego.getWidth(), panelDeJuego.getHeight()/5 * rd.nextInt(2),LADO_ANIMAL, LADO_ANIMAL, rd.nextInt(14) + 1, 0, imagenCiguena, false);
		pato = new Sprite(2, panelDeJuego.getWidth(),  panelDeJuego.getHeight()/5*  rd.nextInt(2), LADO_ANIMAL, LADO_ANIMAL, rd.nextInt(14) + 1, 0, imagenPato, false);
		paloma = new Sprite(3, panelDeJuego.getWidth(),  panelDeJuego.getHeight()/5*  rd.nextInt(2), LADO_ANIMAL, LADO_ANIMAL, rd.nextInt(14) + 1, 0, imagenPaloma, false);
		perdiz = new Sprite(4, panelDeJuego.getWidth(), panelDeJuego.getHeight() - LADO_ANIMAL, LADO_ANIMAL, LADO_ANIMAL, rd.nextInt(14) + 1, 0, imagenPerdiz, false);
		faisan = new Sprite(5, panelDeJuego.getWidth(),  panelDeJuego.getHeight()/5* rd.nextInt(2), LADO_ANIMAL, LADO_ANIMAL, rd.nextInt(14) + 1, 0, imagenFaisan, false);
		aguila = new Sprite(6, panelDeJuego.getWidth(),  panelDeJuego.getHeight()/5*  rd.nextInt(2), 280, LADO_ANIMAL, rd.nextInt(14) + 1, 0, imagenAguila, false);
		curacion = new Sprite(7,panelDeJuego.getWidth(),  panelDeJuego.getHeight()/5* rd.nextInt(2), LADO_CURACION, LADO_CURACION, rd.nextInt(14) + 1, 0, imagenCuracion, false);
		ciervo = new Sprite(8, panelDeJuego.getWidth(),  panelDeJuego.getHeight() - LADO_ANIMAL,280, LADO_ANIMAL, rd.nextInt(14) + 1, 0, imagenCiervo, false);
		zorro = new Sprite(9,panelDeJuego.getWidth(),  panelDeJuego.getHeight() - 120, 150, 120, rd.nextInt(14) + 1, 0, imagenZorro, false);
		lobo = new Sprite(10, panelDeJuego.getWidth(), panelDeJuego.getHeight() - 170,280, LADO_ANIMAL, rd.nextInt(14) + 1, 0, imagenLobo, false);
		oso = new Sprite(11, panelDeJuego.getWidth(),  panelDeJuego.getHeight() - 200, 280, 200, rd.nextInt(14) + 1, 0, imagenOso, false);
		lince = new Sprite(12, panelDeJuego.getWidth(),  panelDeJuego.getHeight()  - LADO_ANIMAL, 280, LADO_ANIMAL, rd.nextInt(14) + 1, 0, imagenLince, false);
		jabali = new Sprite(13, panelDeJuego.getWidth(), panelDeJuego.getHeight() - LADO_ANIMAL,280, LADO_ANIMAL, rd.nextInt(14) + 1, 0, imagenJabali, false);

		//Los añade a un vector y lo actualiza a la clase
		//Escala la imagen de fondo,y crea las variales vidas,contadoBalas y animales Vivos
		Sprite[] animales = { ciguena, pato, paloma, perdiz, faisan,aguila,ciervo,jabali,zorro,lobo,oso,lince};
		setAnimales(animales);
		fondoEscalado = fondo.getScaledInstance(panel.getWidth(), panel.getHeight(), BufferedImage.SCALE_SMOOTH);
		vidas = 3;
		contadorBalas = 0;
		animalesVivos = ANIMALES_INICIALES;
		
	}

	// Ejecuta el hilo del juego
	@Override
	public void run() {
		// TODO Auto-generated method stub
	}

	@Override
	public void inicializarPantalla(PanelJuego panel) {
		this.panelDeJuego = panel;
		tiempoInicial = System.nanoTime();
	}

	// Metodo que se encargara de rellenar de pintar por pantallas las imagenes
	// De los sprites como la mirilla de la escopeta,animales y el fondo de pantalla
	// del juego
	// Tambien se encarga de mostrar mediante Graphics,dibujando el tiempo y la vida
	// que tiene el jugador.
	// Y dependiendo de la cantidad de vida del jugador, mostrara un color,si parpadea el jugador solo le queda una vida
	@Override
	public void pintarPantalla(Graphics g) {
		rellenarFondo(g);
		for (int i = 0; i < animalesLista.size(); i++) {
			animalesLista.get(i).pintarEnMundo(g);

		}

		escopeta.pintarEnMundo(g);
		g.setFont(fuenteTiempo);
		g.setColor(Color.ORANGE);
		tiempo = ((System.nanoTime() - tiempoInicial) / 1e9);
		g.drawString(formato.format(tiempo), 50, 50);
		g.setFont(fuentePuntos);
		g.setColor(Color.YELLOW);
		g.drawString(puntuacion + "", (int) (panelDeJuego.getWidth() * 0.85), 50);
		g.setColor(Color.BLUE);
		g.drawString("Balas: " + contadorBalas + " /20", 40, 100);
		g.setFont(fuenteVidas);
		switch (vidas) {
			case 5:
			default: {
				g.setColor(Color.GREEN);
				break;
			}
			case 4: {
				g.setColor(Color.YELLOW);
				break;
			}
			case 3: {
				g.setColor(Color.ORANGE);
				break;
			}
			case 2: {
				g.setColor(Color.RED);
				break;
			}
			case 1: {
				colorVidas = colorVidas == Color.RED ? Color.YELLOW : Color.RED;
				g.setColor(colorVidas);
				break;
			}
		}
		g.drawString("Vidas restantes", (panelDeJuego.getWidth() / 2), 50);
		g.drawString("            " + vidas, (panelDeJuego.getWidth() / 2), 80);
	}

	// Meotodo en el que duerme un hilo
	// Y va llamando cada tiempo los animales, un sprite de curaion y se ira moviendo
	//por la pantalla
	@Override
	public void ejecutarFrame() {
		try {
			Thread.sleep(25);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		aparecenAnimales();
		apareceCuracion();
		moverSprites();
	}

	// Metodo que realiza estos detalles
	//El jugador para recargar las balas debera de pulsar el boton derecho del raton
	//Y erl iziquiero para disparar,si no tiene balas,no podra disparar 
	//y se ira haciendo sonidos cada vez que se pulse el raton

	//El jugador solo puede recargar balas,cuando aparezcan los animales a disparar
	//dependiendo del id de animal,si su id es 2,es un pato y hara un sonido
	//Si los animales son especies protegidas,se le saltara a una pantalla de derrota especial instatanea y se le pasa el id del animal
	//Cada vez qu se dispare a u animal permitido,se le sumara 5 puntos
	//si ese animal se escapa saliendo fuera de la pantalla,el jugador pierde una vida
	//Tambien si dispara a un objeto curativo,se le sumara una vida
	//El juego termina hasta que el usuario caze 20 animales permitidos
	//Si el jugador pierde todas las vidas,se le saltara a una pantalla de derrota
	@Override
	public void pulsarRaton(MouseEvent e) {
		String nombreSonido = "";
		if (SwingUtilities.isLeftMouseButton(e) && contadorBalas != 0) {
			if (contadorBalas >= 0) {
				reproducirSonido("sonidos/disparar.wav");
				contadorBalas--;
			} else {
				contadorBalas = 0;
			}
		} else {
			if (SwingUtilities.isLeftMouseButton(e) && contadorBalas == 0) {
				reproducirSonido("sonidos/nobalas.wav");
			}
		}

		if (SwingUtilities.isRightMouseButton(e)) {
			reproducirSonido("sonidos/recargar.wav");
			if (contadorBalas < MAX_BALAS) {
				contadorBalas++;
			}
		}
		for (int i = 0; i < animalesLista.size() && contadorBalas != 0; i++) {
			if (SwingUtilities.isLeftMouseButton(e) && animalesLista.get(i).clicRaton(e)) {
				if (animalesLista.get(i).getId() == 7) {
					nombreSonido = "sonidos/curacion.wav";
					animalesLista.get(i).setPosX(panelDeJuego.getWidth());
					animalesLista.remove(i);
					reproducirSonido(nombreSonido);
					vidas = vidas + 1;
				} else {
					if (animalesLista.get(i).getId() == 1 || animalesLista.get(i).getId() == 6 || animalesLista.get(i).getId() == 10 || animalesLista.get(i).getId() == 11 || animalesLista.get(i).getId() == 12) {
						panelDeJuego.setPantalla(new PantallaDerrotaIlegal(panelDeJuego, puntuacion,animalesLista.get(i).getId()));
					} else {
					if(animalesLista.get(i).getId() == 2) {
						nombreSonido = "sonidos/pato.wav";
						reproducirSonido(nombreSonido);
					}
						animalesLista.get(i).setPosX(panelDeJuego.getWidth());
						animalesLista.remove(i);
						animalesVivos--;
						puntuacion += 5;
					}
				}
			}
		}
		for (int i = 0; i < animalesLista.size(); i++) {
			if (animalesLista != null &&  animalesLista.get(i).getPosX() + animalesLista.get(i).getAncho() < 0){
				if (animalesLista.get(i).getId() != 1 && animalesLista.get(i).getId() != 6 && animalesLista.get(i).getId() != 7 && animalesLista.get(i).getId() != 10 && animalesLista.get(i).getId() != 11 && animalesLista.get(i).getId() != 12){
				if (vidas <= 0) {
						vidas = 0;
					} else {
						vidas = vidas - 1;
					 
					}
				}
				animalesLista.remove(i);
				}
			}
		
		if (vidas == 0) {
			panelDeJuego.setPantalla(new PantallaDerrota(panelDeJuego, puntuacion));
		} else {
			if (animalesVivos == 0) {
				panelDeJuego.setPantalla(new PantallaVictoria(panelDeJuego, puntuacion, tiempo));
			}
		}

	}

	// metodo que se encarga de que una vez la mirilla del arma este creada
	// el sprite de la escopeta ira siguiendo el puntero del raton
	@Override
	public void moverRaton(MouseEvent e) {
		escopeta.setPosX(e.getX() - escopeta.getAncho() / 2);
		escopeta.setPosY(e.getY() - escopeta.getAlto() / 2);
	}

	// Redimensiona el fondo del juego cada vez que cambiemos el tamaño de la
	// ventana
	@Override
	public void redimensionar() {
		fondoEscalado = fondo.getScaledInstance(panelDeJuego.getWidth(), panelDeJuego.getHeight(),
				BufferedImage.SCALE_SMOOTH);
	}

	// pinta el fondo de la pantalla del juego cada vez que redimensionamos la
	// pantalla
	private void rellenarFondo(Graphics g) {
		g.drawImage(fondoEscalado, 0, 0, null);
	}

	// Metodo que se encarga de actualizar las posiciones de los sprites
	// Si estan creados estos llamaran a un metodo de la clase Sprite
	private void moverSprites() {
		for (int i = 0; i < animalesLista.size(); i++) {
			animalesLista.get(i).aplicarVelocidad();
		}
	}
	//Cada 3 segundos en el tiempo del juego, se añadira un animal al azar en la lista
	//Y hara que este aparezca en el juego moviendose
	public void aparecenAnimales() {
		Random rd = new Random();
		int posicionAleatoria;
		if ((System.nanoTime() - tiempoInicial) / 1e9 >= entrePajaro){
			posicionAleatoria = rd.nextInt(getAnimales().length);
			getAnimalesLista().add(getAnimales()[posicionAleatoria]);
			entrePajaro += 3;
		}
	}
	//Cada 27 segundos añadira un objeto curativo en la lista
	//Y hara que este aparezca en el juego moviendose
		public void apareceCuracion() {
			if ((System.nanoTime() - tiempoInicial) / 1e9 >= entreCuracion){
				getAnimalesLista().add(curacion);
				entreCuracion += 27;
			}
	}

	// Recibe una ruta de un fichero de audio,lo abrira y lo reproducira
	public void reproducirSonido(String nombreSonido) {
		try {
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(new File(nombreSonido).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("Error al reproducir el sonido.");
		}
	}

	//Getters and Setters del ArrayList y del vector de Animales
	public static ArrayList<Sprite> getAnimalesLista() {
		return animalesLista;
	}

	public static void setAnimalesLista(ArrayList<Sprite> pajarosLista) {
		PantallaJuego.animalesLista = pajarosLista;
	}

	public static Sprite[] getAnimales() {
		return animales;
	}

	public static void setAnimales(Sprite[] aves) {
		PantallaJuego.animales = aves;
	}
}
