/** O objeto iPromotion é responsável por mostrar as opções
 * da promoção de peões para o usuário.
 * 
 * @version 1.0
 * @author Alessandro e Marcelo
 * @since 1.0
 */

package gui;

import engine.Tabuleiro;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.JFrame;

public class iPromotion extends JFrame implements WindowListener {
	
	private static final long serialVersionUID = 1L;
	
	/** Dimensões reais de um tabuleiro de xadrez */
	private static final int HEIGHT =  100  ;
	private static final int WIDTH =   300;
	

	
	public iPromotion()
	{
		super("Promoção de peão") ;
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		
		Dimension screenSize=tk.getScreenSize();
		
		int x = screenSize.width/2 - WIDTH/2   ;
		int y = screenSize.height/2 - HEIGHT/2 ;
		
		setBounds(x,y,WIDTH,HEIGHT); /* Posiciona o tabuleiro no meio da tela do monitor */
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		setResizable(false);
		
		addWindowListener( (WindowListener) this);
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
		this.add(t) ;
		this.add(c) ;
		this.add(b) ;
	}


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
	
	
}