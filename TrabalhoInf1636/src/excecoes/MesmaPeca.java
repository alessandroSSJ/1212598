/**
 * A exceção MesmaPeca é lançada, sempre que
 * o jogador selecionar a mesma peça como 
 * peça de destino.
 * 
 * @version 1.0
 * @author Alessandro e Marcelo
 * @since 1.0
 */

package excecoes;

public class MesmaPeca extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MesmaPeca()
	{
		super("Desligando seleção");
	}
}
