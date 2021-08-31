import java.io.*;
import java.util.*;

public class FirstProgram{
   public static void main (String[] args) {
      File f;
      Scanner s;
      try{
         f = new File("data.txt");
         s = new Scanner(f);
      } catch (Exception e){
         System.exit(0);
         return;
      }
      
      while(s.hasNext()){
         System.out.println(s.nextLine());
      }
      s.close();
   }
}