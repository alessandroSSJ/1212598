/**
 * A exceção AtacarPeca é lançada sempre
 * que se queira checar uma movimentação
 * de peça, cuja posição final é a de uma
 * peça inimiga.
 * 
 * @version 1.0
 * @author Alessandro e Marcelo
 * @since 1.0
 */

package excecoes;

import auxiliar.Ponto;

public class AtacarPeca extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int xPeca;
	private int yPeca;
	
	public AtacarPeca(int xPeca , int yPeca)
	{
		super("Atacando Peca");
		this.xPeca = xPeca;
		this.yPeca = yPeca;
	}
	
	public AtacarPeca(Ponto p)
	{
		super("Atacando Peca");
		this.xPeca = p.getX();
		this.yPeca = p.getY();
	}
	
	public Ponto getPecaAtacada()
	{
		return new Ponto(xPeca , yPeca);
	}
	
	public int getXPecaAtacada()
	{
		return xPeca;
	}
	public int getYPecaAtacada()
	{
		return yPeca;
	}
}
