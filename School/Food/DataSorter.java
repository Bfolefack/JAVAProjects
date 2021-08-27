import java.io.*;
import java.util.*;

public class DataSorter{
    public static void main(String[] args) throws FileNotFoundException{
        int count = 0;
        File f = new File("food.csv");
        Scanner sc = new Scanner(f);
        while(sc.hasNextLine()){
            sc.nextLine();
            count++;
        }
        sc.close();
        sc = new Scanner(f);
        count--;
        String[] raw = new String[count];
        sc.nextLine();
        for(int i = 0; i  < raw.length; i++){
            raw[i] = sc.nextLine();
        }
        sc.close();
        String[][] preprocessed = new String[count][4];
        for(int i = 0; i  < raw.length; i++){
            preprocessed[i][0] = raw[i].substring(1, raw[i].lastIndexOf("\""));
            System.out.println(raw[i].lastIndexOf("\""));
            raw[i] = raw[i].substring(raw[i].lastIndexOf("\""));
        }
        for(int i = 0; i  < raw.length; i++){
            preprocessed[i] = raw[i].split(",");
        }
        Food[] foods = new Food[count];
        for(int i = 0; i  < raw.length; i++){
            foods[i] = new Food(preprocessed[i]);
        }
        System.out.println(count);
        
    }
}