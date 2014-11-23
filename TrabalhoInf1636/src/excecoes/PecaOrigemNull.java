/**
 * A exceção PeçaOrigemNull é lançada
 * sempre que um jogador tente clicar
 * num espaço vazio do tabuleiro.
 * 
 * @version 1.0
 * @author Alessandro e Marcelo
 * @since 1.0
 */

package excecoes;

public class PecaOrigemNull extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PecaOrigemNull()
	{
		super("Espaço vazio");
	}
}