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


	public Graph() {
		map = new TreeMap<>();
    }

    public void add(String s1, String s2, int i) {
		if (map.get(s1) == null) {
			TreeSet<String> tree = new TreeSet<String>();
			tree.add(s2 + "|" + i);
			map.put(s1, tree);
		} else {
			map.get(s1).add(s2 + "|" + i);
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

	public String cutInt(String in){
		if(in.indexOf("|") > -1){
			return in.substring(0, in.indexOf("|"));
		}
		return in;
	}

	public int getInt(String in){
		return Integer.parseInt(in.substring(in.indexOf("|")+1));
	}

	public class Step {
		public ArrayList<String> path = new ArrayList<String>();
		public int pathLength = 0;
	}

	public void shortestPath(String check) {
		String start = "A";
		String target = check;
		Step shortestPath = new Step();
		int shortestPathLength = Integer.MAX_VALUE;
		for (String s : map.get(start)) {
			Step path = new Step();
			path.path.add(start);
			Step currentPath = shortestPath(cutInt(s), target, path, getInt(s));
			if (currentPath != null) {
				if (currentPath.pathLength < shortestPathLength) {
					shortestPathLength = currentPath.pathLength;
					shortestPath.path = new ArrayList<String>(currentPath.path);
				}
			}
		}
		if(shortestPathLength < Integer.MAX_VALUE){
			System.out.println(start + " connects to " + target + " == yes in " + shortestPath.pathLength + " steps");
			System.out.print("Shortest path is: ");
			for (String s : shortestPath.path) {
				System.out.print(s + " > ");
			}
			System.out.print(target + "\n");
		} else {
			System.out.println(start + " connects to " + target + " == no");
		}
		System.out.println();
	}

	private Step shortestPath(String letter, String target, Step lastStep, int pathLength) {
		if(cutInt(letter).equals(target)){
			return lastStep;
		}
		lastStep.pathLength += pathLength;
		Step shortestPath = new Step();
		int shortestPathLength = Integer.MAX_VALUE;
		for (String s : map.get(letter)) {
			if (!lastStep.path.contains(cutInt(s))) {
				Step nextStep = new Step();
				nextStep.path = new ArrayList<String>(lastStep.path);
				nextStep.pathLength = lastStep.pathLength;
				nextStep.path.add(letter);
				nextStep.pathLength += getInt(s);
				Step currentPath = shortestPath(cutInt(s), target, nextStep, pathLength + getInt(s));
				if (currentPath != null) {
					if (currentPath.pathLength < shortestPathLength) {
						shortestPath.pathLength = currentPath.pathLength;
						shortestPath.path = new ArrayList<String>(currentPath.path);
					}
				}
			}
		}
		return shortestPath;
	}

	public String toString() {
		return "";
	}

    public void put(String next) {
		add(next.charAt(0) + "", next.charAt(2) + "", Integer.parseInt(next.substring(6)));
		add(next.charAt(2) + "", next.charAt(0) + "", Integer.parseInt(next.substring(6)));
    }
}