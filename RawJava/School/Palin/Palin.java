//� A+ Computer Science  -  www.apluscompsci.com
//Name -
//Date - 
//Class -
//Lab  -

import static java.lang.System.*;
import java.util.ArrayList;

//define class Palin

public class Palin {
	//instance variable - String
	private String check;

	//constructors
	Palin(String c){
		check = c;
	}

	//getLength method - returns an int
	public int getLength() {
		return check.length();
	}

	//getWord method - returns a String
	public String getWord() {
		return check;
	}

	//isPalin method - returns a boolean
	public boolean isPalin() {
		boolean palin = true;
		for(int i = 0; i < check.length()/2; i++){
			if(check.charAt(i) != check.charAt(check.length() - (i + 1))){
				palin = false;
			}
		}
		return palin;
	}

	//toString method - returns a String
	public String toString(){
		return check;
	}
}

