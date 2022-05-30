//(c) A+ Computer Science
//www.apluscompsci.com
//Name -

import static java.lang.System.*;

public class Word implements Comparable<Word>
{
	private String word;

	public Word( String s )
	{
		word = s;
	}

	public int compareTo( Word rhs )
	{		
		int test = ((Integer)(word.length())).compareTo(rhs.word.length());
		return test != 0 ? test : (word.compareTo(rhs.word) != 0 ? word.compareTo(rhs.word) : 1);
	}

	public String toString()
	{
		return word;
	}
}