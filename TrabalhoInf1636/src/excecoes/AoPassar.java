/**
 * A exceção ao passar é lançada sempre
 * que um peão esteja apto e o jogador
 * execute a movimentação especial ao passar.
 * 
 * @version 1.0
 * @author Alessandro e Marcelo
 * @since 1.0
 */

package excecoes;

public class AoPassar extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AoPassar()
	{
		super("Movimentação ao passar do peao");
	}
}
