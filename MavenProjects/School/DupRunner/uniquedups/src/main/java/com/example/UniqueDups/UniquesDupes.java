//(c) A+ Computer Science
//www.apluscompsci.com

//Name -
package com.example.UniqueDups;

import java.util.Set;
import java.util.TreeSet;
import java.util.Arrays;
import java.util.ArrayList;
import static java.lang.System.*;

public class UniquesDupes
{
	public static Set<String> getUniques(String input)
	{
		String[] words = input.split(" ");
		Set<String> uniques = new TreeSet<String>(Arrays.asList(words));
		return uniques;
	}

	public static Set<String> getDupes(String input)
	{
		String[] words = input.split(" ");
		Set<String> temp2 = new TreeSet<String>();
		Set<String> dupes = new TreeSet<String>();
		for(String s : words){
			if(!temp2.add(s)){
				dupes.add(s);
			}
		}
		
		return dupes;
	}
}