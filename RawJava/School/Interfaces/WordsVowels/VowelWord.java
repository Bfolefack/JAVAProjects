//(c) A+ Computer Science
//www.apluscompsci.com
//Name -

import static java.lang.System.*;

public class VowelWord implements Comparable<VowelWord>
{
	private String word;

	public VowelWord( String s )
	{
		word = s;
	}

	public int compareTo( VowelWord rhs )
	{		
		int test = (numVowels()).compareTo(rhs.numVowels());
		return test != 0 ? test : (word.compareTo(rhs.word) != 0 ? word.compareTo(rhs.word) : 1);
	}

	public Integer numVowels(){
		int count = 0;
		for(int i = 0; i < word.length(); i++){
			if(word.charAt(i) == 'a' || word.charAt(i) == 'e' || word.charAt(i) == 'i' || word.charAt(i) == 'o' || word.charAt(i) == 'u'){
				count++;
			}
		}
		return count;
	}

	public String toString()
	{
		return word;
	}
}