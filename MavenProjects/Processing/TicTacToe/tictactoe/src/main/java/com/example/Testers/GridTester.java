//(c) A+ Computer Science
//www.apluscompsci.com
//Name - 
package com.example.Testers;

import static java.lang.System.*;
import java.awt.Color;

import com.example.Grid;
import com.example.Abstracts.Piece;


public class GridTester
{
	public static void main(String[] args)
	{
		Grid gridTest = new Grid(2,2);
		
		out.println(gridTest.getNumRows());
		out.println(gridTest.getNumCols());
		
		gridTest.setSpot(0,0,new Piece("red checker"));
		out.println(gridTest);	
		
		gridTest.setSpot(1,1,new Piece(100,100,"black bishop"));
		out.println(gridTest);	

		gridTest.setSpot(1,1,new Piece(200 ,200, 20, 20, "bishop", Color.WHITE));
		out.println(gridTest);	
		
		out.println(gridTest.getSpot(1,0));		
		out.println(gridTest.getSpot(0,1));							
	}
}

