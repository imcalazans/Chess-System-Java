package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece{
	
	private Color color;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}
	
	protected boolean IsthereOponentPiece(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		if(p != null && p.color != color) {
			return true;
		}
		return false;
	}
}
