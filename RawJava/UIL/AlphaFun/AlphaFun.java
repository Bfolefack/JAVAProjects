import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * AlphaFun
 */
public class AlphaFun {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("alphafun.dat"));
        TreeSet<Fun> fun = new TreeSet<>();
        while(scan.hasNextLine()){
            fun.add(new Fun(scan.nextLine()));
        }
        for(Fun f : fun){
            System.out.println(f.word);
        }
        scan.close();
    }
}

