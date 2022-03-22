//(c) A+ Computer Science
//www.apluscompsci.com

//Name -

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static java.lang.System.*;

public class Heap {
	private List<Integer> list;

	public Heap() {
		list = new ArrayList<Integer>();
	}

	public Heap(String input){
		list = new ArrayList<Integer>();
		for (String integer : input.split(",")) {
			int i = Integer.parseInt(integer);
			add(i);			
		}
	}

	public void add(int value) {
		list.add(value);
		swapUp(list.size() - 1);
	}

	public void swapUp(int bot) {
		if(bot == 0)
			return;
		Integer me = list.get(bot);
		Integer comp = list.get((bot - 1)/2);
		if(me > comp){
			swap(bot, (bot - 1)/2);
			swapUp((bot - 1)/2);
		}
	}

	public void remove() {
		swap(0, list.size() - 1);
		list.remove(list.size() - 1);
		swapDown(0);
	}

	public void swapDown(int top) {
		if(top >= list.size())
			return;
		int left = top * 2;
		int right = top * 2 + 1;
		int beeg = top;
		if (left < list.size() && list.get(left) > list.get(beeg)){
			beeg = left;
		}
		if (right < list.size() && list.get(right) > list.get(beeg)){
			beeg = right;
		}
		if(beeg != top){
			swap(top, beeg);
			swapDown(beeg);
		}
	}

	private void swap(int start, int finish) {
		int temp = list.get(start);
		list.set(start, list.get(finish));
		list.set(finish, temp);
	}

	public void print() {
		out.println("\n\nPRINTING THE HEAP!\n\n");
		int height =  (int) Math.ceil(Math.log(list.size()));
		int scale = 1;
		for (int i = 0, index = 0; i <= height; i++, scale *= 2) {
			for (int j = 0; j < scale && index < list.size() ; j++, index++) {
				for (int k = 0; k <= (Math.pow(2, height)/scale) - (list.get(index).toString().length() - 1); k++) {
					out.print(" ");
				}
				out.print(list.get(index));
			}
			out.println();
		}
		out.println();
	}
	
	public void printRev(){
		ArrayList<Integer> newList = new ArrayList<>(list);
		Collections.sort(newList);
		System.out.println(newList);
	}

	public ArrayList<Integer> flip(){
		ArrayList<Integer> newList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			newList.add(i, list.get(list.size() - i - 1));
		}
		return newList;
	}



	public String toString() {
		return list.toString();
	}
}