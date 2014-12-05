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
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.*;

import observersxadrez.TabObserver;
import observersxadrez.iTabObserver;



public class iPrincipal extends JFrame {

private static final long serialVersionUID = 1L;
	
	/** Dimensões reais de um tabuleiro de xadrez */
	private static final int HEIGHT =  300;
	private static final int WIDTH =   300;
	
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
				IniciarJogo();
		      }
		});
		
		JButton carregar = new JButton("Carregar um jogo");
		carregar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
		      {
				CarregarJogo();
		      }
		});
		
		JButton sair = new JButton("Sair do jogo");
		sair.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
		      {
				System.exit(1);
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
		princ.DesenhaBotoes();
	}
	
	public void Close()
	{
		this.setVisible(false);
		this.dispose();
	}
	
	private void IniciarJogo()
	{

		TabObserver tab = new TabObserver();
		@SuppressWarnings("unused")
		iTabObserver iTab = new iTabObserver() ;

		/* Inicializando as threads */
	
		tab.start();
		iTabObserver.Notifica();
		close();
	}
	
	private void CarregarJogo()
	{
		
		JFileChooser fileChooser = new JFileChooser();
		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) 
		{
		  File file = fileChooser.getSelectedFile();
		  Scanner scanner = null;
		  
		  String[][] arquivo = new String[10][8];
		  
			try 
			{
				scanner = new Scanner(file);
			} 
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
		  
			scanner.useDelimiter("#");
		 
			int j;
			int i;
			
			for ( j = 0 ; j < 8 ; j++ )
			{
				for ( i = 0 ; i < 8 ; i++)
				{
					arquivo[j][i] = scanner.next();
				}
			}
			
			arquivo[8][0] = scanner.next();
			arquivo[9][0] = scanner.next();
			

			TabObserver tab = new TabObserver(arquivo);
			@SuppressWarnings("unused")
			iTabObserver iTab = new iTabObserver() ;

			/* Inicializando as threads */
		
			tab.start();
			iTabObserver.Notifica();
			close();
		}
		
	}
	
	private void close()
	{
		this.setVisible(false);
		this.dispose();
	}
}