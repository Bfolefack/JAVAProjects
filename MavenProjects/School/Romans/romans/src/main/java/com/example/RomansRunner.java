package com.example;

import com.example.Romans.Romans;

//(c) A+ Computer Science
//www.apluscompsci.com
//Name -

public class RomansRunner
{
	public static void main( String args[] )
	{
		Romans w = new Romans();
		System.out.println( w.getNumber( "V" ) );
		System.out.println( w.getNumber( "D" ) );
		System.out.println( w.getNumber( "M" ) );
		System.out.println( w.getTotal( "V I I" ) );
		System.out.println( w.getTotal( "X X" ) );
		System.out.println( w.getTotal( "C D M" ) );		
		System.out.println( w.getTotal( "L" ) );	
	}
}