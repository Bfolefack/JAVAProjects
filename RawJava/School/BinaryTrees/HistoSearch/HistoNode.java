//(c) A+ Computer Science
//www.apluscompsci.com

//Name -

public class HistoNode implements Comparable {
	private Comparable value;
	private int valueCount;

	public HistoNode(Comparable let, int cnt) {
		value = let;
		valueCount = cnt;
	}

	public Comparable getValue() {
		return value;
	}

	public int getValueCount() {
		return valueCount;
	}

	public void setValue(Comparable let) {
		value = let;
	}

	public void setValueCount(int cnt) {
		valueCount = cnt;
	}

	public void increment() {
		valueCount++;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof HistoNode) {
			return ((HistoNode) o).getValue().compareTo(getValue()) * -1;
		}
		if (o instanceof Comparable)
			return ((Comparable) o).compareTo(this);
		return -1;
	}

	@Override
	public String toString() {
		return value.toString() + " - " + valueCount;
	}
}
