/** O objeto iPeçasComida é responsável por
 * computar todas as peças comida do jogo.
 * 
 * AINDA ESTÁ DESLIGADO!!
 * 
 * @version 1.0
 * @author Alessandro e Marcelo
 * @since 1.0
 */

package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class iPecasComida extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** Dimensões da imagem */
	private static final int HEIGHT = 25  ;
	private static final int WIDTH = 25   ;
	
	/** Offsets */
	private static final int offset1 = 20;
	private static final int offset2 = 20;
	
	/** Vetor do nome das peças comidas no jogo */
	private static String vetorPreto[] = new String[24];
	private static int numBranco = 0  ;
	private static String vetorBranco[] = new String[24];
	private static int numPreto = 0   ;
	
	/** Overriding na classe paintComponent */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		int i = 0 ;
		int realHeight = 334;
		int z = realHeight/2 ;
		
		setBorder(BorderFactory.createLineBorder(Color.black));
		
			for( i = 0 ; i < numBranco ; i++ )
			{
				String name = vetorBranco[i];
				Image imPeca = null;
				
				if(name!= null)
				{
					try
					{
						imPeca = ImageIO.read(new File(name) ) ;
					}
					catch(IOException e)
					{
						System.out.println(e.getMessage());
						return ;
					}
					catch(Exception e)
					{return;}
					
					g.drawImage( imPeca, (i-(i/6)*6)*(offset1 + WIDTH)  , (i/6)*(HEIGHT + offset2), WIDTH , HEIGHT, null ) ;
				}
			}
			
			for( i = 0 ; i < numPreto ; i++ )
			{
				String name = vetorPreto[i];
				Image imPeca = null;
				
				if(name!= null)
				{
					try
					{
						imPeca = ImageIO.read(new File(name) ) ;
					}
					catch(IOException e)
					{
						System.out.println(e.getMessage());
						return ;
					}
					catch(Exception e)
					{return;}
						
					g.drawImage( imPeca, (i-(i/6)*6)*(offset1 + WIDTH) ,  (i/6)*(HEIGHT + offset2) + z, WIDTH , HEIGHT, null ) ;
				}
			}
	}
	
	public static void sendPeca(String filename , char lado)
	{
		if ( lado == 'b' )
		{
			vetorBranco[numBranco] = filename;
			numBranco ++ ;
		}
		else
		{
			vetorPreto[numPreto] = filename;
			numPreto ++ ;
		}
		
	}
}

