package com.example.Numbers;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class BaseTo2To10 {
    public static String convert(String filename){
        Map<Integer, String[]> vals = new TreeMap<>();
        try {
            File f = new File(filename);
            Scanner scan = new Scanner(f);
            ArrayList<String> strings = new ArrayList<>();
            while(scan.hasNextLine()){
                strings.add(scan.nextLine());
            }
            for(String s : strings){
                String[] temp = s.split(" ");
                BaseToBinary(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), vals);
            }
            String out = "";
            for(Integer i : vals.keySet()){
                out += vals.get(i)[1] + " " +  i + " " +  vals.get(i)[0] + "\n";
            }
            scan.close();
            return out;
        } catch (Exception e) {
            //TODO: handle exception
        }
        return null;
    }

    private static void BaseToBinary(int num, int b, Map<Integer, String[]> m){
        int base10 = BaseNumber.fromBase(num + "", b);
        String base2 = BaseNumber.toBase(base10, 2);
        m.put(base10, new String[] {base2, (num + "")});
    }
}
