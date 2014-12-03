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
	
	/** Flag */
	private boolean continua = true;
	
	iTabuleiro iTab = null;
	
	public iTabThread()
	{
		super("Thread da interface do tabuleiro");
		iTab = new iTabuleiro();
		iTab.MenuOpcoes();
		iTab.DrawPecas(TabThread.getTabuleiro());
		iTab.DrawBackground();
	}
	
	/** Metodo run*/
	
	@Override
	public void run()
	{
		while(continua)
		{	
			iTab.repaint();
			
			if (iOptions.readOption() == iOptions.MENU && iConfirmation.readResponse() == iConfirmation.OK)
			{
				iTab.setVisible(false);
				iTab.dispose();
				iTab = null;
				continua = false;
			}
	    }
	}

}
