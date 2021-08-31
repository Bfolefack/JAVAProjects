//(c) A+ Computer Science
//www.apluscompsci.com
//Name - 

import static java.lang.System.*;

public class StringSearch
{
	/*
	 *method countWords will count the occurrences of word in sent
	 *there may many occurrences of word or none at all
	 */
	public static int countWords(String sent, String word)
	{
		return count(sent, word);
	}

	/*
	 *method countLetters will count the occurrences of letter in sent
	 *there may many occurrences of letter or none at all
	 */
	public static int countLetters(String sent, String letter)
	{
		return count(sent, letter);
	}

	public static int count(String sent, String word){
		int count = 0;
		for(int i = 0; i <= sent.length() - word.length(); i++){
			//System.out.println(sent.substring(i, i + word.length()));
			if(sent.substring(i, i + word.length()).equals(word)){
				count++;
			}
		}
		return count;
	}
}