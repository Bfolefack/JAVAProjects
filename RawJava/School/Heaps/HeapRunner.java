//(c) A+ Computer Science
//www.apluscompsci.com

//Name -

import java.util.ArrayList;
import static java.lang.System.*;

public class HeapRunner
{
	public static void main (String[] args)
	{
		Heap heap = new Heap();

		heap.add(1);
		heap.add(2);
		heap.add(8);
		heap.add(9);
		heap.add(10);
		heap.add(7);
		heap.add(75);
		heap.add(17);
		heap.add(5);

		heap.print();
		heap.remove();
		heap.print();
		heap.remove();
		heap.print();
		heap.remove();
		heap.print();
		heap.remove();
		heap.print();
		heap.remove();
		heap.print();
		heap.remove();
		heap.print();
		heap.remove();

		heap.print();
		heap.add(25);
		heap.print();
		heap.add(35);
		heap.print();
		heap.remove();
		heap.print();

		Heap new1 = new Heap("99,2,8,75,10,7,9,17,5,3,4,1,11,1");
		Heap new2 = new Heap("-3,28,18,5,3,17,29,6,5,3,4,1,11,1");
		new1.printRev();
		new2.printRev();		
	}
}