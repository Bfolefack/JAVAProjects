//(c) A+ Computer Science
//www.apluscompsci.com
//Name -
package com.example.Romans;
import java.util.*;

public class Romans
{
	private Map<String, Integer> m;
	
	public Romans()
	{
		m = new TreeMap<String, Integer>();
		
		/*
			Symbol	I	V	X		L		C		D		M
			Value		1	5	10		50		100	500	1000
		*/
		
		m.put( "I", 1 );
		m.put( "V", 5 );
		m.put( "X", 10 );
		m.put( "L", 50 );
		m.put( "C", 100 );
		m.put( "D", 500 );
		m.put( "M", 1000 );					
	}
	
	public int getNumber( String s )
	{
		return m.get( s );
	}

	public int getTotal(String in){
		int total = 0;
		char[] str = in.toCharArray();
		for(char c : str){
			if(m.get(c + "") != null){
				total += m.get(c + "");
			}
		}
		return total;
	}
}