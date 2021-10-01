//(c) A+ Computer Science
//www.apluscompsci.com

//Name -
package com.example.Acronym;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Scanner;
import static java.lang.System.*;

public class Acronyms
{
	private Map<String,String> acronymTable;

	public Acronyms(String filename)
	{
		File f;
		acronymTable = new TreeMap<>();
		String story = "";
		try{
			f = new File(filename);
			Scanner scan = new Scanner(f);
			int max = scan.nextInt();
			scan.nextLine();
			for(int i = 0; i < max; i++){
				String s = scan.nextLine();
				int pivot = s.indexOf("-");
				System.out.println(acronymTable.put(s.substring(0, pivot - 1), s.substring(pivot + 2, s.length())));
			}
			while(scan.hasNextLine()){
				story += scan.nextLine() + " ";
			}
		} catch (Exception e){
			System.out.println(e);
			exit(0);
		}
		System.out.println(convert(story));
		System.out.println(acronymTable);
	}

	public void putEntry(String entry)
	{




	}

	public String convert(String story)
	{
		for(String s : acronymTable.keySet()){
			s = " " + s;
			int index = story.indexOf(s);
			while(index > -1){
				story = story.substring(0, index + 1) + acronymTable.get(s.substring(1)) + story.substring(s.length() + index, story.length());
				index = story.indexOf(s);
			}
		}
		return story;
	}

	public String toString()
	{
		return "";
	}
}