package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece{

	public Pawn(Board board, Color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
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
		}
		return mat;
	}
	
	@Override
	public String toString() {
		return "P";
	} 

}
