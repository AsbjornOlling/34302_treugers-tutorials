/* Handles connection to server;
 *
 * Contains methods that will send
 * specific tic-tac-toe moves.
 *
 * Parses output from server, passes back to main.
 *
 * Blame David for any and all faults.
 */

import java.io.BufferedReader;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ServerConnection {

	// private constants	
	/* are these even reused? if we only use them in the constructor, 
	 * maybe we could make do with just the constructor parameters? */
	private final String HOST; // The host adress
	private final int PORT; // The host port

	// other private fields
	Socket link = null;
	BufferedReader response = null;
	PrintWriter outgoing;

	// fields to be fetched by main class
	public String startingPlayer; // Who starts
	public String boardState; // What does the board look like now
	public String gameState; // The line about win/lose or keep going
	public boolean serverIsActive = false; // whether the connection is working - false until established

	// The constructor handling the server connection
	public ServerConnection(String host, int port) {

		// Initializing our flags
		this.HOST = host;
		this.PORT = port;

		// Error handling for link
		try {
			link = new Socket(HOST, PORT);

			serverIsActive = true;
			response = new BufferedReader(new InputStreamReader(link.getInputStream()));
			outgoing = new PrintWriter(link.getOutputStream());

			// Read the first lines (very first one is unique)
			startingPlayer = response.readLine();
			boardState = response.readLine();
			gameState = response.readLine();
		} catch (IOException ioEx) {
			ioEx.printStackTrace();
		}
	} // constructor

	// just send a passed integer to socket using printwriter
	// TODO: probably add some error handling here - retry if not received, etc
	public void sendPlayerMove(int space) {
		outgoing.print(space+"\r\n");
		outgoing.flush();
		getNewState();
	} // getNewState

	// reads two lines
	// tbh maybe this should just be inside sendPlayerMove()
	private void getNewState(){
		try {
			boardState = response.readLine();
			gameState = response.readLine();
		} catch (IOException ioEx) {
			ioEx.printStackTrace();
		}
	} // getNewState

} // class
