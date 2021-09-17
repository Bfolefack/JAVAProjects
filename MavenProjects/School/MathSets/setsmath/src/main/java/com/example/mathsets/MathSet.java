//(c) A+ Computer Science
//www.apluscompsci.com

//Name -
package com.example.mathsets;

import java.util.Set;
import java.util.TreeSet;
import java.util.Scanner;

public class MathSet
{
	private Set<Integer> one;
	private Set<Integer> two;

	public MathSet()
	{
	}

	public MathSet(String o, String t)
	{
		one = new TreeSet<>();
		two = new TreeSet<>();
		Scanner scan = new Scanner(o);
		while(scan.hasNextInt()){
			int i = scan.nextInt();
			one.add(i);
		}
		scan.close();
		scan = new Scanner(t);
		while(scan.hasNextInt()){
			int i = scan.nextInt();
			two.add(i);
		}
		scan.close();
	}

	public Set<Integer> union()
	{
		Set<Integer> o = new TreeSet<>();
		Set<Integer> t = new TreeSet<>();
		o.addAll(one);
		t.addAll(two);
		o.addAll(t);
		return o;
	}

	public Set<Integer> intersection()
	{
		Set<Integer> o = new TreeSet<>();
		Set<Integer> t = new TreeSet<>();
		o.addAll(one);
		t.addAll(two);
		o.retainAll(t);
		return o;
	}

	public Set<Integer> differenceAMinusB()
	{
		Set<Integer> t = new TreeSet<>();
		t.addAll(two);
		Set<Integer> diff = union();
		diff.removeAll(t);
		return diff;
	}

	public Set<Integer> differenceBMinusA()
	{
		Set<Integer> o = new TreeSet<>();
		o.addAll(one);
		Set<Integer> diff = union();
		diff.removeAll(o);
		return diff;
	}
	
	public Set<Integer> symmetricDifference()
	{		
		Set<Integer> tot = union();
		Set<Integer> inter = intersection();
		tot.removeAll(inter);
		return tot;
	}	
	
	public String toString()
	{
		return "Set one " + one + "\n" +	"Set two " + two +  "\n";
	}
}