package Applocation;

import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class program {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch(); 
		
		while(true) {
		UI.printBoard(chessMatch.getPiece());
		System.out.println();
		System.out.print("Source: ");
		ChessPosition source = UI.readChessPosition(sc);
		
		System.out.println();
		System.out.print("Target: ");
		ChessPosition target = UI.readChessPosition(sc);
		
		ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
		
		}
	}

}
