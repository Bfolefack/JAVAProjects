import java.util.*;

// import org.graalvm.compiler.asm.aarch64.AArch64Assembler.SystemRegister;

import java.io.*;
import java.awt.*;

public class MapDataDrawer
{

  private int[][] grid;

  public MapDataDrawer(String filename, int rows, int cols) throws FileNotFoundException{
      // initialize grid 
      //read the data from the file into the grid
        Scanner scan = new Scanner(new File(filename));
        grid = new int[rows][cols];
        try{
        for(int i = 0; i < rows; i++){
          for(int j = 0; j < cols; j++){
            grid[i][j] = scan.nextInt();
            // System.out.println(grid[i][j]);
          }
        }
      } catch(Exception e){
        System.out.println(e);
        System.exit(0);
      }
        scan.close();    
      
  }
  
  /**
   * @return the min value in the entire grid
   */
  public int findMinValue(){
    int min = Integer.MAX_VALUE;
    for(int i = 0; i < grid.length; i++){
      for(int j = 0; j < grid[i].length; j++){
        if(grid[i][j] < min){
          min = grid[i][j];
        }
      }
    }
    return min;   
  }
  /**
   * @return the max value in the entire grid
   */
  public int findMaxValue(){
    int max = Integer.MIN_VALUE;
    for(int i = 0; i < grid.length; i++){
      for(int j = 0; j < grid[i].length; j++){
        if(grid[i][j] > max){
          max = grid[i][j];
        }
      }
    }
    return max; 
  }
  
  /**
   * @param col the column of the grid to check
   * @return the index of the row with the lowest value in the given col for the grid
   */
  public  int indexOfMinInCol(int col){
  
      return -1;
  }
  
  /**
   * Draws the grid using the given Graphics object.
   * Colors should be grayscale values 0-255, scaled based on min/max values in grid
   */
  public void drawMap(Graphics g){
    float min = findMinValue();
    float max = findMaxValue();
    for(int i = 0; i < grid.length; i++){
      for(int j = 0; j < grid[i].length; j++){
        int c = (int)((grid[i][j] - min)/(max - min) * 255);
        // System.out.println(c);
        g.setColor(new Color(c, c, c));
        g.fillRect(j,i,1,1);
      }
    }
  }

   /**
   * Find a path from West-to-East starting at given row.
   * Choose a foward step out of 3 possible forward locations, using greedy method described in assignment.
   * @return the total change in elevation traveled from West-to-East
   */
  public int drawLowestElevPath(Graphics g, int row){
    return -1;
  }
  
  /**
   * @return the index of the starting row for the lowest-elevation-change path in the entire grid.
   */
  public int indexOfLowestElevPath(Graphics g){
     return -1;
  
  }
  
  
}