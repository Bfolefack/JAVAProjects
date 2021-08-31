import java.io.*;
import java.util.*;

public class Main{
    public static void main(String[] args) {
        int[][] grades = new int[5][];
        grades[0] = new int[]{100, 100, 45, 80, 90};
        for(int row = 0; row < grades.length; row++){
            for(int col = 0; col < grades[0].length; col++){
                grades[row][col] = (int)(Math.random() * 30) + 70;
            }
        }
    }
}