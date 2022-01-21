//(c) A+ Computer Science
//www.apluscompsci.com

//Name -

public class ThingCount implements Comparable
{
	private int count;
	private Comparable thing;
	
	public ThingCount()
	{
	}
	
	public ThingCount(Object thang, int cnt)
	{
		thing = (Comparable)thang;
		count = cnt;
	}
	
	public int getCount()
	{
		return count;
	}
	
	public void setCount(int cnt)
	{
		count = cnt;
	}

	public void setThing(Object obj)
	{
		thing = (Comparable)obj;
	}	
	
	public Object getThing()
	{
		return thing;
	}
	
	public boolean equals(Object obj)
	{
		ThingCount other = (ThingCount)obj;
		return other.thing == thing;
	}
	
	public int compareTo(Object obj)
	{
		ThingCount other = (ThingCount)obj;
		return thing.compareTo((Comparable)other.thing);		
	}
	
	public String toString()
	{
		return ""+ getThing() + " - " + getCount();
	}

    public void increment() {
		count++;
    }
}