/**
 * O objeto iFundo (interface do fundo) é responsável pelo
 * fundo do tabuleiro de xadrez. Ou seja, desenha retângulos
 * brancos e pretos no JFRAME do tabuleiro.
 * 
 * @version 1.0
 * @author Alessandro e Marcelo
 * @since 1.0
 */

package gui;

import java.awt.*      ; 
import java.awt.geom.* ;
import javax.swing.*   ;
import engine.*        ;
import auxiliar.*      ;

public class iFundo extends JLayeredPane {
	
	private static final long serialVersionUID = 1L;
	
	/** Dimensões de um retângulo de xadrez */
	private static final int HEIGHT = 80  ;
	private static final int WIDTH = 80   ;
	
	/** Overriding na classe paintComponent */
	@Override public void paintComponent(Graphics g)
	{
		super.paintComponent(g) ;
		double leftPoint;
		double rightPoint;
		int i ;
		int j ;
		
		/* Algoritmo para desenhar os retangulos preto e branco alternadamente */
		for ( j = 0 ; j < Tabuleiro.getLinhas() ; j++ )
			for ( i = 0 ; i < Tabuleiro.getColunas() ; i++)
			{
				leftPoint = i*WIDTH ;
				rightPoint = j*HEIGHT ;
				
				Graphics2D g2d = (Graphics2D) g;
				
				Rectangle2D rt = new Rectangle2D.Double(leftPoint , rightPoint , WIDTH , HEIGHT) ;
				
				/** Peça selecionada (Para pintar o retângulo de azul) */
				Ponto orig = iTabuleiro.getOrig();
				
				if ( orig != null && orig.getX() == i && orig.getY() == ( Tabuleiro.getColunas() - 1 ) - j  )
						g2d.setPaint(Color.blue);
				
				else
				{
					if ( j % 2 == 0 )
					{
						if( i % 2 == 0 )
							g2d.setPaint(Color.WHITE)  ;
						else
							g2d.setPaint(Color.BLACK)  ;
					}
					else
					{
						if( i % 2 == 0)
							g2d.setPaint(Color.BLACK);
						else
							g2d.setPaint(Color.WHITE);
					}
				}
					
				g2d.fill(rt);
				
			}
		
	}
	
	/** Retorna a largura do quadrado */
	public static int getLargura()
	{
		return iFundo.WIDTH;
	}
	
	/** Retorna a altura do quadrado */
	public static int getAltura()
	{
		return iFundo.HEIGHT;
	}
}
