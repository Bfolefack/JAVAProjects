package com.example;
//(c) A+ Computer Science
//www.apluscompsci.com

//Name -

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import com.example.EngtoSpan.*;
import static java.lang.System.*;

public class SpanRunner
{
	public static void main( String args[] ) throws IOException
	{
		SpanishToEnglish se = new SpanishToEnglish("engtospan/src/main/java/com/example/EngtoSpan/spantoeng.dat");
		se.translate();
	}
}