//(c) A+ Computer Science
//www.apluscompsci.com
package com.example;
import com.example.Histogram.*;
public class HistogramRunner
{
	public static void main( String args[] )
	{
		Histogram h1 = new Histogram("a b c d e f g h i a c d e g h i h k");
		Histogram h2 = new Histogram("1 2 3 4 5 6 1 2 3 4 5 1 3 1 2 3 4");
		Histogram h3 = new Histogram("Y U I O Q W E R T Y");
		Histogram h4 = new Histogram("4 T # @ ^ # # #");
		System.out.println(h1 + "\n" + h2 + "\n" + h3 + "\n" + h4 + "\n");
	}
}