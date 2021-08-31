import java.io.*;
import java.util.*;

public class Scrabble
{
   public static void main(String[] args) throws Exception
   {
      // String[][] board = new String[15][15];        
      Scanner file = new Scanner(new File("dictionary.txt"));
      int count = 0;
      while(file.hasNext())
      {
         file.nextLine();
         count++;
      }
      String[] words = new String[count];
      file = new Scanner(new File("dictionary.txt"));
      for(int i=0;i<words.length;i++)
      {
         words[i] = file.nextLine();
      }
      
      String word = words[(int)(Math.random()*words.length)];
      //System.out.println(word);
      
      String[] blanks = new String[word.length()];
      for(int i=0; i<blanks.length; i++)
      {
         blanks[i] = "-";
      }
      
      Scanner keyboard = new Scanner(System.in);
      boolean hasBlanks = true;
      // int check = 0;
      while(hasBlanks)
      {
         giveEmTheBlanks(blanks);
         System.out.print("Guess a letter: ");
         String letter = keyboard.nextLine();
         
         boolean foundLetter = false;
         for(int i = 0; i<word.length(); i++)
         {
            if(word.substring(i,i+1).equals(letter))
            {
               foundLetter = true;
               blanks[i] = letter;
            }
         }
         if(foundLetter)
         {
            System.out.println("CORRCT!");
         }
         else
         {  
            
            System.out.println("INCORRECT"+letter);
            System.out.println("STICKMAN");
         }
            hasBlanks = false;
            for(String s: blanks)
            {
               if(s.equals("-"))
                  hasBlanks = true;
            }

      }
      System.out.println(blanks);
      keyboard.close();
   }
   public static void giveEmTheBlanks(String[] arr)
   {
   String blankOut = "";
      for(int i = 0; i<arr.length; i++)
      {
         blankOut +=arr[i];
      }
      System.out.println(blankOut);
   }
}