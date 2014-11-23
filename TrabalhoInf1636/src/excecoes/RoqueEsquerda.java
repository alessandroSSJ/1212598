/**
 * A exceção RoqueEsquerda é lançada
 * sempre que um jogador tente rocar
 * a esquerda do seu rei.
 * 
 * @version 1.0
 * @author Alessandro e Marcelo
 * @since 1.0
 */

package excecoes;

public class RoqueEsquerda extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int xTorre;
	private int yTorre;

	public RoqueEsquerda(int x , int y)
	{
		super("Roque do rei para a esquerda");
		xTorre = x ;
		yTorre = y ;
	}
	
	public int getX()
	{
		return xTorre;
	}
	
	public int getY()
	{
		return yTorre;
	}
}
