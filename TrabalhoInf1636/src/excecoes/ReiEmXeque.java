/**
 * A exceção ReiEmXeque é lançada sempre
 * que um jogador tentar realizar uma jogada
 * que não é o suficiente para tirar seu rei
 * da posição de xeque.
 * 
 * @version 1.0
 * @author Alessandro e Marcelo
 * @since 1.0
 */

package excecoes;

public class ReiEmXeque extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ReiEmXeque()
	{
		super("Seu rei esta em xeque!!");
	}
}
