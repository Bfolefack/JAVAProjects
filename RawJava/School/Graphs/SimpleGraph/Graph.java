//(c) A+ Computer Science
//www.apluscompsci.com

//Name -

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import org.omg.PortableInterceptor.ACTIVE;

public class Graph {
	public TreeMap<String, String> map;
	private boolean found;

	public Graph(String line) throws IOException {
		Scanner scan = new Scanner(line);
		map = new TreeMap<>();
		while (scan.hasNext()) {
			char[] s = scan.next().toCharArray();
			add(s[0], s[1]);
			add(s[1], s[0]);
		}
	}

	public void add(char c1, char c2) {
		if (map.get("" + c1) == null) {
			map.put("" + c1, "" + c2);
		} else {
			map.put("" + c1, map.get("" + c1) + c2);
		}
	}

	public boolean contains(String letter) {
		return true;
	}

	public void check(String check) {
		String start = check.charAt(0) + "";
		char target = check.charAt(1);
		HashSet<String> active = new HashSet<>();
		String checked = "";
		active.add(start);
		while (active.size() > 0) {
			HashSet<String> nextActive = new HashSet<>();
			for (String string : active) {
				checked += string;
			}
			for (String string : active) {
				if (map.get(string) != null)
					for (char ch : map.get(string).toCharArray()) {
						if (ch == target) {
							System.out.println(start + " connects to " + target + " == yes");
							return;
						} else if (!checked.contains(ch + "")) {
							nextActive.add(ch + "");
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
		String start = check.charAt(0) + "";
		String target = check.charAt(1)  + "";
		String shortestPath = "";
		int shortestPathLength = Integer.MAX_VALUE;
		for (char c : map.get(start).toCharArray()) {
			String currentPath = shortestPath(c + "", target, start);
			if (currentPath != null) {
				if (currentPath.length() < shortestPathLength) {
					shortestPathLength = currentPath.length();
					shortestPath = currentPath;
				}
			}
		}
		if(shortestPathLength < Integer.MAX_VALUE){
			System.out.println(start + " connects to " + target + " == yes in " + shortestPath.length() + " steps");
			System.out.print("Shortest path is: ");
			for (char c1 : shortestPath.toCharArray()) {
				System.out.print(c1 + " > ");
			}
			System.out.print(target + "\n");
		} else {
			System.out.println(start + " connects to " + target + " == no");
		}
		System.out.println();;
	}

	private String shortestPath(String letter, String target, String path) {
		if(letter.equals(target)){
			return path;
		}
		path += letter;
		String shortestPath = null;
		int shortestPathLength = Integer.MAX_VALUE;
		for (char c : map.get(letter).toCharArray()) {
			if (!path.contains("" + c)) {
				String currentPath = shortestPath(c + "", target, path);
				if (currentPath != null) {
					if (currentPath.length() < shortestPathLength) {
						shortestPathLength = currentPath.length();
						shortestPath = currentPath;
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