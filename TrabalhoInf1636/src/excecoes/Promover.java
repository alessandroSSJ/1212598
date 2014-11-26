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

import auxiliar.Ponto;

public class Promover extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Ponto comer;
	
	public Promover()
	{
		super("Promoção de peão");
		comer = null;
	}
	
	public Promover(Ponto p)
	{
		super("Promoção de peão");
		comer = p;
	}
	
	public Ponto getPecaComida()
	{
		return comer;
	}
}
