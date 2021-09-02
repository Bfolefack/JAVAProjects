package com.example;
import java.util.*;
import java.io.*;
import com.example.MatrixSearch.*;
/**
 * Hello world!
 *
 */
public class MatrixSearchRunner 
{
    public static void main( String[] args )
    {
        MatrixSearch ms = new MatrixSearch(40, 40, 15);   
        System.out.println(ms);     
        System.out.println( "Odd count = " + ms.countOdds() ); 
        System.out.println( "Even count = " + ms.countEvens() );
        System.out.println( "Prime count = " + ms.countPrimes() );
    }
}
