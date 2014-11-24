/**
 * Um objeto do tipo Rei, representa a peça Rei
 * de um jogo de xadrez
 * 
 * @version 1.0
 * @author Alessandro e Marcelo
 * @since 1.0
 */

package engine;

import auxiliar.Ponto;
import excecoes.*;

public class Rei implements Peca {
	
	/** Representa qual lado esta peça é B de branco e P de preto */
	private char lado;
	
	/** Representa qual é a peça*/
	private static final String tipo = "rei";
	
	/** Ponto do espaço na qual peça comida está */
	private Peca comida;
	
	/** Ponto do espaço tabuleiro (quadrado) no qual a peça se encontra */
	private Ponto pt0;
	
	/** Indica se o rei se movimentou */
	private boolean movimentou;
	
	public Rei( char lado , int y , int x )
	{
		this.lado = lado ;
		
		pt0 = new Ponto( x , y ) ;
	}
	
	public boolean ChecaPosicionamento(int xFinal , int yFinal)
	{
		return false;
	}
	
	public boolean ChecaMovimentoPeca(int xFinal , int yFinal) throws RoqueDireita, RoqueEsquerda
	{
		int distX = pt0.getX() - xFinal ;
		int distY = pt0.getY() - yFinal ;
		boolean sePode;
		
		comida = Tabuleiro.getPeca(yFinal, xFinal) ;
		
		if ( !movimentou && distY == 0 )
		{
			if( distX == -2 && ChecaRoqueDireita() )
			{
				movimentou = true;
				
				if ( lado == 'b' )
					Tabuleiro.setReiBranco(xFinal , yFinal);
				else
					Tabuleiro.setReiPreto(xFinal , yFinal);
				
				throw new RoqueDireita(pt0.getX() + 3 , pt0.getY() );
			}
			else if( distX == 2 && ChecaRoqueEsquerda() )
			{
				movimentou = true;
				
				if ( lado == 'b' )
					Tabuleiro.setReiBranco(xFinal , yFinal);
				else
					Tabuleiro.setReiPreto(xFinal , yFinal);
				throw new RoqueEsquerda(pt0.getX() - 4 , pt0.getY() );
			}
		}
		
		sePode = pt0.Vizinho(new Ponto( xFinal , yFinal) ) ;
		
		if ( !movimentou )
			movimentou = sePode;
		
		if ( sePode )
		{
			if ( lado == 'b' )
				Tabuleiro.setReiBranco(xFinal , yFinal);
			else
				Tabuleiro.setReiPreto(xFinal , yFinal);
		}
		
		return sePode;
	}
	
	public boolean VefXeque() 
	{
		Ponto posRei;
		
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
			ChecaPosicionamento(posRei.getX() , posRei.getY() ) ;
		}
		catch(Exception a)
		{
			if(lado == 'p')
				Tabuleiro.XequeReiBranco(true);
			else
				Tabuleiro.XequeReiPreto(true);
			
			return true;
		}
		
		if(lado == 'p')
			Tabuleiro.XequeReiBranco(false);
		else
			Tabuleiro.XequeReiPreto(false);
		
		return false;
	}
	
	private boolean ChecaRoqueDireita()
	{		
		/* Peças a direita */
		Peca p1    = Tabuleiro.getPeca(pt0.getY() , pt0.getX() + 1 ) ;
		Peca p2    = Tabuleiro.getPeca(pt0.getY() , pt0.getX() + 2 ) ;
		Peca Torre = Tabuleiro.getPeca(pt0.getY() , pt0.getX() + 3 ) ;
		
		return (p1 == null) && (p2 == null) && ( !Torre.getSpecial() ) ;
	}
	
	private boolean ChecaRoqueEsquerda()
	{		
		/* Peças a esquerda */
		Peca p1    = Tabuleiro.getPeca(pt0.getY() , pt0.getX() - 1 ) ;
		Peca p2    = Tabuleiro.getPeca(pt0.getY() , pt0.getX() - 2 ) ;
		Peca p3    = Tabuleiro.getPeca(pt0.getY() , pt0.getX() - 3 ) ;
		Peca Torre = Tabuleiro.getPeca(pt0.getY() , pt0.getX() - 4 ) ;
		
		return (p1 == null) && (p2 == null) && (p3 == null) && ( !Torre.getSpecial() ) ;
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
