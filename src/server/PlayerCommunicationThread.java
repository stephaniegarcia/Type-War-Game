package server;
import java.io.BufferedReader;
import java.io.PrintStream;
/**
 * Handles the threads of the players.
 * @author Stephanie Garcia Ribot
 *
 */

public class PlayerCommunicationThread extends Thread {
	
	private BufferedReader playerABufferedReader;
	private PrintStream playerBPrintStream;
	
	public PlayerCommunicationThread(BufferedReader playerABufferedReader, PrintStream playerBPrintStream) {
		this.playerABufferedReader = playerABufferedReader;
		this.playerBPrintStream = playerBPrintStream;
		
	}
	
	public void run() {
		try {

			String playerString = "";
			
			boolean gameRunning = true;
			
			while(gameRunning) {
				playerString = playerABufferedReader.readLine();
				playerBPrintStream.println(playerString);
			}	
		} catch(Exception e) {
			System.out.println(e);
			playerBPrintStream.println("The other player has disconnected");
			ServerThread.stopServerThread();
		}
	}
}
