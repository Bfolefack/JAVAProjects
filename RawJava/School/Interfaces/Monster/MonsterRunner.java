//(c) A+ Computer Science
//www.apluscompsci.com
//Name -

import java.util.Scanner;
import static java.lang.System.*;

public class MonsterRunner
{
	public static void main( String args[] )
	{
		Scanner keyboard = new Scanner(System.in);
		
		Skeleton s1 = makeSkeleton(keyboard);
		Skeleton s2 = makeSkeleton(keyboard);
		System.out.println(s1.isBigger(s2) ? s1.getName() + " is bigger than " + s2.getName() : s2.getName() + " is bigger than " + s1.getName());
		s1 = makeSkeleton(keyboard);
		s2 = makeSkeleton(keyboard);
		System.out.println(s1.isSmaller(s2) ? s1.getName() + " is smaller than " + s2.getName() : s2.getName() + " is smaller than " + s1.getName());
		s1 = makeSkeleton(keyboard);
		s2 = makeSkeleton(keyboard);
		System.out.println((s1.getName() + " and " + s2.getName()) +  (s1.namesTheSame(s2) ? " have the same name" : " have different names"));
	}

	public static Skeleton makeSkeleton(Scanner s)
	{
		System.out.println("Enter a name: ");
		String name = s.next();
		
		System.out.println("Enter a size: ");
		int size = s.nextInt();
		
		return new Skeleton(name, size);
	}
}