package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.MouseEvent;

import javax.imageio.ImageIO;

/**
* @author Mario Gracia Torres
* @version 1.0
* @since   1.0
* @see Esta clase se encarga la creacion de Sprites en el juego
*/
//Clase Sprite
public class Sprite {
	//Atributos
	protected int id;
	protected int posX;
	protected int posY;
	protected int ancho;
	protected int alto;
	protected int velX;
	protected int velY;
	protected BufferedImage buffer;

//Constructor de la clase Sprite
	private Sprite(int id,int posX, int posY, int ancho, int alto, int velX, int velY) {
		this.id = id;
		this.posX = posX;
		this.posY = posY;
		this.ancho = ancho;
		this.alto = alto;
		this.velX = velX;
		this.velY = velY;
	}
//Este Sprite puede pintar el color que se le pase y sus caracteristicas
	public Sprite(int id,int posX, int posY, int ancho, int alto, int velX, int velY, Color color) {
		this(id,posX, posY, ancho, alto, velX, velY);
		pintarBuffer(color);
	}
//Este Sprite puede pintar la ruta que se le pase y sus caracteristicas
	public Sprite(int id,int posX, int posY, int ancho, int alto, int velX, int velY, String ruta) {
		this(id,posX, posY, ancho, alto, velX, velY);
		pintarBuffer(ruta);
	}
//Este Sprite puede pintar la imagen que se le pase y sus caracteristicas
	public Sprite(int id,int posX, int posY, int ancho, int alto, int velX, int velY, Image imgConstructor,
			boolean redimensionar) {
		this(id,posX, posY, ancho, alto, velX, velY);
		pintarBuffer(imgConstructor, redimensionar);
	}

	//Metodos de pintar el buffer ya sea por color,por imagen o escalando la imagen pasada por parametros
	private void pintarBuffer(Color color) {
		buffer = new BufferedImage(this.ancho, this.alto, BufferedImage.TYPE_INT_ARGB);
		Graphics g = buffer.getGraphics();
		g.setColor(color);
		g.fillRect(0, 0, this.ancho, this.alto);
		g.dispose();
	}
	

	private void pintarBuffer(String ruta) {
		buffer = new BufferedImage(this.ancho, this.alto, BufferedImage.TYPE_INT_ARGB);
		Graphics g = buffer.getGraphics();
		try {
			BufferedImage img = ImageIO.read(new File(ruta));
			g.drawImage(img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH), 0, 0, null);

		} catch (IOException e) {
			e.printStackTrace();
		}
		g.dispose();
	}

	private void pintarBuffer(Image imgConstructor, boolean redimensionar) {
		buffer = new BufferedImage(this.ancho, this.alto, BufferedImage.TYPE_INT_ARGB);
		Graphics g = buffer.getGraphics();
		g.drawImage(redimensionar ? imgConstructor.getScaledInstance(this.ancho, this.alto, Image.SCALE_SMOOTH)
				: imgConstructor, 0, 0, null);
		g.dispose();
	}
//Actualiza la posicion de los sprites haciendo que se muevan,tambien les otorga una velocidad
//para hacer que se muevan a lo largo de la pantalla 
	public void actualizarPosicion(PanelJuego panelJuego) {

		if (posX + ancho >= panelJuego.getWidth()) {
			velX = -Math.abs(velX);
		}
		if (posX < 0) {
			velX = Math.abs(velX);
		}
		if (posY + alto >= panelJuego.getHeight()) {
			velY = -Math.abs(velY);
		}
		if (posY < 0) {
			velY = Math.abs(velY);
		}

		posX = posX + velX;
		posY = posY + velY;
	}
//metodo que se encarga de detectar si el sprite rewcibe dentro una pulsacion del raton
//retorna si en las dos posiciones hay un MouseListener dentro del sprite
	public boolean clicRaton(MouseEvent e){
		boolean pulsacionX =  e.getX() > posX && e.getX() < posX + ancho;

		boolean pulsacionY =  e.getY() > posY && e.getY() < posX + alto;

		return pulsacionX && pulsacionY;
	}

//pinta el buffer de la imagen en el juego
	public void pintarEnMundo(Graphics g) {
		g.drawImage(buffer, posX, posY, null);
	}
//metodo que aplica una velocidad dependiendo de la que tenga el sprite
//haciendo que estas se muevan a la derecha 
	public void aplicarVelocidad() {
		posX -= velX;
		posY -= velY;

	}
//Getters and Setters de la clase Sprite
	public int getVelX() {
		return velX;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public int getVelY() {
		return velY;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int getAlto() {
		return alto;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}

	public BufferedImage getBuffer() {
		return buffer;
	}

	public void setBuffer(BufferedImage buffer) {
		this.buffer = buffer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
