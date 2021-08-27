import java.awt.*;
import javax.swing.*; 

class WindowTutorial {
	
	public static void main(String[] args){
		JFrame frame = new JFrame("Simple GUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setSize(800,600);
		frame.setResizable(false);
		frame.setVisible(true);
    }
}