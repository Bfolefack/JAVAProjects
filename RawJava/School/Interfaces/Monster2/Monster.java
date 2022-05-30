//(c) A+ Computer Science
//www.apluscompsci.com
//Name -

import static java.lang.System.*;

public class Monster implements Comparable
{
	private int myWeight;
	private int myHeight;
	private int myAge;

	//write a default Constructor
	public Monster(){
		myWeight = 0;
		myHeight = 0;
		myAge = 0;
	}



	//write an initialization constructor with an int parameter ht
	public Monster(int ht){
		myWeight = 0;
		myHeight = ht;
		myAge = 0;
	}


	//write an initialization constructor with int parameters ht and wt
	public Monster(int ht, int wt){
		myWeight = wt;
		myHeight = ht;
		myAge = 0;
	}



	//write an initialization constructor with int parameters ht, wt, and age
	public Monster(int ht, int wt, int age){
		myWeight = wt;
		myHeight = ht;
		myAge = age;
	}


	//modifiers - write set methods for height, weight, and age
	public void setWeight(int wt){
		myWeight = wt;
	}

	public void setHeight(int ht){
		myHeight = ht;
	}

	public void setAge(int age){
		myAge = age;
	}
	
	
	//accessors - write get methods for height, weight, and age
	public int getWeight(){
		return myWeight;
	}

	public int getHeight(){
		return myHeight;
	}

	public int getAge(){
		return myAge;
	}
	
	
	//creates a new copy of this Object
	public Object clone()
	{
		return new Monster(myHeight, myWeight, myAge);
	}

	public boolean equals( Object obj )
	{
		if(obj instanceof Monster)
		{
			Monster m = (Monster)obj;
			return (myHeight == m.myHeight && myWeight == m.myWeight && myAge == m.myAge);
		}
		return false;
	}

	public int compareTo( Object obj )
	{
		Monster rhs = (Monster)obj;

		if(myHeight < rhs.myHeight)
			return -1;
		else if(myHeight > rhs.myHeight)
			return 1;
		else if(myWeight < rhs.myWeight)
			return -1;
		else if(myWeight > rhs.myWeight)
			return 1;
		else if(myAge < rhs.myAge)
			return -1;
		else if(myAge > rhs.myAge)
			return 1;
		else
			return 0;
	}

	//write a toString() method
	public String toString(){
		return  myHeight + " " + myWeight + " " + myAge;
	}
	
}