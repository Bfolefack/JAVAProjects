package com.example.Numbers;

import java.util.HashMap;
import java.util.Map;

public class BaseNumber {
    static Map<Integer, String> baseMap;
    static Map<String, Integer> altMap;

    public static void initMap() {
        baseMap = new HashMap<>();
        baseMap.put(0, "0");
        baseMap.put(1, "1");
        baseMap.put(2, "2");
        baseMap.put(3, "3");
        baseMap.put(4, "4");
        baseMap.put(5, "5");
        baseMap.put(6, "6");
        baseMap.put(7, "7");
        baseMap.put(8, "8");
        baseMap.put(9, "9");
        baseMap.put(10, "A");
        baseMap.put(11, "B");
        baseMap.put(12, "C");
        baseMap.put(13, "D");
        baseMap.put(14, "E");
        baseMap.put(15, "F");

        altMap = new HashMap<>();
        for (Integer i : baseMap.keySet())
            altMap.put(baseMap.get(i), i);
    }

    public static String fBtB(String s, int b1, int b2) {
        String out = toBase(fromBase(s, b1), b2);
        return s + "-" + b1 + " == " + out + "-" + b2;
    }

    public static int fromBase(String value, int b) {
        int total = 0;
        for (int i = value.length() - 1; i >= 0; i--) {
            int temp = altMap.get(value.charAt(value.length() - (i) - 1) + "");
            total += temp * (float) Math.pow(b, i);
        }
        return total;
    }

    public static String toBase(int value, int base) {
        int num = value;
        String out = "";
        while (true) {
            int val = num / base;
            if (val > 0) {
                out += baseMap.get(num % (base * val));
            } else {
                out += baseMap.get(num);
                break;
            }
            num = val;
        }
        return new StringBuffer(out).reverse().toString();
    }

    public static String toBinary(String s){
        String out = "";
        char[] characters = s.toCharArray();
        for(char c : characters){
            out += "0" + toBase((int) c, 2) + " ";
        }
        return s + "\n" + out;
    }
}
