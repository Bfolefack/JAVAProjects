package com.example;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import com.example.UniqueDups.*;

/**
 * Hello world!
 *
 */
public class DupRunner 
{
    public static void main( String[] args )
    {
        System.out.println(UniquesDupes.getUniques("1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 6"));
        System.out.println(UniquesDupes.getDupes("1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 6"));
        System.out.println("\n");
        System.out.println(UniquesDupes.getDupes("a b c d e f g h a b c d e f g h i j k"));
        System.out.println(UniquesDupes.getUniques("a b c d e f g h a b c d e f g h i j k"));
        System.out.println("\n");
        System.out.println(UniquesDupes.getDupes("one two three one two three six seven one two"));
        System.out.println(UniquesDupes.getUniques("one two three one two three six seven one two"));
    }
}
