/**
 * O objeto iTabuleiro (interface de um tabuliro) é responsável pela
 * parte gráfico do tabuleiro do xadrez.
 * 
 * @version 1.2
 * @author Alessandro e Marcelo
 * @since 1.0
 */

package gui;

import java.awt.* ;
import java.awt.event.*;

import javax.swing.*;

import engine.*;
import auxiliar.*;
import excecoes.*;

public class iTabuleiro extends JFrame implements MouseListener {
	
	private static final long serialVersionUID = 1L;
	
	/** Dimensões reais de um tabuleiro de xadrez */
	private static final int HEIGHT =  8 * iFundo.getAltura();
	private static final int WIDTH =   8 * iFundo.getLargura();// + iOptions.getLargura() ;
	
	/** Pontos clicados pelo mouse para movimentar peças */
	private static Ponto ptOrig = null ;
	private static Ponto ptDest = null ;
	
	/** Variável para determinar se ja é o momento para realizar a jogada*/
	private static boolean jogadaValida = false;
	
	/** Opções */
	iOptions op;
	
	public iTabuleiro()
	{
		super("Jogo de Xadrez, by Marcelo e Alessandro") ;
		setLayout(new BorderLayout());
		Toolkit tk = Toolkit.getDefaultToolkit();
		
		op = new iOptions();
		
		Dimension screenSize=tk.getScreenSize();
		
		int x = screenSize.width/2 - WIDTH/2   ;
		int y = screenSize.height/2 - HEIGHT/2 ;
		setBounds(x,y,WIDTH,HEIGHT); /* Posiciona o tabuleiro no meio da tela do monitor */
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setResizable(false);
		
		addMouseListener( (MouseListener) this);
	}
	
	/** Desenha o LayeredPane do fundo */
	
	public void DrawBackground()
	{
		iFundo f = new iFundo() ;
		this.setVisible(true) ;
		this.add(f);
	}
	
	/** Desenha o LayeredPane das peças */
	public void DrawPecas(Tabuleiro tab)
	{
		iPeca p = new iPeca(tab) ;
		this.setVisible(true);
		this.add(p) ;
	}
	
	/** Desenha o menu de opções */
	public void MenuOpcoes()
	{
		op.adiciona();
		this.add("East",op);
		this.setVisible(true);
	}
	
	/** Retorna a largura do tabuleiro */
	public static int getLargura()
	{
		return iTabuleiro.WIDTH;
	}
	
	/** Retorna a altura do tabuleiro */
	public static int getAltura()
	{
		return iTabuleiro.HEIGHT;
	}
	
	/** Retorna a peça a ser movimentada */
	public static Ponto getOrig()
	{
		return iTabuleiro.ptOrig;
	}
	
	/** Retorna ponto destino */
	public static Ponto getDest()
	{
		return iTabuleiro.ptDest;
	}
	
	/** Retorna se jogada é válida */
	public static boolean getJogadaValida()
	{
		return iTabuleiro.jogadaValida;
	}
	
	/** Atribui valor a jogada */
	public static void setJogadaValida(boolean var)
	{
		iTabuleiro.jogadaValida = var;
	}
	
	/** Zerar os pontos */
	public static void ZerarRodada()
	{
		iTabuleiro.ptOrig = null;
		iTabuleiro.ptDest = null;
	}
	
	/** Implementa os tratadores de mouse input */
	
	@Override
    public void mouseClicked(MouseEvent e)
	{
		int xi = e.getX() / iFundo.getLargura() ;
		int yi = (8*iFundo.getAltura() - e.getY() ) / iFundo.getAltura()  ;
		
		if( ptOrig == null && ptDest == null && xi >= 0 && xi <= 7 && yi >= 0 && yi <= 7 )
		{
			ptOrig = new Ponto(xi , yi ) ;
			try
			{
				ChecaOrigem(ptOrig) ;
			}
			catch(PecaOrigemNull pn)
			{
				ptOrig = null;
			}
			catch(CorDePecaErrado pn)
			{
				ptOrig = null;
			}
			catch(Exception ex)
			{
				System.out.printf("ERRO FATAL NO TRATADOR\n");
				System.exit(1);
			}
		}
		else if( ptOrig != null && ptDest == null && xi >= 0 && xi <= 7 && yi >= 0 && yi <= 7 )
		{
		    ptDest = new Ponto(xi , yi ) ;
		    
		    try
		    {
		    	ChecaDestino(ptDest);
		    }
		    catch(PropriaPeca e1)
		    {
		    	ptOrig = ptDest;
		    	ptDest = null;
		    	return;
		    }
		    catch(MesmaPeca e2)
		    {
		    	ptOrig = null;
		    	ptDest = null;
		    	return;
		    }
		    catch(Exception e3)
		    {
		    	System.out.printf("Erro fatal bizarro \n");
		    	e3.printStackTrace();
		    	System.exit(1);
		    }
		    setJogadaValida(true);
		}
	}
	
	@Override
    public void mousePressed(MouseEvent e) 
	{
		
	}
	
    @Override
    public void mouseEntered(MouseEvent e) 
    {
    	
    }
    
    @Override
    public void mouseExited(MouseEvent e)
    {
    	
    }
    
    @Override
    public void mouseReleased(MouseEvent e)
    {
    	
    }
	
    /* Trata o ponto do primeiro click */
    private void ChecaOrigem(Ponto p) throws PecaOrigemNull , CorDePecaErrado
    {
    	Peca orig = Tabuleiro.getPeca(p) ;
    	
		if ( orig == null )
			throw new PecaOrigemNull();
		if ( orig.getLado() != Tabuleiro.getVez() )
			throw new CorDePecaErrado();
    }
    
    /** Trata o ponto do segundo click */
    private void ChecaDestino(Ponto p) throws PropriaPeca , MesmaPeca
    {
    	Peca dest = Tabuleiro.getPeca(p) ;
    	    	
    	if ( p.getX() == ptOrig.getX() && p.getY() == ptOrig.getY())
			throw new MesmaPeca();
		if ( (dest != null) && (dest.getLado() == Tabuleiro.getVez()) )
			throw new PropriaPeca();
    }
    
}
