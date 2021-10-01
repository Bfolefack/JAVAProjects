package com.example.Part;
//(c) A+ Computer Science
//www.apluscompsci.com

import java.util.Scanner;

//Name -

public class Part implements Comparable<Part>
{
	public String make, model, rest;
	public Integer year;

	public Part(String line) 
	{
		Scanner scan = new Scanner(line);
		while(!scan.hasNextInt()){
			scan.next();
		}
		String s = scan.nextInt() + "";
		model  = line.substring(0, s.length() + line.indexOf(s));
		while(!scan.hasNextInt()){
			scan.next();
		}
		year = scan.nextInt();
		String y = year + "";
		make = line.substring(s.length() + line.indexOf(s) + 1, line.indexOf(y) - 1);
	}

	//have to have compareTo if implements Comparable
	public int compareTo( Part rhs )
	{
		if(make.compareTo(rhs.make) != 0){
			return make.compareTo(rhs.make);
		} else if(model.compareTo(rhs.model) != 0){
			return model.compareTo(rhs.model);
		} else {
			return year.compareTo(rhs.year);
		}
	}

	public String toString()
	{
		return model + " " + make + " " + year;
	}
}