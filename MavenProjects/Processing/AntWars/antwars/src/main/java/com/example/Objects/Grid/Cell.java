package com.example.Objects.Grid;

public class Cell {
    public int xPos, yPos;
    int ID;
    public CellStates state;
    
    int currColor; 
    Cell(int x, int y, CellStates s){
        xPos = x;
        yPos = y;
        state = s;
    }
}
