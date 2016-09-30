package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import javafx.application.Platform;
/**
 * Handles the client's communication with the server
 * @author Stephanie Garcia Ribot 
 *
 */
public class CommunicationUtilities {
	public static final String GAME_START = "Ø";
	public static final String SEND_NAME = "¶";
	public static final String ATTACK_MSG = "æ";
	public static final String DEFEND_MSG = "õ";
	public static final String WORD_MISSPELLED = "ß"; 

	Socket socket;
	private static BufferedReader bufferedReader;
	private static PrintStream printStream;
	private boolean gameRunning = true;
	private static String remotePlayerString;
	
	public CommunicationUtilities() {

		try {
			socket = new Socket("localhost", 7889);	
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			printStream = new PrintStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		createRXThread();
	}
	
	// Creates and starts RX thread
	public void createRXThread() {
		Thread rxThread = new Thread(new Runnable() {
			public void run() {
				while(gameRunning) {
					// Listen for incoming messages
					try {
						remotePlayerString = bufferedReader.readLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					if(remotePlayerString.contains(CommunicationUtilities.ATTACK_MSG)) { // Being attacked
						// Get subIndex from string
						String subIndex = remotePlayerString.substring(1);
						
						// Add word to GUI; Run this in UI thread
						Platform.runLater(new Runnable(){
							@Override
							public void run() {
								Main.addDefendingWord(WordList.listOfLists.get(Integer.parseInt(subIndex)).get(WordList.REVERSE_WORD), subIndex); 
							}
						});
					} else if (remotePlayerString.contains(CommunicationUtilities.DEFEND_MSG)) { // An attack was defended by the remote user
						// Get subIndex from string
						String subIndex = remotePlayerString.substring(1);
						
						// Remove word from GUI
						Platform.runLater(new Runnable(){
							@Override
							public void run() {
								Main.removeAttackingWordfromGUI(subIndex); 
							}
						});  
					} else if ((remotePlayerString.equals(CommunicationUtilities.WORD_MISSPELLED))) { // The opponent misspelled a word
						// Update score, and GUI; Run this in the UI thread
						Platform.runLater(new Runnable(){
							@Override
							public void run() {
								Main.secondPlayerScore -= 5;
								Main.gameController.setRemotePlayerScoreLabel(Main.secondPlayerScore); 
							}
						});
					} else if ((remotePlayerString.contains(CommunicationUtilities.GAME_START))) { // Begin the game
						
						long startTime = Long.valueOf(remotePlayerString.substring(1));
						
						// Send local player's name to opponent
						sendMessage(CommunicationUtilities.SEND_NAME + ((Main) Main.application).getLocalPlayerName());
						
						// Set the game scene; Run this in the UI thread
						Platform.runLater(new Runnable(){
							@Override
							public void run() {
								while(System.currentTimeMillis() < startTime) {
									// Wait
								}
								((Main) Main.application).setGameScene();
								Main.startTimer(); 
							}
						});
					} else if ((remotePlayerString.contains(CommunicationUtilities.SEND_NAME))) { // Set opponent's name
						// Get opponent's name from received string
						String opponentName = remotePlayerString.substring(1);
						
						// Set opponent's name
						((Main) Main.application).setOpponentName(opponentName);
					}
				}
				
				// Game over; close socket
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		rxThread.start();
	}
	
	/**
	 * Sends a message to server
	 * @param msg The message it receives.
	 */
	public static void sendMessage(String msg) {
		printStream.println(msg);
	}
	/**
	 * Sets the game to run
	 * @param gameRunning true if game is running, false if it is not.
	 */
	public void setGameRunning(boolean gameRunning) {
		this.gameRunning = gameRunning;
	}
	
	
}
