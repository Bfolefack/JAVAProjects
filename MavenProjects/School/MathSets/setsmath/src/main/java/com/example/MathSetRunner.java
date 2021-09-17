package com.example;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import com.example.mathsets.*;

/**
 * Hello world!
 *
 */
public class MathSetRunner 
{
    public static void main( String[] args ) throws IOException
    {
        Scanner scan = new Scanner(new File("setsmath/src/main/java/com/example/mathsets/mathsetdata.dat"));
        ArrayList<String> strings = new ArrayList<>();
        while(scan.hasNextLine()){
            strings.add(scan.nextLine());
        }
        // System.out.println(strings);
        for(int i = 0; i < strings.size(); i += 2){
            MathSet oes = new MathSet(strings.get(i),  strings.get(i + 1));
            System.out.println(oes.union());
            System.out.println(oes.intersection());
            System.out.println(oes.differenceAMinusB());
            System.out.println(oes.differenceBMinusA());
            System.out.println(oes.symmetricDifference());
            System.out.println(oes.toString()); 
        }
    }
}
