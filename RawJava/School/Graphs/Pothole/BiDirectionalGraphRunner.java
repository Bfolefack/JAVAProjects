//(c) A+ Computer Science
//www.apluscompsci.com

//Name -

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeSet;
import static java.lang.System.*;

public class BiDirectionalGraphRunner {
	public static void main(String[] args) throws IOException {
		lab1("potholes.dat");
	}

	public static void lab1(String in) throws IOException {
		Scanner file = new Scanner(new File(in));

		String target = file.nextLine();
		
		for (int i = 0; i < 2; i++) {
			Graph graph = new Graph();
			while (file.hasNextLine()) {
				String s = file.nextLine();
				if(s.length() < 2){
					target = s; 
					graph.shortestPath(target);
					break;
				}
				graph.put(s);
			}
			graph.shortestPath(target);
		}
		
		
	}

}