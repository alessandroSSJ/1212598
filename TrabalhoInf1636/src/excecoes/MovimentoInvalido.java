/**
 * A exceção MovimentoInvalido é lançada
 * sempre que um jogador tente fazer um 
 * movimento (com suas próprias peças)
 * inválido.
 * 
 * @version 1.0
 * @author Alessandro e Marcelo
 * @since 1.0
 */

package excecoes;

public class MovimentoInvalido extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MovimentoInvalido()
	{
		super("Movimentação Invalida");
	}
}