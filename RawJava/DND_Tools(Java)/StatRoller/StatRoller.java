import java.util.Scanner;
import java.util.Random;
import java.lang.Math;

class StatRoller {
	
	public static void main(String[] args){
		// Scanner scan = new Scanner(System.in);
		System.out.println("How many arrays would you like to generate?");
		// int statCount = scan.nextInt();
		System.out.println("David:" + statString());
		System.out.println("Drew:" + statString());
		System.out.println("Cooper:" + statString());
		System.out.println("Peyton:" + statString());
		System.out.println("That one Person:" + statString());
		// for(int i = 0; i < statCount; i++){
		// 	System.out.println(statString());	
		// }
	}
	
	static int generateStat(){
		Random rand = new Random();
		int[] rolls = new int[4];
		
		for ( int i = 0; i < 4; i++ ){
			rolls[i] = rand.nextInt(6);
			rolls[i]++;
		}
		
		int temp = 99;
		int tempIndex = 5;
		
		for ( int i = 0; i < 4; i++ ){
			if( rolls[i] < temp ){
				temp = rolls[i];
				tempIndex = i;
			}
		}
		rolls[tempIndex] = 0;
		return rolls[0] + rolls[1] + rolls[2] + rolls[3];
	}
	
	static int[] generateStats(){
		int[] stats = new int[6];
		for(int i = 0; i < 6; i++){
			stats[i] = generateStat();
		}
		return stats;
	}
	
	static String statString(){
		int[] stats = generateStats();
		String s = "";
		for (int i = 0; i < stats.length; i++ ){
			s += " " + stats[i];
		}
		return s;
	}	
}