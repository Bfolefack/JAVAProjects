//(c) A+ Computer Science
//www.apluscompsci.com

//Name -

import static java.lang.System.*;

import org.w3c.dom.NodeList;

public class ListFunHouse
{
	//this method will print the entire list on the screen
   public static void print(ListNode list)
   {
		String s = "";
		ListNode t = list;
		while(t != null){
			s += t.getValue().toString() + " ";
			t = t.getNext();
		}
		System.out.println(s);
   }		
	
	//this method will return the number of nodes present in list
	public static int nodeCount(ListNode list)
	{
		int c = 0;
		while(list != null){
			c++;
			list = list.getNext();
		}
		return c;
	}
		
	//this method will create a new node with the same value as the first node and add this
	//new node to the list.  Once finished, the first node will occur twice.
	public static void doubleFirst(ListNode list)
	{
		ListNode l2 = new ListNode(list.getValue(), list.getNext());
		list.setNext(l2);
	}

	//this method will create a new node with the same value as the last node and add this
	//new node at the end.  Once finished, the last node will occur twice.
	public static void doubleLast(ListNode list)
	{
		ListNode prevList = null;
		while(list != null){
			prevList = list;
			list = list.getNext();
		}
		prevList.setNext(new ListNode(prevList.getValue(), null));

	}
		
	//method skipEveryOther will remove every other node
	public static void skipEveryOther(ListNode list)
	{
		while(list != null && list.getNext() != null){
			ListNode next = list.getNext().getNext();
			list.setNext(list.getNext().getNext());
			list = next;
		}
	}

	//this method will set the value of every xth node in the list
	public static void setXthNode(ListNode list, int x, Comparable value)
	{
		for(int i = 0; i < x; i++){
			list = list.getNext();
		}
		list.setValue(value);
	}	

	//this method will remove every xth node in the list
	public static void removeXthNode(ListNode list, int x)
	{
		ListNode prev = null;
		for(int i = 0; i < x; i++){
			prev = list;
			list = list.getNext();
		}
		prev.setNext(list.getNext());
	}
}