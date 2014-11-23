/**
 * A exceção RoqueDireita é lançada
 * sempre que um jogador tente rocar
 * a direita do seu rei.
 * 
 * @version 1.0
 * @author Alessandro e Marcelo
 * @since 1.0
 */

package excecoes;

public class RoqueDireita extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int xTorre;
	private int yTorre;
	
	public RoqueDireita(int x , int y)
	{
		super("Roque do rei para a direita");
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
