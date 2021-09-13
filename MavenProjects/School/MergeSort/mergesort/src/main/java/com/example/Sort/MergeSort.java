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
		mergeSort(list, 0, list.length);
		System.out.println(passCount);
		System.out.println("DONE!");
		System.out.println(Arrays.toString(list));
	}

	private static void mergeSort( int[] list, int front, int back)  //O( Log N )
	{
		int mid = (back + front)/2;
		if(front == mid){
			//merge(list, front, back);
			passCount++;
			return;
		}
		mergeSort(list, front, mid);
		mergeSort(list, mid, back);
		merge(list, front, back);
	}

	private static void merge(int[] list, int front, int back)  //O(N)
	{
		int mid = (front + back)/2;
		int[] a = new int[mid - front];
		for(int i = 0; i < mid - front; i++){
			a[i] = list[front + i];
		}
		int[] b = new int[back - mid];
		for(int i = mid; i < back; i++){
			if(i < list.length){
				b[i - mid] = list[i];
			}
		}
		int[] temp = new int[back - front];
		int aHead = 0;
		int bHead = 0;
		for(int i = 0; i < temp.length; i++){
			if(bHead >= b.length){
				temp[i] = a[aHead];
				aHead++;
			} else if(aHead >= a.length){
				temp[i] = b[bHead];
				bHead++;
			} else if(a[aHead] < b[bHead]){
				temp[i] = a[aHead];
				aHead++;
			} else {
				temp[i] = b[bHead];
				bHead++;
			}
		}
		for(int i = 0; i < temp.length; i++){
			list[i + front] = temp[i];
		}
	}
}