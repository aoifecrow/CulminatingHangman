package luellaoife;

/**
 * Class to represent a single word
 * @version 1.0
 */
public class Word {
	private String word;
	
	/**
	 * Constructor for Word
	 */
	public Word(String w) {
		word = w;
	}
	
	/**
	 * Checks in word contains letter
	 * @param letter a letter in the english alphabet
	 * @return if letter is in the word
	 */
	public boolean containsLetter(String letter) {
		return word.contains(letter);
	}
	
	/**
	 * Returns letter locations as a key
	 * @param letter a letter in the english alphabet
	 * @return a string that is a code for the letter and its locations in the word
	 */
	public String getLetterKey(String letter) {
		String locations = letter;
		if (containsLetter(letter)) {
			for (int i=0;i< word.length(); i++) {
				if (word.substring(i,i+1).equals(letter)) {
					locations+=i;
				}
			}
			return locations;
		}
		return locations +"-1";
	}
	/**
	 * Finds and returns the indices of letter in word
	 * @param letter a letter in the english alphabet
	 * @return an array of the locations of letter in the word
	 */
	public int[] getLetterIndices(String letter) {
		String numbers = getLetterKey(letter).substring(1);
		int[] index = new int[numbers.length()];
		for (int i=0; i<index.length; i++ ) {
			index[i] = Integer.valueOf(String.valueOf(numbers.charAt(i)));
		}
		return index;
	}
	
	/**
	 * Shows word
	 * @return word 
	 */
	public String getWord() {
		return word;
	}
	/**
	 * Create a string representation of the object Word
	 */
	@Override
	public String toString() {
		return ("The word is: "+word);
	}
}
