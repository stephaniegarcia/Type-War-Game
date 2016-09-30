package client;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * Shows the ending scene.
 * @author Stephanie Garcia Ribot 
 *
 */
public class EndController implements Initializable {
	
	@FXML Label localUserScore;
	@FXML Label remoteUserScore;
	@FXML Label localUserName;
	@FXML Label remoteUserName;
	@FXML ImageView resultsImage;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		localUserName.setText(((Main) Main.application).getLocalPlayerName());
		remoteUserName.setText(((Main) Main.application).getOpponentName());
		localUserScore.setText(Integer.toString(Main.firstPlayerScore));
		remoteUserScore.setText(Integer.toString(Main.secondPlayerScore));
		
		if(Main.firstPlayerScore > Main.secondPlayerScore ) {
			// Load the image
			Image image = new Image("/YouWin.png");
			
			// Set the image
			resultsImage.setImage(image);
		} else {
			// Load the image
			Image image = new Image("/YouLose.png");
			
			// Set the image
			resultsImage.setImage(image);
		} 
	}
	
}
