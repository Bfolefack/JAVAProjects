//(c) A+ Computer Science
//www.apluscompsci.com

//Name -

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;
import static java.lang.System.*;

public class HashTableRunner {
	public static void main(String[] args) {
		try {
			Scanner scan = new Scanner(new File("numbers.dat"));
			HashSet<Number> in = new HashSet<>();
			scan.next();
			while (scan.hasNextInt()) {
				in.add(new Number(scan.nextInt()));
			}
			HashTable tabby = new HashTable();
			for (Number number : in) {
				tabby.add(number);
			}
			System.out.println(tabby);

			scan = new Scanner(new File("words.dat"));
			scan.next();
			HashSet<Word> in2 = new HashSet<>();
			while (scan.hasNext()) {
				in2.add(new Word(scan.next()));
			}
			tabby = new HashTable();
			for (Word word : in2) {
				tabby.add(word);
			}
			System.out.println(tabby);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}