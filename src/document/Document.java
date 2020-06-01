package document;

/** 
 * A class that represents a text document
 * @author UC San Diego Intermediate Programming MOOC team
 */
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Document {

	private String text;
	
	/** Create a new document from the given text.
	 * Because this class is abstract, this is used only from subclasses.
	 * @param text The text of the document.
	 */
	protected Document(String text)
	{
		this.text = text;
	}
	
	/** Returns the tokens that match the regex pattern from the document 
	 * text string.
	 * @param pattern A regular expression string specifying the 
	 *   token pattern desired
	 * @return A List of tokens from the document text that match the regex 
	 *   pattern
	 */
	protected List<String> getTokens(String pattern)
	{
		ArrayList<String> tokens = new ArrayList<String>();
		
		Pattern tokSplitter = Pattern.compile(pattern);
		
		Matcher m = tokSplitter.matcher(text);
		
		while (m.find()) {
			tokens.add(m.group());
		}
		
		return tokens;
	}
	
	/** This is a helper function that returns the number of syllables
	 * in a word.  You should write this and use it in your 
	 * BasicDocument class.
	 * 
	 * You will probably NOT need to add a countWords or a countSentences 
	 * method here.  The reason we put countSyllables here because we'll 
	 * use it again next week when we implement the EfficientDocument class.
	 * 
	 * For reasons of efficiency you should not create Matcher or Pattern 
	 * objects inside this method. Just use a loop to loop through the 
	 * characters in the string and write your own logic for counting 
	 * syllables.
	 * 
	 * @param word  The word to count the syllables in
	 * @return The number of syllables in the given word, according to 
	 * this rule: Each contiguous sequence of one or more vowels is a syllable, 
	 *       with the following exception: a lone "e" at the end of a word 
	 *       is not considered a syllable unless the word has no other syllables. 
	 *       You should consider y a vowel.
	 */
	protected int countSyllables(String word)
	{
		// TODO: Implement this method so that you can call it from the 
	    // getNumSyllables method in BasicDocument (module 2) and 
	    // EfficientDocument (module 3).
		char[] origArray = makeSyllableIndexArray(word);
		//avoid changing the original array 
		char[] modArray = new char[origArray.length];
		for(int i = 0; i < origArray.length; i++)
			modArray[i] = origArray[i];
		int i = 0, syllableCount = 0;
		//if two or more vowels are next to each other, keep the preceding one
		while(i < origArray.length - 1) { 
			  if(origArray[i] != 0 && origArray[i + 1] != 0) {
				  modArray[i + 1] = 0; 
			  } 
			  i++; 
		}
		 
		//print arrays
        System.out.println("\nModified SyllableIndexArray[] elements:"); 
        for (int j = 0; j <modArray.length; j++) 
        	 System.out.printf("\nj = %d, modArray[%d] = %c", j, j, modArray[j]); 
        
        //count number of syllables
		for(int j = 0; j < modArray.length; j++) {
			if(modArray[j] != 0)
				syllableCount++;
		}
		
		//a lone "e" at the end of a word is not considered a syllable 
		//unless the word has no other syllables.
		if(syllableCount == 1 && modArray[modArray.length - 1] == 'e')
			syllableCount = 0;
		
		System.out.printf("\nSyllable count = %d\n", syllableCount);
	    return syllableCount;
	}
	
	protected char[] makeSyllableIndexArray(String word) {
		char[] cWord = word.toCharArray();
		//declare the array
		char[] vowelArray = new char[cWord.length];
		int i = 0, startPos = 0;
		String syllable = "aeiouyAEIOUY";
		//For each character in the word
		for (char c : cWord) {
			//Is it a vowel?
			int cPos = syllable.indexOf(c);
			System.out.printf("index position %d in the vowel array\n", cPos);
			//If found, initialize the array
			if(cPos != -1) {
				//a lone "e" at the end of a word is not considered a syllable 
				//unless the word has no other syllables.
				/*
				 * if(c == 'e' && i == 0) {
				 * System.out.printf("\nlone e at the end of the word, index = %d", cPos);
				 * vowelArray[i] = 0; break; }
				 */
				System.out.printf("index position %d in the string\n", word.indexOf(c, startPos));
				vowelArray[word.indexOf(c, startPos)] = c;
				System.out.print(vowelArray[word.indexOf(c, startPos)] + " ");
				i++;
				startPos = word.indexOf(c, startPos) + 1;
			}
		}
		
		//print arrays
        System.out.println("\nSyllableIndexArray[] elements:"); 
        System.out.printf("\nSyllableIndexArray[] elements: = %d", vowelArray.length);
        
        for (int j = 0; j < vowelArray.length; j++) {
            System.out.printf("\nj = %d, vowelArray[%d] = %c", j, j, vowelArray[j]); 
        }
		return vowelArray;
	}
	
	/** A method for testing
	 * 
	 * @param doc The Document object to test
	 * @param syllables The expected number of syllables
	 * @param words The expected number of words
	 * @param sentences The expected number of sentences
	 * @return true if the test case passed.  False otherwise.
	 */
	public static boolean testCase(Document doc, int syllables, int words, int sentences)
	{
		System.out.println("Testing text: ");
		System.out.print(doc.getText() + "\n....");
		boolean passed = true;
		int syllFound = doc.getNumSyllables();
		int wordsFound = doc.getNumWords();
		int sentFound = doc.getNumSentences();
		//
		//doc.countSyllables("are");
		
		if (syllFound != syllables) {
			System.out.println("\nIncorrect number of syllables.  Found " + syllFound 
					+ ", expected " + syllables);
			passed = false;
		}
		if (wordsFound != words) {
			System.out.println("\nIncorrect number of words.  Found " + wordsFound 
					+ ", expected " + words);
			passed = false;
		}
		if (sentFound != sentences) {
			System.out.println("\nIncorrect number of sentences.  Found " + sentFound 
					+ ", expected " + sentences);
			passed = false;
		}
		
		if (passed) {
			System.out.println("passed.\n");
		}
		else {
			System.out.println("FAILED.\n");
		}
		return passed;
	}
	
	
	/** Return the number of words in this document */
	public abstract int getNumWords();
	
	/** Return the number of sentences in this document */
	public abstract int getNumSentences();
	
	/** Return the number of syllables in this document */
	public abstract int getNumSyllables();
	
	/** Return the entire text of this document */
	public String getText()
	{
		return this.text;
	}
	
	/** return the Flesch readability score of this document */
	public double getFleschScore()
	{
	    // TODO: You will play with this method in week 1, and 
		// then implement it in week 2
	    return 0.0;
	}
	
	
	
}
