package luellaoife;

/**
 * Class to represent a single letter
 * @author L.Mailloux
 * @version 1.0
 */

public class Letter {
	private char letter; 
	private boolean guessed;
	
/**
 * letter is made lowercase and not guessed by default
 * @param value the character chosen
 */
	
	public Letter (char value) {
		this.letter = Character.toLowerCase(value);
		guessed = false;
	}	
	
/**
 * 	returns the letter chosen
 * @return the letter
 */
	public char getLetter() {
		return letter;
	}
	
/**
 * returns letter if it has been guessed	
 * @return true if guessed, elsewise false 
 */
	
	public boolean isGuessed() {
		return guessed; 
	}
	
/**
 * letter has been guessed	
 */
	
	public void guess() {
		guessed = true;
	}
	
	/**
	 * Create a string representation of the object Letter
	 */
	@Override
	public String toString() {
		return ("The letter is: "+letter);
	}

}
