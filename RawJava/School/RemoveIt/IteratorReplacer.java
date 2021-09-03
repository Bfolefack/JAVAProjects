//(c) A+ Computer Science
//www.apluscompsci.com

import java.util.ArrayList;
import java.util.Iterator;

public class IteratorReplacer
{
	private ArrayList<String> list;
	private String toRemove, replaceWith;

	public IteratorReplacer(String line, String rem, String rep)
	{
        toRemove = rem;
        replaceWith = rep;
		list = new ArrayList<String>();
		String[] temp = line.split(" ");
		for(String s : temp){
			list.add(s);
		}
	}

	public void setEmAll(String line, String rem, String rep)
	{
        toRemove = rem;
        replaceWith = rep;
		list = new ArrayList<String>();
		String[] temp = line.split(" ");
		for(String s : temp){
			list.add(s);
		}
	}

	public void replace()
	{
        Iterator<String> iter = list.iterator();
        int count = 0;
		while(iter.hasNext()){
			String temp = iter.next();
			if(temp.equals(toRemove)){
                list.set(count, replaceWith);
            }
            count++;
		}
        count++;
	}

	public String toString()
	{
		return list.toString()+"\n\n";
	}
}