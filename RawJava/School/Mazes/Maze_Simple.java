//(c) A+ Computer Science
//www.apluscompsci.com

import java.util.*;
import java.io.*;
import java.math.*;

public class Maze_Simple {
	public static void main(String[] args) throws Exception {
		lab("maze_simple.dat");
		lab("maze_bombs.dat");
	}

	public static void lab(String s) throws FileNotFoundException{
		Scanner scan = new Scanner(new File(s));
		while (scan.hasNextLine()) {
			int rows = scan.nextInt();
			scan.nextLine();
			char[][] mat = new char[rows][];
			for (int i = 0; i < rows; i++) {
				mat[i] = scan.nextLine().toCharArray();
			}
			MazeSolver ms = new MazeSolver(mat);
			ms.AStar();
		}
	}
}
