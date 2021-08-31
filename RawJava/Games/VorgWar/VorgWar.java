import javax.swing.JFrame;

class VorgWar { 
	
	public static void main(String[] args){
		System.out.println();
		System.out.println("Welcome young traveler to the world of WargLand!");
		System.out.println("WargLand is a wonderful world inhabited by creatures called Wargs. These creatures battle for glory and friendship.");
		System.out.println("And now it is your turn to pick your first warg and leap headfirst into this world of adventure");
		System.out.println();
		System.out.println("Feel free to pick your first warg");
		
		JFrame frame = new JFrame("Happy Coding");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 300);
		frame.setVisible(true);
		
	}
	
}