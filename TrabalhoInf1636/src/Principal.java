/**
 * Classe principal que instanciará todas as threads
 * necessárias para o funcionamento do jodo de xadrez.
 * 
 * @version 1.0
 * @author Alessandro e Marcelo
 * @since 1.0
 */


import threadsxadrez.*;

public class Principal {

	public static void main(String[] args) {
		
		TabThread tab = new TabThread();
		iTabThread iTab = new iTabThread() ;

		/* Inicializando as threads */
		
		tab.start();
		iTab.start();
		
	}

}
