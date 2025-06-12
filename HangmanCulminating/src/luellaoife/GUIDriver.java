package luellaoife;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.FileInputStream;

/**
 * Hangman Game Interface with Multiple Modes
 * 
 * @author A.Crow & L. Mailloux
 */
public class GUIDriver extends Application {

	int guesses = 6; //total guesses for regular hangman modes
	int evilGuesses =8; //total guesses for evil hangman mode
	
	@Override
	public void start(Stage stage) throws FileNotFoundException {
		
		// Create a StackPane to layer the title on top of the VBox
		StackPane menu = new StackPane();
		
		//create background 
		BackgroundImage background = new BackgroundImage(
			    new Image(new FileInputStream("src/images/first.png")),
			    BackgroundRepeat.REPEAT,
			    BackgroundRepeat.NO_REPEAT,
			    BackgroundPosition.DEFAULT,
			    new BackgroundSize(100, 100, true, true, true, false)
			);

		menu.setBackground(new Background(background));

		Text title = new Text("Hangman");
		//set styling for title
		title.setTranslateY(-120);
		title.setFill(Color.WHEAT); // Inner color
		title.setStroke(Color.web("#602c04")); // Outline
		title.setStrokeWidth(1); // Thickness of outline
		title.setStyle("-fx-font-family: 'Copperplate'; -fx-font-size: 50; -fx-padding: 4; -fx-font-weight: bold;");
		
		Text options = new Text("Pick your category to save Bob!");
		//set styling for options
		options.setFill(Color.WHEAT); // Inner color
		options.setStroke(Color.web("#602c04")); // Outline
		options.setStrokeWidth(1); // Thickness of outline
		options.setFont(Font.font("Copperplate", FontWeight.BOLD, 38));
		
		
		Button genZBtn = new Button("Gen Z Phrases");
		Button movieBtn = new Button("Movie Quotes");
		Button songBtn = new Button("Song Lyrics");
		Button boomerBtn = new Button("Boomer Slang");
		Button evilBtn = new Button("Evil Hangman");
		
		//set styling for buttons
		options.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20; -fx-padding: 5; -fx-text-fill: #000000; -fx-font-weight: bold;");
		genZBtn.setStyle("-fx-font-size: 15; -fx-padding: 5; -fx-background-color: #4CAF50; -fx-text-fill: white;");
		movieBtn.setStyle("-fx-font-size: 15; -fx-padding: 5; -fx-background-color: #4CAF50; -fx-text-fill: white;");
		songBtn.setStyle("-fx-font-size: 15; -fx-padding: 5; -fx-background-color: #4CAF50; -fx-text-fill: white;");
		boomerBtn.setStyle("-fx-font-size: 15; -fx-padding: 5; -fx-background-color: #4CAF50; -fx-text-fill: white;");
		evilBtn.setStyle("-fx-font-size: 15; -fx-padding: 5; -fx-background-color: #B22222; -fx-text-fill: white;");
		
		//set up buttons and call their respective game methods
		genZBtn.setOnAction(e -> {
			try {
				startGame(stage, "genz_quotes.txt");  
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
				startGame(stage, "song_titles.txt");
			} catch (Exception e1) {
				System.out.println("There must be a file issue");
				System.out.println(e1.getMessage());
			}
		});
		
		boomerBtn.setOnAction(e -> {
			try {
				startGame(stage, "boomer.txt");
			} catch (Exception e1) {
				System.out.println("There must be a file issue");
				System.out.println(e1.getMessage());
			}
		});
		evilBtn.setOnAction(e -> {
			try {
				evilGame(stage);  
			} catch (Exception e1) {
				System.out.println("There must be a file issue");
				System.out.println(e1.getMessage());
			}
		});
		
		// Create the VBox for the menu buttons and options
		VBox buttonBox = new VBox(20);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setTranslateY(35);
		buttonBox.getChildren().addAll(options, genZBtn, movieBtn, songBtn, boomerBtn, evilBtn);

		menu.getChildren().addAll(title,buttonBox);
		Scene startScene = new Scene(menu, 800, 400);
		stage.setScene(startScene);
		stage.show();
		menu.requestFocus();
	}
	
	/**
	 * selects a random sentence from the chosen category 
	 * creates letters, tries label, word display, and images
	 * updates letters and images based on user's guesses 
	 * 
	 * @param stage where the main game will be displayed
	 * @param filename text file with sentences separated by commas
	 * @throws Exception if any image or file is not found 
	 * @author luellaoife
	 * @version 1.0
	 */
	
	public void startGame(Stage stage, String filename) throws Exception {
		
		Random rand = new Random();
		ArrayList<String> sentences = new ArrayList<>();

		// Read file and load sentences
		try { 
			File f = new File(filename);
			Scanner in = new Scanner(f);
			String content = "";

			while (in.hasNext()) {
				content += " " + in.next().replaceAll("'", "").replaceAll("\\.", " ");
			}

			String[] sentencesArray = content.split(",");
			for (String s : sentencesArray) {
				sentences.add(s);
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("There must be a file issue");
			System.out.println(e.getMessage());
		}

		// Randomly select sentence
		String randomSentence = sentences.get(rand.nextInt(sentences.size())).toLowerCase();
		System.out.println(randomSentence);

		// Declare variables
		Button[] letterBtns = new Button[26];
		String[] alphabet = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", 
		                      "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
		Letter[] letters = new Letter[26];
		Text triesLeft = new Text("You have " + guesses + " tries left.");
		VBox gameContent = new VBox(10);
		VBox letterCenter = new VBox(10);
		HBox wordBox1 = new HBox(8);
		HBox wordBox2 = new HBox(8);
		HBox row1 = new HBox(4);
		HBox row2 = new HBox(4);

		// Create Letter objects
		for (int i = 0; i < letters.length; i++) {
			Letter letter = new Letter(alphabet[i].charAt(0));
			letters[i] = letter;
		}

		// Styles and alignments 
		gameContent.setAlignment(Pos.CENTER_LEFT);
		letterCenter.setAlignment(Pos.CENTER);
		wordBox1.setAlignment(Pos.CENTER_LEFT);
		wordBox2.setAlignment(Pos.CENTER_LEFT);
		row1.setAlignment(Pos.CENTER_LEFT);
		row2.setAlignment(Pos.CENTER_LEFT);
		wordBox1.setPadding(new Insets(0, 0, 0, 100));
		wordBox2.setPadding(new Insets(0, 0, 0, 100));
		row1.setPadding(new Insets(0, 0, 0, 50));
		row2.setPadding(new Insets(0, 0, 0, 50));
		triesLeft.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 14; -fx-padding: 5; -fx-text-fill: #A000000; -fx-font-weight: bold;");
		triesLeft.setFill(Color.WHITE); // Inner color
		triesLeft.setStroke(Color.BLACK); // Outline
		triesLeft.setStrokeWidth(1); // Thickness of outline
		triesLeft.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
		
		
		// Create and load images
		Image leg2 = new Image(new FileInputStream("src/images/leg2.png"));
		Image leg1 = new Image(new FileInputStream("src/images/leg1.png"));
		Image arm2 = new Image(new FileInputStream("src/images/arm2.png"));
		Image arm1 = new Image(new FileInputStream("src/images/arm1.png"));
		Image body = new Image(new FileInputStream("src/images/body.png"));
		Image head = new Image(new FileInputStream("src/images/head.png"));
		Image nothing = new Image(new FileInputStream("src/images/nothing.png"));
		Image[] hangmanImages = { nothing, head, body, arm1, arm2, leg1, leg2 };

		ImageView hangmanView = new ImageView(nothing);
		hangmanView.setFitWidth(280);
		hangmanView.setPreserveRatio(true);
		hangmanView.setX(575);
		hangmanView.setY(0);

		//current row indicator 
		int row = 1;
		
		// Create an array of Text objects to represent each character in the sentence (underscores or spaces)
		Text[] unrevealedWord = new Text[randomSentence.length()];
		
		// Loop through each character in the sentence
		for (int i = 0; i < unrevealedWord.length; i++) {
			char letter = randomSentence.charAt(i);//current character from the sentence

			// Switch to second row if we pass 15 characters and encounter a space
			if (row != 2 && i > 15 && letter == ' ') {
				row = 2;
			}
			
			// If the character is a space, display a space; otherwise, display an underscore placeholder
			if (letter == ' ') {
				unrevealedWord[i] = new Text(" ");
			} else {
				unrevealedWord[i] = new Text("_");
			}
			// Set styling
			unrevealedWord[i].setFill(Color.WHITE); // Inner color
			unrevealedWord[i].setStroke(Color.BLACK); // Outline
			unrevealedWord[i].setStrokeWidth(1); // Thickness of outline
			unrevealedWord[i].setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
			
			// Add the Text element to the appropriate container based on the row
			if (row == 2) {
				wordBox2.getChildren().add(unrevealedWord[i]);
			} else {
				wordBox1.getChildren().add(unrevealedWord[i]);
			}
		}

		// Create buttons and add to layout
		for (int i = 0; i < letterBtns.length; i++) {
			Button letter = new Button(alphabet[i]);
			letterBtns[i] = letter;
			letter.setFocusTraversable(false);
			letter.setStyle("-fx-font-size: 18;");

			if (i < 13) {
				row1.getChildren().add(letter);
			} else {
				row2.getChildren().add(letter);
			}
		}

		//loops through letter buttons
		for (int i = 0; i < letterBtns.length; i++) {
			final int index = i;
			
			//click on a letter button
			letterBtns[i].setOnAction(e -> {
				
				// Check if the letter hasn't been guessed yet and player still has guesses left
				if (!letters[index].isGuessed() && guesses != 1) {
					letters[index].guess(); //marks as guessed
					letterBtns[index].setDisable(true); //disables button

					 // Check if the chosen letter is in the sentence
					if (randomSentence.contains(letterBtns[index].getText())) {
						letterBtns[index].setStyle("-fx-background-color: #90EE90");//sets button colour as green for correct

						 // Reveal all occurrences of the guessed letter in the displayed word
						for (int j = 0; j < randomSentence.length(); j++) {
							String letter = randomSentence.substring(j, j + 1);
							if (letter.equals(letterBtns[index].getText())) {
								unrevealedWord[j].setText(letter); // Replace underscore with the correct letter
							}
						}
					} 
					 // If the letter is not in the sentence
					else {
						letterBtns[index].setStyle("-fx-background-color: #FF0000"); //set red for correct
						guesses -= 1; //lose guesses
						hangmanView.setImage(hangmanImages[6 - guesses]);//update hangman image
						triesLeft.setText("You have " + guesses + " tries left."); // update tries left 
					}

					String currentGuess = "";
					//finds current guess
					for (Text l : unrevealedWord) {
						currentGuess += l.getText();
					}

					// If the player has revealed the entire sentence correctly, end game with win
					if (currentGuess.equals(randomSentence)) {
						try {
							endGame(randomSentence, true, stage);
						} catch (FileNotFoundException e1) {
							System.out.println("There must be a file issue");
							System.out.println(e1.getMessage());
						}
					}
				} 
				// If the letter was already guessed or no guesses left, end the game with a loss
				else {
					try {
						endGame(randomSentence, false, stage);
					} catch (FileNotFoundException e1) {
						System.out.println("There must be a file issue");
						System.out.println(e1.getMessage());
					}
				}
			});
		}

		//create root
		Pane layout = new Pane();
		
		//set up background
		BackgroundImage background = new BackgroundImage(
				new Image(new FileInputStream("src/images/second.png")),
			    BackgroundRepeat.REPEAT,
			    BackgroundRepeat.NO_REPEAT,
			    BackgroundPosition.DEFAULT,
			    new BackgroundSize(100, 100, true, true, true, false)
			);
		layout.setBackground(new Background(background));
		
		//align the letters and guess statement to center
		letterCenter.setAlignment(Pos.CENTER); 
		letterCenter.getChildren().addAll( row1, row2, triesLeft);
		
		//combines unrevealed word and letterCenter
		gameContent.getChildren().addAll(wordBox1, wordBox2, letterCenter);
		
		gameContent.setLayoutY(100);
		
		//add everything to layout
		layout.getChildren().addAll(gameContent,hangmanView);
		Scene scene1 = new Scene (layout, 800, 400);
		stage.setScene(scene1);
		gameContent.requestFocus();
	
		stage.show();
		
	}
	/**
	 * Starts the Evil Hangman game, a variation of Hangman where no single word 
	 * is selected at the start. Instead, the game actively adapts to the player's 
	 * guesses to make the game as difficult as possible.
	 * 
	 * When a player guesses a letter, the game chooses the pattern (letter positions) 
	 * that retains the largest possible set of valid 5 letter words. This 
	 * approach avoids committing to a word until necessary, increasing the challenge.
	 * @param stage where the main game will be displayed
	 * @throws FileNotFoundException if any image or file is not found 
	 */
	public void evilGame(Stage stage) throws FileNotFoundException {
		ArrayList<Word> words = new ArrayList<>();
		
		// Read file and load sentences
		try {
			File f = new File("words2.txt");
			Scanner in = new Scanner(f);
			while (in.hasNextLine()) {
				Word word = new Word(in.nextLine());
				words.add(word);
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("There must be a file issue");
			System.out.println(e.getMessage());
		}
		

		// Declare variables
				Button[] letterBtns = new Button[26];
				String[] alphabet = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
				                      "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
				Letter[] letters = new Letter[26];
				Text triesLeft = new Text("You have " + guesses + " tries left.");
				VBox gameContent = new VBox(10);
				VBox letterCenter = new VBox(10);
				HBox wordBox1 = new HBox(8);
				HBox row1 = new HBox(4);
				HBox row2 = new HBox(4);
				// Create Letter objects
				for (int i = 0; i < letters.length; i++) {
					Letter letter = new Letter(alphabet[i].charAt(0));
					letters[i] = letter;
				}
				// Styles and alignments
				gameContent.setAlignment(Pos.CENTER_LEFT);
				letterCenter.setAlignment(Pos.CENTER);
				wordBox1.setAlignment(Pos.CENTER_LEFT);
				row1.setAlignment(Pos.CENTER_LEFT);
				row2.setAlignment(Pos.CENTER_LEFT);
				wordBox1.setPadding(new Insets(0, 0, 0, 250));
				row1.setPadding(new Insets(0, 0, 0, 50));
				row2.setPadding(new Insets(0, 0, 0, 50));
				triesLeft.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 14; -fx-padding: 5; -fx-text-fill: #A000000; -fx-font-weight: bold;");
				triesLeft.setFill(Color.WHITE); // Inner color
				triesLeft.setStroke(Color.BLACK); // Outline
				triesLeft.setStrokeWidth(1); // Thickness of outline
				triesLeft.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
				
				
				// Create and load images
				Image leg2 = new Image(new FileInputStream("src/images/leg2.png"));
				Image leg1 = new Image(new FileInputStream("src/images/leg1.png"));
				Image arm2 = new Image(new FileInputStream("src/images/arm2.png"));
				Image arm1 = new Image(new FileInputStream("src/images/arm1.png"));
				Image body = new Image(new FileInputStream("src/images/body.png"));
				Image head = new Image(new FileInputStream("src/images/head.png"));
				Image nothing = new Image(new FileInputStream("src/images/nothing.png"));
				Image hat = new Image(new FileInputStream("src/images/hat.png"));
				Image[] hangmanImages = { nothing, head, body, arm1, arm2, leg1, leg2, hat,hat };
				ImageView hangmanView = new ImageView(nothing);
				hangmanView.setFitWidth(280);
				hangmanView.setPreserveRatio(true);
				hangmanView.setX(575);
				hangmanView.setY(0);
		
				Text[] unrevealedWord = new Text[5];
		for (int i = 0; i < 5; i++) {
			unrevealedWord[i] = new Text("_");
			unrevealedWord[i].setFill(Color.WHITE); // Inner color
			unrevealedWord[i].setStroke(Color.BLACK); // Outline
			unrevealedWord[i].setStrokeWidth(1); // Thickness of outline
			unrevealedWord[i].setStyle("-fx-font-size: 20; -fx-font-weight:bold;"); 
		    wordBox1.getChildren().add(unrevealedWord[i]);
		}
		
		// Create buttons and add to layout
				for (int i = 0; i < letterBtns.length; i++) {
					Button letter = new Button(alphabet[i]);
					letterBtns[i] = letter;
					letter.setFocusTraversable(false);
					letter.setStyle("-fx-font-size: 18;");
					if (i < 13) {
						row1.getChildren().add(letter);
					} else {
						row2.getChildren().add(letter);
					}
				}

		//loops through letter buttons
		for (int i= 0; i < letterBtns.length; i++) {
			final int index = i;
			
			//click on a letter button
			letterBtns[i].setOnAction(e-> {
				//checks if its already guessed
				if (!letters[index].isGuessed()) {
					letters[index].guess(); //marks as guessed
					letterBtns[index].setDisable(true); //disables button
					ArrayList<Word> temporaryWords = new ArrayList<>(); //makes temporary array list
					temporaryWords.addAll(words); //sets equal to all possible words
					HashMap<String,ArrayList<Word>> possibleLocations = new HashMap<>(); //hasmap to organize words
					String keySave = ""; //one possible key
					
					//organizes words into groups based on positon of selected letter
					for (int j=0; j<temporaryWords.size(); j++) {
						String key = temporaryWords.get(j).letterLocations(letterBtns[index].getText()); //gets key
						keySave = key; //saves a possible key
						
						//if key already exists adds to that keys array
						if (possibleLocations.containsKey(key)) {
							possibleLocations.get(key).add(temporaryWords.get(j)); 
						}
						//creates new key and array list
						else {
							possibleLocations.put(key, new ArrayList<>());
							possibleLocations.get(key).add(temporaryWords.get(j)); 
						}
					}
					String largestGroupKey = keySave; //saves one of the keys as the preset largest group
					
					//finds largest group of words
					for (String key: possibleLocations.keySet()) {
						int currentSize =possibleLocations.get(key).size();
						if (currentSize>possibleLocations.get(largestGroupKey).size()){
							largestGroupKey = key;
						}
					}
					
					//replaces current word set with new word set
					words.removeAll(words);
					words.addAll(possibleLocations.get(largestGroupKey));
					
					// Get the word from the largest group
					Word wordExample = possibleLocations.get(largestGroupKey).get(0);
					String guessedLetter = letterBtns[index].getText();

					if (possibleLocations.get(largestGroupKey).size() == 1 && !wordExample.containsLetter(guessedLetter)) {
						// Wrong guess even though the key format says "largest group"
						letterBtns[index].setStyle("-fx-background-color: #FF0000");//sets colour red for incorrect
						evilGuesses -= 1; //lose guesses
						hangmanView.setImage(hangmanImages[8 - evilGuesses]); // update hangman image 
						triesLeft.setText("You have " + evilGuesses + " tries left."); // update tries left 
					} else if (largestGroupKey.equals(guessedLetter + "-1")) {
						// The typical wrong-guess case when many words still remain
						letterBtns[index].setStyle("-fx-background-color: #FF0000");//sets colour red for incorrect
						evilGuesses -= 1;
						
						hangmanView.setImage(hangmanImages[8 - evilGuesses]);// update hangman image 
						triesLeft.setText("You have " + evilGuesses + " tries left."); // update tries left 
					} else {
						// Correct guess, show letters
						int[] indices = wordExample.getLetterIndices(guessedLetter);
						letterBtns[index].setStyle("-fx-background-color: #90EE90");//sets colour green for correct
						for (int j = 0; j < indices.length; j++) {
							int letterIndex = indices[j];
							unrevealedWord[letterIndex].setText(guessedLetter);
						}
					}

					String currentGuess = "";
					//finds current guess
					for (Text l : unrevealedWord) {
						currentGuess += l.getText();
					}
					// if the largest group only has one word and the current guess is equal to that word, end game on win
					if (possibleLocations.get(largestGroupKey).size()==1 && currentGuess.equals(possibleLocations.get(largestGroupKey).get(0))) {
						try {
							endGame(currentGuess, true, stage);
						} catch (FileNotFoundException e1) {
							System.out.println("There must be a file issue");
							System.out.println(e1.getMessage());
						}
					}
					//if the user has run out of guesses, end gamne on loss
					else if (evilGuesses==0){
						try {
							endGame("never created",false, stage);
						} catch (FileNotFoundException e1) {
							System.out.println("There must be a file issue");
							System.out.println(e1.getMessage());
						}
						
					}	
				}
					
			});//end of button click
			}
		
		//create root
		Pane layout = new Pane();
		
		//set up background
		BackgroundImage background = new BackgroundImage(
				new Image(new FileInputStream("src/images/second.png")),
			    BackgroundRepeat.REPEAT,
			    BackgroundRepeat.NO_REPEAT,
			    BackgroundPosition.DEFAULT,
			    new BackgroundSize(100, 100, true, true, true, false)
			);
		layout.setBackground(new Background(background));
		
		//align the letters and guess statement to center
		letterCenter.setAlignment(Pos.CENTER); 
		letterCenter.getChildren().addAll( row1, row2, triesLeft);
		
		//combines unrevealed word and letterCenter
		gameContent.getChildren().addAll(wordBox1, letterCenter);
		
		gameContent.setLayoutY(100);
		
		//add everything to layout
		layout.getChildren().addAll(gameContent,hangmanView);
		Scene scene1 = new Scene (layout, 800, 400);
		stage.setScene(scene1);
		gameContent.requestFocus();
	
		stage.show();
			
	}
	
	/** 
	 * Ends the game for both Evil Hangman and regular Hangman modes.
	 * Displays the game over screen, reveals the chosen/unchosen sentence, and shows
	 * whether the player has won or lost.
	 * @param randomSentence sentence that was chosen, gets revealed at end
	 * @param win indicates whether or not the player won 
	 * @param stage where the game over stage is displayed
	 * @throws FileNotFoundException if the image file is not found 
	 */
	public void endGame(String randomSentence, boolean win, Stage stage) throws FileNotFoundException {

		// Create layout containers
		VBox overContent = new VBox(10);
		Pane over = new Pane();

		// Create UI elements
		Label revealedSentence = new Label("The sentence was: " + randomSentence);
		ImageView endingView = new ImageView();

		// Set status and image depending on win or loss
		if (win) {
			BackgroundImage background = new BackgroundImage(
		    	    new Image(new FileInputStream("src/images/win.png")),
		    	    BackgroundRepeat.NO_REPEAT,
		    	    BackgroundRepeat.NO_REPEAT,
		    	    BackgroundPosition.DEFAULT,
		    	    new BackgroundSize(100, 100, true, true, true, true)
		    	);

				over.setBackground(new Background(background));
				revealedSentence.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20; -fx-padding: 5; -fx-text-fill: #630c09; -fx-font-weight: bold;");
				
		} else {
		    BackgroundImage background = new BackgroundImage(
		    	    new Image(new FileInputStream("src/images/loss.png")),
		    	    BackgroundRepeat.NO_REPEAT,
		    	    BackgroundRepeat.NO_REPEAT,
		    	    BackgroundPosition.DEFAULT,
		    	    new BackgroundSize(100, 100, true, true, true, true)
		    	);

				over.setBackground(new Background(background));
				
		    revealedSentence.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20; -fx-padding: 5; -fx-text-fill: #630c09; -fx-font-weight: bold;");
		}

		// Configure ImageView properties
		endingView.setFitWidth(280);
		endingView.setPreserveRatio(true);
		endingView.setX(550);

		// Add labels to VBox and position it
		overContent.getChildren().addAll(revealedSentence);
		overContent.setAlignment(Pos.TOP_LEFT);
		overContent.setLayoutX(2);
		overContent.setLayoutY(25);

		// Add components to Pane
		over.getChildren().addAll(overContent, endingView);

		// Create and set the scene
		Scene gameOver = new Scene(over, 800, 400);
		stage.setScene(gameOver);
		stage.show();
	}

	
	public static void main(String[] args) {
		launch(args);
	}

}
