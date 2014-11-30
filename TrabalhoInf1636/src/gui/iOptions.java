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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class iOptions extends JLayeredPane {
	
	private static final long serialVersionUID = 1L;
	
	/** Dimensões da interface */
	private static final int HEIGHT = 50  ;
	private static final int WIDTH = 250   ;
	
	/**  Botões retornar menu inicial, salvar jogo e sair */
	private JButton menuInicial ;
	private JButton salvarJogo ;
	private JButton sair;
	
	/** JPanels */
	JPanel buttons;
	iPecasComida pecasComida;
	JLabel mess ;
	
	public iOptions()
	{
		super();
	
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(Color.black));
		buttons = new JPanel();
		buttons.setLayout(new GridLayout(5,0));
		
		menuInicial = new JButton("Retornar ao menu inicial");
		menuInicial.setBackground(Color.GREEN);
		
		salvarJogo = new JButton("Salvar o jogo");
		salvarJogo.setBackground(Color.green);
		
		sair = new JButton("Sair do jogo");
		sair.setBackground(Color.green);
		
		menuInicial.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		salvarJogo.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		sair.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		
		mess = new JLabel("        Pecas perdidas");
		mess.setFont(new Font("Papyrus", Font.ITALIC, 20));
		
		pecasComida = new iPecasComida();
		
	}
	
	/** Overriding na classe paintComponent */
	public void adiciona()
	{	
		buttons.add(menuInicial);
		buttons.add(salvarJogo);
		buttons.add(sair);
		
		this.add("North",buttons);
		this.add("South",mess);
		this.add(pecasComida);
		
	}
	
	public static int getLargura()
	{
		return WIDTH;
	}
	
	
}
	