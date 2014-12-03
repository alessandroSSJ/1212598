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
import gui.iConfirmation;
import gui.iOptions;
import gui.iPecasComida;
import gui.iPrincipal;
import gui.iPromotion;
import gui.iTabuleiro;

public class TabThread extends Thread{
	
	public static Tabuleiro tab = null;
	
	/** Flag */
	private boolean continua = true;
	
	/** Arquivos de som*/
	private static File mov = new File("Sons/mov.wav");
	private static Clip clipMov;
	
	private static File movMate = new File("Sons/xequemate.wav");
	private static Clip clipMate;
	
	private static File movError = new File("Sons/error.wav");
	private static Clip clipError;
	
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
		    
		    /** Som de xeque mate */
		    stream = AudioSystem.getAudioInputStream(movMate);
		    format = stream.getFormat();
		    info = new DataLine.Info(Clip.class, format);
		    clipMate = (Clip) AudioSystem.getLine(info);
		    clipMate.open(stream);
		    
		    /** Som de movimentação inválida */
		    stream = AudioSystem.getAudioInputStream(movError);
		    format = stream.getFormat();
		    info = new DataLine.Info(Clip.class, format);
		    clipError = (Clip) AudioSystem.getLine(info);
		    clipError.open(stream);
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	}
	
	/** Tabuleiro de arquivo externo */
	public TabThread( String[][] arquivo )
	{
		super("Thread do tabuleiro");
		tab = Tabuleiro.getTabuleiro(arquivo);
		
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
		    
		    /** Som de xeque mate */
		    stream = AudioSystem.getAudioInputStream(movMate);
		    format = stream.getFormat();
		    info = new DataLine.Info(Clip.class, format);
		    clipMate = (Clip) AudioSystem.getLine(info);
		    clipMate.open(stream);
		    
		    /** Som de movimentação inválida */
		    stream = AudioSystem.getAudioInputStream(movError);
		    format = stream.getFormat();
		    info = new DataLine.Info(Clip.class, format);
		    clipError = (Clip) AudioSystem.getLine(info);
		    clipError.open(stream);
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
			
 /* ***************************************** Verificar os xeques ************************************************* */
			if ( PreverXeque(pecaDestino , ptOrig , ptDest) )
				throw new ReiEmXeque();
/* ************************************************************************************************************************ */
			
			/* Se eu passei, meu rei nao ta em xeque */
			
			if (Tabuleiro.getVez() == 'b')
				Tabuleiro.XequeReiBranco(false);
			else
				Tabuleiro.XequeReiPreto(false);
			
			if (  pecaOrigem.ChecaMovimentoPeca(ptDest.getX(), ptDest.getY() ) != true )
				throw new MovimentoInvalido();
			
			if ( pecaDestino == null )
			{
				Tabuleiro.ChangePeca(ptOrig.getY() , ptOrig.getX() , ptDest.getY() , ptDest.getX() ) ;
				clipMov.loop(1);
			}
			else 
			{
				if ( pecaOrigem.getLado() == pecaDestino.getLado())
				{
					throw new PropriaPeca();
				}
				
				if ( pecaOrigem.pecaComida() != null )
				{
					Peca temp = pecaOrigem.pecaComida();
					tab.ComePeca(temp.getPonto());
					iPecasComida.sendPeca("Pecas/" + temp.getLado() + "_" + temp.getTipo() + ".gif" , temp.getLado());
				}
				else
					System.out.printf("Algum erro estranho\n");
				
				Tabuleiro.ChangePeca(ptOrig.getY() , ptOrig.getX() , ptDest.getY() , ptDest.getX() ) ;
				clipMov.loop(1);
		    }
			
		}
		catch(AoPassar e)
		{			
			Peca temp = pecaOrigem.pecaComida();
			Tabuleiro.ChangePeca(ptOrig.getY() , ptOrig.getX() , ptDest.getY() , ptDest.getX() ) ;
			tab.ComePeca(temp.getPonto());
			iPecasComida.sendPeca("Pecas/" + temp.getLado() + "_" + temp.getTipo() + ".gif" , temp.getLado());
			clipMov.loop(1);
		}
		catch(RoqueDireita e)
		{
			Tabuleiro.ChangePeca(ptOrig.getY() , ptOrig.getX() , ptOrig.getY() , ptOrig.getX() + 2 ) ;
			Tabuleiro.ChangePeca(e.getY() , e.getX() , e.getY() , e.getX() - 2 ) ;
			clipMov.loop(1);
		} 
		catch(RoqueEsquerda e)
		{
			Tabuleiro.ChangePeca(ptOrig.getY() , ptOrig.getX() , ptOrig.getY() , ptOrig.getX() - 2 ) ;
			Tabuleiro.ChangePeca(e.getY() , e.getX() , e.getY() , e.getX() + 3 ) ;
			clipMov.loop(1);
		}
		catch(Promover e)
		{
			iPromotion p = new iPromotion(ptOrig);
			p.DrawPecas();
			
			if(e.getPecaComida() != null)
			{
				Peca temp = (Peca) e.getPecaComida();
				tab.ComePeca(e.getPecaComida());
				iPecasComida.sendPeca("Pecas/" + temp.getLado() + "_" + temp.getTipo() + ".gif" , temp.getLado());
			}
			
			Tabuleiro.ChangePeca(ptOrig.getY() , ptOrig.getX() , ptDest.getY() , ptDest.getX() ) ;
			
			while( p.getPromovida() == null ) System.out.printf("Aguardando escolha\n") ;
						
			tab.CriaPeca(ptDest, p.getPromovida());
			
			p.getPromovida().setPonto(ptDest.getX() , ptDest.getY());
			
			clipMov.loop(1);
			
			p.Close();
			
		}
		catch(MovimentoInvalido e)
		{
			clipError.loop(1);
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
			clipError.loop(1);
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
	 	
	 	
  /* ******************************* Checa se ocorreu xeque mate **************************************** */ 	
		
		if ( Tabuleiro.getVez() == 'b' && Tabuleiro.getXequeReiPreto() && Tabuleiro.ChecaXequeMateReiPreto() )
		{
			System.out.printf("XEQUE MATE\nPEÇAS BRANCAS GANHARAM!\n");
			clipMate.loop(0);
		}
		else if ( Tabuleiro.getVez() == 'p' && Tabuleiro.getXequeReiBranco() && Tabuleiro.ChecaXequeMateReiBranco() )
		{
			System.out.printf("XEQUE MATE\nPEÇAS PRETAS GANHARAM\n");
			clipMate.loop(0);
		}
		
  /* ************************************************************************************************************** */		
				
		/* Vira a rodada */

		Tabuleiro.ViraVez();
		Tabuleiro.ComputaRodada();
		/* *********************************************** */
	}
	
	/** get Tabuleiro */
	public static Tabuleiro getTabuleiro()
	{
		return TabThread.tab;
	}
	
	/** Verifica se a jogada atual deixa o rei em xeque */
	public static boolean PreverXeque(Peca pecaDestino , Ponto ptOrig , Ponto ptDest)
	{
		if(Tabuleiro.getPeca(ptOrig).getLado() == 'b' )
		{
			Peca temp = pecaDestino;
			
			boolean reiState = Tabuleiro.getXequeReiBranco();
			
			if ( temp != null )
				tab.ComePeca(temp.getPonto());
			
			Tabuleiro.ChangePeca(ptOrig.getY() , ptOrig.getX() , ptDest.getY() , ptDest.getX() ) ;
			
			if ( Tabuleiro.ChecaXequeReiBranco() ) 
			{
				Tabuleiro.ChangePeca(ptDest.getY() , ptDest.getX() , ptOrig.getY() , ptOrig.getX() ) ;
				tab.CriaPeca(ptDest , temp);
				Tabuleiro.XequeReiBranco(reiState);
				return true;
			}
			
			Tabuleiro.ChangePeca(ptDest.getY() , ptDest.getX() , ptOrig.getY() , ptOrig.getX() ) ;
			Tabuleiro.XequeReiPreto(reiState);
			tab.CriaPeca(ptDest , temp);
			
			return false;
		}
		else
		{
			Peca temp = pecaDestino;
			boolean reiState = Tabuleiro.getXequeReiPreto();
			
			if ( temp != null )
				tab.ComePeca(temp.getPonto());
			
			Tabuleiro.ChangePeca(ptOrig.getY() , ptOrig.getX() , ptDest.getY() , ptDest.getX() ) ;
			
			if ( Tabuleiro.ChecaXequeReiPreto() )
			{
				Tabuleiro.ChangePeca(ptDest.getY() , ptDest.getX() , ptOrig.getY() , ptOrig.getX() ) ;
				tab.CriaPeca(ptDest , temp);
				Tabuleiro.XequeReiPreto(reiState);
				return true;
			}
			
			Tabuleiro.ChangePeca(ptDest.getY() , ptDest.getX() , ptOrig.getY() , ptOrig.getX() ) ;
			Tabuleiro.XequeReiPreto(reiState);
			tab.CriaPeca(ptDest , temp);
			
			return false;
		}
		
	}
	
	
	/** Metodo run*/
	@Override public void run()
	{
		while(continua)
		{
			Rodada();
			if ( iConfirmation.readResponse() == iConfirmation.NOTOK )
				iConfirmation.close();
			
			if (iOptions.readOption() == iOptions.SAIR && iConfirmation.readResponse() == iConfirmation.OK)
				System.exit(1);
			else if (iOptions.readOption() == iOptions.MENU && iConfirmation.readResponse() == iConfirmation.OK)
			{
				iOptions.zeraOption();
				Tabuleiro.DestruirTabuleiro();
				iPrincipal.StartGame();
				continua = false;
				iConfirmation.close();
			}
		}
	}
	
}
