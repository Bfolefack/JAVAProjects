import java.util.*;
import java.io.*;

public class CSVReader{
    public static void main(String[] args) {
        try {
            // int count = 0;
            File f = new File("injuries.csv");
            Scanner scan = new Scanner(f);
            while(scan.hasNext()){
                System.out.println(scan.nextLine());
                // count++;
            }
            scan.close();

        } catch (Exception e) {
            System.exit(0);
        }
    }
}