package server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
/**
 * Handles the threads the server has to run.
 * @author Stephanie Garcia Ribot
 *
 */

public class ServerThread extends Thread {
	private static final String GAME_START = "Ø";
	
	private Socket socketPlayer1;
	private Socket socketPlayer2;
	
	private static boolean gameRunning = true;
	private BufferedReader playerBufferedReader1;
	private PrintStream playerPrintStream2;
	private BufferedReader playerBufferedReader2;
	private PrintStream playerPrintStream1;
	
	public ServerThread(Socket socketPlayer1, Socket socketPlayer2) {
		this.socketPlayer1 = socketPlayer1;
		this.socketPlayer2 = socketPlayer2;
	}
	
	public void run() {
		
		try {
			playerBufferedReader1 = new BufferedReader(new InputStreamReader(socketPlayer1.getInputStream()));
			playerBufferedReader2 = new BufferedReader(new InputStreamReader(socketPlayer2.getInputStream()));
			playerPrintStream1 = new PrintStream(socketPlayer1.getOutputStream());
			playerPrintStream2 = new PrintStream(socketPlayer2.getOutputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			while(!playerBufferedReader1.readLine().equals(GAME_START)) {
				// Wait for player 1 to press "Start" button
			}
			
			while(!playerBufferedReader2.readLine().equals(GAME_START)) {
				// Wait for player 2 to press "Start" button
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		long startTime = System.currentTimeMillis() + 5000;
		
		playerPrintStream1.println(GAME_START + startTime);
		playerPrintStream2.println(GAME_START + startTime);
		
		Thread player1CommunicationThread = new PlayerCommunicationThread(playerBufferedReader1,playerPrintStream2);
		Thread player2CommunicationThread = new PlayerCommunicationThread(playerBufferedReader2,playerPrintStream1);
		player1CommunicationThread.start();
		player2CommunicationThread.start();
		
		while(gameRunning) {
			// Game running
		}
	}
	
	public static void stopServerThread() { 
		gameRunning = false;
	}
}