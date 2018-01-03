/*
 * This is the main class to be compiled and run.
 *
 * Passes data between SeverConnection and BoardDrawer.
 * (maybe also an AI player)
 *
 * Also handles processing of userinput.
 *
 * Blame Asbjørn for any and all faults.
 */

// TODO: 
// UI flow for human player mode is not perfect
// see especially end game scenarios

// AI still *occasionally* makes an illegal move
// like ever 100 games or so - why is this?
// bug is most easily provokable in continuous mode

import java.util.*;

// comfy gui shizzle
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToe extends JFrame implements ActionListener {
	// connection object
	private ServerConnection LarsServer;

	// gamestate vars
	private int playerMove;
	private char playerSymbol;
	// init boardstate
	private char[] blankBoard = {'.','.','.','.','.','.','.','.','.'};
	public char[] boardState = blankBoard.clone();

	// win/loss counters 
	// mostly relevant against AI
	private int noOfWins;
	private int noOfTies;
	private int noOfLosses;

	// ui stuff
	public JButton[] buttons;

	// constructor
	public TicTacToe() {
		// set 3x3 grid
		getContentPane().setLayout(new GridLayout(3,3));		

		// fill grid with buttons
		buttons = new JButton[9];
		for (Integer i = 0; i < 9; i++) {
			JButton button = new JButton(Character.toString(boardState[i]));
			button.addActionListener(this);
			getContentPane().add(button);
			this.buttons[i] = button;
		}

		// establish server connection
		// init board state
		startNewGame();

	}

	// event listener 
	public void actionPerformed(ActionEvent event) {
		String cstring = event.getSource().getClass().getName();
		System.out.println("EVENT CLASS: " + cstring);

		if (cstring == "javax.swing.JButton") {
			// get index of button
			JButton eBtn = (JButton) event.getSource();
			int eIdx = Arrays.asList(buttons).indexOf(eBtn);

			// send command to server
			LarsServer.sendPlayerMove(eIdx + 1);

			// get new boardstate and update ui
			updateUI();	
		} else {
			System.out.println("SOMETHING ELSE HAPPENED");
		}
	} // event listener

	public void updateUI() {
		parseBoardState();
		// go through the board, copying text from boardState
		for (int i = 0; i < boardState.length; i++) {
			buttons[i].setText(Character.toString(boardState[i]));
		}
		handleWin();
	}

	public void handleWin() {
		String resultstring;
		if (! LarsServer.gameState.equals("YOUR TURN")) {
			if ( LarsServer.gameState.equals("SERVER WINS") ) {
				noOfLosses++;
				resultstring = "YOU LOSE. WEAK.";	
			} else if ( LarsServer.gameState.equals("NOBODY WINS") ) {
				noOfTies++;
				resultstring = "WOW. YOU DIDN'T LOSE";
			} else { // i don't see this very often...
				resultstring = "DID YOU JUST WIN? WHAT THE HELL.";	
				System.out.println(LarsServer.gameState);
				noOfWins++;
			}
			String statsstring = "\r\n Wins: "+noOfWins+"\r\n";
			statsstring += "Ties: "+noOfTies+"\r\n";
			statsstring += "Losses: "+noOfLosses+"\r\n";
			JOptionPane.showMessageDialog(null, resultstring + "\r\n" + statsstring);	
			startNewGame();
		}
	}

	// parse starting player line from field in connection object
	private char parsePlayerSymbol() {
		char symbol = LarsServer.startingPlayer.charAt(
																LarsServer.startingPlayer.length() - 1);
		if (! (symbol == 'X' || symbol== 'O') ) {
			throw new IllegalArgumentException(
																				 "parsePlayerSymbol() method"
																				 +"received a funny value.");
		}
		return symbol;
	} // parseStartingPlayer

	// takes the string field in ServerConnection, and puts it into a neat array
	// has already replaced a version that does more error checking
	// because fuck error checking
	private void parseBoardState() {
		String boardLine = LarsServer.boardState;

		// SOME error handlign
		// this is where ILLEGALMOVE is caught
		if (boardLine.length() != 18) {
			throw new IllegalArgumentException("Read bad boardLine from server: "+boardLine);
		}

		// loop through last 9 chars in the line from server
		for (int i = 0; i < boardLine.length() - 9; i++ ) {
			char boardLineChar = boardLine.charAt(i+9);
			boardState[i] = boardLineChar;
		} // loop
	} // parseBoardState */


	private void startNewGame() {
		// TODO put these value into fields or vars or SOMETHING
		LarsServer = new ServerConnection("itkomsrv.fotonik.dtu.dk",1102); 
		playerSymbol = parsePlayerSymbol();
		parseBoardState();
		boardState = blankBoard.clone();
		updateUI();
	} // startNewGame

	// temporary drawing mechanism
	private void tempBoardDrawer() {
		for (int i = 0; i < boardState.length; i++ ) {
			System.out.print(boardState[i]);
			if ((i+1) % 3 == 0) {
				System.out.print("\n");
			}
		} // loop
	} // BoardDrawer


	public static void main(String[] args) {
		TicTacToe game = new TicTacToe();

		// necessary window boilerplate shizzle
		game.setTitle("FUCK KRYDS! BOLLE. FOR. LIFE.");
		game.setSize(500, 500);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setVisible(true);


	} // main
} // class
