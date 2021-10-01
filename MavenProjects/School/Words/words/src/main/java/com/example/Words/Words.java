//(c) A+ Computer Science
//www.apluscompsci.com
//Name -
package com.example.Words;

import java.util.*;

public class Words
{
	private Map<String, Integer> m;
	
	public Words()
	{
		m = new HashMap<>();
		m.put("one", 1);
		m.put("two", 2);
		m.put("three", 3);
		m.put("four", 4);
		m.put("five", 5);
		m.put("six", 6);
		m.put("seven", 7);
		m.put("eight", 8);
		m.put("nine", 9);
		m.put("ten", 10);
		//create a new map
		//load in values						
	}
	
	public int getNumber( String s )
	{
		return m.get(s);   //change this
	}

}