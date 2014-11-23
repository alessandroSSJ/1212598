/**
 * A exceção PropriaPeca é lançada
 * sempre que um jogador de o seu
 * segundo click numa peça sua, ou seja
 * ou o mesmo errou o clique ou deseja trocar
 * a peça na qual executará o próximo movimento.
 * 
 * @version 1.0
 * @author Alessandro e Marcelo
 * @since 1.0
 */

package excecoes;

public class PropriaPeca extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PropriaPeca()
	{
		super("Trocando de Peça");
	}
}
