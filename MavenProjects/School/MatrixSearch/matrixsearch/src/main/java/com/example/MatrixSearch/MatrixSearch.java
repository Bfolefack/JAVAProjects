//(c) A+ Computer Science
//www.apluscompsci.com
//Name - Boueny Folefack
package com.example.MatrixSearch;

import java.util.*;
import java.io.*; 

public class MatrixSearch
{
    private int[][] mat;

		/*
		 *pre - mat is null
		 *post - mat will be rows X cols
		 *post - mat will contain integers that are <= upper and >= 1
		 */
    public MatrixSearch( int rows, int cols, int upper )
    {
		mat = new int[rows][cols];
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				mat[i][j] = (int) (Math.random() * upper + 1);
			}
		}
    }


		/*
		 *pre - mat is not null
		 *post - count of odd numbers will be returned
		 *post - no values in mat will have been changed
		 */
    public int countOdds( )
    {
		int count = 0;
		for (int[] is : mat) {
			for (int i : is) {
				if(i % 2 != 0){
					count++;
				}
			}
		}
		return count;
    }


		/*
		 *pre - mat is not null
		 *post - count of even numbers will be returned
		 *post - no values in mat will have been changed
		 */
    public int countEvens( )
    {
		int count = 0;
		for (int[] is : mat) {
			for (int i : is) {
				if(i % 2 == 0){
					count++;
				}
			}
		}
		return count;
    }


		/*
		 *pre - mat is not null
		 *post - count of prime numbers will be returned
		 *post - no values in mat will have been changed
		 */
    public int countPrimes( )
    {
		int count = 0;
		for (int[] is : mat) {
			for (int i : is) {
				if(isPrime(i)){
					count++;
				}
			}
		}
		return count;
    }

		/*
		 *pre - num has a value
		 *post - false is returned if num is divisble by any number between 2 and itself
		 *post - true is returned if num is not divisble by any number between 2 and itself
		 */
    private boolean isPrime(int n)
    {
		
		if(n == 1){
			return false;
		}
		if(n == 2 || n == 3){
			return true;
		}
		for (int i = 2; i < n; i++) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
    }

		/*
		 *pre - mat is not null
		 *post - all values from mat are concatenated to a string and returned
		 */
    public String toString()
    {
		String s = "";
		for (int[] is : mat) {
			s += Arrays.toString(is) + "\n";
		}
		return s;
    }
}
