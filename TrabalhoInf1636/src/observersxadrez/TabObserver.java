/**
 * Classe responsável por implementar a thread que irá lidar
 * com a parte funcional do jogo, isto é, organizará o mesmo
 * em rodadas, atribuirá vitórias, derrotas e etc.
 * 
 * (IMPLEMENTA PADRÃO DE DESIGN OBSERVER)
 * 
 * @version 2.0
 * @author Alessandro e Marcelo
 * @since 1.0
 */

package observersxadrez;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.JOptionPane;

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

public class TabObserver{
	
	public static Tabuleiro tab = null;
	
	/** Arquivos de som*/
	private static File mov = new File("Sons/mov.wav");
	private static Clip clipMov;
	
	private static File movMate = new File("Sons/xequemate.wav");
	private static Clip clipMate;
	
	private static File movError = new File("Sons/error.wav");
	private static Clip clipError;
	
	private static File movXeque = new File("Sons/xeque.wav");
	private static Clip clipXeque;
	
	/** Pontos promovidos */
	private static Ponto pontoPromove = null;

	/** Estado de promoção de peao */
	private static boolean promovendo = false;
	
	public TabObserver()
	{
		tab = Tabuleiro.getTabuleiro();
		
		promovendo = false;
		
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
		    
		    /** Som de xeque */
		    stream = AudioSystem.getAudioInputStream(movXeque);
		    format = stream.getFormat();
		    info = new DataLine.Info(Clip.class, format);
		    clipXeque = (Clip) AudioSystem.getLine(info);
		    clipXeque.open(stream);
		    
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
	public TabObserver( String[][] arquivo )
	{
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
		    
		    /** Som de xeque */
		    stream = AudioSystem.getAudioInputStream(movXeque);
		    format = stream.getFormat();
		    info = new DataLine.Info(Clip.class, format);
		    clipXeque = (Clip) AudioSystem.getLine(info);
		    clipXeque.open(stream);
		    
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
	public static void Rodada()
	{
		
		/* Uma rodada normal */
		
		if ( !iTabuleiro.getJogadaValida() || promovendo )
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
				somMov();
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
				somMov();
		    }
			
		}
		catch(AoPassar e)
		{			
			Peca temp = pecaOrigem.pecaComida();
			Tabuleiro.ChangePeca(ptOrig.getY() , ptOrig.getX() , ptDest.getY() , ptDest.getX() ) ;
			tab.ComePeca(temp.getPonto());
			iPecasComida.sendPeca("Pecas/" + temp.getLado() + "_" + temp.getTipo() + ".gif" , temp.getLado());
			somMov();
		}
		catch(RoqueDireita e)
		{
			Tabuleiro.ChangePeca(ptOrig.getY() , ptOrig.getX() , ptOrig.getY() , ptOrig.getX() + 2 ) ;
			Tabuleiro.ChangePeca(e.getY() , e.getX() , e.getY() , e.getX() - 2 ) ;
			somMov();
		} 
		catch(RoqueEsquerda e)
		{
			Tabuleiro.ChangePeca(ptOrig.getY() , ptOrig.getX() , ptOrig.getY() , ptOrig.getX() - 2 ) ;
			Tabuleiro.ChangePeca(e.getY() , e.getX() , e.getY() , e.getX() + 3 ) ;
			somMov();
		}
		catch(Promover e)
		{
			
			iPromotion p = new iPromotion(ptOrig);
			p.DrawPecas();
			
			promovendo = true;
			
			if(e.getPecaComida() != null)
			{
				Peca temp =  Tabuleiro.getPeca(e.getPecaComida());
				tab.ComePeca(e.getPecaComida());
				iPecasComida.sendPeca("Pecas/" + temp.getLado() + "_" + temp.getTipo() + ".gif" , temp.getLado());
			}
			
			Tabuleiro.ChangePeca(ptOrig.getY() , ptOrig.getX() , ptDest.getY() , ptDest.getX() ) ;
			
			pontoPromove = ptDest;
			
		}
		catch(MovimentoInvalido e)
		{
			somError();
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
			somError();
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
			iTabuleiro.ZerarRodada();
			iTabuleiro.setJogadaValida(false);
		}	
		
		if ( promovendo )
			return;
		
		FimDeRodada();

	}
	
	/** get Tabuleiro */
	public static Tabuleiro getTabuleiro()
	{
		return TabObserver.tab;
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
			Tabuleiro.XequeReiBranco(reiState);
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
	
	private static void FimDeRodada()
	{
		/* ********************* Checa se o outro rei foi deixado em xeque! ********************************* */
		
		if ( promovendo )
			return;
		
	 	if( Tabuleiro.getVez() == 'b')
	 	{
			if (Tabuleiro.ChecaXequeReiPreto() )
			{
	 			System.out.printf("XEQUE REI PRETO\n");
			}
		} 
		else
			if (Tabuleiro.ChecaXequeReiBranco())
			{
				System.out.printf("XEQUE REI BRANCO\n");
			}
	 	
  /* ************************************************************************************************** */
	 	
	 	
  /* ******************************* Checa se ocorreu xeque mate **************************************** */ 	
		
		if ( Tabuleiro.getVez() == 'b' && Tabuleiro.getXequeReiPreto() && Tabuleiro.ChecaXequeMateReiPreto() )
		{
			System.out.printf("XEQUE MATE\nPEÇAS BRANCAS GANHARAM!\n");
			clipMate.loop(0);
			JOptionPane.showMessageDialog(null, "XEQUE MATE\nPEÇAS BRANCAS GANHARAM!\n",null, JOptionPane.PLAIN_MESSAGE);
		}
		else if ( Tabuleiro.getVez() == 'p' && Tabuleiro.getXequeReiBranco() && Tabuleiro.ChecaXequeMateReiBranco() )
		{
			System.out.printf("XEQUE MATE\nPEÇAS PRETAS GANHARAM\n");
			clipMate.loop(0);
			JOptionPane.showMessageDialog(null, "XEQUE MATE\nPEÇAS PRETAS GANHARAM\n",null, JOptionPane.PLAIN_MESSAGE);
		}
		else if ( Tabuleiro.getXequeReiPreto() || Tabuleiro.getXequeReiBranco() )
			somXeque();
		
  /* ************************************************************************************************************** */	
		
		Tabuleiro.ViraVez();
		Tabuleiro.ComputaRodada();
	}
	
	public static void promove(Peca promoted)
	{		
		tab.CriaPeca(pontoPromove, promoted);
		
		iPromotion.getPromovida().setPonto(pontoPromove.getX() , pontoPromove.getY());
		
		clipMov.loop(1);
		
		promovendo = false;
		
		iTabuleiro.ZerarRodada();
		
		FimDeRodada();
	}
	
	private static void somMov()
	{
		clipMov.setFramePosition(0);
		clipMov.loop(0);
	}
	
	private static void somError()
	{
		clipError.setFramePosition(0);
		clipError.loop(0);
	}
	
	private static void somXeque()
	{
		clipXeque.setFramePosition(0);
		clipXeque.loop(0);
	}

	/** Metodo notify*/
	public static void Notifica()
	{
		if ( promovendo )
			return;
		
		if (iOptions.readOption() == iOptions.SAIR && iConfirmation.readResponse() == iConfirmation.OK)
			System.exit(1);
		else if (iOptions.readOption() == iOptions.MENU && iConfirmation.readResponse() == iConfirmation.OK)
		{
			Tabuleiro.DestruirTabuleiro();
			iPrincipal.StartGame();
			return;
		}
		
		Rodada();
	}
	
}
