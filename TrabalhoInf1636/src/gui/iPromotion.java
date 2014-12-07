/** O objeto iPromotion é responsável por mostrar as opções
 * da promoção de peões para o usuário.
 * 
 * @version 1.0
 * @author Alessandro e Marcelo
 * @since 1.0
 */

package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import observersxadrez.TabObserver;
import observersxadrez.iTabObserver;
import auxiliar.Ponto;
import engine.Bispo;
import engine.Cavalo;
import engine.Dama;
import engine.Peca;
import engine.Tabuleiro;
import engine.Torre;

public class iPromotion extends JFrame implements WindowListener , MouseListener {
	
	private static final long serialVersionUID = 1L;
	
	/** Dimensões reais de um tabuleiro de xadrez */
	private static final int HEIGHT =  100  ;
	private static final int WIDTH =   300;
	
	/** Offset entre as peças na horizontal */
	private static final int OFFSET = 25; 
	
	/** Peca a set promovida */
	private static Peca promovida = null;
	private static Ponto orig = null;
	
	
	public iPromotion(Ponto pt0)
	{
		super("Qual peça você deseja promover?") ;
		promovida = null;
		orig = pt0;
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		
		Dimension screenSize=tk.getScreenSize();
		
		int x = screenSize.width/2 - WIDTH/2   ;
		int y = screenSize.height/2 - HEIGHT/2 ;
		
		setBounds(x,y,WIDTH,HEIGHT); /* Posiciona o tabuleiro no meio da tela do monitor */
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setAlwaysOnTop(true);
		
		setResizable(false);
		
		addWindowListener( (WindowListener) this);
		addMouseListener( (MouseListener) this );
		
	}

	
	/** Desenha o JPanel das peças */
	public void DrawPecas()
	{
		iDamaAPromover d = new iDamaAPromover() ;
		iTorreAPromover t = new iTorreAPromover() ;
		iCavaloAPromover c = new iCavaloAPromover() ;
		iBispoAPromover b = new iBispoAPromover() ;
		
		this.setVisible(true);
		this.add(d) ;
		this.setVisible(true) ;
		this.add(t) ;
		this.setVisible(true) ;
		this.add(c) ;
		this.setVisible(true) ;
		this.add(b) ;
		this.toFront();
	}

	/** Retorna a altura do JFrame */
	public static int getAltura()
	{
		return HEIGHT;
	}
	
	/** Retorna a largura do JFrame */
	public static int getLargura()
	{
		return WIDTH;
	}
	
	/** Retorna o offset */
	public static int getOffset()
	{
		return OFFSET;
	}
	
	/** Retorna a peca promovida */
	public static Peca getPromovida()
	{
		return promovida;
	}
	
	/** Implementa os tratadores da janela*/
	
	@Override
	public void windowActivated(WindowEvent arg0) {
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		System.out.printf("Você deve escolher uma peça para promover\n");
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		
	}
	
	/** Implementa os tratadores de mouse input */
	
	@Override
    public void mouseClicked(MouseEvent e)
	{
		int xi = e.getX() ;
		int yi = e.getY() ;
		
		if ( yi > 50 && yi < 90  )
		{
			if(xi > OFFSET && xi < OFFSET + 40)
				promovida = new Dama(Tabuleiro.getVez() , orig);
			else if(xi > 2*OFFSET + 40 && xi < 2*OFFSET + 80)
				promovida = new Torre(Tabuleiro.getVez() , orig);
			else if(xi > 3*OFFSET + 80 && xi < 3*OFFSET + 120)
				promovida = new Cavalo(Tabuleiro.getVez() , orig);
			else if(xi > 4*OFFSET + 120 && xi < 4*OFFSET + 160)
				promovida = new Bispo(Tabuleiro.getVez() , orig);
		}
		
		if ( promovida != null )
		{
			TabObserver.promove(promovida);
			this.setVisible(false);
			this.dispose();
			iTabObserver.Notifica();
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
	
	
}