package boardgame;

public abstract class Piece {
	
	protected Position position;
	protected Board board;
	
	public Piece(Board board) {
		this.board = board;
		position = null;
	}

	public Board getBoard() {
		return board;
	}
	public abstract boolean[][] PossibleMoves();
	
	public boolean PossibleMovie(Position position) {
		return PossibleMoves()[position.getRow()][position.getColumn()];
	}
	
	public boolean IsThereAnyPossibleMove() {
		boolean[][] mat = PossibleMoves();
		for(int i=0; i<mat.length; i++) {
			for(int j=0; j<mat.length; j++) {
				if(mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
}
