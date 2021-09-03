//(c) A+ Computer Science
//www.apluscompsci.com

public class IteratorRemoverRunner
{
	public static void main ( String[] args )
	{
		String a = "a b c a b c a";
		String b = "a b c d e f g h i j x x x x";
		String c = "1 2 3 4 5 6 a b c a b c";
		String d = "a b c a b c";
		String e = "a b c d e f g h i j x x x x";
		String f = "1 2 3 4 5 6 a b c a b c";
		IteratorRemover iter = new IteratorRemover(a, "a");
		iter.remove();
		System.out.println(iter);
		iter.setTest(b, "x");
		iter.remove();
		System.out.println(iter);
		iter.setTest(c, "b");
		iter.remove();
		System.out.println(iter);
		IteratorReplacer iter2 = new IteratorReplacer(d, "a", "+");
		iter2.replace();
		System.out.println(iter2);
	}
}