/**
 * Um objeto do tipo Dama, representa a peça Dama
 * de um jogo de xadrez
 * 
 * @version 1.0
 * @author Alessandro e Marcelo
 * @since 1.0
 */

package engine;

import excecoes.AtacarPeca;
import auxiliar.Ponto;

public class Dama implements Peca {
	
	/** Representa qual lado esta peça é B de branco e P de preto */
	private char lado;
	
	/** Representa qual é a peça*/
	private static final String tipo = "dama";
	
	/** Ponto do espaço tabuleiro (quadrado) no qual a peça se encontra */
	private Ponto pt0;
	
	/** Ponto do espaço na qual peça comida está */
	private Peca comida;
	
	public Dama( char lado , int y , int x )
	{
		this.lado = lado ;
		
		pt0 = new Ponto( x , y ) ;
		
		comida = null;
	}
	
	public Dama( char lado , Ponto atual )
	{
		this.lado = lado ;
		
		pt0 = atual ;
		
		comida = null;
	}
	
	public boolean ChecaPosicionamento(int xFinal , int yFinal) throws AtacarPeca
	{	
		Ponto pFinal = new Ponto( xFinal , yFinal ) ;
				
		boolean pode = ( ( pt0.AlinhadoVH( pFinal ) || pt0.AlinhadoIncl(pFinal) ) && !PecaNoCaminho(xFinal , yFinal) )  ;
		
		if(pode && Tabuleiro.getPeca(yFinal , xFinal) != null)
			throw new AtacarPeca(xFinal , yFinal);
		
		return pode; 
	}
	
	public boolean ChecaMovimentoPeca(int xFinal , int yFinal)
	{
		boolean sePode = false;
		try
		{
			sePode = ChecaPosicionamento(xFinal , yFinal);
		}
		catch(AtacarPeca a)
		{
			comida = Tabuleiro.getPeca(yFinal , xFinal);
			return true;
		}
		
		comida = Tabuleiro.getPeca(yFinal , xFinal);
		return sePode;
	}
	
	public boolean VefXeque() 
	{
		Ponto posRei;
		Peca temp = comida;
		boolean sePode = false;
		
		if ( lado == 'b' )
		{
			posRei = Tabuleiro.getReiPreto();
		}
		else
		{
			posRei = Tabuleiro.getReiBranco();
		}
				
		try
		{
			sePode = ChecaPosicionamento(posRei.getX() , posRei.getY() ) ;
		}
		catch(AtacarPeca a)
		{	
			comida = temp;
			return true;
		}
		
		comida = temp;
		return sePode;
	}
	
	private boolean PecaNoCaminho(int xFinal, int yFinal)
	{
		Peca teste;
		
		int deltaY = yFinal - pt0.getY();
		int rY = Direcao(deltaY);
		
		int deltaX = xFinal - pt0.getX();
		int rX = Direcao(deltaX);
		
		int xTeste = pt0.getX() + rX ;
		int yTeste = pt0.getY() + rY ;
		
		teste = Tabuleiro.getPeca(yTeste , xTeste ) ;
		
		while( !(xTeste == xFinal && yTeste == yFinal) )
		{
			if ( teste != null )
				return true;
			
			xTeste += rX;
			yTeste += rY;
			teste = Tabuleiro.getPeca(yTeste , xTeste) ;
		}
		
		return false;
	}
	
	private int Direcao(int delta)
	{
		if ( delta == 0 )
			return 0;
		
		if ( delta < 0 )
			return -1;
		else
			return 1;
	}
	
	public void setPonto(int x, int y)
	{
		pt0.setX(x);
		pt0.setY(y);
	}
	
	public Ponto getPonto()
	{
		return pt0;
	}
	
	public char getLado()
	{
		return lado;
	}

	public String getTipo()
	{
		return tipo ;
	}
	
	public Peca pecaComida()
	{
		return comida;
	}
	
	/* Nada em especial para esta peça */
	public boolean getSpecial() { return true;}

}
