package com.example;

import com.example.Numbers.BaseNumber;
import com.example.Numbers.BaseTo2To10;

/**
 * Hello world!
 *
 */
public class Main 
{
    public static void main( String[] args )
    {
        BaseNumber.initMap();
        System.out.println(BaseTo2To10.convert("numsystems/src/main/java/com/example/Numbers/number.dat"));
    }
}
