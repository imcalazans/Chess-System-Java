package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece{
	
	private ChessMatch chessMatch;

	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public boolean[][] PossibleMoves() {
		boolean[][] mat = new boolean[board.getRows()][board.getColumns()];
		
		Position p = new Position(0,0);
		
		if(getColor()==Color.White) {
			p.setValue(position.getRow() - 1, position.getColumn());
			if(getBoard().PositionExists(p) && !getBoard().ThereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			p.setValue(position.getRow() - 2, position.getColumn());
			Position p2 = new Position(position.getRow() - 1, position.getColumn());
			if(getBoard().PositionExists(p) && !getBoard().ThereIsAPiece(p) && getBoard().PositionExists(p2) && !getBoard().ThereIsAPiece(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			p.setValue(position.getRow() - 1, position.getColumn() - 1);
			if(getBoard().PositionExists(p) && IsthereOponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			p.setValue(position.getRow() - 1, position.getColumn() + 1);
			if(getBoard().PositionExists(p) && IsthereOponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//#Especial move en passant white
			if(position.getRow() == 3) {
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				if(getBoard().PositionExists(left) && IsthereOponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVunerable()) {
					mat[left.getRow() - 1][left.getColumn()] = true;
				}
				
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				if(getBoard().PositionExists(right) && IsthereOponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVunerable()) {
					mat[right.getRow() - 1][right.getColumn()] = true;
				}
			}
			
		}
		else{
			p.setValue(position.getRow() + 1, position.getColumn());
			if(getBoard().PositionExists(p) && !getBoard().ThereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			p.setValue(position.getRow() + 2, position.getColumn());
			Position p2 = new Position(position.getRow() + 1, position.getColumn());
			if(getBoard().PositionExists(p) && !getBoard().ThereIsAPiece(p) && getBoard().PositionExists(p2) && !getBoard().ThereIsAPiece(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			p.setValue(position.getRow() + 1, position.getColumn() + 1);
			if(getBoard().PositionExists(p) && IsthereOponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			p.setValue(position.getRow() + 1, position.getColumn() - 1);
			if(getBoard().PositionExists(p) && IsthereOponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//#Especial move en passant white
			if(position.getRow() == 4) {
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				if(getBoard().PositionExists(left) && IsthereOponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVunerable()) {
					mat[left.getRow() + 1][left.getColumn()] = true;
				}
				
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				if(getBoard().PositionExists(right) && IsthereOponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVunerable()) {
					mat[right.getRow() + 1][right.getColumn()] = true;
				}
			}
		}
		return mat;
	}
	
	@Override
	public String toString() {
		return "P";
	} 

}
