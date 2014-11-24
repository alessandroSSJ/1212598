/**
 * O objeto iPeca (interface da peça) é responsável por 
 * desenhar as imagens das peças no JFrame Tabuleiro.
 * 
 * @version 1.0
 * @author Alessandro e Marcelo
 * @since 1.0
 */

package gui;

import java.awt.*      ; 
import java.io.*       ;
import javax.imageio.* ;
import javax.swing.*   ;
import engine.*        ;

public class iPeca extends JLayeredPane {

	private static final long serialVersionUID = 1L;
	
	/** Tabuleiro com as peças a se desenhas */
	Tabuleiro tab;
		
	public iPeca(Tabuleiro tab)
	{
		super();
		this.tab = tab;
	}
	
	/** Overriding na classe paintComponent */
	@Override public void paintComponent(Graphics g)
	{
		super.paintComponent(g) ;
		int i;
		int j;
		int imWidth = 40 ;
		int imHeight = imWidth ;
		
		/* Offsets para centralizar as peças */
		
		int offsetWidth = iFundo.getLargura()/2 - imWidth/2 ;
		int offsetHeight = 8*iFundo.getAltura()-iFundo.getAltura()/2 - imHeight/2;
		
		for( i = 0 ; i < Tabuleiro.getLinhas() ; i++ )
			for( j = 0; j < Tabuleiro.getColunas() ; j++)
			{
				Peca p = Tabuleiro.getPeca(i, j) ;
				
				if ( p != null ) /* null significa que não tem peça nenhuma naquela posição */
				{
					Image imPeca = null;
					
					/* Associa o tipo da peça ao arquivo que contem sua imagem */

					String filename = "Pecas/" + p.getLado() + "_" + p.getTipo() + ".gif" ;
					
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
					
					g.drawImage( imPeca, j*iFundo.getLargura() + offsetWidth , offsetHeight - i*iFundo.getAltura(), imWidth , imHeight, null ) ;
				}
			}
		
	}
}
