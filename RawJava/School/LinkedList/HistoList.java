//(c) A+ Computer Science
//www.apluscompsci.com

//Name -

import java.util.*;
import static java.lang.System.*;

public class HistoList
{
   private HistoNode front;

	public HistoList()
	{
		front = null;
	}

	//addLetter will add a new node to the front for let if let does not exist
	//addLetter will bump up the count if let already exists
	public void addLetter(char let)
	{
		HistoNode temp = front;
		HistoNode lastTemp = null;
		while (temp != null) {
			if(temp.getLetter() == let){
				temp.increment();
				return;
			}
			lastTemp = temp;
			temp = temp.getNext();
		}
		if(lastTemp != null){
			lastTemp.setNext(new HistoNode(let, 1, null));
			return;
		}
		front = new HistoNode(let, 1, null);
	}

	//returns the index pos of let in the list if let exists
	public int indexOf(char let)
	{









		return -1;
	}

	//returns a reference to the node at spot
	private HistoNode nodeAt(int spot)
	{
		HistoNode current=null;










		return current;
	}

	//returns a string will all values from list
	public String toString()
	{
		String output = "";
		HistoNode temp = front;
		while(temp != null){
			output += temp.getLetter() + " - " + temp.getLetterCount() + "\n";
			temp = temp.getNext();
		}
		return output;
	}
}