//(c) A+ Computer Science
//www.apluscompsci.com

//Name -

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.Scanner;
import static java.lang.System.*;

public class Graph
{
	private TreeMap<String, TreeSet<String>> map;
	private boolean found;

	public Graph(String line) throws IOException {
		Scanner scan = new Scanner(line);
		map = new TreeMap<>();
		while (scan.hasNext()) {
			String s1 = scan.next();
			String s2 = scan.next();
			add(s1, s2);
			add(s2, s1);
		}
	}

	public void add(String s1, String s2) {
		if (map.get(s1) == null) {
			TreeSet<String> tree = new TreeSet<String>();
			tree.add(s2);
			map.put(s1, tree);
		} else {
			map.get(s1).add(s2);
		}
	}

	public boolean contains(String letter) {
		return true;
	}

	public void check(String check) {
		String start = check.split(" ")[0];
		String target = check.split(" ")[1];
		HashSet<String> active = new HashSet<>();
		HashSet<String> checked = new HashSet<>();
		active.add(start);
		while (active.size() > 0) {
			HashSet<String> nextActive = new HashSet<>();
			for (String string : active) {
				checked.add(string);
			}
			for (String string : active) {
				if (map.get(string) != null)
					for (String s : map.get(string)) {
						if (s.equals(target)) {
							System.out.println(start + " connects to " + target + " == yes");
							return;
						} else if (!checked.contains(s)) {
							nextActive.add(s);
						}
					}
				else if (string == start)
					System.out.println(start + " connects to " + target + " == no");
			}
			active = nextActive;
		}
		System.out.println(start + " connects to " + target + " == no");
	}

	public void shortestPath(String check) {
		String start = check.split(" ")[0];
		String target = check.split(" ")[1];
		ArrayList<String> shortestPath = new ArrayList<>();
		int shortestPathLength = Integer.MAX_VALUE;
		for (String s : map.get(start)) {
			ArrayList<String> path = new ArrayList<String>();
			path.add(start);
			ArrayList<String> currentPath = shortestPath(s, target, path);
			if (currentPath != null) {
				if (currentPath.size() < shortestPathLength) {
					shortestPathLength = currentPath.size();
					shortestPath = currentPath;
				}
			}
		}
		if(shortestPathLength < Integer.MAX_VALUE){
			System.out.println(start + " connects to " + target + " == yes in " + shortestPath.size() + " steps");
			System.out.print("Shortest path is: ");
			for (String s : shortestPath) {
				System.out.print(s + " > ");
			}
			System.out.print(target + "\n");
		} else {
			System.out.println(start + " connects to " + target + " == no");
		}
		System.out.println();;
	}

	private ArrayList<String> shortestPath(String letter, String target, ArrayList<String> path) {
		if(letter.equals(target)){
			return path;
		}
		path.add(letter);
		ArrayList<String> shortestPath = null;
		int shortestPathLength = Integer.MAX_VALUE;
		for (String s : map.get(letter)) {
			if (!path.contains(s)) {
				ArrayList<String> currentPath = shortestPath(s, target, path);
				if (currentPath != null) {
					if (currentPath.size() < shortestPathLength) {
						shortestPathLength = currentPath.size();
						shortestPath = new ArrayList<String>(currentPath);
					}
				}
			}
		}
		return shortestPath;
	}

	public String toString() {
		return "";
	}
}