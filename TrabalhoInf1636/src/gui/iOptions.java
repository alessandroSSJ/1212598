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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import engine.Peca;
import engine.Tabuleiro;

public class iOptions extends JLayeredPane {
	
	private static final long serialVersionUID = 1L;
	
	/** Dimensões da interface */
	private static final int HEIGHT = 50  ;
	private static final int WIDTH = 250   ;
	
	/** Mensagens */
	public static final int MENU = 1;
	public static final int SAIR = 2;
	public static final int SALVAR = 3;
	public static final int IND = 4;
	
	private static int option = IND;
	
	/**  Botões retornar menu inicial, salvar jogo e sair */
	private JButton menuInicial ;
	private JButton salvarJogo ;
	private JButton sair;
	
	/** JPanels */
	private JPanel buttons;
	private iPecasComida pecasComida;
	private JLabel mess ;
	
	public iOptions()
	{
		super();
	
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(Color.black));
		buttons = new JPanel();
		buttons.setLayout(new GridLayout(5,0));
		
		menuInicial = new JButton("Retornar ao menu inicial");
		menuInicial.addActionListener(new ActionListener() { 
		public void actionPerformed(ActionEvent e)
		      {
				option = MENU;
				iConfirmation con = iConfirmation.getWindow("Você deseja voltar ao menu?");
				con.getResponse();
		      }
		});
		
		salvarJogo = new JButton("Salvar o jogo");
		
		salvarJogo.addActionListener(new ActionListener() { 
		public void actionPerformed(ActionEvent e)
		{
			JFileChooser fileChooser = new JFileChooser();
			
			if (fileChooser.showSaveDialog(salvarJogo) == JFileChooser.APPROVE_OPTION) {
			  File file = fileChooser.getSelectedFile();
			  FileWriter out = null;
			  
			  try
			  {
				  out = new FileWriter(file); 
			  }
			  catch (Exception excecao)
			  {
				  excecao.printStackTrace();
			  }
				  
			  try
			  {
					  /*  ESCREVENDO O TABULEIRO NO ARQUIVO */
					  
					
				int numLinhas = Tabuleiro.getLinhas();
				int numColunas = Tabuleiro.getColunas();
				int j;
				int i;
					
				for ( j = 0 ; j < numLinhas ; j++ )
				{
					for ( i = 0 ; i < numColunas ; i++)
					{
						Peca atual = Tabuleiro.getPeca(j,i);
						if ( atual == null )
							out.write("-1#");
						else if ( atual.getTipo() == "bispo" )
							out.write("B"+atual.getLado()+"#");
						else if (atual.getTipo() == "cavalo")
							out.write("C"+atual.getLado()+"#");
						else if (atual.getTipo() == "dama")
							out.write("D"+atual.getLado()+"#");
						else if (atual.getTipo() == "torre")
						{
							String movimentou;
							
							if ( atual.getSpecial() )
								movimentou = "1";
							else
								movimentou = "0";
							
							out.write("T"+atual.getLado()+movimentou+"#");
						}
						else if (atual.getTipo() == "rei")
						{
							String movimentou;
							String xeque;
							
							char lado = atual.getLado();
							
							if ( atual.getSpecial() )
								movimentou = "1";
							else
								movimentou = "0";
							
							if ( lado == 'b' )
							{
								if ( Tabuleiro.getXequeReiBranco() )
									xeque = "1";
								else
									xeque = "0";
							}
							else
							{
								if ( Tabuleiro.getXequeReiPreto() )
									xeque = "1";
								else
									xeque = "0";
							}
							
								
							out.write("R"+lado+movimentou+xeque+"#");
						}
						else if (atual.getTipo() == "peao")
						{
							String pulou;
							
							if ( atual.getSpecial() )
								pulou = "1";
							else
								pulou = "0";
							
							out.write("P"+atual.getLado()+pulou+"#");
							
						}
					}
					
				}
				
				out.write(Tabuleiro.getVez());
				out.write("#");
				out.write(Integer.toString(Tabuleiro.getNumRodadas()));
				out.write("#");
					
					
					/* FIM */
					
				out.close();
			} 
			catch (IOException e1)
			{
				e1.printStackTrace();
			}

		}
			
		    option = SALVAR;
		    
		}
		
		});
		
		sair = new JButton("Sair do jogo");
		sair.addActionListener(new ActionListener() { 
		public void actionPerformed(ActionEvent e)
		 {
			iConfirmation con = iConfirmation.getWindow("Você deseja sair do jogo?");
			con.getResponse();
			option = SAIR;
		 }
		
		});
		
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
	
	public static int readOption()
	{
		return option;
	}
	
	public static void zeraOption()
	{
		option = IND;
	}
	
	
	
}
	