package luellaoife;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUIDriver extends Application {

	int wrongs = 0;
	@Override
	public void start(Stage stage) throws Exception {
		
		Random rand = new Random();
		ArrayList<String> sentences = new ArrayList<>() ;
		try { 
			File f = new File("hangman_sentences_500_cleaned.txt");
			Scanner in = new Scanner(f);
			String content = "";
			while (in.hasNext()) {
				content += " " +in.next().replaceAll("'", "").replaceAll("\\."," ");
			}
			String[] sentencesArray = content.split(",");
			
			for (String s: sentencesArray) {
				sentences.add(s);
			}
			
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("There must be a file issue");
			System.out.println(e.getMessage());
		}
		
		
		
		//randomly select sentence
		
		String randomSentence = sentences.get(rand.nextInt(sentences.size())).toLowerCase();
		System.out.println(randomSentence);
		
		VBox root = new VBox(10);
		root.setAlignment(Pos.CENTER);
		Label triesLeft = new Label("You have "+(6-wrongs)+" tries left.");
		
		Button[] letterBtns = new Button[26];
		String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", 
	            "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
		
		Label[] unrevealedWord = new Label[randomSentence.length()];
		
		HBox wordBox = new HBox(8);
		
		wordBox.setAlignment(Pos.CENTER);
		
		for (int i = 0; i < unrevealedWord.length; i++) {
		    char letter = randomSentence.charAt(i);
		    if (letter == ' ') {
		        unrevealedWord[i] = new Label(" ");
		    } else {
		    	unrevealedWord[i] = new Label("_");
		    }
		    unrevealedWord[i].setStyle("-fx-font-size: 20; -fx-padding: 5;");
		}
		
		wordBox.getChildren().addAll(unrevealedWord);
	
		Letter[] letters = new Letter[26];
		
		for (int i=0; i < letters.length;i++) {
			Letter letter = new Letter(alphabet[i].charAt(0));
			letters[i] = letter;
		}
		
		HBox row1 = new HBox(4);
		HBox row2 = new HBox(4);
		
		row1.setAlignment(Pos.CENTER);
		row2.setAlignment(Pos.CENTER);

		for (int i =0; i < letterBtns.length;i++) {
			Button letter = new Button(alphabet[i]);
			letterBtns[i] = letter; 
			if (i < 13) {
				row1.getChildren().add(letter);
			}
			else {
				row2.getChildren().add(letter);
			}
		}
		
		for (int i= 0; i < letterBtns.length; i++) {
			final int index = i;
			letterBtns[i].setOnAction(e-> {
				if (!letters[index].isGuessed()) {
					letters[index].guess();
					letterBtns[index].setDisable(true);
					
					
					if (randomSentence.contains(letterBtns[index].getText())) {
						letterBtns[index].setStyle("-fx-background-color: #90EE90");
						for (int j = 0; j < randomSentence.length(); j++) {
							String letter =randomSentence.substring(j,j+1);
							if (letter.equals(letterBtns[index].getText())) {
								unrevealedWord[j].setText(letter);
							}
						}
					}
					else {
						letterBtns[index].setStyle("-fx-background-color: #FF0000");
						wrongs+=1;
						triesLeft.setText("You have "+(6-wrongs)+" tries left.");
					}
				}
					
			});
		}
		
		root.getChildren().addAll(triesLeft, wordBox, row1, row2);
		Scene scene1 = new Scene (root, 500, 500);
		stage.setScene(scene1);
		
		
		stage.show();
	}

	public static void main(String[] args) {
		
		
		launch(args);

	}

}
