package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece{

	public Rook(Board board, Color color) {
		super(board, color);
	}
	
	@Override
	public String toString() {
		return "R ";
	}
	
	@Override
	public boolean[][] PossibleMoves() {
		boolean[][] mat = new boolean[board.getRows()][board.getColumns()];
		return mat;
	} 
}
