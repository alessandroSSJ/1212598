/**
 * Um objeto do tipo peao, representa o peao
 * de um jogo de xadrez
 * 
 * @version 1.0
 * @author Alessandro e Marcelo
 * @since 1.0
 */

package engine;

import auxiliar.Ponto;
import excecoes.*;

public class Peao implements Peca {
	
	/** Representa qual lado esta peça é B de branco e P de preto */
	private char lado;
	
	/** Representa qual é a peça*/
	private static final String tipo = "peao";
	
	/** Ponto do espaço tabuleiro (quadrado) no qual a peça se encontra */
	private Ponto pt0;
	
	/** Ponto do espaço na qual peça comida está */
	private Peca comida = null;
	
	/** Indica que o mesmo pulou duas casas */
	private boolean pulou;
	
	/** Indica a rodada que o peão pulou */
	private int rodadaDePulo;
	
	public Peao( char lado , int y, int x)
	{
		this.lado = lado ;
		
		pt0 = new Ponto(x,y) ;
		
		comida = null;
	}
	
	public boolean ChecaPosicionamento(int xFinal , int yFinal) throws AtacarPeca
	{
		boolean pode;
		Peca temp = comida ;
		
		try 
		{
			pode = SePode(xFinal , yFinal);
		}
		catch(AoPassar e)
		{
			comida = temp;
			if (lado == 'b')
				throw new AtacarPeca(xFinal , yFinal-1);
			else
				throw new AtacarPeca(xFinal , yFinal+1);
		}
		catch(Promover e)
		{
			comida = temp;
			return true;
		}
		
		if ( comida != null && pode )
		{
			Ponto aux = comida.getPonto();
			comida = temp;
			throw new AtacarPeca(aux);
		}
		
		return pode;
	}
	
	private boolean SePode(int xFinal, int yFinal) throws AoPassar , Promover
	{

		int distX = pt0.getX() - xFinal ;
		int distY = pt0.getY() - yFinal ;
		
		if ( lado == 'b' )
		{
			/* Promoção */
			
			Peca p = Tabuleiro.getPeca(yFinal, xFinal) ;
			
			comida = p;
			
			if ( p == null && pt0.getY() == 6 && yFinal == 7 && distX == 0)
				throw new Promover();
			if ( p != null && pt0.getY() == 6 && yFinal == 7 && (distX == -1 || distX ==1) )
				throw new Promover(p.getPonto());
			
			/* Ao passar */
			if ( distY == -1 && pt0.getY() == 4 )
			{
				Peca pAnterior = Tabuleiro.getPeca(yFinal-1 , xFinal);
				
				comida = pAnterior ;
				
				if (pAnterior != null && pAnterior.getTipo() == "peao" && pAnterior.getLado() != lado && (distX == 1 || distX == -1))
				{
					if ( ( pt0.getY() == 4 && pAnterior.getSpecial() ) )
						throw new AoPassar();
				}
			}
			
			/* Movimentos normais */
			
			p = Tabuleiro.getPeca(yFinal, xFinal) ;
			
			comida = p;
			
			if ( (p != null ) && ( distX == -1 || distX == 1 ) && distY == -1 )
				return true;
			else if( p == null && distX == 0 )
			{
				if ( distY == -1 )
					return true;
				if ( pt0.getY() == 1 && distY == -2 && !PecaNoCaminho() )
				{
					pulou = true;
					rodadaDePulo = Tabuleiro.getNumRodadas();
					return true;
				}
			}
			
			return false;
		}
		
		else
		{
			/* Promoção */
			
			Peca p = Tabuleiro.getPeca(yFinal, xFinal) ;
			
			comida = p;
			
			if ( p == null && pt0.getY() == 1 && yFinal == 0 && distX == 0)
				throw new Promover();
			if ( p != null && pt0.getY() == 1 && yFinal == 0 && (distX == -1 || distX ==1) )
				throw new Promover(p.getPonto());
			
			
			/* Ao passar */ 
			if ( distY == 1 && pt0.getY() == 3 )
			{
				Peca pAnterior = Tabuleiro.getPeca(yFinal+1 , xFinal);
				
				comida = pAnterior ;
				
				if (pAnterior != null && pAnterior.getTipo() == "peao" && pAnterior.getLado() != lado && (distX == 1 || distX == -1))
				{
					if ( ( pt0.getY() == 3 && pAnterior.getSpecial() ) )
						throw new AoPassar();
				}
			}
			
			/* Movimentos normais */
			
			p = Tabuleiro.getPeca(yFinal, xFinal) ;
			
			comida = p;
			
			if ( (p != null ) && ( distX == -1 || distX == 1 ) && distY == 1 )
				return true;
			else if( p == null && distX == 0 )
			{
				if ( distY == 1 )
					return true;
				if ( pt0.getY() == 6 && distY == 2 && !PecaNoCaminho() )
				{
					pulou = true;
					rodadaDePulo = Tabuleiro.getNumRodadas();
					return true;
				}
			}
			
			return false;
			
		}
		
	}
	
	public boolean ChecaMovimentoPeca(int xFinal , int yFinal) throws AoPassar , Promover
	{
		boolean checa;
		
		try
		{
			checa = SePode(xFinal , yFinal);
		}
		catch(AoPassar e)
		{
			throw e;
		}
		catch(Promover e)
		{
			throw e;
		}
		
		return checa;
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
	
	private boolean PecaNoCaminho()
	{
		Peca teste;
		if ( lado == 'b' )
			teste = Tabuleiro.getPeca(pt0.getY()+1 , pt0.getX());
		else
			teste = Tabuleiro.getPeca(pt0.getY()-1 , pt0.getX());
		
		if(teste != null)
			return true; /*Existe peca no caminho*/
		
		return false; /*Não existe peca no caminho*/
	}
	
	public void setPonto(int x, int y)
	{
		pt0.setX(x);
		pt0.setY(y);
	}
	
	public char getLado()
	{
		return lado;
	}

	public String getTipo()
	{
		return tipo ;
	}
	
	public boolean getSpecial()
	{
		return pulou && (rodadaDePulo == Tabuleiro.getNumRodadas() - 1 );
	}
	
	public Ponto getPonto()
	{
		return pt0;
	}
	
	public Peca pecaComida()
	{
		return comida;
	}

}
