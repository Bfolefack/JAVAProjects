//(c) A+ Computer Science
//www.apluscompsci.com
//Name - 
package com.example;

import processing.core.PApplet;

import java.awt.Color;
import java.awt.Font;

import com.example.Abstracts.Cell;
import com.example.Abstracts.Piece;

public class Grid {
	private Piece[][] grid;
	boolean OTurn;

	public Grid() {
		setSize(3, 3);
	}

	public Grid(int rows, int cols) {
		setSize(rows, cols);
	}

	public void setSize(int rows, int cols) {
		grid = new Piece[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {

			}
		}
	}

	public boolean setSpot(int row, int col, Piece val) {
		if (row >= 0 && row < grid.length && col >= 0 && col < grid[row].length) {
			grid[row][col] = (Piece) val;
			return true;
		}
		return false;
	}

	public Piece getSpot(int row, int col) {
		if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length)
			return null;
		return grid[row][col];
	}

	public int getNumRows() {
		return grid.length;
	}

	public int getNumCols() {
		return grid[0].length;
	}

	public void drawGrid(PApplet window) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] != null || grid[i][j].getName().equals("empty")) {
					grid[i][j].draw(window);
				}
			}
		}
	}

	public String toString() {
		String s = "";
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] != null) {
					s += grid[i][j].toString();
				} else
					s += "-------empty-------";
				s += "\n";
			}

		}
		s += "\n\n\n";
		return s;
	}
}