//(c) A+ Computer Science
//www.apluscompsci.com

//Name -

import java.util.LinkedList;
import java.util.Scanner;
import static java.lang.System.*;

public class HashTable {
	private LinkedList<Object>[] table;

	public HashTable() {
		table = new LinkedList[10];
	}

	public void add(Object obj) {
		int i = obj.hashCode();
		for (int j = 0; j < table.length; j++) {
			if (table[j] == null) {
				table[j] = new LinkedList<>();
			}
			if (j == i) {
				if (!table[j].contains(obj))
					table[j].add(obj);
			}
		}
	}

	public String toString() {
		String output = "HASHTABLE\n";
		for (int i = 0; i < table.length; i++) {
			output += "bucket " + i + ":";
			for (Object o : table[i]) {
				output += " " + o;
			}
			output += "\n";
		}
		return output;
	}
}