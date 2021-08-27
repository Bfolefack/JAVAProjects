import java.io.*;
import java.util.*;

public class DataSorter{
    public static void main(String[] args) throws FileNotFoundException{
        int count = 0;
        File f = new File("tweetSmall.txt");
        Scanner sc = new Scanner(f);
        while(sc.hasNextLine()){
            sc.nextLine();
            count++;
        }
        sc.close();
        sc = new Scanner(f);
        String[] raw = new String[count];
        for(int i = 0; i  < raw.length; i++){
            raw[i] = sc.nextLine();
        }
        sc.close();
        String[][] preprocessed = new String[count][];
        for(int i = 0; i  < raw.length; i++){
            preprocessed[i] = raw[i].split(",");
        }
        Tweet[] tweets = new Tweet[count];
        for(int i = 0; i  < raw.length; i++){
            tweets[i] = new Tweet(preprocessed[i]);
        }
        System.out.println(count);
        
    }
}