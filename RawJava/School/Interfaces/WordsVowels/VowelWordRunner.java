//(c) A+ Computer Science
//www.apluscompsci.com
//Name -

import java.io.File; 
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Arrays;
import static java.lang.System.*;

public class VowelWordRunner
{
	public static void main( String args[] ) throws IOException
	{
		Scanner file = new Scanner(new File("wordsLab.dat"));
		TreeSet<VowelWord> words = new TreeSet<VowelWord>();
		while(file.hasNext())
		{
			VowelWord word = new VowelWord(file.next());
			words.add(word);
		}
		file.close();
		System.out.println(words);
	}
}