//(c) A+ Computer Science
//www.apluscompsci.com
//Name -

import static java.lang.System.*;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet; 

public class RomanNumeral implements Comparable<RomanNumeral>
{
	private Integer number;
	private String roman;

	private static final TreeMap <String, Integer> stringMap = new TreeMap<String, Integer>();
	private static final TreeMap <Integer, String> intMap = new TreeMap<Integer, String>();

	static {
		stringMap.put("I", 1);
		stringMap.put("V", 5);
		stringMap.put("X", 10);
		stringMap.put("L", 50);
		stringMap.put("C", 100);
		stringMap.put("D", 500);
		stringMap.put("M", 1000);
		stringMap.put("CM", 900);
		stringMap.put("CD", 400);
		stringMap.put("XC", 90);
		stringMap.put("XL", 40);
		stringMap.put("IX", 9);
		stringMap.put("IV", 4);
		for (String key : stringMap.keySet()) {
			intMap.put(stringMap.get(key), key);
		}
	}

	public RomanNumeral(String str)
	{
		roman = str;
		number = getInteger(roman);
	}

	public RomanNumeral(Integer orig)
	{
		roman = getRomanNumeral(orig);
	}

	//write a set number method
	public void setNumber(Integer num)
	{
		number = num;
		roman = getRomanNumeral(num);
	}

	private String getRomanNumeral(Integer num) {
		if(num == 0) {
			return "";
		}	
		int prevKey = 0;
		for (Integer key : intMap.keySet()) {
			if (key > num) {
				return intMap.get(prevKey) + getRomanNumeral(num - prevKey);
			}
			prevKey = key;
		}
		if(num >= 1000){
			return "M" + getRomanNumeral(num - 1000);
		}
		return "";
	}

	private int getInteger(String rom){
		for (Integer key : new TreeSet<Integer>(intMap.keySet()).descendingSet()) {
			if (rom.startsWith(intMap.get(key))) {
				if(rom.length() > intMap.get(key).length()) {
					return key + getInteger(rom.substring(intMap.get(key).length()));
				} else {
					return key;
				}
			}
		}
		return 0;
	}

	public void setRoman(String rom)
	{
		roman = rom;
		number = getInteger(roman);
	}
	


	//write get methods for number and roman
	public int getNumber()
	{
		return number;
	}

	public String getRoman()
	{
		return roman;
	}
	

	public int compareTo(RomanNumeral r)
	{
		return number - r.getNumber();
	}

	public boolean equals(RomanNumeral r){
		return number == r.getNumber();
	}

	//write  toString() method
	public String toString()
	{
		return roman;
	}
	
	
}