package main;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;
import pantallas.Pantalla;
import pantallas.PantallaInicial;
/**
* @author Mario Gracia Torres
* @version 1.0
* @since   1.0
* @see Esta clase lleva la logistica del juego y se encarga de implementar los hilos
*		Y lanzar las diferentes pantallas del juego
**/

public class PanelJuego extends JPanel implements Runnable {
	//Atributos de la clase PanelJuego
	
	private static final long serialVersionUID = 1753104767350913679L;
	Pantalla pantallaEjecucion;

	public PanelJuego() {
		//creara la intancia a PantallaInicial
		pantallaEjecucion = new PantallaInicial(this);

		//lanzara un hilo la propia clase
		new Thread(this).start();
		
		//si se pulsa el raton llamara al metodo de la clase PantallaInicial
		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				pantallaEjecucion.pulsarRaton(e);

			}
		});

		//Se encarga de llamar al metodo redimensionarPantalla
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				pantallaEjecucion.redimensionar();
			}
		});
		//Se encarga de llamar al metodo moverRaton,cuando el raton se mueva y se arrastre
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				pantallaEjecucion.moverRaton(e);
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				pantallaEjecucion.moverRaton(e);
			}
		});
	}
	//Metodo que pintara la pantalla de ejecucion

	@Override
	public void paintComponent(Graphics g) {
		pantallaEjecucion.pintarPantalla(g);
	}

	//Metodo para ejecutar la pantalla del juego
	@Override
	public void run() {
		pantallaEjecucion.inicializarPantalla(this);
		while (true) {
			repaint();
			Toolkit.getDefaultToolkit().sync();
			pantallaEjecucion.ejecutarFrame();
		}

	}
	//metodo que actualizara la interfaz pantalla del PanelJuego
	public void setPantalla(Pantalla pantallaJuego) {
		this.pantallaEjecucion=pantallaJuego;
	}
}
