package pantallas;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import main.PanelJuego;
//Interfaz que se puede implementar en otras clases constando de unos metodos 
//para su uso dependiendo de la pantalla que se utiliza,esta interfaz implementa Runnable
public interface Pantalla extends Runnable {

	public void inicializarPantalla(PanelJuego panel);
	public void pintarPantalla(Graphics g);
	public void ejecutarFrame();
	
	public void pulsarRaton(MouseEvent e);	
	public void moverRaton(MouseEvent e);	
	public void redimensionar();
	
}
