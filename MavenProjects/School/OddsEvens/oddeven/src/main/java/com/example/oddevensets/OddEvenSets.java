//(c) A+ Computer Science
//www.apluscompsci.com

//Name -
package com.example.oddevensets;

import java.util.*;
import static java.lang.System.*;

public class OddEvenSets
{
	private Set<Integer> odds;
	private Set<Integer> evens;

	public OddEvenSets()
	{
	}

	public OddEvenSets(String line)
	{
		odds = new TreeSet<>();
		evens = new TreeSet<>();
		Scanner scan = new Scanner(line);
		Set<Integer> temp = new TreeSet<Integer>();
		while(scan.hasNextInt()){
			int i = scan.nextInt();
			if(i % 2 == 0){
				evens.add(i);
			} else {
				odds.add(i);
			}
		}
		System.out.println(toString());
		
	}

	public String toString()
	{
		return "ODDS : " + odds + "\nEVENS : " + evens + "\n\n";
	}
}