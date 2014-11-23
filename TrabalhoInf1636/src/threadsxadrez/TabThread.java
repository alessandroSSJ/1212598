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
			
		Peca pecaOrigem = tab.getPeca( ptOrig.getY() , ptOrig.getX() )  ;
		Peca pecaDestino = tab.getPeca( ptDest.getY() , ptDest.getX() ) ;
		
		/* Movimentação Das pesas */
		
		try
		{
			if( pecaOrigem == null)
				throw new PecaOrigemNull();
				
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
				
				tab.ComePeca(pecaOrigem.pecaComida().getPonto());
				tab.ChangePeca(ptOrig.getY() , ptOrig.getX() , ptDest.getY() , ptDest.getX() ) ;
				clipMov.loop(1);
		    }
		}
		catch(AoPassar e)
		{
			System.out.println(e.getMessage());
			tab.ChangePeca(ptOrig.getY() , ptOrig.getX() , ptDest.getY() , ptDest.getX() ) ;
			tab.ComePeca(pecaOrigem.pecaComida().getPonto());
			clipMov.loop(1);
		}
		catch(RoqueDireita e)
		{
			System.out.println(e.getMessage());
			tab.ChangePeca(ptOrig.getY() , ptOrig.getX() , ptOrig.getY() , ptOrig.getX() + 2 ) ;
			tab.ChangePeca(e.getY() , e.getX() , e.getY() , e.getX() - 2 ) ;
			clipMov.loop(1);
		} 
		catch(RoqueEsquerda e)
		{
			System.out.println(e.getMessage());
			tab.ChangePeca(ptOrig.getY() , ptOrig.getX() , ptOrig.getY() , ptOrig.getX() - 2 ) ;
			tab.ChangePeca(e.getY() , e.getX() , e.getY() , e.getX() + 3 ) ;
			clipMov.loop(1);
		}
		catch(Promover e)
		{
			System.out.println(e.getMessage());
		}
		catch(MovimentoInvalido e)
		{
			iPromotion p = new iPromotion();
			p.DrawPecas();
			
			System.out.println(e.getMessage());
			return;
		}
		catch(PecaOrigemNull e)
		{
			System.out.println(e.getMessage());
			return;
		}
		catch(PropriaPeca e)
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
		
		Tabuleiro.ViraVez();
		Tabuleiro.ComputaRodada();
		
		
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
