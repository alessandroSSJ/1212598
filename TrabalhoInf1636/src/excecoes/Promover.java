/**
 * A exceção ao Promover é lançada
 * sempre que algum peão do jogo
 * tenha chegado à ultima casa de sua
 * vertical e esteja pronto para 
 * executar a movimentação especial
 * que é a promoção de peão
 * 
 * @version 1.0
 * @author Alessandro e Marcelo
 * @since 1.0
 */

package excecoes;

public class Promover extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Promover()
	{
		super("Promoção de peão");
	}
}
