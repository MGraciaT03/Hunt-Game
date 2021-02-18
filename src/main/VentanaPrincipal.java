package main;

import java.awt.GridLayout;
import javax.swing.JFrame;

/**
* @author Mario Gracia Torres
* @version 1.0
* @since   1.0
* @see Esta clase implementa el frame principal y hace la instancia a la clase PanelJuego
*/
public class VentanaPrincipal {

//Atributos de la clase VentanaPrincipal
	JFrame ventana;
	PanelJuego panelJuego;
	//Constructor de la clase VentanaPrincipak
	public VentanaPrincipal() {
		ventana = new JFrame();
		ventana.setBounds(100, 50, 800, 550);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	//Crea un GridLayout,y crea el objeto PanelJuego y lo añade al frame
	public void inicializarComponentes(){	
		ventana.setLayout(new GridLayout(1,1));
		panelJuego = new PanelJuego();
		ventana.add(panelJuego);
	}
	
	public void inicializarListeners(){
		
	}
		//Este metodo lo hace visible y muestra la instancia de PanelJuego
	public void inicializar(){
		ventana.setVisible(true);
		inicializarComponentes();	
		inicializarListeners();		
	}
}