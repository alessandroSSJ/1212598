/**
 * Um objeto do tipo Torre, representa a peça Torre
 * de um jogo de xadrez
 * 
 * @version 1.0
 * @author Alessandro e Marcelo
 * @since 1.0
 */

package engine;

import auxiliar.Ponto;
import excecoes.AtacarPeca;

public class Torre implements Peca {
	
	/** Representa qual lado esta peça é B de branco e P de preto */
	private char lado;
	
	/** Representa qual é a peça*/
	private static final String tipo = "torre";
	
	/** Ponto do espaço tabuleiro (quadrado) no qual a peça se encontra */
	private Ponto pt0;
	
	/** Ponto do espaço na qual peça comida está */
	private Peca comida;
	
	/** Indica se torre já se movimentou */
	private boolean movimentou = false;
	
	public Torre( char lado , int y , int x)
	{
		this.lado = lado ;
		
		pt0 = new Ponto( x , y ) ;
		
		comida = null;
	}
	
	public Torre( char lado , Ponto atual )
	{
		this.lado = lado ;
		
		pt0 = atual ;
		
		comida = null;
	}
	
	public Torre( char lado , int y , int x , boolean movimentou )
	{
		this.lado = lado ;
		
		pt0 = new Ponto( x , y ) ;
		
		comida = null;
		
		this.movimentou = movimentou ;
	}
	
	public boolean ChecaPosicionamento(int xFinal , int yFinal) throws AtacarPeca 
	{
		boolean pode = ( pt0.AlinhadoVH(new Ponto( xFinal , yFinal) ) && !PecaNoCaminho(xFinal , yFinal) ) ;
		
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
		catch(AtacarPeca e)
		{
			comida = Tabuleiro.getPeca(yFinal , xFinal);
			
			if ( movimentou )
				return  true ;
			
			movimentou = true ;
			return true;
		}
		
		comida = Tabuleiro.getPeca(yFinal , xFinal);
		
		if ( movimentou )
			return  sePode ;
		
		movimentou = sePode ;
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
	
	public boolean getSpecial()
	{ 
		return movimentou;
	}
}
