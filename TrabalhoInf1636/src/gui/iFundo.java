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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JLayeredPane;

import auxiliar.Ponto;
import engine.Peca;
import engine.Tabuleiro;
import excecoes.AtacarPeca;

public class iFundo extends JLayeredPane {
	
	private static final long serialVersionUID = 1L;
	
	/** Dimensões de um retângulo de xadrez */
	private static final int HEIGHT = 80  ;
	private static final int WIDTH = 80   ;
	
	public iFundo()
	{
		super();
		setPreferredSize(new Dimension(8*WIDTH, 8*HEIGHT));
	}
	
	/** Overriding no metodo paintComponent */
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
				boolean pintou = false;
				boolean selecionou = false;
				Graphics2D g2d = (Graphics2D) g;
				Rectangle2D rt = new Rectangle2D.Double(leftPoint , rightPoint , WIDTH , HEIGHT) ;
				
				/** Peça selecionada (Para pintar o retângulo de azul) */
				Ponto orig = iTabuleiro.getOrig();
				
				Peca origem = Tabuleiro.getPeca(orig);
							
				if ( orig != null && orig.getX() == i && orig.getY() == ( Tabuleiro.getColunas() - 1 ) - j  )
				{
					selecionou = true;
					g2d.setPaint(Color.blue);
				}
				else if ( origem != null)
				{
					boolean pode = false;
					try
					{
						pode = origem.ChecaPosicionamento(i, ( Tabuleiro.getColunas() - 1 ) - j);
					}
					catch(AtacarPeca a)
					{
						if(Tabuleiro.getPeca(a.getPecaAtacada()).getLado() != origem.getLado() )
						{
							g2d.setPaint(Color.red);
							pintou = true;
						}
					}
					if ( pode && !pintou)
					{
						if ( j % 2 == 0 )
						{
							if( i % 2 == 0 )
								g2d.setPaint(Color.gray);
							else
								g2d.setPaint(Color.LIGHT_GRAY);
						}
						else
						{
							if( i % 2 == 0)
								g2d.setPaint(Color.LIGHT_GRAY);
							else
								g2d.setPaint(Color.gray);
						}
					}
					else if(!pintou)
					{
						if ( j % 2 == 0 )
						{
							if( i % 2 == 0 )
								g2d.setPaint(Color.WHITE);
							else
								g2d.setPaint(Color.BLACK);
						}
						else
						{
							if( i % 2 == 0)
								g2d.setPaint(Color.BLACK);
							else
								g2d.setPaint(Color.WHITE);
						}
					}
				}
				else if(!pintou)
				{
					if ( j % 2 == 0 )
					{
						if( i % 2 == 0 )
							g2d.setPaint(Color.WHITE);
						else
							g2d.setPaint(Color.BLACK);
					}
					else
					{
						if( i % 2 == 0)
							g2d.setPaint(Color.BLACK);
						else
							g2d.setPaint(Color.WHITE);
					}
					
				}
				if ( !selecionou && Tabuleiro.getXequeReiBranco() && Tabuleiro.getReiBranco().getX() == i && Tabuleiro.getReiBranco().getY() == ( Tabuleiro.getColunas() - 1 ) - j )
					g2d.setPaint(Color.pink);
				if ( !selecionou && Tabuleiro.getXequeReiPreto() && Tabuleiro.getReiPreto().getX() == i && Tabuleiro.getReiPreto().getY() == ( Tabuleiro.getColunas() - 1 ) - j )
					g2d.setPaint(Color.pink);
					
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
