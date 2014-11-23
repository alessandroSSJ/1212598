/**
 * A interface do tipo peça é o coração de todas
 * as peças de um jogo de xadrez, todas as operações
 * que podem ser feitas pelo programador são definidas
 * nessa interface.
 * 
 * @version 1.2
 * @author Alessandro e Marcelo
 * @since 1.0
 */

package engine;

import excecoes.*;
import auxiliar.Ponto;

public interface Peca {
	
	/** Retorna a qual lado a devida peça pertençe */
	public abstract char getLado();
	
	/** Verifica se a peça pode ir para uma dada posição 
	 * @throws AoPassar , Promover */
	public abstract boolean ChecaMovimentoPeca(int xFinal , int yFinal) throws AoPassar , Promover, RoqueDireita, RoqueEsquerda ;
	
	/** Retorna tipo de peça */
	public abstract String getTipo();
	
	/** Retorna ponto na qual a peça está */
	public abstract Ponto getPonto();
	
	/** Muda ponto na qual a peça está */
	public abstract void setPonto(int x, int y);
	
	/** Retorna peça a ser retirada do tabuleiro caso seja o caso */
	public abstract Peca pecaComida();
	
	/** Informação especial individual de uma peça */
	public abstract boolean getSpecial();
	
}
