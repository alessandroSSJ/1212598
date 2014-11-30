/**
 * O objeto tabuleiro é responsável por armazenar e processar toda a informação
 * do jogo referente à localização das peças. Ou seja, ele será responsável por
 * identificar xeques-mates, xeques, jogadas inválidas e etc. Ou seja, em suma, 
 * este objeto será um tabuleiro inteligente.
 * 
 * @version 1.1
 * @author Alessandro e Marcelo
 * @since 1.0
 */

package engine;


import auxiliar.Ponto;
import excecoes.AtacarPeca;

public class Tabuleiro {
	
	/** Padrão singleton */
	private static Tabuleiro tab = null;
	
	/** Dimensões de um tabuleiro de xadrez*/
	
	private static final int LINHAS = 8 ; 
	private static final int COLUNAS = 8;
	
	/** Indica de quem é a vez */
	
	private static char vez = 'b' ;
	
	/** Matriz de peças que irá gerar o jogo, caso alguma posição da matriz seja
	 * null, significa que não há peças naquela posição. O tamanho da matriz
	 * é o tamanho da dimensão do tabuleiro.
	 */
	
	private static Peca pecas[][] ;
	
	/** Numero de rodadas */
	private static int numRodadas = 0 ;
	
	/** Posicao dos reis */
	private static Ponto reiPreto;
	private static Ponto reiBranco;
	private static boolean xequeReiPreto = false;
	private static boolean xequeReiBranco = false;
	
	private Tabuleiro()
	{
		int i;
		pecas = new Peca[LINHAS][COLUNAS] ;
		
		for(i = 0 ; i < COLUNAS ; i++)
			for(int j = 0 ; j < LINHAS ; j++)
				pecas[i][j] = null;
		
		/* Alocação dos peões */
		for ( i = 0 ; i < COLUNAS ; i++ )
		{
			pecas[1][i] = new Peao('b' , 1 , i);
			pecas[(LINHAS-1)-1][i] = new Peao('p' , (LINHAS-1)-1 , i) ;
		}
		
		/* Alocação das torres */
		pecas[0][0] = new Torre('b' , 0 , 0);
		pecas[0][COLUNAS-1] = new Torre('b' , 0 , COLUNAS-1); 
		pecas[LINHAS-1][0] = new Torre('p' , LINHAS-1 , 0);
		pecas[LINHAS-1][COLUNAS-1] = new Torre('p' , LINHAS-1 , COLUNAS-1);
		
		/* Alocação dos cavalos */
		pecas[0][1] = new Cavalo('b' , 0 , 1);
		pecas[0][COLUNAS-2] = new Cavalo('b' , 0 , COLUNAS-2); 
		pecas[LINHAS-1][1] = new Cavalo('p' , LINHAS-1 , 1);
		pecas[LINHAS-1][COLUNAS-2] = new Cavalo('p' , LINHAS-1 , COLUNAS-2);
		
		/* Alocação dos bispos */
		pecas[0][2] = new Bispo('b' , 0 , 2);
		pecas[0][COLUNAS-3] = new Bispo('b' , 0 , COLUNAS-3); 
		pecas[LINHAS-1][2] = new Bispo('p' , LINHAS-1 , 2);
		pecas[LINHAS-1][COLUNAS-3] = new Bispo('p' , LINHAS-1, COLUNAS-3);
		
		/* Alocação das damas */
		pecas[0][3] = new Dama('b' , 0 , 3);
		pecas[LINHAS-1][3] = new Dama('p' , LINHAS-1 , 3); 
		
		/* Alocação dos reis */
		pecas[0][4] = new Rei('b' , 0 , 4 );
		reiBranco = new Ponto(4 , 0);
		
		pecas[LINHAS-1][4] = new Rei('p' , LINHAS-1 , 4); 
		reiPreto = new Ponto(4 , LINHAS-1);
	}
	
	/** Pega a única instancia do tabuleiro  SINGLETON*/
	public static Tabuleiro getTabuleiro()
	{
		if ( tab == null )
			tab = new Tabuleiro();
		return tab;
	}
	
	/** Retorna uma peça do tabuleiro */
	public static Peca getPeca(int linha, int coluna)
	{
		return pecas[linha][coluna] ;
	}
	
	/** Retorna uma peça do tabuleiro */
	public static Peca getPeca(Ponto p)
	{
		if ( p == null )
			return null;
		return pecas[p.getY()][p.getX()];
	}
	
	/** Muda a posição de duas peças */
	public void ChangePeca(int linhaO, int colunaO, int linhaD, int colunaD)
	{
		Peca temp = pecas[linhaD][colunaD];
		Peca orig = pecas[linhaO][colunaO];
		Peca dest = pecas[linhaD][colunaD];
		
		//System.out.printf("Peca1 : "); System.out.println(orig.getTipo());
		//if (dest!=null) {System.out.printf("Peca2 : "); System.out.println(dest.getTipo());} else System.out.printf("Peca2 : null\n");
		
		orig.setPonto(colunaD,linhaD);
		

		if ( dest != null )			
			dest.setPonto(colunaO,linhaO);
		
		pecas[linhaD][colunaD] = orig;
		pecas[linhaO][colunaO] = temp;
				
		Peca corrig = Tabuleiro.getPeca(linhaO , colunaO);
		
		if (corrig != null &&  corrig.getTipo() == "rei" )
		{
			if ( corrig.getLado() == 'b' )
				Tabuleiro.setReiBranco(colunaO , linhaO);
			else
				Tabuleiro.setReiPreto(colunaO , linhaO);
		}
		
		corrig = Tabuleiro.getPeca(linhaD , colunaD);
		
		if ( corrig != null &&  corrig.getTipo() == "rei" )
		{
			if ( corrig.getLado() == 'b' )
				Tabuleiro.setReiBranco(colunaD , linhaD);
			else
				Tabuleiro.setReiPreto(colunaD , linhaD);
		}
		
	}
	
	/** Retira uma peca do tabuleiro*/
	public void ComePeca(int linha, int coluna)
	{
		pecas[linha][coluna] = null;
	}
	
	/** Retira uma peca do tabuleiro*/
	public void ComePeca(Ponto p)
	{
		pecas[p.getY()][p.getX()] = null;
	}
	
	/** Cria uma peça no tabuleiro*/
	public void CriaPeca(Ponto p, Peca pec)
	{
		pecas[p.getY()][p.getX()] = pec;
	}
	
	/** Retorna o número de linhas do tabuleiro */
	public static int getLinhas()
	{
		return Tabuleiro.LINHAS;
	}
	
	/** Retorna o número de colunas do tabuleiro */
	public static int getColunas()
	{
		return Tabuleiro.COLUNAS;
	}
	
	/** Verifica a rodada*/
	public static char getVez()
	{
		return vez;
	}
	
	/** Vira a rodada */
	public static void ViraVez()
	{
		if( vez == 'b')
			vez = 'p' ;
		else 
			vez = 'b' ;
	}
	
	/** Computa mais uma rodada */
	public static void ComputaRodada()
	{
		numRodadas ++;
	}
	
	/** Pega numero de rodadas	 */
	public static int getNumRodadas()
	{
		return numRodadas;
	}
	
	/** Checa se rei branco está em xeque*/
	public static boolean ChecaXequeReiBranco()
	{
		return ChecaXeque('p');
	}
	
	/** Checa se rei preto está em xeque*/
	public static boolean ChecaXequeReiPreto()
	{
		return ChecaXeque('b');
	}
	
	/** Checa xeque */
	private static boolean ChecaXeque(char lado)
	{
		int i;
		int j;
		
		for ( j = 0 ; j < Tabuleiro.getLinhas() ; j++ )
			for ( i = 0 ; i < Tabuleiro.getColunas() ; i++)
			{
				Peca atual = pecas[j][i];
				if ( atual != null && atual.getLado() == lado && atual.VefXeque())
				{
					if ( lado == 'b' )
						Tabuleiro.XequeReiPreto(true);
					else
						Tabuleiro.XequeReiBranco(true);
					return true;
				}
			}
		
		if ( lado == 'b' )
			Tabuleiro.XequeReiPreto(false);
		else
			Tabuleiro.XequeReiBranco(false);
		
		return false;
	}
	
	/** Checa se rei branco está em xeque mate*/
	public static boolean ChecaXequeMateReiBranco()
	{
		return ChecaXequeMate('p');
	}
	
	/** Checa se rei preto está em xeque mate*/
	public static boolean ChecaXequeMateReiPreto()
	{
		return ChecaXequeMate('b');
	}

	/** Checa xeque mate */
	private static boolean ChecaXequeMate(char lado)
	{
		int i;
		int j;
		int numPecasXeque = 0;
		Peca pecasDeXeque[] = new Peca[16];
		
		/* Verificando xeque simples e pegando as peças responsáveis */
		
		for ( j = 0 ; j < Tabuleiro.getLinhas() ; j++ )
			for ( i = 0 ; i < Tabuleiro.getColunas() ; i++)
			{
				Peca atual = pecas[j][i];
				if ( atual != null && atual.getLado() == lado && atual.VefXeque())
				{
					pecasDeXeque[numPecasXeque] = atual;
					numPecasXeque++;
				}
			}
		
		if ( numPecasXeque == 0 ) /* Se ninguem esta dando xeque, xeque mate é falso */
			return false;

		/* ********************************************************************** */
		
		/* Verificando se rei pode se mover (Xeque nas redondezas) */
		
		Ponto posOriginal;
		
		if (lado == 'b')
			posOriginal = Tabuleiro.getReiPreto();
		else
			posOriginal = Tabuleiro.getReiBranco();
		
		int xOriginal = posOriginal.getX();
		int yOriginal = posOriginal.getY();
		
		int indexX = 0 ;
		int indexY = 0 ;
		
		int numXequesRedondezas = 0 ;
		
		for(int z = 0 ; z < 9 ; z ++)
		{
			boolean flag = false;
			boolean posInvalida = false;
			int direcaoX;
			int direcaoY;
			
			if ( z == 0 || z == 3 || z == 6 )
				indexX = -1;
			else if ( z == 1 || z == 4 || z == 7)
				indexX = 0 ;
			else if ( z == 2 || z == 5 || z == 8 )
				indexX = 1;
			
			if ( z < 3 )
				indexY = 1;
			else if ( z < 6 )
				indexY = 0;
			else if ( z < 9 )
				indexY = -1;
			
			direcaoX = xOriginal + indexX;
			direcaoY = yOriginal + indexY;
			
			Peca redondeza;
			
			if ( (direcaoX >= 0) && (direcaoY >= 0) && (direcaoX <= 7) && (direcaoY <= 7) )
				redondeza = Tabuleiro.getPeca(direcaoY , direcaoX);
			else
			{
				redondeza = null;
				posInvalida = true;
			}
			
			if ( (redondeza != null && redondeza.getLado() == lado) || ( redondeza == null && !posInvalida ) )
			{
				Peca temp = Tabuleiro.getPeca(direcaoY,direcaoX);
				
				if (temp != null)
					tab.ComePeca(temp.getPonto());
				
				tab.ChangePeca(yOriginal , xOriginal , direcaoY , direcaoX);				
			
				for ( j = 0 ; j < Tabuleiro.getLinhas() && !flag ; j++ )
					for ( i = 0 ; i < Tabuleiro.getColunas() && !flag ; i++)
					{
						Peca atual = pecas[j][i];
						if ( atual != null && atual.getLado() == lado && atual != redondeza && atual.VefXeque())
						{
							System.out.printf("Somei aqui a peça %s (%d,%d)\n" , atual.getTipo() , direcaoX,direcaoY);
							numXequesRedondezas++;
							flag = true;
						}
					}
				
				tab.ChangePeca(direcaoY , direcaoX , yOriginal, xOriginal);
				
				if(temp != null)
					tab.CriaPeca(temp.getPonto() , temp);
				
				System.out.printf("Pega rei de (%d,%d) e bota em (%d,%d)\n" , direcaoX,direcaoY , posOriginal.getX(), posOriginal.getY());
				/* Retorna rei para sua posição de origem */
			}
			else
				{numXequesRedondezas++;System.out.printf("Somei aqui (%d,%d)\n" , direcaoX,direcaoY);} 
		}
		
		System.out.printf("NUMERO DE XEQUES NA REDONDEZA : %d\n" , numXequesRedondezas);
		
		if ( numXequesRedondezas < 9 ) /* Se existem menos de 8 */
			return false;
		
		/* ***************************************************************************************** */
		
		
		if ( numPecasXeque > 1) /* Se rei não pode se mecher, e existem duas peças dando xeque nele, xeque mate*/
			return true;
		
		/* ****************************************************************************************** */
		
		/* Checando se alguem pode comer a única peça que pode dar cheque no rei */
		/* Para isso, posiciona-se o rei no lugar dessa peça, e checa se alguma peça da
		 * xeque nesse "rei virtual" */
		
		if (lado == 'p')
			posOriginal = Tabuleiro.getReiPreto();
		else
			posOriginal = Tabuleiro.getReiBranco();
		
		xOriginal = posOriginal.getX();
		yOriginal = posOriginal.getY();
		
		tab.ChangePeca(pecasDeXeque[0].getPonto().getY() , pecasDeXeque[0].getPonto().getX() , posOriginal.getY(), posOriginal.getX());
		
		for ( j = 0 ; j < Tabuleiro.getLinhas() ; j++ )
			for ( i = 0 ; i < Tabuleiro.getColunas() ; i++)
			{
				Peca atual = pecas[j][i];
				if ( atual != null && atual.getLado() != lado && atual.getTipo()!="rei" && atual.VefXeque())
				{
					tab.ChangePeca( posOriginal.getY(), posOriginal.getX() , pecasDeXeque[0].getPonto().getY() , pecasDeXeque[0].getPonto().getX());    /* Retorna rei para sua posição de origem */				
					return false ; /* pelo menos uma peça pode comer a única de xeque, não é mate */
				}
			}
			
		 /* Retorna rei para sua posição de origem */
		tab.ChangePeca( posOriginal.getY(), posOriginal.getX() , pecasDeXeque[0].getPonto().getY() , pecasDeXeque[0].getPonto().getX());
						
		System.out.printf("NINGUEM PODE COMER A PECA\n");
	
		/* ****************************************************************************************************** */
		
		/* Se chegar aqui, significa que ninguém pode comer aquela única peça de xeque, portanto, se ela for um cavalo
		 * é xeque mate!
		 */
		
		if ( pecasDeXeque[0].getTipo() == "cavalo" )
			return true;
		
		/* ******************************************************************************************************** */
		
		/* Se chegar aqui, significa que ninguém pode comer a peça de xeque, porém ela não é um cavalo, ou seja, 
		 * ainda pode acontecer de alguma peça poder se colocar na frente dela! */
		
		if (lado == 'b')
			posOriginal = Tabuleiro.getReiPreto();
		else
			posOriginal = Tabuleiro.getReiBranco();
		
		for ( j = 0 ; j < Tabuleiro.getLinhas() ; j++ )
			for ( i = 0 ; i < Tabuleiro.getColunas() ; i++)
			{
				Peca atual = pecas[j][i];
				if ( atual != null && atual.getLado() != lado && atual.getTipo() != "rei" )
				{	
					if ( Chega(pecasDeXeque[0].getPonto(), posOriginal , atual ) )
					{
						System.out.printf("A peça %s na posicao (%d,%d) pode intersectar\n" , atual.getTipo() , atual.getPonto().getX() , atual.getPonto().getY());
						return false;
					}
				}
			}
		
		/* ****************************************************************************************************************** */
		
		/* Aqui, por eliminação, é com certeza xeque mate */
		
	    return true;	
			
	}
	
	/** Função que checa se determinada peça intersecta a outra */
	private static boolean Chega(Ponto pt0 , Ponto p , Peca atual)
	{	
		int xFinal = p.getX();
		int yFinal = p.getY();
		
		int deltaY = yFinal - pt0.getY();
		int rY = Direcao(deltaY);
		
		int deltaX = xFinal - pt0.getX();
		int rX = Direcao(deltaX);
		
		int xTeste = pt0.getX() + rX ;
		int yTeste = pt0.getY() + rY ;
		
		while( !(xTeste == xFinal && yTeste == yFinal) )
		{

			try
			{
			if ( atual.ChecaPosicionamento(xTeste, yTeste) )
				return true;
			}
			catch(AtacarPeca a)
			{
				if(atual.getTipo() == "peao")
					return false;
				return true;
			}
			
			xTeste += rX;
			yTeste += rY;
		}
		
		return false;
	}
	
	private static int Direcao(int delta)
	{
		if ( delta == 0 )
			return 0;
		
		if ( delta < 0 )
			return -1;
		else
			return 1;	
	}
	
	
	/** Le a variável de xeque do rei branco */
	public static boolean getXequeReiBranco()
	{
		return xequeReiBranco;
	}
	
	/** Le a variável de xeque do rei preto*/
	public static boolean getXequeReiPreto()
	{
		return xequeReiPreto;
	}
	
	/** Atualiza xeque do rei branco */
	public static void XequeReiBranco(boolean xequeState)
	{
		xequeReiBranco = xequeState;
	}
	
	/** Atualiza xeque do rei preto */
	public static void XequeReiPreto(boolean xequeState)
	{
		xequeReiPreto = xequeState;
	}
	
	/** Pega a posição do rei branco */
	public static Ponto getReiBranco()
	{
		return reiBranco;
	}
	
	/** Pega a posição do rei preto */
	public static Ponto getReiPreto()
	{
		return reiPreto;
	}
	
	/** Atualiza Posição do rei branco */
	public static void setReiBranco(int x , int y)
	{
		reiBranco.setX(x);
		reiBranco.setY(y);
	}
	
	public static void setReiBranco(Ponto novaPos)
	{
		reiBranco = novaPos;
	}
	
	/** Atualiza Posição do rei preto */
	public static void setReiPreto(int x , int y)
	{
		reiPreto.setX(x);
		reiPreto.setY(y);
	}
	
	public static void setReiPreto(Ponto novaPos)
	{
		reiPreto = novaPos;
	}
	
	
}
