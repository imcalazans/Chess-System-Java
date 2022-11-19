package Applocation;

import boardgame.Board;
import chess.ChessMatch;

public class program {

	public static void main(String[] args) {
	
		ChessMatch chessMatch = new ChessMatch(); 
		UI.printBoard(chessMatch.getPiece());
	}

}
