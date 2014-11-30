/**
 * Classe responsável por implementar a thread que irá lidar
 * com a parte gráfica do jogo.
 * 
 * @version 1.0
 * @author Alessandro e Marcelo
 * @since 1.0
 */

package threadsxadrez;

import gui.*;

public class iTabThread extends Thread {
	
	iTabuleiro iTab = null;
	
	public iTabThread()
	{
		super("Thread da interface do tabuleiro");
		iTab = new iTabuleiro();
		//iTab.MenuOpcoes();
		iTab.DrawPecas(TabThread.getTabuleiro());
		iTab.DrawBackground();	
	}
	
	/** Metodo run*/
	
	@Override
	public void run()
	{
		while(true)
		{	
			iTab.repaint();
	    }
	}
}
