package com.example.Sort;
//� A+ Computer Science  -  www.apluscompsci.com
//Name -
//Date - 
//Class - 
//Lab  - 

import static java.lang.System.*;
import java.util.Arrays;		//use Arrays.toString() to help print out the array

import javax.xml.namespace.QName;

public class MergeSort
{
	private static int passCount;

	public static void mergeSort(int[] list)
	{
		passCount=0;
		mergeSort(list, list.length);
		System.out.println(Arrays.toString(list));
	}

	private static void mergeSort( int[] list, int n)  //O( Log N )
	{
		if(n < 2){
			return;
		}
		int mid = n/2;
		int[] l = new int[mid];
		int[] r = new int[n - mid];

		for(int i = 0; i < mid; i++){
			l[i] = list[i];
		}
		for(int i = 0; i < mid; i++){
			r[i] = list[mid + i];
		}
		mergeSort(l, mid);
		mergeSort(r, n - mid);
		System.out.println(Arrays.toString(list));
		merge(list, l, r, mid, n - mid);
	}

	private static void merge(int[] list, int[] l, int[] r, int)  //O(N)
	{
		int[] temp = new int[back - front];
		int i = front;
		int j = (front + back)/2;
		int k = 0;
		int  mid = j;
		for(i = i; i < j; i++){
			if(list[i] < list[j]){
				temp[i] =  list[i];
			} else {
				temp[j] = list[j];
			}
		}
		for(i = i; i < mid; i++){
			temp[i] = list[i];
		}
		for(j = j; j < back; j++){
			temp[j] = list[j];
		}
		for(int l = 0; l < temp.length; l++){
			list[l] = temp[l];
		}
	}
}