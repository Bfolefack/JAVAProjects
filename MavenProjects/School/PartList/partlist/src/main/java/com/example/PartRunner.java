package com.example;
//(c) A+ Computer Science
//www.apluscompsci.com

//Name -

import static java.lang.System.*;
import com.example.Part.*;

//Files needed
 	//Part.java
 	//PartList.java

public class PartRunner
{
	public static void main(String[] args)
	{
		PartList prog = new PartList("partlist/src/main/java/com/example/Part/partinfo.dat");
		out.println(prog);
	}
}
