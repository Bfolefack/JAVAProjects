package com.example.Objects.Grid;

import java.util.Set;

import com.example.AntWars;

public class Cell {
    public int xPos, yPos;
    int ID;
    public CellStates state;

    int currColor;
    public boolean active;

    Cell(int x, int y, CellStates s) {
        xPos = x;
        yPos = y;
        state = s;
    }

    public void floodFill(Grid grid, Set<Cell> nextActive, int id2) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i != 0 || j != 0) {
                    Cell c = grid.getCell(xPos + i, yPos + j);
                    if (c != null) {
                        if (c.ID == 0 && c.state != CellStates.FILLED) {
                            c.ID = id2;
                            nextActive.add(c);
                        }
                    }
                }
            }
        }
    }

    public void update(AntWars aw, Set<Cell> nextActiveCells) {
        switch (state) {
            case FILLED:
                currColor = aw.color(0);
                break;
            case FOOD:
                currColor = aw.color(0, 255, 0);
                break;
            case EMPTY:
                currColor = aw.color(255);
                break;
        }
    }

    public void display() {
    }

    void buildCave(Grid grid) {
        int neighbors = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                Cell cell = grid.getCell(xPos + i, yPos + j);
                if (cell != null) {
                    if (cell.state == CellStates.FILLED) {
                        neighbors++;
                    }
                } else {
                    neighbors += 10;
                }
            }
        }
        if (neighbors < 3) {
            if (state == CellStates.FILLED)
                state = CellStates.EMPTY;
        } else if (neighbors > 4) {
            state = CellStates.FILLED;
        }
    }

    void smoothWalls(Grid grid) {
        int neighbors = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (Math.abs(i) + Math.abs(j) != 0) {
                    Cell cell = grid.getCell(xPos + i, yPos + j);
                    if (cell != null) {
                        if (cell.state == CellStates.FILLED) {
                            neighbors++;
                        }
                    } else {
                        neighbors += 10;
                    }
                }
            }
        }
        if (neighbors < 4) {
            if (state == CellStates.FILLED) {
                state = CellStates.EMPTY;
            }
        }
    }
}
