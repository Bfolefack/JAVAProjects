package com.example.Entities.Stats;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import processing.core.PVector;

public class Cell implements Comparable<Cell> {
    int xPos;
    int yPos;
    boolean active;
    boolean filled;
    boolean border;
    Integer index;

    public Cell(int i, int j, boolean b) {
        xPos = i;
        yPos = j;
        filled = b;
    }

    public void floodFill(World world, Set<Cell> nextActive, Map<Integer, Map<Integer, Cell>> grid) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i != 0 || j != 0) {
                    Cell c = world.getCell(xPos + i, yPos + j, grid);
                    if (c != null) {
                        if (!c.active && c.filled == filled) {
                            c.active = true;
                            nextActive.add(c);
                        }
                    }
                }
            }
        }
    }

    void buildCave(World world, Map<Integer, Map<Integer, Cell>> grid) {
        int neighbors = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (Math.abs(i) + Math.abs(j) != 0) {
                    Cell cell = world.getCell(xPos + i, yPos + j, grid);
                    if (cell != null) {
                        if (cell.filled) {
                            neighbors++;
                        }
                    } else {
                        neighbors += 10;
                    }
                }
            }
        }
        if (neighbors < 3) {
            filled = false;
        } else if (neighbors > 4) {
            filled = true;
        }
    }

    void smoothWalls(World world, Map<Integer, Map<Integer, Cell>> grid) {
        int neighbors = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (Math.abs(i) + Math.abs(j) != 0) {
                    Cell cell = world.getCell(xPos + i, yPos + j, grid);
                    if (cell != null) {
                        if (cell.filled) {
                            neighbors++;
                        }
                    } else {
                        neighbors += 10;
                    }
                }
            }
        }
        if (neighbors < 4) {
            filled = false;
        }
    }

    void chopWalls(World world, Map<Integer, Map<Integer, Cell>> grid) {
        int neighbors = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (Math.abs(i) + Math.abs(j) != 0) {
                    Cell cell = world.getCell(xPos + i, yPos + j, grid);
                    if (cell != null) {
                        if (cell.filled) {
                            neighbors++;
                        }
                    } else {
                        neighbors += 10;
                    }
                }
            }
        }
        if (neighbors < 5) {
            filled = false;
        }
    }

    public boolean checkBorders(World w, Map<Integer, Map<Integer, Cell>> grid) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (Math.abs(i) + Math.abs(j) == 1) {
                    if (w.getCell(xPos + i, yPos + j, grid) != null)
                        if (w.getCell(xPos + i, yPos + j, grid).filled != filled) {
                            border = true;
                            return true;
                        }
                }
            }
        }
        return false;
    }

    public Cell getBorder(int id, Cell giver, World w, Map<Integer, Map<Integer, Cell>> grid) {
        index = id;
        ArrayList<Cell> c = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (Math.abs(i) + Math.abs(j) != 0) {
                    Cell cel = w.getCell(xPos + i, yPos + j, grid);
                    c.add(cel);
                    if (cel.index == null && cel.border && cel.active) {
                        return cel;
                    }
                }
            }
        }
        return getNearestBorder(w, giver, grid);
    }

    public Cell getNearestBorder(World w, Cell giver, Map<Integer, Map<Integer, Cell>> grid) {
        ArrayList<Cell> cells = new ArrayList<>();
        for (int i = -10; i < 11; i++) {
            for (int j = -10; j < 11; j++) {
                if (Math.abs(i) + Math.abs(j) != 0) {
                    Cell cel = w.getCell(xPos + i, yPos + j, grid);
                    if (cel.index == null && cel.border && cel.active) {
                        cells.add(cel);
                    }
                }
            }
        }
        cells.remove(this);
        cells.remove(giver);
        Cell nearest = null;
        float nearestDist = Integer.MAX_VALUE;
        for (Cell c : cells) {
            if (PVector.dist(new PVector(xPos, yPos), new PVector(c.xPos, c.yPos)) < nearestDist) {
                nearestDist = PVector.dist(new PVector(xPos, yPos), new PVector(c.xPos, c.yPos));
                nearest = c;
            }
        }
        return nearest;
    }

    @Override
    public int compareTo(Cell c) {
        return index.compareTo(c.index);
    }
}
