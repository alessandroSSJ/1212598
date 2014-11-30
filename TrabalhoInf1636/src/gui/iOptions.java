/** O objeto iOptions é responsável por
 * informar ao usuário informações sobre o jogo,
 * tal como peças comidas e a vez atual. Além disso,
 * mostrará opções como salvar jogo, retornar ao menu inicial
 * e sair da partida.
 * 
 * AINDA ESTÁ DESLIGADO!!
 * 
 * @version 1.0
 * @author Alessandro e Marcelo
 * @since 1.0
 */

package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class iOptions extends JLayeredPane {
	
	private static final long serialVersionUID = 1L;
	
	/** Dimensões da interface */
	private static final int HEIGHT = 50  ;
	private static final int WIDTH = 140   ;
	
	/**  Botões retornar menu inicial, salvar jogo e sair */
	private JButton menuInicial ;
	private JButton salvarJogo ;
	private JButton sair;
	
	/** Overriding na classe paintComponent */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		setLayout(new BorderLayout());
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(5,0));
		
		menuInicial = new JButton("Retornar ao menu inicial");
		salvarJogo = new JButton("Salvar o jogo");
		sair = new JButton("Sair do jogo");
		
		menuInicial.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		salvarJogo.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		sair.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		
		buttons.add(menuInicial);
		buttons.add( Box.createVerticalStrut(5) );
		buttons.add(salvarJogo);
		buttons.add( Box.createVerticalStrut(60) );
		buttons.add(sair);
		
		this.add("North",buttons);
		//this.add("South",pecasComidas);
		
	}
	
	public static int getLargura()
	{
		return WIDTH;
	}
	
}
	