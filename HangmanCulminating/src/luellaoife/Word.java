package luellaoife;

public class Word {
	private String word;
	
	public Word(String w) {
		word = w;
	}
	public boolean containsLetter(String letter) {
		return word.contains(letter);
	}
	public String letterLocations(String letter) {
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
	public int[] getLetterIndices(String letter) {
		String numbers = letterLocations(letter).substring(1);
		int[] index = new int[numbers.length()];
		for (int i=0; i<index.length; i++ ) {
			index[i] = Integer.valueOf(String.valueOf(numbers.charAt(i)));
		}
		return index;
	}
	public String getWord() {
		return word;
	}
}
