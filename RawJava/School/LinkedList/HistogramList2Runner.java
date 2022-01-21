//(c) A+ Computer Science
//www.apluscompsci.com

//Name -

import static java.lang.System.*;

public class HistogramList2Runner
{
	public static void main(String[] args)
	{
		HistoList4Lab5b test = new HistoList4Lab5b();
		String testString = "ABCDEFABCDEFFEDCBAAAAABBBBBCCCDAAAAAAAEEFFF";
		for(char c : testString.toCharArray())
		{
			test.add(c);
		}
		System.out.println(test);


		//add more test cases
		
		
		//demonstrate bad data check
		test = new HistoList4Lab5b();
		test.add("dog");
		test.add(33);
		test.add(3.4);
		System.out.println(test);
	}
}