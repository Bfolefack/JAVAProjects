package com.example.Part;
//(c) A+ Computer Science
//www.apluscompsci.com

//Name -

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.Scanner;
import static java.lang.System.*;

public class PartList
{
	private TreeMap<Part, Integer> partsMap;
	
	public PartList()
	{

	}
	
	public PartList(String fileName)
	{
		this();
		try
		{
			Scanner file = new Scanner(new File(fileName));
			partsMap = new TreeMap<>();
			while(file.hasNextLine()){
				Part p = new Part(file.nextLine());
				if(partsMap.get(p) == null){
					partsMap.put(p, 1); 
				} else {
					partsMap.put(p, partsMap.get(p) + 1);
				}
			}
		}
		catch( IOException e )  //Most specific exceptions must be listed 1st
		{
			out.println(e);
			e.printStackTrace();
		}
		catch( RuntimeException e )
		{
			out.println(e);
			e.printStackTrace();
		}
		catch( Exception e )
		{
			out.println(e);
			e.printStackTrace();
		}
	}
	
	public String toString()
	{
		return partsMap.toString();
	}
}