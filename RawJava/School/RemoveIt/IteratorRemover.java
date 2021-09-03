//(c) A+ Computer Science
//www.apluscompsci.com

import java.util.ArrayList;
import java.util.Iterator;

public class IteratorRemover
{
	private ArrayList<String> list;
	private String toRemove;

	public IteratorRemover(String line, String rem)
	{
		toRemove = rem;
		list = new ArrayList<String>();
		String[] temp = line.split(" ");
		for(String s : temp){
			list.add(s);
		}
	}

	public void setTest(String line, String rem)
	{
		toRemove = rem;
		list = new ArrayList<String>();
		String[] temp = line.split(" ");
		for(String s : temp){
			list.add(s);
		}
	}

	public void remove()
	{
		Iterator<String> iter = list.iterator();
		while(iter.hasNext()){
			String temp = iter.next();
			if(temp.equals(toRemove))
				iter.remove();
		}
	}

	public String toString()
	{
		return list.toString();
	}
}