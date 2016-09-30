package client;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 *Handles the logic of the game.
 * @author Stephanie Garcia Ribot	
 */

public class Main extends Application {
	
	public static final int GAME_TIME = 90; 		// Number of seconds the game will last
	public static final int CHANGE_LIST_TIME = 45;	// Time at which the word list will change during the game
	
	public static Application application;
	private Stage primaryStage;
	public static final WordList wordList= new WordList();
	private static Group root;
	private static Vector<PathTransition> attackingWordVector= new Vector<PathTransition>();
	private static Vector<PathTransition> defendingWordVector= new Vector<PathTransition>();
	private String localPlayerName = "Player";
	private String opponentName = "Opponent";
	public static GameController gameController;
	public static int firstPlayerScore;
	public static int secondPlayerScore;
	
	@Override
	public void start(Stage primaryStage) {
		application = this;
		
		try {
				this.primaryStage = primaryStage;
				
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/IntroView.fxml"));
				root = new Group(fxmlLoader.load());
				Scene scene = new Scene(root);
				
				this.primaryStage.setScene(scene);
				this.primaryStage.show();
				
				@SuppressWarnings("unused") // RX thread is created here
				CommunicationUtilities commUtils = new CommunicationUtilities();
			} catch(Exception e) { 
				e.printStackTrace();
		}
	}
	/**
	 * Add to the vector the words that are attacking.
	 * @param wordText The word to be added to the vector
	 * @param wordIndex The index of the word.
	 */
	public static void addAttackingWord(String wordText, String wordIndex) {
		attackingWordVector.addElement(AnimatedWordGenerator.addWord(root, wordText, wordIndex, AnimatedWordGenerator.WORD_DIRECTION_UP));
	}
	/**
	 * Adds to the vector the words that are to be defended.
	 * @param wordText The word to be added to the vector
	 * @param wordIndex The index of the word.
	 */
	public static void addDefendingWord(String wordText, String wordIndex) {
		defendingWordVector.addElement(AnimatedWordGenerator.addWord(root, wordText, wordIndex, AnimatedWordGenerator.WORD_DIRECTION_DOWN));
	}
	
	/**
	 * Called when a word is successfully defended by the local user
	 * @param wordIndex The index of the word.
	 */
	public static void removeDefendedWordfromGUI(String wordIndex) {
		
		for(int i = 0; i < defendingWordVector.size(); i++) {
			if(wordIndex.equals(defendingWordVector.get(i).getNode().getId())) {
				PathTransition wordTransition = defendingWordVector.get(i);	// Get reference to word object
				wordTransition.stop();									// Stop animation
				wordTransition.getNode().setVisible(false);
				root.getChildren().remove(wordTransition.getPath());	// Remove path from GUI
				root.getChildren().remove(wordTransition.getNode());	// Remove shape from GUI
				defendingWordVector.remove(i); // Remove word object reference from vector
				break;
			}
		}
	}
	
	/**
	 * Called when a word is successfully defended by the remote user
	 *@param wordIndex The index of the word.
	 */
	public static void removeAttackingWordfromGUI(String wordIndex) {
		 
		for(int i = 0; i < attackingWordVector.size(); i++) {
			if(wordIndex.equals(attackingWordVector.get(i).getNode().getId())) {
				PathTransition wordTransition = attackingWordVector.get(i);	// Get reference to word object
				wordTransition.getNode().setVisible(false);
				wordTransition.getPath().setVisible(false);
				wordTransition.stop();									// Stop animation
				attackingWordVector.remove(i); // Remove word object reference from vector
				break;
			}
		}
	}
	
	/**
	 *Called when a word reaches the end line 
	 * @param source The reference to the object
	 */
	public static void removeWordfromGUI(Object source) {
		PathTransition wordTransition = (PathTransition) source;// Get reference to word object
		Node node =  wordTransition.getNode();
		root.getChildren().remove(wordTransition.getPath());	// Remove path from GUI
		root.getChildren().remove(node);	// Remove shape from GUI
		
		if(node.getRotate() == 180) {		// If opponent's word reached the end line
			secondPlayerScore +=  Integer.parseInt(WordList.listOfLists.get(Integer.parseInt(node.getId())).get(WordList.WORD_POINTS));
			gameController.setRemotePlayerScoreLabel(secondPlayerScore);
			defendingWordVector.remove(wordTransition);			// Remove word object reference from vector
		} else {												// If the local user's word reached the end line
			firstPlayerScore +=  Integer.parseInt(WordList.listOfLists.get(Integer.parseInt(node.getId())).get(WordList.WORD_POINTS));
			gameController.setLocalPlayerScoreLabel(firstPlayerScore);
			attackingWordVector.remove(wordTransition);			// Remove word object reference from vector
		}
		
	}
	
	public static void startTimer() {
		Timer timer = new Timer();
		
		timer.scheduleAtFixedRate(new TimerTask() {
			int timeRemaining = GAME_TIME; // Number of seconds the game will last
			
			@Override
			public void run() {
				
				gameController.updateTimer(timeRemaining);
				
				if(timeRemaining == CHANGE_LIST_TIME) { // Change list
					Platform.runLater(new Runnable(){
						public void run() {
							WordList.setListLowerBound(10);
							WordList.setListUpperBound(19);
							gameController.changeList();
							gameController.populateWordGrid();
						}
					});
				}

				if(timeRemaining == 0) {
					// Game over 
					timer.cancel();
					
					Platform.runLater(new Runnable(){
						public void run() {
							((Main) Main.application).setEndScene();
						}
					});
				}
				
				timeRemaining--;
			}
		}, 0, 1000); // Every second 
	}
	
	public void setGameScene() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/GameView.fxml"));
		
		try {
			root = new Group(fxmlLoader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
		gameController = (GameController) fxmlLoader.getController();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); 
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void setEndScene() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/EndView.fxml"));
		
		try {
			root = new Group(fxmlLoader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public String getOpponentName() {
		return opponentName;
	}
	/**
	 * Puts the opponents name
	 * @param opponentName The name of the opponent.
	 */
	public void setOpponentName(String opponentName) {
		this.opponentName = opponentName;
	}

	public String getLocalPlayerName() {
		return localPlayerName;
	}
	/**
	 * Puts the name of the local player.
	 * @param localPlayerName The name of the local player
	 */

	public void setLocalPlayerName(String localPlayerName) {
		this.localPlayerName = localPlayerName;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
