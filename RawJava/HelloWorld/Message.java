public class Message{
	
	String myMessage;
	
	public Message(String myMessage){
		this.myMessage = myMessage;
	}
	
	public void printMessage(){
		System.out.println(myMessage);
	}
		
	public static void main(String[] args){
		Message messageOne = new Message("Hello");
		Message messageTwo = new Message("Bonjour");
		Message messageThree = new Message("Hola");

		messageOne.printMessage(); //prints Hello
		messageTwo.printMessage(); //prints Bonjour
		messageThree.printMessage(); //prints Hola
	}
}