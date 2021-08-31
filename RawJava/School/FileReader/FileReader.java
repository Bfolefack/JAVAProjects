import java.io.*;
import java.util.*;

public class FileReader{
    public static void main(String[] args) throws FileNotFoundException {
        dictionary();
    }

    public static void dictionary() throws FileNotFoundException {
        int count = 0;
        String longest = "";

        File f = new File("dictionary.txt");
        Scanner scan = new Scanner(f);
        while(scan.hasNext()){
            scan.next();
            count++;
        }
        scan.close();
        
        String[] words = new String[count];

        scan = new Scanner(f);
        for(int i = 0; i < count; i++){
            words[i] = scan.next();
        }

        for(int i = 0; i < words.length; i++){
            if(words[i].length() > longest.length()){
                longest = words[i];
                System.out.println(longest);
            }
        }
        scan.close();
    }
    
    public static void numbers() throws FileNotFoundException {
        int smallest = Integer.MAX_VALUE;
        int biggest = Integer.MIN_VALUE;
        int count = 0;
        File f = new File("numbers.txt");
        Scanner scan = new Scanner(f);
        while(scan.hasNext()){
            scan.nextInt();
            count++;
        }
        scan.close();
        scan = new Scanner(f);
        int[] numbers = new int[count];
        for(int i = 0; i < count; i++){
            numbers[i] = scan.nextInt();
        }
        scan.close();
        for(int i = 0; i < numbers.length; i++){
            if(numbers[i] < smallest){
                smallest = numbers[i];
            }
            if(numbers[i] > biggest){
                biggest = numbers[i];
            }
        }

        System.out.println(count);
        System.out.println(smallest);
        System.out.println(biggest);
        System.out.println(smallest * 1.0/Integer.MAX_VALUE);
        System.out.println(biggest * 1.0/Integer.MAX_VALUE);
    }
}