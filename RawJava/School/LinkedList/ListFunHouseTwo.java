//(c) A+ Computer Science
//www.apluscompsci.com

//Name -

import static java.lang.System.*;

public class ListFunHouseTwo {
	private ListNode theList;

	public ListFunHouseTwo() {

	}

	public void add(Comparable data) {
		if (theList == null) {
			theList = new ListNode(data, null);
			return;
		}
		ListNode head = theList;
		ListNode nextList = theList.getNext();
		while (nextList != null) {
			head = head.getNext();
			nextList = head.getNext();
		}
		head.setNext(new ListNode(data, null));

	}

	// this method will return the number of nodes present in list
	public int nodeCount() {
		int count = 0;
		ListNode head = theList;
		while (head != null) {
			count++;
			head = head.getNext();
		}
		return count;
	}

	// this method will create a new node with the same value as the first node and
	// add this
	// new node at the front of the list. Once finished, the first node will occur
	// twice.
	public void doubleFirst() {
		ListNode list = theList;
		ListNode l2 = new ListNode(list.getValue(), list.getNext());
		list.setNext(l2);
	}

	// this method will create a new node with the same value as the last node and
	// add this
	// new node at the end. Once finished, the last node will occur twice.
	public void doubleLast() {
		ListNode list = theList;
		ListNode prevList = null;
		while (list != null) {
			prevList = list;
			list = list.getNext();
		}
		prevList.setNext(new ListNode(prevList.getValue(), null));

	}

	// this method will set the value of every xth node in the list
	public void setXthNode(int x, Comparable value) {
		int count = 0;
		ListNode head = theList;
		while (head != null) {
			count++;
			if (count % x == 0)
				head.setValue(value);
			head = head.getNext();
		}
	}

	// this method will remove every xth node in the list
	public void removeXthNode(int x) {
		ListNode list = theList;
		int count = 0;
		ListNode prevList = null;
		while (list != null) {
			count++;
			if(count % x == 0){
				prevList.setNext(list.getNext());
			}
			prevList = list;
			list = list.getNext();
		}
	}

	// this method will return a String containing the entire list
	public String toString() {
		String out = "";
		ListNode t = theList;
		while (t != null) {
			out += t.getValue() + " ";
			t = t.getNext();
		}
		// System.out.println(out);
		return out;
	}

}