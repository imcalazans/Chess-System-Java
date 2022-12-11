package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {

	public Knight(Board board, Color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "N";
	}

	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}

	@Override
	public boolean[][] PossibleMoves() {
		boolean[][] mat = new boolean[board.getRows()][board.getColumns()];

		Position p = new Position(0, 0);


		p.setValue(position.getRow() - 1, position.getColumn() - 2);
		if (getBoard().PositionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}


		p.setValue(position.getRow() - 1, position.getColumn() + 2);
		if (getBoard().PositionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}


		p.setValue(position.getRow() + 1, position.getColumn() - 2);
		if (getBoard().PositionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}


		p.setValue(position.getRow() + 1, position.getColumn() + 2);
		if (getBoard().PositionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}


		p.setValue(position.getRow() - 2, position.getColumn() - 1);
		if (getBoard().PositionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}


		p.setValue(position.getRow() - 2, position.getColumn() + 1);
		if (getBoard().PositionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}


		p.setValue(position.getRow() + 2, position.getColumn() - 1);
		if (getBoard().PositionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}


		p.setValue(position.getRow() + 2, position.getColumn() + 1);
		if (getBoard().PositionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		return mat;
	}

}
