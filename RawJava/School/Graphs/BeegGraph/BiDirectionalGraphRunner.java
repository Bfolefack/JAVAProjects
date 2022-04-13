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
		lab1("bidgraph.dat");
	}

	public static void lab1(String s) throws IOException {
		Scanner file = new Scanner(new File(s));

		while (file.hasNextLine()) {
			Graph graph = new Graph(file.nextLine());
			String next = file.nextLine();
			graph.check(next);
		}
	}

}