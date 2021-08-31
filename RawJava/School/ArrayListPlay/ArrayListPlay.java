import java.io.*;
import java.util.*;

public class ArrayListPlay
{
   public static void main(String[] args)
   {
      ArrayList<String> list = new ArrayList<>();
      list.add("purple");
      list.add("red");
      list.add("orange");
      System.out.println(list);
      System.out.println(list.get(1));
      list.add(0, "indigo");
      System.out.println(list);
      list.set(2, "blue");
      System.out.println(list);
   }
}