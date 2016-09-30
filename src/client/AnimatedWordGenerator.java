package client;


import java.util.Random;

import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.util.Duration;
/**
 * Generates animation for the words. 
 * @author Stephanie Garcia Ribot
 **/
@SuppressWarnings("deprecation")
public class AnimatedWordGenerator {
	
	public static final int WORD_DIRECTION_UP = 0; 
	public static final int WORD_DIRECTION_DOWN = 1;
	
	private static final int WORD_TRANSITION_TIME = 10; // In seconds

	/**
	    * Generate Path upon which animation will occur.
	    * @param wordDirection Holds the direction of the word. If 0, word is going up. If 1, word is going down.
	    * @return Generated path.
	    */
   private static Path generateStraightPath(int wordDirection) {
	   
		// RNG to randomize x coordinates on which the words will appear
		Random rng = new Random();
		final Path path = new Path();
		
		if(wordDirection == WORD_DIRECTION_UP) {
			int randomXCoordinate = rng.nextInt((500 - 300) + 1) + 300;
			path.getElements().add(new MoveTo((float) randomXCoordinate, 400.0f));
			path.getElements().add(new LineTo((float) randomXCoordinate, 110.0f));
		} else { // Word direction down
			int randomXCoordinate = rng.nextInt((250 - 50) + 1) + 50;
			path.getElements().add(new MoveTo((float) randomXCoordinate, 110.0f));
			path.getElements().add(new LineTo((float) randomXCoordinate, 400.0f));
		}
		
       path.setOpacity(0.0);
       return path;
   }

   /**
    * Generate the path transition.
    * 
    * @param shape Shape to travel along path.
    * @param path Path to be traveled upon.
    * @param duration Duration of single animation.
    * @param delay Delay before beginning first animation.
    * @param orientation Orientation of shape during animation.
    * @return PathTransition.
    */
   public static PathTransition generatePathTransition(
	  final String wordIndex,
      final Shape shape, final Path path,
      final Duration duration, final Duration delay,
      final OrientationType orientation)
   	  {
	   	  shape.setId(wordIndex);
	      final PathTransition pathTransition = new PathTransition();
	      pathTransition.setDuration(duration);
	      pathTransition.setDelay(delay);
	      pathTransition.setPath(path);
	      pathTransition.setNode(shape);
	      pathTransition.setOrientation(orientation);
	      pathTransition.setCycleCount(1);
	      return pathTransition;
   	  }

   /**
    * Generate text string with set properties
    * @param wordDirection Holds the direction of the word. If 0, word is going up. If 1, word is going down.
    * @param wordText The word to be animated.
    * @return text with properties
    */
	private static Text generateText(int wordDirection, String wordText) {
		int rotation = 0;
		
		Text text = null;
		
		if(wordDirection == WORD_DIRECTION_DOWN) {
			rotation = 180;
			
			text = TextBuilder.create().text(wordText).x(20).y(20).rotate(rotation).fill(Color.YELLOW)
			.font(Font.font(java.awt.Font.SERIF, 25))
			.effect(new Glow(1)).build();
		} else { // Word direction up
			text = TextBuilder.create().text(wordText).x(20).y(20).rotate(rotation).fill(Color.ORANGE)
			.font(Font.font(java.awt.Font.SERIF, 25))
			.effect(new Glow(1)).build();
		}
		
		return text;
	}
	

	 /**
	    * Apply animation.
	    *  
	    * @param group Group to which animation is to be applied.
	    * @param wordText Word to be animated.
	    * @param wordIndex Index of the word to be animated.
	    * @param wordDirection Holds the direction of the word. If 0, word is going up. If 1, word is going down.
	    * @return The transition of the word.
	    */
	public static PathTransition addWord(final Group group, String wordText, String wordIndex, int wordDirection) {
		final Path path = generateStraightPath(wordDirection);
		group.getChildren().add(path);
		final Shape wordShape = generateText(wordDirection, wordText);
		group.getChildren().add(wordShape);
		final PathTransition wordTransition = generatePathTransition(wordIndex, wordShape, path, Duration.seconds(WORD_TRANSITION_TIME), 
																		Duration.seconds(0.0), OrientationType.NONE);
		
		// Set listener
		wordTransition.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent AE) {
				Main.removeWordfromGUI(AE.getSource());
			}
		});
		
		// Start animation
		wordTransition.play();
		
		return wordTransition;
	}
}
