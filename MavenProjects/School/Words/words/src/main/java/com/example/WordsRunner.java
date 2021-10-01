//(c) A+ Computer Science
//www.apluscompsci.com
//Name -
package com.example;

import com.example.Words.*;

public class WordsRunner
{
	public static void main( String args[] )
	{
		Words w = new Words();
		System.out.println( w.getNumber( "one" ) );
		System.out.println( w.getNumber( "five" ) );
		System.out.println( w.getNumber( "seven" ) );
	}
}