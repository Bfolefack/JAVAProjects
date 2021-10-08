package com.example;

import java.io.PrintStream;
import java.util.Map;
import java.util.TreeMap;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        PrintStream out = System.out;



        TreeMap<Integer,Integer> map;
        map = new TreeMap<Integer,Integer>();
        Integer[] list = {9,2,3,4,5,6,7,10,3,4,2,3,4,2,3};

        for(Integer num : list)
           if(map.get(num)==null)
              map.put(num,1);
           else
              map.put(num,map.get(num)+1);

        System.out.println( map.lastEntry() );


    }
}
