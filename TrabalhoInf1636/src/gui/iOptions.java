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

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLayeredPane;

public class iOptions extends JLayeredPane {
	
	private static final long serialVersionUID = 1L;
	
	/** Dimensões da interface */
	private static final int HEIGHT = 80  ;
	private static final int WIDTH = 80   ;
	
	/**  Botões retornar menu inicial, salvar jogo e sair */
	private JButton menuInicial ;
	private JButton salvarJogo ;
	private JButton sair;
	
	/** Overriding na classe paintComponent */
	public void Pinta()
	{
		setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 20));
		
		menuInicial = new JButton("Retorna ao menu inicial");
		salvarJogo = new JButton("Salvar o jogo");
		sair = new JButton("Sair do jogo");
		
		
		this.add(sair);
		
	}
	
}
	