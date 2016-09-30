package client;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
/**
 * Handles the introduction to the game scene.
 * @author Stephanie Garcia Ribot
 *
 */
public class IntroController implements Initializable {
	
	@FXML private Label logoLabel;
	@FXML private TextField nameTextField;
	@FXML private Button playButton;
	
	@FXML private void startGame() {
		if(!nameTextField.getText().equals("")) {
			((Main) Main.application).setLocalPlayerName(nameTextField.getText());
		} else {
			nameTextField.setText("Player");
		}
		playButton.setText("Starting game, please wait...");
		playButton.setDisable(true);
		nameTextField.setDisable(true);
		CommunicationUtilities.sendMessage(CommunicationUtilities.GAME_START);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
}
