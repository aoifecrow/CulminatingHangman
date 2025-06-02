package luellaoife;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
public class textDriver {

	public static void main(String[] args) {
		//read 
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
		String randomSentence = sentences.get(rand.nextInt(sentences.size()));
		System.out.println(randomSentence);
	}

}
