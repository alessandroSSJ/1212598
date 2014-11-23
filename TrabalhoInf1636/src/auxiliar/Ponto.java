/**
 * Um objeto do tipo Ponto implementa literalmente um ponto 
 * do espaço do tabuleiro, ou seja, cada ponto ilustra um 
 * quadrado do mesmo.
 *  
 * @version 1.0
 * @author Alessandro e Marcelo
 * @since 1.0
 */
package auxiliar;

public class Ponto {

	private int x;
	private int y;
	
	public Ponto(int x, int y)
	{
		this.x = x ;
		this.y = y ;
	}
	
	/** Retorna a coordenada x do ponto */
	public int getX()
	{
		return this.x;
	}
	
	/** Retorna a coordenada y do ponto */
	public int getY()
	{
		return this.y;
	}
	
	/** Muda a coordenada y do ponto */
	public void setY( int y)
	{
		this.y = y;
	}
	
	/** Muda a coordenada x do ponto */
	public void setX( int x)
	{
		this.x = x;
	}
	
	/** Verifica se os pontos são vizinhos no plano */
	public boolean Vizinho(Ponto pt)
	{
		if ( pt.x - this.x > 1 || pt.x - this.x < -1 )
			return false;
		if ( pt.y - this.y > 1 || pt.y - this.y < -1  )
			return false;
		return true;
	}
	
	/** Verifica se os pontos são alinhados verticalmente ou horizontalmente */
	public boolean AlinhadoVH(Ponto pt)
	{
		if(this.x == pt.x || this.y == pt.y)
			return true;
		return false;
	}
	
	/** Verifica se os pontos são alinhados por uma reta de inclinação 1 */
	public boolean AlinhadoIncl(Ponto pt)
	{
		float deltaY = pt.y-this.y ;
		float deltaX = pt.x-this.x ;
		
		if ( deltaY / deltaX == 1 || deltaY / deltaX == -1 )
			return true;
		return false;
	}
	
	/** Verifica se os pontos estão alinhados em relação a um L*/
	public boolean AlinhadoL(Ponto pt)
	{
		int distX = this.x - pt.x ;
		int distY = this.y - pt.y ;
		
		if ( (distX == 2 || distX == -2) && (distY == 1 || distY == -1) )
			return true;
		if ( (distX == 1 || distX == -1) && (distY == 2 || distY == -2) )
			return true;
		return false;
	}

}
