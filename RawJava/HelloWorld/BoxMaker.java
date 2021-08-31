import java.util.Scanner;

class BoxMaker{
	
	public static void main(String[] Args){
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter your preferred box width");
		int width = scan.nextInt();
		System.out.println("Please enter your preferred box height");
		int height = scan.nextInt();
		System.out.println("Please enter your preferred box character");
		String trueChar = scan.next();
		System.out.println("Would you like your box to be hollow? (true/false)");
		boolean hollow = scan.nextBoolean();
		System.out.println("Wait just a moment");
		String outputLine = "   ";
		
		System.out.println();
		System.out.println();
		
		if(hollow == false){
			for (int i = 0; i < width; i++){
				outputLine = outputLine + trueChar;
			}
			
			for (int i = 0; i < height; i++){
				System.out.println(outputLine);
			}
		} else {
			for (int i = 0; i < width; i++){
				outputLine = outputLine + trueChar;
			}
			String hollowLine = "   ";
			hollowLine = hollowLine + trueChar;
			for (int i = 0; i < width - 2; i++){
				hollowLine = hollowLine + " ";
			}
			hollowLine = hollowLine + trueChar;
			
			System.out.println(outputLine);
			for (int i = 0; i < height - 2; i++){
				System.out.println(hollowLine);
			}
			System.out.println(outputLine);
		}
		
		
		
	}
	
}