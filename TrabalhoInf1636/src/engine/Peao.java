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
	private Peca comida;
	
	/** Indica que o mesmo pulou duas casas */
	private boolean pulou;
	
	/** Indica a rodada que o peão pulou */
	private int rodadaDePulo;
	
	public Peao( char lado , int y, int x)
	{
		this.lado = lado ;
		
		pt0 = new Ponto(x,y) ;
	}
	
	private boolean SePode(int xFinal, int yFinal) throws AoPassar , Promover
	{

		int distX = pt0.getX() - xFinal ;
		int distY = pt0.getY() - yFinal ;
		boolean promove = false;
		
		/* Para próximas versões : Otimizar esse algoritmo, pois apesar de funcionar não está muito bem implementado */
		
		/* Implementação da promoção */
		if ( lado == 'b' && pt0.getY() == 6 && yFinal == 7 )
			promove = true;
		else if ( lado == 'p' && pt0.getY() == 1 && yFinal == 0 )
			promove = true;
		
		/* Implementação dos checa movimento especial dos peoes */
		
		/* Ao passar */
		if ( lado == 'b' && distY < 0 && pt0.getY() == 4)
		{
			Peca pAnterior = Tabuleiro.getTabuleiro().getPeca(yFinal-1 , xFinal);
			
			comida = pAnterior ;
			
			if (pAnterior != null && pAnterior.getTipo() == "peao" && pAnterior.getLado() != lado && distX != 0)
			{
				if ( ( pt0.getY() == 4 && pAnterior.getSpecial() ) )
					throw new AoPassar();
			}
		}
		else if ( lado == 'p' && distY > 0 && pt0.getY() == 3 )
		{
			Peca pAnterior = Tabuleiro.getTabuleiro().getPeca(yFinal+1 , xFinal);
			
			comida = pAnterior ;
			
			if (pAnterior != null && pAnterior.getTipo() == "peao" && pAnterior.getLado() != lado && distX != 0)
			{
				if (pt0.getY() == 3 && pAnterior.getSpecial() )
						throw new AoPassar();
			}
			
		}
		
		/* Comer para onde não se move */
		
		Peca p = Tabuleiro.getTabuleiro().getPeca(yFinal, xFinal) ;
		
		comida = p;
		
		if ( p != null )
		{
			if ( lado == 'b' )
			{
				if ( ( distX == -1 || distX == 1 ) && distY == -1 )
				{
					if ( promove )
						throw new Promover();
					else
						return true;
				}
				
			}
			else
			{	
				if ( ( distX == -1 || distX == 1 ) && distY == 1 )
				{
					if ( promove )
						throw new Promover();
					else
						return true;
				}
			}
		}
		
		else
		{
			/* Movimentação para frente */
			
			if ( distX != 0 )
				return false;
			
			if ( lado == 'b' )
			{
				if ( distY == -1 )
				{
					if ( promove )
						throw new Promover();
					else
						return true;
				}
				if ( pt0.getY() == 1 && distY == -2 && !PecaNoCaminho() )
				{
					pulou = true;
					rodadaDePulo = Tabuleiro.getNumRodadas();
					return true;
				}
			}
			else if ( lado == 'p' )
			{
				if ( distY == 1 )
				{
					if ( promove )
						throw new Promover();
					else
						return true;
				}
				if ( pt0.getY() == 6 && distY == 2 && !PecaNoCaminho() )
				{
					pulou = true;
					rodadaDePulo = Tabuleiro.getNumRodadas();
					return true;
				}
			}
		}
		
		return false;
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
	
	private boolean PecaNoCaminho()
	{
		Peca teste;
		if ( lado == 'b' )
			teste = Tabuleiro.getTabuleiro().getPeca(pt0.getY()+1 , pt0.getX());
		else
			teste = Tabuleiro.getTabuleiro().getPeca(pt0.getY()-1 , pt0.getX());
		
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
