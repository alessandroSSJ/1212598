/** O objeto iBispoAPromover é responsável por tratar
 * inputs do usuários com o objetivo de promover peões
 * 
 * 
 * @version 1.0
 * @author Alessandro e Marcelo
 * @since 1.0
 */

package gui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLayeredPane;

import engine.Tabuleiro;


public class iBispoAPromover extends JLayeredPane{


	private static final long serialVersionUID = 1L;
	
	/** Dimensões da imagem */
	private static final int HEIGHT = 40  ;
	private static final int WIDTH = 40   ;

	/** Overriding na classe paintComponent */
	@Override public void paintComponent(Graphics g)
	{
		super.paintComponent(g) ;
		
		Image imPeca = null;
		
		/* Associa o tipo da peça ao arquivo que contem sua imagem */

		String filename = "Pecas/" + Tabuleiro.getVez() + "_" + "bispo" + ".gif" ;
		
		try
		{
			imPeca = ImageIO.read(new File(filename) ) ;
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
			return ;
		}
		catch(Exception e)
		{
			System.out.println("Erro fatal");
			System.exit(1);
		}
		
		g.drawImage( imPeca, 4*iPromotion.getOffset() + 3*WIDTH , 20 , WIDTH , HEIGHT, null ) ;
	}
	
}
