//(c) A+ Computer Science
//www.apluscompsci.com

//Name -
package com.example.EngtoSpan;

import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Scanner;
import java.util.Set;

import static java.lang.System.*;

import java.io.File;

public class SpanishToEnglish
{
	private Map<String,String> pairs;
	Set<String> story;

	public SpanishToEnglish(String file)
	{
		pairs = new TreeMap<String, String>(new StrLengthComparator());
		story = new TreeSet<String>();
			try {
			File f = new File(file);
			Scanner scan = new Scanner(f);
			int count = scan.nextInt();
			scan.nextLine();
			for(int i = 0; i < count; i++){
				String s = scan.nextLine();
				putEntry(s);
			}
			while(scan.hasNextLine()){
				story.add(scan.nextLine());
			}
			scan.close();
		} catch (Exception e) {
			//TODO: handle exception
		}
	}

	public void putEntry(String s)
	{
		int pivot = s.indexOf(" ");
		pairs.put(s.substring(0, pivot), s.substring(pivot + 1, s.length()));
	}

	public void translate()
	{
		for(String bigStory : story){
			String[] strs = bigStory.split(" ");
			String output ="";
			for(String s : strs){
				output += pairs.get(s) + " ";
			}
			System.out.println(output);
		}
	}

	public String toString()
	{
		return pairs.toString().replaceAll("\\,","\n");
	}
}