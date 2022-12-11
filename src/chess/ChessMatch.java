package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean Check;
	private boolean checkMate;

	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();

	public ChessMatch() {
		board = new Board(8, 8);
		InitialSetup();
		turn = 1;
		currentPlayer = Color.White;
	}

	public int getTurn() {
		return turn;
	}

	public Color getcurrentPlayer() {
		return currentPlayer;
	}

	public boolean getCheck() {
		return Check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}

	public ChessPiece[][] getPiece() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;

	}

	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).PossibleMoves();
	}

	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);

		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself in check");
		}

		Check = (testCheck(opponent(currentPlayer)))? true : false;

		if (testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		} else {
			nextTurn();
		}

		return (ChessPiece) capturedPiece;
	}

	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece) board.removePiece(source);
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);

		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		//#specialmove castling kingside rook
		if(p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1 );
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}
		
		//#specialmove castling Queenside rook
		if(p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1 );
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}
		
		return capturedPiece;
	}

	private void undoMove(Position source, Position target, Piece caputedPiece) {
		ChessPiece p = (ChessPiece) board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);

		if (caputedPiece != null) {
			board.placePiece(caputedPiece, target);
			capturedPieces.remove(caputedPiece);
			piecesOnTheBoard.add(caputedPiece);

		}
		
		//#specialmove castling kingside rook
		if(p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1 );
			ChessPiece rook = (ChessPiece)board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}
		
		//#specialmove castling Queenside rook
		if(p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1 );
			ChessPiece rook = (ChessPiece)board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}
		
		
		
	}

	private void validateSourcePosition(Position position) {
		if (!board.ThereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
			throw new ChessException("The chosen piece is not yours");
		}
		if (!board.piece(position).IsThereAnyPossibleMove()) {
			throw new ChessException("There iss no Possible Moves for the chose Piece");
		}
	}

	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).PossibleMovie(target)) {
			throw new ChessException("The chosen piece can't move to the target position");
		}
	}

	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.White) ? Color.Black : Color.White;
	}

	public Color opponent(Color color) {
		return (color == Color.White) ? Color.Black : Color.White;
	}

	public ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece) p;
			}
		}
		throw new IllegalStateException("There is no " + color + " King on the board");
	}

	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.PossibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) {
			return false;
		}

		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece p : list) {
			boolean[][] mat = p.PossibleMoves();
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColumns(); j++) {
					if (mat[i][j]) {
						Position source = ((ChessPiece) p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}

	private void InitialSetup() {
		placeNewPiece('a', 1, new Rook(board, Color.White));
		placeNewPiece('c', 1, new Bishop(board, Color.White));
		placeNewPiece('b', 1, new Knight(board, Color.White));
		placeNewPiece('e', 1, new King(board, Color.White, this));
		placeNewPiece('d', 1, new Queen(board, Color.White));
		placeNewPiece('f', 1, new Bishop(board, Color.White));
		placeNewPiece('g', 1, new Knight(board, Color.White));
		placeNewPiece('h', 1, new Rook(board, Color.White));
		placeNewPiece('a', 2, new Pawn(board, Color.White));
		placeNewPiece('b', 2, new Pawn(board, Color.White));
		placeNewPiece('c', 2, new Pawn(board, Color.White));
		placeNewPiece('d', 2, new Pawn(board, Color.White));
		placeNewPiece('e', 2, new Pawn(board, Color.White));
		placeNewPiece('f', 2, new Pawn(board, Color.White));
		placeNewPiece('g', 2, new Pawn(board, Color.White));
		placeNewPiece('h', 2, new Pawn(board, Color.White));

		placeNewPiece('a', 8, new Rook(board, Color.Black));
		placeNewPiece('c', 8, new Bishop(board, Color.Black));
		placeNewPiece('b', 8, new Knight(board, Color.Black));
		placeNewPiece('e', 8, new King(board, Color.Black, this));
		placeNewPiece('d', 8, new Queen(board, Color.Black));
		placeNewPiece('f', 8, new Bishop(board, Color.Black));
		placeNewPiece('g', 8, new Knight(board, Color.Black));
		placeNewPiece('h', 8, new Rook(board, Color.Black));
		placeNewPiece('a', 7, new Pawn(board, Color.Black));
		placeNewPiece('b', 7, new Pawn(board, Color.Black));
		placeNewPiece('c', 7, new Pawn(board, Color.Black));
		placeNewPiece('d', 7, new Pawn(board, Color.Black));
		placeNewPiece('e', 7, new Pawn(board, Color.Black));
		placeNewPiece('f', 7, new Pawn(board, Color.Black));
		placeNewPiece('g', 7, new Pawn(board, Color.Black));
		placeNewPiece('h', 7, new Pawn(board, Color.Black));
	}

}
