package com.example.EngtoSpan;

import java.util.Comparator;

public class StrLengthComparator implements Comparator<String>{

    @Override
    public int compare(String o1, String o2) {
        // TODO Auto-generated method stub
        int chk = Integer.valueOf(o1.length()).compareTo(o2.length());
        if(chk == 0){
            return o1.compareTo(o2);
        }
        return (-1 * chk);
    }
}
