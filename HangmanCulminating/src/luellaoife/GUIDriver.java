package luellaoife;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.FileInputStream;

public class GUIDriver extends Application {

	int guesses = 6;
	boolean gameDone = false;
	@Override
	public void start(Stage stage) throws FileNotFoundException {
		
		
		
		VBox menu = new VBox(20);
		menu.setAlignment(Pos.CENTER);

		menu.setStyle("-fx-background-color: beige");
		Label title = new Label("Hangman");
		title.setStyle("-fx-font-family: 'Copperplate'; -fx-font-size: 40; -fx-padding: 4; -fx-text-fill: #A67B5B;");
		Label options = new Label("Pick your category to save Bob!");
		Button genZBtn = new Button("Gen Z Phrases");
		Button movieBtn = new Button("Movie Quotes");
		Button songBtn = new Button("Song Lyrics");
		options.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20; -fx-padding: 5; -fx-text-fill: #A67B5B; -fx-font-weight: bold;");
		genZBtn.setStyle("-fx-font-size: 15; -fx-padding: 5; -fx-background-color: #4CAF50; -fx-text-fill: white;");
		movieBtn.setStyle("-fx-font-size: 15; -fx-padding: 5; -fx-background-color: #4CAF50; -fx-text-fill: white;");
		songBtn.setStyle("-fx-font-size: 15; -fx-padding: 5; -fx-background-color: #4CAF50; -fx-text-fill: white;");
		genZBtn.setOnAction(e -> {
			try {
				startGame(stage, "genZ_phrase.txt");  
			} catch (Exception e1) {
				System.out.println("There must be a file issue");
				System.out.println(e1.getMessage());
			}
		});

		movieBtn.setOnAction(e -> {
			try {
				startGame(stage, "movie_tv_quotes_5_6_words.txt");
			} catch (Exception e1) {
				System.out.println("There must be a file issue");
				System.out.println(e1.getMessage());
			}
		});

		songBtn.setOnAction(e -> {
			try {
				startGame(stage, "song_lyrics.txt");
			} catch (Exception e1) {
				System.out.println("There must be a file issue");
				System.out.println(e1.getMessage());
			}
		});

		menu.getChildren().addAll(title, options, genZBtn, movieBtn, songBtn);
		Scene startScene = new Scene(menu, 800, 400);
		stage.setScene(startScene);
		stage.show();
		menu.requestFocus(); 
	}
	public void startGame(Stage stage, String filename) throws Exception {
		
		Random rand = new Random();
		ArrayList<String> sentences = new ArrayList<>() ;
		try { 
			File f = new File(filename);
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
		
		VBox gameContent = new VBox(10);
		gameContent.setAlignment(Pos.CENTER_LEFT);

		Label triesLeft = new Label("You have "+(guesses)+" tries left.");
		triesLeft.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 14; -fx-padding: 5; -fx-text-fill: #A67B5B; -fx-font-weight: bold;");
		
		Button[] letterBtns = new Button[26];
		String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", 
	            "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
		
		Label[] unrevealedWord = new Label[randomSentence.length()];
		
		HBox wordBox1 = new HBox(8);
		HBox wordBox2 = new HBox(8);
		
		//TEST IMAGE
		Image leg2 = new Image(new FileInputStream("src/images/leg2.png"));
		Image leg1 = new Image(new FileInputStream("src/images/leg1.png"));
		Image arm2 = new Image(new FileInputStream("src/images/arm2.png"));
		Image arm1 = new Image(new FileInputStream("src/images/arm1.png"));
		Image body = new Image(new FileInputStream("src/images/body.png"));
		Image head = new Image(new FileInputStream("src/images/head.png"));
		Image nothing = new Image(new FileInputStream("src/images/nothing.png"));
		
		Image[] hangmanImages = {nothing,head,body,arm1,arm2,leg1,leg2};
		
		ImageView hangmanView = new ImageView(nothing);
		hangmanView.setFitWidth(280);
		hangmanView.setPreserveRatio(true);
		hangmanView.setX(575);
		hangmanView.setY(0);
		
		wordBox1.setAlignment(Pos.CENTER_LEFT);
		wordBox2.setAlignment(Pos.CENTER_LEFT);
		
		// Add left padding to shift it off the edge
		wordBox1.setPadding(new Insets(0, 0, 0, 15));
		wordBox2.setPadding(new Insets(0, 0, 0, 15));
		
		int row = 1;
		for (int i = 0; i < unrevealedWord.length; i++) {
		    char letter = randomSentence.charAt(i);
		    
		    if (row !=2 && i>15 && letter == ' ') {
		    	row = 2;
		    }
		    
		    if (letter == ' ') {
		        unrevealedWord[i] = new Label(" ");
		    } else {
		    	unrevealedWord[i] = new Label("_");
		    }
		    unrevealedWord[i].setStyle("-fx-font-size: 15; -fx-padding: 5;");
		    
		    if (row==2) {
		    	wordBox2.getChildren().add(unrevealedWord[i]);
		    }
		    else {
		    	wordBox1.getChildren().add(unrevealedWord[i]);
		    }
		}
		
		Letter[] letters = new Letter[26];
		
		for (int i=0; i < letters.length;i++) {
			Letter letter = new Letter(alphabet[i].charAt(0));
			letters[i] = letter;
		}
		
		HBox row1 = new HBox(4);
		HBox row2 = new HBox(4);
		
		row1.setAlignment(Pos.CENTER_LEFT);
		row2.setAlignment(Pos.CENTER_LEFT);
		
		// Add left padding to shift it off the edge
		row1.setPadding(new Insets(0, 0, 0, 50));
		row2.setPadding(new Insets(0, 0, 0, 50));

		for (int i =0; i < letterBtns.length;i++) {
			Button letter = new Button(alphabet[i]);
			letterBtns[i] = letter; 
			letter.setFocusTraversable(false);
			if (i < 13) {
				row1.getChildren().add(letter);
			}
			else {
				row2.getChildren().add(letter);
			}
			letter.setStyle("-fx-font-size: 15;");
		}
		
		for (int i= 0; i < letterBtns.length; i++) {
			final int index = i;
			letterBtns[i].setOnAction(e-> {
				if (!letters[index].isGuessed() && guesses!=1) {
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
						guesses-=1;
						hangmanView.setImage(hangmanImages[6-guesses]);
						triesLeft.setText("You have "+(guesses)+" tries left.");
					}
					String currentGuess = "";
					for (Label l : unrevealedWord) {
						currentGuess += l.getText();
					}
					if (currentGuess.equals(randomSentence)) {
						try {
							endGame(randomSentence, true, stage);
						} catch (FileNotFoundException e1) {
							System.out.println("There must be a file issue");
							System.out.println(e1.getMessage());
						}
					}
				}
				else {
					try {
						endGame(randomSentence,false, stage);
					} catch (FileNotFoundException e1) {
						System.out.println("There must be a file issue");
						System.out.println(e1.getMessage());
					}
				}
					
			});
		}
		Pane layout = new Pane();
		layout.setStyle("-fx-background-color: beige");
		
		VBox letterCenter = new VBox(10);
		letterCenter.setAlignment(Pos.CENTER); 
		letterCenter.getChildren().addAll( row1, row2, triesLeft);
		gameContent.getChildren().addAll(wordBox1, wordBox2, letterCenter);
		
		gameContent.setLayoutY(100);
		layout.getChildren().addAll(gameContent,hangmanView);
		Scene scene1 = new Scene (layout, 800, 400);
		stage.setScene(scene1);
		gameContent.requestFocus();
	
		stage.show();
	}
	
	
	public void endGame(String randomSentence, boolean win, Stage stage) throws FileNotFoundException {
		Image happy = new Image(new FileInputStream("src/images/happy.png"));
		Image sad = new Image(new FileInputStream("src/images/sad.png"));
		VBox overContent = new VBox (10);
		Pane over = new Pane();
		
		Label status = new Label("");
		Label revealedSentence = new Label("The sentence was: " +randomSentence);
		ImageView endingView = new ImageView();
		
		if (win == true) {
			status.setText("You survived!");
			endingView.setImage(happy);
		}
		else {
			status.setText("You got hanged :(");
			status.setStyle("-fx-font-family: 'Impact'; -fx-font-size: 20; -fx-padding: 5; -fx-text-fill: #911111;");
			endingView.setImage(sad);
			revealedSentence.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20; -fx-padding: 5; -fx-text-fill: #911111; -fx-font-weight: bold;");
		}
		endingView.setFitWidth(280);
		endingView.setPreserveRatio(true);
		endingView.setX(550);
		overContent.getChildren().addAll(status, revealedSentence);
		overContent.setLayoutY(170);
		overContent.setLayoutX(150);
		over.getChildren().addAll(overContent,endingView);
		Scene gameOver = new Scene(over, 800,400);
		stage.setScene(gameOver);
		stage.show();
		overContent.setAlignment(Pos.CENTER);

		
		
	}

	public static void main(String[] args) {
		
		
		launch(args);

	}

}
