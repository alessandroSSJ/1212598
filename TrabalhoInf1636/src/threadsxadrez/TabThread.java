/**
 * Classe responsável por implementar a thread que irá lidar
 * com a parte funcional do jogo, isto é, organizará o mesmo
 * em rodadas, atribuirá vitórias, derrotas e etc.
 * 
 * @version 1.1
 * @author Alessandro e Marcelo
 * @since 1.0
 */

package threadsxadrez;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

import auxiliar.Ponto;
import engine.Peca;
import engine.Tabuleiro;
import excecoes.AoPassar;
import excecoes.MovimentoInvalido;
import excecoes.PecaOrigemNull;
import excecoes.Promover;
import excecoes.PropriaPeca;
import excecoes.ReiEmXeque;
import excecoes.RoqueDireita;
import excecoes.RoqueEsquerda;
import gui.iPromotion;
import gui.iTabuleiro;

public class TabThread extends Thread{
	
	public static Tabuleiro tab = null;
	
	/** Arquivos de som*/
	private static File mov = new File("Sons/mov.wav");
	private static Clip clipMov;
	
	public TabThread()
	{
		super("Thread do tabuleiro");
		tab = Tabuleiro.getTabuleiro();
		
		AudioInputStream stream;
	    AudioFormat format;
	    DataLine.Info info;

	    try
	    {
	    	/** Som da movimentação das peças */
		    stream = AudioSystem.getAudioInputStream(mov);
		    format = stream.getFormat();
		    info = new DataLine.Info(Clip.class, format);
		    clipMov = (Clip) AudioSystem.getLine(info);
		    clipMov.open(stream);
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	}
	
	/** Faz uma jogada no tabuleiro */
	public void Rodada()
	{
		
		/* Uma rodada normal */
		
		if ( !iTabuleiro.getJogadaValida() )
			return;
		
		Ponto ptOrig = iTabuleiro.getOrig();
		Ponto ptDest = iTabuleiro.getDest();
			
		Peca pecaOrigem = Tabuleiro.getPeca( ptOrig.getY() , ptOrig.getX() )  ;
		Peca pecaDestino = Tabuleiro.getPeca( ptDest.getY() , ptDest.getX() ) ;

		/* Movimentação das peças */
		
		try
		{
			if( pecaOrigem == null)
				throw new PecaOrigemNull();
			
			if (  pecaOrigem.ChecaMovimentoPeca(ptDest.getX(), ptDest.getY() ) != true )
				throw new MovimentoInvalido();
			
 /* ***************************************** Verificar os xeques ************************************************* */
	
			if(Tabuleiro.getVez() == 'b' )
			{
				Peca temp = pecaOrigem.pecaComida();
				
				if ( temp != null )
					tab.ComePeca(temp.getPonto());
				
				tab.ChangePeca(ptOrig.getY() , ptOrig.getX() , ptDest.getY() , ptDest.getX() ) ;
				
				if ( Tabuleiro.ChecaXequeReiBranco() ) 
				{
					tab.ChangePeca(ptDest.getY() , ptDest.getX() , ptOrig.getY() , ptOrig.getX() ) ;
					tab.CriaPeca(ptDest , temp);
					
					throw new ReiEmXeque();
				}
				
				tab.ChangePeca(ptDest.getY() , ptDest.getX() , ptOrig.getY() , ptOrig.getX() ) ;
				tab.CriaPeca(ptDest , temp);
			}
			else
			{
				Peca temp = pecaOrigem.pecaComida();
				
				if ( temp != null )
					tab.ComePeca(temp.getPonto());
				
				tab.ChangePeca(ptOrig.getY() , ptOrig.getX() , ptDest.getY() , ptDest.getX() ) ;
				
				if ( Tabuleiro.ChecaXequeReiPreto() )
				{
					tab.ChangePeca(ptDest.getY() , ptDest.getX() , ptOrig.getY() , ptOrig.getX() ) ;
					tab.CriaPeca(ptDest , temp);
					
					throw new ReiEmXeque();
				}
				
				tab.ChangePeca(ptDest.getY() , ptDest.getX() , ptOrig.getY() , ptOrig.getX() ) ;
				
				tab.CriaPeca(ptDest , temp);
			}
			
/* ************************************************************************************************************************ */
			if (  pecaOrigem.ChecaMovimentoPeca(ptDest.getX(), ptDest.getY() ) != true )
				throw new MovimentoInvalido();
			
			if ( pecaDestino == null )
			{
				tab.ChangePeca(ptOrig.getY() , ptOrig.getX() , ptDest.getY() , ptDest.getX() ) ;
				clipMov.loop(1);
			}
			else 
			{
				if ( pecaOrigem.getLado() == pecaDestino.getLado())
				{
					throw new PropriaPeca();
				}
				
				if ( pecaOrigem.pecaComida() != null )
					tab.ComePeca(pecaOrigem.pecaComida().getPonto());
				else
					System.out.printf("Algum erro estranho\n");
				
				tab.ChangePeca(ptOrig.getY() , ptOrig.getX() , ptDest.getY() , ptDest.getX() ) ;
				clipMov.loop(1);
		    }
			
		}
		catch(AoPassar e)
		{
			tab.ChangePeca(ptOrig.getY() , ptOrig.getX() , ptDest.getY() , ptDest.getX() ) ;
			tab.ComePeca(pecaOrigem.pecaComida().getPonto());
			clipMov.loop(1);
		}
		catch(RoqueDireita e)
		{
			tab.ChangePeca(ptOrig.getY() , ptOrig.getX() , ptOrig.getY() , ptOrig.getX() + 2 ) ;
			tab.ChangePeca(e.getY() , e.getX() , e.getY() , e.getX() - 2 ) ;
			clipMov.loop(1);
		} 
		catch(RoqueEsquerda e)
		{
			tab.ChangePeca(ptOrig.getY() , ptOrig.getX() , ptOrig.getY() , ptOrig.getX() - 2 ) ;
			tab.ChangePeca(e.getY() , e.getX() , e.getY() , e.getX() + 3 ) ;
			clipMov.loop(1);
		}
		catch(Promover e)
		{
			iPromotion p = new iPromotion(ptOrig);
			p.DrawPecas();
			
			while( p.getPromovida() == null ) System.out.printf("Aguardando escolha\n") ;
			
			tab.CriaPeca(ptOrig, p.getPromovida());
			
			tab.ChangePeca(ptOrig.getY() , ptOrig.getX() , ptDest.getY() , ptDest.getX() ) ;
			
			clipMov.loop(1);
			
			p.Close();
			
		}
		catch(MovimentoInvalido e)
		{
			System.out.println(e.getMessage());	
			return;
		}
		catch(PecaOrigemNull e)
		{
			return;
		}
		catch(PropriaPeca e)
		{
			return;
		}
		catch(ReiEmXeque e)
		{
			System.out.println(e.getMessage());
			return;
		}
		catch(Exception e)
		{
			System.out.printf("Erro fatal\n");
			e.printStackTrace();
			System.exit(1);
		}
		finally
		{
			iTabuleiro.setJogadaValida(false);
			iTabuleiro.ZerarRodada();
		}	
		
  /* ********************* Checa se o outro rei foi deixado em xeque! ********************************* */
		
	 	if( Tabuleiro.getVez() == 'b'){
			if (Tabuleiro.ChecaXequeReiPreto() )
	 			System.out.printf("XEQUE REI PRETO\n");}
		else
			if (Tabuleiro.ChecaXequeReiBranco())
				System.out.printf("XEQUE REI BRANCO\n");
	 	
  /* ************************************************************************************************** */
	 	
	 	if (Tabuleiro.getXequeReiBranco())
	 		System.out.printf("CHEQUE REI BRANCO\n");
	 	if (Tabuleiro.getXequeReiPreto())
	 		System.out.printf("CHEQUE REI PRETO\n");
	 	
  /* ******************************* Checa se ocorreu xeque mate **************************************** */ 	
		/*
		if ( Tabuleiro.getVez() == 'b' && Tabuleiro.getXequeReiPreto() && Tabuleiro.ChecaXequeMateReiPreto() )
		{
			System.out.printf("\n\nXEQUE MATE\nBRANCO WINS\n");
		}
		else if ( Tabuleiro.getVez() == 'p' && Tabuleiro.getXequeReiBranco() && Tabuleiro.ChecaXequeMateReiBranco() )
		{
			System.out.printf("XEQUE MATE\n PRETO WINS\n");
		}*/
		
  /* ************************************************************************************************************** */		
	 	
	System.out.printf("AS REAIS : \nRei branco: (%d,%d)\nRei Preto: (%d,%d)\n" , Tabuleiro.getReiBranco().getX() , Tabuleiro.getReiBranco().getY() , Tabuleiro.getReiPreto().getX() , Tabuleiro.getReiPreto().getY() );
		
		/* Vira a rodada */
		
		Tabuleiro.ViraVez();
		Tabuleiro.ComputaRodada();
		System.out.printf("FIM DA RODADA ************************************************\n");
		/* *********************************************** */
	}
	
	/** get Tabuleiro */
	public static Tabuleiro getTabuleiro()
	{
		return TabThread.tab;
	}
	
	
	/** Metodo run*/
	@Override public void run()
	{
		while(true)
		{
			Rodada();
		}
	}
	
}
