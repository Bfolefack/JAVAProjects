//(c) A+ Computer Science
//www.apluscompsci.com

//Name -

public class Number
{
	private int val;
	
	public Number(int value)
	{
		val = value;
	}	
	
	public int getValue()
	{
		return val;
	}
	
	public boolean equals(Object obj)
	{
		return val == ((Number) obj).val;
	} 
	
	public int hashCode()
	{
		return val % 10;
	}

	public String toString()
	{
		return "" + val;
	}	
}