package com.example.Objects.Stats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import com.example.VectorWars;

import processing.core.PApplet;
import processing.core.PVector;

public class Cell implements Comparable<Cell> {
    int xPos;
    int yPos;
    int color = 0;
    int activeContours;
    boolean active;
    boolean filled;
    ArrayList<PVector[]> contours;
    boolean[] fillValues;
    boolean border;

    public Cell(int i, int j, boolean[] b) {
        xPos = i;
        yPos = j;
        fillValues = b;
        contours = new ArrayList<>();

        String s = "";
        boolean notFilled = false;
        for (boolean bool : fillValues) {
            if (bool) {
                filled = true;
                s += 1;
            } else {
                notFilled = true;
                s += 0;
            }
        }
        if (filled && notFilled) {
            border = true;
        }
        // System.out.println(s);
        switch (s) {
        case "0000":
            break;
        case "0001":
            contours.add(vec(i + 0.5f, j, i + 1, j + 0.5f));
            break;
        case "0010":
            contours.add(vec(i + 1, j + 0.5f, i + 0.5f, j + 1));
            break;
        case "0011":
            contours.add(vec(i + 0.5f, j, i + 0.5f, j + 1));
            break;
        case "0100":
            contours.add(vec(i, j + 0.5f, i + 0.5f, j + 1));
            break;
        case "0101":
            contours.add(vec(i, j + 0.5f, i + 0.5f, j));
            contours.add(vec(i + 1, j + 0.5f, i + 0.5f, j + 1));
            break;
        case "0110":
            contours.add(vec(i + 1, j + 0.5f, i, j + 0.5f));
            break;
        case "0111":
            contours.add(vec(i + 0.5f, j, i, j + 0.5f));
            break;
        case "1000":
            contours.add(vec(i + 0.5f, j, i, j + 0.5f));
            break;
        case "1001":
            contours.add(vec(i + 1, j + 0.5f, i, j + 0.5f));
            break;
        case "1010":
            contours.add(vec(i, j + 0.5f, i + 0.5f, j + 1));
            contours.add(vec(i + 0.5f, j, i + 1, j + 0.5f));
            break;
        case "1011":
            contours.add(vec(i, j + 0.5f, i + 0.5f, j + 1));
            break;
        case "1100":
            contours.add(vec(i + 0.5f, j, i + 0.5f, j + 1));
            break;
        case "1101":
            contours.add(vec(i + 1, j + 0.5f, i + 0.5f, j + 1));
            break;
        case "1110":
            contours.add(vec(i + 1, j + 0.5f, i + 0.5f, j));
            break;
        case "1111":
            break;
        default:
            if (!(i < 0)) {
                int e = 1 / 0;
            }
            break;
        }

        activeContours = contours.size();
    }

    public void display(PApplet sketch) {
        for (PVector[] p : contours) {
            if (PVector.dist(p[0], p[1]) > 1) {
                System.out.println(contours);
            }
            if (PVector.dist(new PVector(xPos, yPos), new PVector(VectorWars.truMouseX, VectorWars.truMouseY)) < 0.5f) {
                sketch.textSize(1);
                sketch.fill(255);
                sketch.text(Arrays.toString(fillValues), xPos, yPos);
                System.out.println(Arrays.toString(fillValues));
            }
            sketch.stroke(color);
            sketch.line(p[0].x, p[0].y, p[1].x, p[1].y);
            sketch.line(xPos + 1, yPos, xPos, yPos);
            sketch.line(xPos + 1, yPos, xPos + 1, yPos + 1);
            sketch.line(xPos + 1, yPos + 1, xPos, yPos + 1);
            sketch.line(xPos, yPos, xPos, yPos + 1);
            sketch.stroke(0);
        }
    }

    PVector[] vec(float a, float b, float c, float d) {
        PVector p1 = new PVector(a, b);
        PVector p2 = new PVector(c, d);
        if (PVector.dist(p1, p2) > 1.0f) {
            System.out.println("uh oh");
        }
        return new PVector[] { p1, p2 };
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

    public void fillBorders(World world, Set<Cell> nextActive, Map<Integer, Map<Integer, Cell>> grid) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (Math.abs(i) + Math.abs(j) == 1) {
                    Cell c = world.getCell(xPos + i, yPos + j, grid);
                    if (c != null) {
                        if (!c.active && c.border) {
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

    
    public Cell getNextWall(ArrayList<PVector> poses, Map<Integer, Map<Integer, Cell>> grid, World w, int scalar){
        for(int i = -1; i < 2; i++){
            for (int j = -1; j < 2; j++) {
                if(Math.abs(i) + Math.abs(j) == 1){
                    Cell c = w.getCell(xPos + i, yPos + j, grid);
                    if(c != null){
                        PVector p = compareContours(c);
                        if(c.activeContours > 0 && p != null){
                            poses.add(p.mult(scalar));
                            activeContours--;
                            return c;
                        }
                    }
                }
            }
        }
        return null;
    }

    public PVector getBarrierPoint(){
        ArrayList<PVector> points = new ArrayList<>();
        if(fillValues[0]){
            points.add(new PVector(0, 0));
        }
        if(fillValues[1]){
            points.add(new PVector(1, 0));
        }
        if(fillValues[2]){
            points.add(new PVector(1, 1));
        }
        if(fillValues[3]){
            points.add(new PVector(0, 1));
        }
        PVector total = new PVector();
        for(PVector p : points){
            total.add(p);
        }
        total.div(points.size());
        return total.add(new PVector(xPos, yPos));
    }

    private PVector compareContours(Cell cell) {
        for (PVector[] pva : contours) {
            for (PVector p : pva) {
                for (PVector[] cpva : cell.contours) {
                    for (PVector cellp : cpva) {
                        if (p.equals(cellp)) {
                            return p;
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public int compareTo(Cell c) {
        return this.toString().compareTo(c.toString());
    }
}
