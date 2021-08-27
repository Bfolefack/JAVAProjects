import java.util.Scanner;
import java.io.*;
import java.util.Random;
import java.io.PrintStream;
import java.lang.Math;

class GATG{
	static String alphabet;
	static Random rand;
	
	public static void main(String [] args){
		alphabet = "abcdefghijklmnopqrstuvwxyz ";
		rand = new Random();
		
		Scanner scan = new Scanner(System.in);                                 
		System.out.println("Population Size?");
		int popSize = scan.nextInt();
		
		
		System.out.println("Input String?");
		scan = new Scanner(System.in);
		String target = scan.nextLine();
		int stringLength = target.length();
		
		Population pop = new Population(popSize, (10000/popSize)/10000, target);
		int e = 0;
		while(!(pop.answerFound)){
			pop.newGeneration();
			e++;
			System.out.println("Generation: " + e);
		}
	}
	
}