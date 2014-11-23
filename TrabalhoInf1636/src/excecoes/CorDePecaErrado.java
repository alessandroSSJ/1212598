/**
 * A exceção CorDePecaErrada é lançada
 * sempre que um jogador tente movimentar
 * as peças do aversário.
 * 
 * @version 1.0
 * @author Alessandro e Marcelo
 * @since 1.0
 */

package excecoes;

public class CorDePecaErrado extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CorDePecaErrado()
	{
		super("Não é sua vez");
	}
}