//(c) A+ Computer Science
//www.apluscompsci.com

//Name -

public class ThingCount implements Comparable {
	private int count;
	private Comparable thing;

	public ThingCount() {
	}

	public ThingCount(Comparable thang, int cnt) {
		thing = thang;
		count = cnt;
	}

	public void setThing(Object obj) {
		thing = (Comparable) obj;
	}

	public void setCount(int cnt) {
		count = cnt;
	}

	public void increment() {
		count++;
	}

	public Object getThing() {
		return null;
	}

	public int getCount() {
		return count;
	}

	public boolean equals(ThingCount obj) {
		return compareTo(obj) == 0;
	}

	public boolean equals(Object obj) {
		return compareTo(obj) == 0;
	}

	public int compareTo(Object obj) {
		return thing.compareTo((Comparable) obj);
	}

	public int compareTo(ThingCount obj) {
		try {

			return obj.thing.compareTo((Comparable) thing);
		} catch (Exception e) {
			return -1;
		}
	}

	public String toString() {
		return thing + " - " + count;
	}
}