package client;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
/**
 * Handles the game scene objects' logic.
 * @author Stephanie Garcia Ribot
 *
 */
public class GameController implements Initializable {
	

	@FXML private Button attackButton;
	@FXML private Button defendButton;
	@FXML private TextField attackTextField;
	@FXML private TextField defendTextField;
	@FXML private Label gameTimer;
	@FXML private GridPane wordGridPane; 
	@FXML private Label localPlayerNameLabel;
	@FXML private Label opponentNameLabel;
	@FXML private Label scoreLabel1;
	@FXML private Label scoreLabel2;
	
	@FXML private void attack(ActionEvent event) {
		String attackText = attackTextField.getText();

		int wordIndex = Main.wordList.isAttackWordInList(attackText);

		if(wordIndex == WordList.WORD_NOT_FOUND) { // If the word was misspelled
			// Notify opponent's client that a word was misspelled, so the score is updated
			CommunicationUtilities.sendMessage(CommunicationUtilities.WORD_MISSPELLED);
			
			// Update score
			Main.firstPlayerScore -= 5;
			setLocalPlayerScoreLabel(Main.firstPlayerScore);
		} else {  // If the word is correctly spelled
			// Send message to remote user
			CommunicationUtilities.sendMessage(CommunicationUtilities.ATTACK_MSG + Integer.toString(wordIndex));
			
			// Add word to GUI
			Main.addAttackingWord(attackText, Integer.toString(wordIndex)); 
		}
		attackTextField.setText(""); // Clear TextField
	}
	
	@FXML private void defend(ActionEvent event) {
		String defendText = defendTextField.getText();

		int wordIndex = Main.wordList.isDefendWordInList(defendText);

		if(wordIndex == WordList.WORD_NOT_FOUND) { // If the word is not in list
			// Update score and notify opponent
			CommunicationUtilities.sendMessage(CommunicationUtilities.WORD_MISSPELLED);
			Main.firstPlayerScore -= 5;
			setLocalPlayerScoreLabel(Main.firstPlayerScore);
		} else { // If the word is in the list
			// Send message to remote user
			
			CommunicationUtilities.sendMessage(CommunicationUtilities.DEFEND_MSG + Integer.toString(wordIndex));
			// Remove defended word from the GUI
			Main.removeDefendedWordfromGUI(Integer.toString(wordIndex));
		}
		defendTextField.setText(""); // Clear TextField
	}
	
	public void updateTimer(int timeRemaining) {
		// Run this in the UI Thread
		Platform.runLater(new Runnable(){
			public void run() {
				gameTimer.setText(Integer.toString(timeRemaining));	
			}
		});
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Enable attacking with the "enter" key
		attackTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			  @Override
			  public void handle(KeyEvent event) {
			    if (event.getCode()==KeyCode.ENTER) {
			      attack(null);
			    } 
			  }
		});
		
		// Enable defending with the "enter" key
		defendTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			  @Override
			  public void handle(KeyEvent event) {
			    if (event.getCode()==KeyCode.ENTER) {
			      defend(null);
			    }
			  }
		});
		
		// Add backspace and delete key filter
		attackTextField.addEventFilter(KeyEvent.KEY_PRESSED, disableKeysFilter());
		defendTextField.addEventFilter(KeyEvent.KEY_PRESSED, disableKeysFilter());
		
		localPlayerNameLabel.setText(((Main) Main.application).getLocalPlayerName());
		opponentNameLabel.setText(((Main) Main.application).getOpponentName());
		populateWordGrid();
		
	}
	
	public void changeList() {
		WordList.listLowerBound = 10;
		populateWordGrid();
		
        FadeTransition fadeTransition 
        = new FadeTransition(Duration.millis(200), wordGridPane);
		fadeTransition.setFromValue(0.0);
		fadeTransition.setToValue(1.0);
		fadeTransition.play();
	}
	public void populateWordGrid() {

		int wordIndex = WordList.listLowerBound;
		wordGridPane.getChildren();
		for (Node node : wordGridPane.getChildren()) {
	            ((Label) node).setText(WordList.listOfLists.get(wordIndex).get(WordList.WORD));
	        wordIndex++;
	    }
	}
	
	// Filter to disable backspace and delete keys
	public static EventHandler<KeyEvent> disableKeysFilter() {
        EventHandler<KeyEvent> filter = new EventHandler<KeyEvent>() {
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.BACK_SPACE || keyEvent.getCode() == KeyCode.DELETE) {
                    keyEvent.consume();
                }
            }
        };
        return filter;
    }
	
	// Change the local player's score in the GUI
	public void setLocalPlayerScoreLabel(int firstPlayerScore) { 
		scoreLabel1.setText(Integer.toString(firstPlayerScore));
		
		// Animate score change
        FadeTransition fadeTransition 
                = new FadeTransition(Duration.millis(400), scoreLabel1);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
	}
	
	// Change the remote player's score in the GUI
	public void setRemotePlayerScoreLabel(int secondPlayerScore) { 
		scoreLabel2.setText(Integer.toString(secondPlayerScore));
		
		// Animate score change
        FadeTransition fadeTransition 
                = new FadeTransition(Duration.millis(400), scoreLabel2);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
	}
	
}
