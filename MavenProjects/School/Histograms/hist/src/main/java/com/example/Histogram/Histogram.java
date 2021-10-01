//(c) A+ Computer Science
//www.apluscompsci.com
package com.example.Histogram;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Histogram
{
	private Map<String,Integer> hist;

	public Histogram( String line )
	{
		char[] charlie = line.toCharArray();
		hist = new TreeMap<>();
		for(char c : charlie){
			if(c != ' ')
				if(hist.get(c + "") == null){
					hist.put(c + "", 1);
				} else {
					hist.put(c + "", hist.get(c + "") + 1);
				}
		}
	}
	
	public int getValue( String s )
	{
		return hist.get(s);
	}

	public String toString()
	{
		String total = "char 1---5---10---15\n";
		// for(String s : hist.keySet()){
		// 	total += s + " COUNT = " + hist.get(s) + "\n";
		// }
		for(String s : hist.keySet()){
			total += s + "    ";
			for(int i = 0; i < hist.get(s); i++){
				total += "*";
			}
			total += "\n";
		}
		return total;
	}
}