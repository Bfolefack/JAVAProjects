import java.util.Scanner;

public class Greeter{	
	public static void main(String[] args){
		
		System.out.println("What's your name? (Please type your name and press enter.");
		
		Scanner scanner = new Scanner(System.in);
		String name = scanner.nextLine();
		
		System.out.println("Hello " + name + "!");
	}
}