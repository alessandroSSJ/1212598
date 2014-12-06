/**
 * Classe responsável por implementar a thread que irá lidar
 * com a parte gráfica do jogo.
 * 
 * (IMPLEMENTA PADRÃO DE DESIGN OBSERVER)
 * 
 * @version 2.0
 * @author Alessandro e Marcelo
 * @since 1.0
 */

package observersxadrez;

import gui.iConfirmation;
import gui.iOptions;
import gui.iTabuleiro;

public class iTabObserver {
	
	private static iTabuleiro iTab ;
	
	public iTabObserver()
	{
		iTab = new iTabuleiro();
		iTab.MenuOpcoes();
		iTab.DrawPecas(TabObserver.getTabuleiro());
		iTab.DrawBackground();
	}
	
	/** Metodo run*/
	
	public static void Notifica()
	{
		iTab.repaint();

		if (iOptions.readOption() == iOptions.MENU && iConfirmation.readResponse() == iConfirmation.OK)
		{
			iConfirmation.close();
			iOptions.zeraOption();
			iTab.setVisible(false);
			iTab.dispose();
			iTab = null;
		}
	}

}
