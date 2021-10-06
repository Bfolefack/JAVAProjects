package com.example.relatives;
//(c) A+ Computer Science
//www.apluscompsci.com

//Name -

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Scanner;
import static java.lang.System.*;

import java.io.File;

public class Relatives
{
	private Map<String,Set<String>> map;

	public Relatives(String filename)
	{
		map = new TreeMap<>();
		try {
			File f = new File(filename);
			Scanner scan = new Scanner(f);
			scan.nextLine();
			String next = "";
			while (scan.hasNextLine()){
				if(!next.equals(""))
					setPersonRelative(next);
				next = scan.nextLine();
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			//TODO: handle exception
		}


	}

	public void setPersonRelative(String line)
	{
		String[] personRelative = line.split(" ");
		String person = personRelative[0];
		if(map.get(person) == null){
			map.put(person, new TreeSet<String>());
			for(String s : personRelative){
				if(s != person){
					map.get(person).add(s);
				}
			}
		} else {
			for(String s : personRelative){
				if(s != person){
					map.get(person).add(s);
				}
			}
		}
	}


	public String getRelatives(String person)
	{
		return "";
	}


	public String toString()
	{
		String output="";
		for(String s : map.keySet()){
			output += "\n";
			output += s + " is related to: ";
			Set<String> set = map.get(s);
			if(set.size() < 2){
				for(String str : set){
					output += str + ".";
				}
			} else if(set.size() == 2){
				int count = 0;
				for(String str : set){
					if(count < set.size() - 1){
						output += str;
					} else {
						output += " and " + str + ".";
					}
					count++;
				}
			} else {
				int count = 0;
				for(String str : set){
					if(count < set.size() - 1){
						output += str + ", ";
					} else {
						output += "and " + str + ".";
					}
					count++;
				}
			}
		}
		return output;
	}
}