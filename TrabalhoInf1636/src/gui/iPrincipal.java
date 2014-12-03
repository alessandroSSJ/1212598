/**
 * O objeto iPrincipal (interface de um tabuliro) é responsável pelas
 * opções iniciais do jogador na partida.
 * 
 * @version 1.0
 * @author Alessandro e Marcelo
 * @since 1.0
 */

package gui;

import java.awt.* ;
import java.awt.event.*;

import javax.swing.*;

import threadsxadrez.MenuThread;



public class iPrincipal extends JFrame {

private static final long serialVersionUID = 1L;
	
	/** Dimensões reais de um tabuleiro de xadrez */
	private static final int HEIGHT =  300;
	private static final int WIDTH =   300;
	
	/** Constantes de inicialização */
	public static final int IND = 0;
	public static final int INICIAR_UM_JOGO = 1 ;
	public static final int CARREGAR_UM_JOGO = 2;
	public static final int SAIR_DO_JOGO = 3 ;
	
	/** Inidica as opções de inicialização */
	private static int initOption = 0 ;
	
	
	/** Opções */
	
	public iPrincipal()
	{
		super("Menu Principal") ;
		setLayout(new GridLayout(3,0));
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		
		Dimension screenSize=tk.getScreenSize();
		
		int x = screenSize.width/2 - WIDTH/2   ;
		int y = screenSize.height/2 - HEIGHT/2 ;
		setBounds(x,y,WIDTH,HEIGHT); /* Posiciona o tabuleiro no meio da tela do monitor */
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setResizable(false);
		
	}
	
	public void DesenhaBotoes()
	{
		JButton iniciar = new JButton("Iniciar um jogo");
		iniciar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
		      {
				setInitializationOptions(INICIAR_UM_JOGO);
		      }
		});
		
		JButton carregar = new JButton("Carregar um jogo");
		carregar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
		      {
				setInitializationOptions(CARREGAR_UM_JOGO);
		      }
		});
		
		JButton sair = new JButton("Sair do jogo");
		sair.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
		      {
				setInitializationOptions(SAIR_DO_JOGO);
		      }
		});
		
		this.add(iniciar);
		this.add(carregar);
		this.add(sair);
		this.setVisible(true);
	}
	
	public static void StartGame()
	{
		iPrincipal princ = new iPrincipal();
		MenuThread.getMenu(princ);
		princ.DesenhaBotoes();
	}
	
	public void Close()
	{
		this.setVisible(false);
		this.dispose();
	}
	
	public static void setInitializationOptions(int index)
	{
		initOption = index;
	}
	public static int getInitializationOptions()
	{
		return initOption;
	}
	
	
}