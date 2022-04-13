//(c) A+ Computer Science
//www.apluscompsci.com

//Name -

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import static java.lang.System.*;

public class GraphRunner
{
	public static void main( String[] args ) throws IOException
	{
		lab1("graph1.dat");
		System.out.println();
		lab1("graph2.dat");
		System.out.println();
		System.out.println();
		//lab2("graph1.dat");
		System.out.println();
		lab2("graph1.dat");
		lab2("graph2.dat");
		lab2("graph3.dat");
	}

	public static void lab1(String s) throws IOException
	{
		Scanner file = new Scanner(new File(s));
		
		while(file.hasNextLine()){
			Graph graph = new Graph(file.nextLine());
			String next = file.nextLine();
			graph.check(next);
		}
	}

	public static void lab2(String s) throws IOException
	{
		Scanner file = new Scanner(new File(s));
		
		while(file.hasNextLine()){
			Graph graph = new Graph(file.nextLine());
			String next = file.nextLine();
			graph.shortestPath(next);
		}
		System.out.println("\n\n\n");
	}
}