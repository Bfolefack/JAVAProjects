import java.io.File;
import java.io.FileNotFoundException;
import java.io.LineNumberInputStream;
import java.util.PriorityQueue;
import java.util.Scanner;

import javafx.scene.Node;

public class MazeSolver {
    int[][] maze;
    char[][] mat;

    public MazeSolver(char[][] c){
        mat = c;
        maze = new int[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                switch (mat[i][j]) {
                    case '#':
                        maze[i][j] = 100000;
                        break;
                    case '.':
                        maze[i][j] = 1;
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public MazeSolver(String file) throws FileNotFoundException {
        // constructor
        Scanner scan = new Scanner(new File(file));
        int rows = scan.nextInt();
        scan.nextLine();
        mat = new char[rows][];
        for (int i = 0; i < rows; i++) {
            mat[i] = scan.nextLine().toCharArray();
        }

        maze = new int[rows][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                switch (mat[i][j]) {
                    case '#':
                        maze[i][j] = 100000;
                        break;
                    case '.':
                        maze[i][j] = 1;
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void AStar(){
        char[][] active = new char[mat.length][mat[0].length];
        char[][] pathGiver = new char[mat.length][mat[0].length];
        int[][] gCost = new int[mat.length][mat[0].length];
        
        
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                active[i][j] = ' ';
                pathGiver[i][j] = ' ';
                gCost[i][j] = 1000000;
            }
        }

        active[0][0] = 'a';
        gCost[0][0] = 0;

        boolean pathFound = false;

        //Path Giver:
        //-1 = not visited
        //0 = north
        //1 = east
        //2 = south
        //3 = west

        while(!pathFound){
            // arrPrinter(active);
            // arrPrinter(gCost);
            // arrPrinter(pathGiver);
            for (int i = 0; i < gCost.length; i++) {
                for (int j = 0; j < gCost[0].length; j++) {
                    if(active[gCost.length - 1][gCost[0].length - 1] == 'v'){
                        pathFound = true;
                        break;
                    }

                    if(active[i][j] == 'a'){
                        if (i != 0) {
                            if (active[i - 1][j] == ' ') {
                                active[i - 1][j] = 'a';
                                pathGiver[i - 1][j] = 'v';
                                gCost[i - 1][j] = gCost[i][j] + maze[i - 1][j];
                            } else {
                                if (gCost[i - 1][j] > gCost[i][j] + 1) {
                                    gCost[i - 1][j] = gCost[i][j] + maze[i - 1][j];;
                                    pathGiver[i - 1][j] = 'v';
                                }
                            }
                        }
                        if (j != 0) {
                            if(active[i][j - 1] == ' '){
                                active[i][j - 1] = 'a';
                                pathGiver[i][j - 1] = '>';
                                gCost[i][j - 1] = gCost[i][j] + maze[i][j - 1];
                            } else {
                                if(gCost[i][j - 1] > gCost[i][j] + 1){
                                    gCost[i][j - 1] = gCost[i][j] + maze[i][j - 1];
                                    pathGiver[i][j - 1] = '>';
                                }
                            }
                        }
                        if (i != gCost.length - 1) {
                            if(active[i + 1][j] == ' '){
                                active[i + 1][j] = 'a';
                                pathGiver[i + 1][j] = '^';
                                gCost[i + 1][j] = gCost[i][j] + maze[i + 1][j];
                            } else {
                                if(gCost[i + 1][j] > gCost[i][j] + 1){
                                    gCost[i + 1][j] = gCost[i][j] + maze[i + 1][j];
                                    pathGiver[i + 1][j] = '^';
                                }
                            }
                        }
                        if (j != gCost[0].length - 1) {
                            if(active[i][j + 1] == ' '){
                                active[i][j + 1] = 'a';
                                pathGiver[i][j + 1] = '<';
                                gCost[i][j + 1] = gCost[i][j] + maze[i][j + 1];
                            } else {
                                if(gCost[i][j + 1] > gCost[i][j] + 1){
                                    gCost[i][j + 1] = gCost[i][j] + maze[i][j + 1];
                                    pathGiver[i][j + 1] = '<';
                                }
                            }
                        }
                    }
                    active[i][j] = 'v';
                }
            }
        }
        int x = gCost.length - 1;
        int y = gCost[0].length - 1;
        char[][] sol = new char[mat.length][mat[0].length];
        for (int i = 0; i < sol.length; i++) {
            for (int j = 0; j < sol[0].length; j++) {
                sol[i][j] = mat[i][j];
            }
        }
        while(x != 0 || y != 0){
            switch(mat[x][y]){
                case '#':
                    sol[x][y] = 'B';
                    break;
                case '.':
                    sol[x][y] = 'P';
                    break;
                default:
                    break;
            }

            if(pathGiver[x][y] == '^'){
                x--;
            } else if(pathGiver[x][y] == '>'){
                y++;
            } else if(pathGiver[x][y] == 'v'){
                x++;
            } else if(pathGiver[x][y] == '<'){
                y--;
            }
        }
        sol[0][0] = 'P';
        arrPrinter(mat);
        System.out.println();
        arrPrinter(sol);
        int lastX = gCost.length - 1;
        int lastY = gCost[0].length - 1;
        if(gCost[lastX][lastY] < 100000){
            System.out.println("Path found!");
        } else {
            System.out.println("No path found!");
        }
        System.out.println("Path Cost: " + (gCost[lastX][lastY] - ((gCost[lastX][lastY]/100000) * 100000) + (gCost[lastX][lastY]/100000)));
        System.out.println("Path Bomb Cost: " + (gCost[lastX][lastY]/100000));
        System.out.println();
        System.out.println();
    }

    public void arrPrinter(char[][] arr){
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void arrPrinter(int[][] arr){
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }
}