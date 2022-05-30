//(c) A+ Computer Science
//www.apluscompsci.com
//Name -

import static java.lang.System.*; 

public class Skeleton implements Monster
{
	public String name;
	public int size;

	public Skeleton(String nm, int sz)
	{
		name = nm;
		size = sz;
	}

	@Override
	public int getHowBig() {
		return size;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isBigger(Monster other) {
		return size > other.getHowBig();
	}

	@Override
	public boolean isSmaller(Monster other) {
		return size < other.getHowBig();
	}

	@Override
	public boolean namesTheSame(Monster other) {
		return name.equals(other.getName());
	}
}