import java.io.*;
import java.util.*;

public class GenericDataSorter{
    public static void main(String[] args) throws FileNotFoundException{
        int count = 0;
        File f = new File("Injuries.csv");
        Scanner sc = new Scanner(f);
        while(sc.hasNextLine()){
            sc.nextLine();
            count++;
        }
        sc.close();
        sc = new Scanner(f);
        count--;
        String s = sc.nextLine();
        String[] ss = s.split(",");
        String[] raw = new String[count];
        for(int i = 0; i  < raw.length; i++){
            raw[i] = sc.nextLine();
        }
        sc.close();
        String[][] preprocessed = new String[count][ss.length];
        for(int i = 0; i  < raw.length; i++){
            preprocessed[i][0] = raw[i].substring(0, raw[i].indexOf(","));
            raw[i] = raw[i].substring(raw[i].indexOf(",") + 1);
            for(int j = 1; j < 6; j++){
                if(raw[i].indexOf("\"") == 0){
                    raw[i] = raw[i].substring(raw[i].indexOf("\"") + 1);
                    preprocessed[i][j] = raw[i].substring(0, raw[i].indexOf("\""));
                    raw[i] = raw[i].substring(raw[i].indexOf("\"") + 1);
                }else {
                    if(raw[i].indexOf(",") == -1){
                        preprocessed[i][j] = raw[i];
                        break;
                    } else {
                        preprocessed[i][j] = raw[i].substring(0, raw[i].indexOf(","));
                    }
                }
                raw[i] = raw[i].substring(raw[i].indexOf(",") + 1);
            }
        }
        
        System.out.println(count);
        
    }
}