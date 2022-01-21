//(c) A+ Computer Science
//www.apluscompsci.com

//Name -

public class HistoList4Lab5b
{
	private ListNode front;

	public HistoList4Lab5b( )
	{
		front = null;
	}


	//ADDS NEW NODE TO THE FRONT OF THE LIST FOR LET IF IT DOES NOT EXIST.
	//IF IT EXISTS, IT BUMPS UP LET'S COUNT BY ONE
	public void add(Object other)
	{
		ThingCount obj = new ThingCount(other, 1);
		ListNode temp = front;
		ListNode lastTemp = null;
		while (temp != null) {
			if(((ThingCount)temp.getValue()).equals(obj)){
				temp.increment();
				return;
			}
			lastTemp = temp;
			temp = temp.getNext();
		}
		if(lastTemp != null){
			lastTemp.setNext(new ListNode(obj, null));
			return;
		}
		front = new ListNode(obj, null);
	}

	//RETURNS THE INDEX POSITION OF LET IN THE LIST
	public int indexOf(Object obj)
	{
		int spot=-1;
		return -1;
	}

	//RETURNS A REFERENCE TO THE NODE AT SPOT
	private ListNode nodeAt(int spot)
	{
		ListNode current=front;
		return current;
	}

	//RETURNS THE LIST AS A BIG STRING
	public String toString()
	{
		String output = "";
		ListNode temp = front;
		while(temp != null){
			output += temp.getValue() + "\n";
			temp = temp.getNext();
		}
		return output;
	}
}