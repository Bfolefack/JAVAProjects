//(c) A+ Computer Science
//www.apluscompsci.com

//Name -

public class Word
{
	private String val;
	
	//write a constructor method
	Word(String s){
		val = s;
	}
	
	
	//write the getValue method
	public String getValue() {
		return val;
	}
	
	
	//write the equals method
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return val.equals(((Word) obj).val);
	}
	
	
	//write the hashCode method
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return (countVowels() * val.length()) % 10;
	}
	
	
	//write the toString method
	@Override
	public String toString() {
		return val;
	}

	private int countVowels(){
		int count = 0;
		for(char c : val.toCharArray()){
			if(c == 97 || c == 101 || c == 105 || c == 111 || c == 117){
				count++;
			}
		}
		return count;

	}
	
}