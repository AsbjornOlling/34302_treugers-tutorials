/*
 * Asbjørns failsome attempt at AI
 */

import java.util.ArrayList;

public class AI {
	private final int MAX_DEPTH = 10000;
	private final char PLAYER;
	private final char OPPONENT;
	private static final boolean DEBUG = true;

	/*
	public static void main(String[] args) {
		// works fine from here (last i checked)
		char[] aBoard = {'O','.','O',
										 '.','X','.',
										 'X','X','O'};
		AI robot = new AI('O');
		robot.makeMove(aBoard);
	}
		//*/


	// constructor
	public AI(char playerSymbol) {
		// set player symbols for the game
		this.PLAYER = playerSymbol;
		if (this.PLAYER == 'X') this.OPPONENT = 'O';
		else if (this.PLAYER == 'O') this.OPPONENT = 'X';
		else {
			this.OPPONENT = ' '; // TODO is this line even necessary?
			throw new IllegalArgumentException("Bad playerSymbol passed to AI constructor:" + playerSymbol);
		}
	} // constructor


	// top level function
	// returns a best move in range [1;9[
	public int makeMove(char[] passedBoard) {
		// get list of empty indexes
		ArrayList<Integer> validMoves = getEmptyIndexes(passedBoard);

		if ( DEBUG ) System.out.println("Checking moves for "+PLAYER+" at depth 0");

		int bestMove = -1; // invalid move - to ake sure its replaced
		int bestScore = -20; // lowest possible score
		for ( int i : validMoves ) {

			// make board based on the hypothetical move
			char[] newBoard = passedBoard.clone();
			newBoard[i] = PLAYER;

			// check if this move is the best found so far
			int thisScore = evaluateBoard(newBoard, OPPONENT, 1);
			if ( thisScore > bestScore ) {
				bestMove = i;	
				bestScore = thisScore;
			} 
		} // loop

		if ( DEBUG ) System.out.println("Final move decision (index): " + bestMove);

		// +1 to match board count starting on 1
		return bestMove + 1;
	} // makeMove

	
	// recursive minmax algorithm for finding the best move
	// returns array {score,moveindex}
	private int evaluateBoard(char[] passedBoard, char currentPlayer, int depth) {

		// figure out indentation level for debuggin prints
		String indent = "";
		if (DEBUG) {
			for (int j = 0; j < depth; j++ ) indent+="   ";
		}
		// print indented debug line
		if (DEBUG) System.out.println(indent+"Checking the moves for "+currentPlayer+" at depth: "+depth);

		// figure out who the other player is
		char otherPlayer = ' ';
		if ( currentPlayer == 'X' ) {
			otherPlayer = 'O';
		} else if ( currentPlayer == 'O' ) {
			otherPlayer = 'X';
		}

		// get list of empty indexes
		ArrayList<Integer> validMoves = getEmptyIndexes(passedBoard);
		
		// check for terminal gamestates, and break and return
		if ( checkForWin(passedBoard, PLAYER) ) {
			if ( DEBUG ) System.out.println(indent+"Found win for PLAYER");
			return 10;
		} else if ( checkForWin(passedBoard, OPPONENT) ) {
			if ( DEBUG ) System.out.println(indent+"Found win for OPPONENT");
			return -10;
		} else if (validMoves.size() == 0) {
			if ( DEBUG ) System.out.println(indent+"Found TIE");
			return 0;
		}

		int bestScore = 0; // TODO change this to high if PLAYER, low if OPPONENT
		for (int i : validMoves) {

			// make board based on the hypothetical move
			char[] newBoard = passedBoard.clone();
			newBoard[i] = currentPlayer;

			int thisScore = evaluateBoard(newBoard, otherPlayer, depth + 1);

			// if OPPONENT finds a low score
			// or PLAYER finds a high score
			if ( ( thisScore > bestScore && currentPlayer == PLAYER ) || ( thisScore < bestScore && currentPlayer == OPPONENT ) ) {
				bestScore = thisScore;
			}
		} // loop


		if ( DEBUG ) System.out.println(indent+"Passing up score: "+bestScore);
		return bestScore;
	} // evaluateBoard


	public ArrayList<Integer> getEmptyIndexes(char[] passedBoard) {
		// find the empty spaces on the board
		ArrayList<Integer> validMoves = new ArrayList<Integer>();
		for (int i = 0; i < passedBoard.length; i++) {
			if (passedBoard[i] == '.') {
				validMoves.add(i);
			}
		} // loop
		return validMoves;
	} // getValidMoves


	public boolean checkForWin(char[] board, char player){
		if (
		// horizontal wins
		(board[0] == player && board[1] == player && board[2] == player) ||
		(board[3] == player && board[4] == player && board[5] == player) ||
		(board[6] == player && board[7] == player && board[8] == player) ||
		// vertical wins
		(board[0] == player && board[3] == player && board[6] == player) ||
		(board[1] == player && board[4] == player && board[7] == player) ||
		(board[2] == player && board[5] == player && board[8] == player) ||
		// diagonal wins
		(board[0] == player && board[4] == player && board[8] == player) ||
		(board[2] == player && board[4] == player && board[6] == player) 
	 	) {

			// if win
			return true;
		} else  {
			// if no win
			return false;
		}
	} // checkForWin
} // class
