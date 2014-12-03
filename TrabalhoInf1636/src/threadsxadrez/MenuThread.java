/**
 * Classe responsável por handle as entradas do usuário
 * 
 * @version 1.0
 * @author Alessandro e Marcelo
 * @since 1.0
 */

package threadsxadrez;


import gui.iPrincipal;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFileChooser;


public class MenuThread extends Thread{
	
	static iPrincipal iMenu;
	
	private static boolean continua = true;
	
	private static MenuThread menu = null;
	
	private MenuThread()
	{
		super("Thread do menu inicial");
	}
	
	public static MenuThread getMenu(iPrincipal princ)
	{
		continua = true;
		
		if(menu == null)
		{
			menu = new MenuThread();
			menu.start();
		}
		
		iMenu = princ;
		return menu;
	}
	
	/** Metodo run*/
	@Override public void run()
	{
		while(true)
		{
			System.out.printf("");
			if(continua)
			{
				if(iPrincipal.getInitializationOptions() == iPrincipal.INICIAR_UM_JOGO)
				{
					TabThread tab = new TabThread();
					iTabThread iTab = new iTabThread() ;
		
					/* Inicializando as threads */
				
					tab.start();
					iTab.start();
					iMenu.setVisible(false);
					iMenu.dispose();
					close();
				}
				else if (iPrincipal.getInitializationOptions() == iPrincipal.CARREGAR_UM_JOGO)
				{
					
					JFileChooser fileChooser = new JFileChooser();
					if (fileChooser.showOpenDialog(iMenu) == JFileChooser.APPROVE_OPTION) 
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
						

						TabThread tab = new TabThread(arquivo);
						iTabThread iTab = new iTabThread() ;
			
						/* Inicializando as threads */
					
						tab.start();
						iTab.start();
						
						iMenu.setVisible(false);
						iMenu.dispose();
						close();
					}
					iPrincipal.setInitializationOptions(iPrincipal.IND);
				}
				else if (iPrincipal.getInitializationOptions() == iPrincipal.SAIR_DO_JOGO)
				{
					System.exit(1);
				}
			}
		}
	}
	
	private void close()
	{
		iPrincipal.setInitializationOptions(iPrincipal.IND);
		continua = false;
	}

}