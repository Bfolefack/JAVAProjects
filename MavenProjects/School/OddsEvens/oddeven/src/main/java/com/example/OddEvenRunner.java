package com.example;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Hello world!
 *
 */

import com.example.oddevensets.*;
public class OddEvenRunner 
{
    public static void main( String[] args ) throws IOException
    {
        Scanner scan = new Scanner(new File("oddeven/src/main/java/com/example/oddevensets/sets.dat"));
        ArrayList<String> strings = new ArrayList<>();
        while(scan.hasNextLine())
            strings.add(scan.nextLine());
        System.out.println(strings);
        for(String s : strings){
            OddEvenSets oes = new OddEvenSets(s);
        }
    }
}
