//(c) A+ Computer Science
//www.apluscompsci.com

//Name -
package com.example;

import com.example.relatives.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import static java.lang.System.*;

public class RelativesRunner
{
	public static void main( String args[] ) throws IOException
	{
		Relatives rs = new Relatives("relatives/src/main/java/com/example/relatives/relatives.dat");
		System.out.println(rs);
	}
}