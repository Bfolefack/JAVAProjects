/** new arrayplotter
 * ArrayPlotter.java  10/18/14
 *
 * @author - Jane Doe
 * @author - Period n
 * @author - Id nnnnnnn
 *
 * @author - I received help from ...
 *
 *
 * The ArrayPlotter class provides methods for drawing in
 *    a grid by setting the boolean values of a 2D array.
 *
 *    Each drawing method must
 *    - take zero arguments,
 *    - have a void return type, and
 *    - have a name that conforms to the on...ButtonClick format.
 *      (This restriction allows the GridPlotterGUI to recognize methods,
 *       for which it automatically generates buttons.)
 *
 * License Information:
 *   This class is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation.
 *
 *   This class is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 */

import javax.swing.JOptionPane;

public class ArrayPlotter
{
	/** The Array Plotter Graphical User Interface. */
  	private ArrayPlotterGUI gui = new ArrayPlotterGUI(this);

	/** The Color Array.  The element values indicate how to color a grid cell:
	 *  - true: Color the cell with the Drawing Color.
	 *  - false: Color the cell with the Background Color.
	 */
  	private boolean[][] colorArray = null;


	/** Constructs an Array Plotter */
	public ArrayPlotter()
	{
     
      
   }

	/** Initialize this's Color Array to a new 2D array of boolean values
	 *  with the given dimensions.
	 *  @param rows the number of rows in the new array.
	 *  @param cols the number of columns in the new array.
	 *  Postcondition: All of the Color Array's elements have the value false.
	 */
	public void initializeColorArray(int rows, int cols)
	{
      colorArray = new boolean[rows][cols];
  	}


  	// Drawing Methods

	public void onClearGridButtonClick()
	{
		for(int i = 0; i < colorArray.length; i++)
         for(int j = 0; j < colorArray[0].length; j++){
            if(colorArray[i][j]){
               colorArray[i][j] = false;
               
            }
         }
         gui.update(colorArray);
	}



	public void onRowMajorFillButtonClick()
	{
		for(int i = 0; i < colorArray.length; i++)
         for(int j = 0; j < colorArray[0].length; j++){
            colorArray[i][j] = true;
            gui.update(colorArray);
         }
	}
   
   
   
   public void onReverseRowMajorFillButtonClick()
	{
		for(int i = colorArray.length - 1; i >=0 ; i--)
         for(int j =  colorArray[0].length - 1; j >= 0; j--){
            colorArray[i][j] = true;
            gui.update(colorArray);
         }
	}
   
   
   public void onReverseColMajorFillButtonClick()
	{
		for(int i = colorArray[0].length - 1; i >=0 ; i--)
         for(int j =  colorArray.length - 1; j >= 0; j--){
            colorArray[j][i] = true;
            gui.update(colorArray);
         }
	}
   
   
   
   public void onColMajorFillButtonClick(){
		for(int i = 0; i < colorArray[0].length; i++)
         for(int j = 0; j < colorArray.length; j++){
            colorArray[j][i] = true;
            gui.update(colorArray);
         }
	}
   
   
   public void onMainDiagonalFillButtonClick(){
      for(int i = 0; i < colorArray.length; i++)
         for(int j = 0; j < colorArray[0].length; j++){
            if(i == j){
               colorArray[i][j] = true;
               gui.update(colorArray);
            }
         }
   }
   
   
   public void onTriangleDiagonalFillButtonClick(){
      for(int i = 0; i < colorArray.length; i++)
         for(int j = 0; j < colorArray[0].length; j++){
            if(i >= j){
               colorArray[i][j] = true;
               gui.update(colorArray);
            }
         }
   }
   
   
   
   public void onOtherDiagonalFillButtonClick(){
      for(int i = 0; i < colorArray.length; i++)
         for(int j = 0; j < colorArray[0].length; j++){
            if(colorArray[0].length - 1 - i == j){
               colorArray[i][j] = true;
               gui.update(colorArray);
            }
         }
   }
   
   public void onOtherTriangleFillButtonClick(){
      for(int i = 0; i < colorArray.length; i++)
         for(int j = 0; j < colorArray[0].length; j++){
            if(colorArray[0].length - 1 - i <= j){
               colorArray[i][j] = true;
               gui.update(colorArray);
            }
         }
   }
   
   private void fillRowLeftToRight(boolean[] row){
      for(int i = 0; i < row.length; i++){
         row[i] = true;
         gui.update(colorArray);
      }
   }
   
   private void fillRowRightToLeft(boolean[] row){
      for(int i = row.length - 1; i >= 0 ; i--){
         row[i] = true;
         gui.update(colorArray);
      }
   }
   
   public void onSerpentineFillButtonClick(){
      for(int i = 0; i < colorArray.length; i++){
         if(i % 2 == 0){
            fillRowLeftToRight(colorArray[i]);
         } else {
            fillRowRightToLeft(colorArray[i]);
         }
      }
   }
   
     
   public void onXFillButtonClick(){
      onMainDiagonalFillButtonClick();
      onOtherDiagonalFillButtonClick();
   }
   
   public void onBorderFillButtonClick(){
      fillRowLeftToRight(colorArray[0]);
      for(int i = 1; i < colorArray.length - 1; i++){
         colorArray[i][colorArray[0].length - 1] = true;
         gui.update(colorArray);
      }
      fillRowRightToLeft(colorArray[colorArray.length - 1]);
      for(int i = colorArray.length - 2; i >= 1; i--){
         colorArray[i][0] = true;
         gui.update(colorArray);
      }
   }



  	/** main method that creates the grid plotter application. */
	public static void main(String[] args) {
		ArrayPlotter p = new ArrayPlotter();
	}
}