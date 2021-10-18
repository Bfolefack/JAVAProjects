package com.example.Objects.Stats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


import com.example.VectorWars;
import com.example.Objects.Entity;
import com.example.Utils.QuadTree;

import processing.core.PConstants;
import processing.core.PVector;

public class World {
    VectorWars sketch;
    int width;
    int height;
    float scale;
    QuadTree Barriers;
    ArrayList<PVector> cave = new ArrayList<>();
    Set<ArrayList<PVector>> blocks = new HashSet<ArrayList<PVector>>();
    Set<Barrier> walls = new HashSet<Barrier>();

    public World(VectorWars vw, int w, int h, float s) {
        width = w;
        height = h;
        sketch = vw;
        scale = s; 
        Map<Integer, Map<Integer, Cell>> grid = new HashMap<Integer, Map<Integer, Cell>>();
        for (int i = 0; i < width; i++) {
            grid.put(i, new HashMap<Integer, Cell>());
            for (int j = 0; j < height; j++) {
                float off = 0;
                if(PVector.dist(new PVector(width/2, height/2), new PVector(i, j)) > 450){
                    // System.out.println(PVector.dist(new PVector(width/2, height/2), new PVector(i, j)));
                    off = (PVector.dist(new PVector(width/2, height/2), new PVector(i, j)) - 450)/(100f);
                }
                float noise = sketch.noise(i * 0.025f, j * 0.025f, (i * 0.025f * 0.5f + j * 0.025f * 0.5f));
                if (noise < 0.45f + off) {
                    grid.get(i).put(j, new Cell(i, j, true));
                } else {
                    grid.get(i).put(j, new Cell(i, j, false));
                }
            }
        }
        buildCave(grid);
        ArrayList<Set<Cell>> caverns = new ArrayList<Set<Cell>>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (!getCell(i, j, grid).filled && !getCell(i, j, grid).active) {
                    floodFill(i, j, caverns, grid);
                }
            }
        }
        Set<Cell> biggest = new HashSet<Cell>();
        for (Set<Cell> cs : caverns) {
            if (cs.size() > biggest.size()) {
                biggest = cs;
            }
        }
        caverns.remove(biggest);
        for (Set<Cell> cs : caverns) {
            for (Cell cell : cs) {
                cell.filled = true;
            }
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                getCell(i, j, grid).active = false;
            }
        }

        caverns = new ArrayList<Set<Cell>>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (getCell(i, j, grid).filled && !getCell(i, j, grid).active) {
                    floodFill(i, j, caverns, grid);
                }
            }
        }

        Set<Set<Cell>> temp = new HashSet<>();
        Set<Cell> tempCave = null;
        for(Set<Cell> c : caverns){
            if(c.size() < 10){
                temp.add(c);
            }
            if(c.contains(getCell(0, 0, grid))){
                tempCave = c;
                temp.add(c);
            }
        }
        caverns.removeAll(temp);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                getCell(i, j, grid).active = false;
            }
        }

        cave = checkBorders(tempCave, grid);

        blocks = new HashSet<>();
        for(Set<Cell> c : caverns){
            blocks.add(checkBorders(c, grid));
        }
        Barriers = new QuadTree(4, width * scale, height * scale);
        for(PVector p : cave){
            Barriers.insert(new Barrier(p.x, p.y));
        }
        for(ArrayList<PVector> pv : blocks){
            for (PVector p : pv){
                Barriers.insert(new Barrier(p.x, p.y));
            }
        }
    }

    private ArrayList<PVector> checkBorders(Set<Cell> cells, Map<Integer, Map<Integer, Cell>> grid) {
        Set<Cell> wall = new HashSet<Cell>();
        for(Cell c : cells){
            if(c.checkBorders(this, grid)){
                wall.add(c);
            }
        }

        Cell cell = null;
        for(Cell c : wall){
            c.active = true;
        }
        for(Cell c : wall){
            cell = c;
            break;
        }
        int id= 0;
        Cell nextCell = new Cell(-1, -1, false);
        Set<Cell> orderedWall = new TreeSet<>();
        while(nextCell != null){
            cell.index = id;
            orderedWall.add(cell);
            nextCell = cell.getBorder(id, cell, this, grid);
            cell = nextCell;
            id++;
        }

        for(Cell c : wall){
            c.active = false;
        }

        ArrayList<PVector> p = new ArrayList<>();
        for(Cell c : orderedWall){
            p.add(new PVector(c.xPos * scale, c.yPos * scale));
        }

        return p;
    }

    void floodFill(int x, int y, ArrayList<Set<Cell>> caverns, Map<Integer, Map<Integer, Cell>> grid) {
        Set<Cell> active = new HashSet<Cell>();
        Set<Cell> nextActive = new HashSet<Cell>();
        Set<Cell> cavern = new HashSet<Cell>();
        active.add(getCell(x, y, grid));
        while (active.size() > 0) {
            for(Cell c : active){
                c.active = true;
            }
            nextActive = new HashSet<Cell>();
            for (Cell c : active) {
                c.floodFill(this, nextActive, grid);
                cavern.add(c);
            }
            active = nextActive;
        }
        caverns.add(cavern);
    }

    void buildCave(Map<Integer, Map<Integer, Cell>> grid) {
        // Random randy = new Random(seed);
        Set<Cell> tempCells = new HashSet<Cell>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tempCells.add(getCell(i, j, grid));
            }
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            for (Cell c : tempCells) {
                c.buildCave(this, grid);
            }
        }
        for (int i = 0; i < 5; i++) {
            System.out.println(i);
            for (Cell c : tempCells) {
                c.smoothWalls(this, grid);
            }
        }

        for (int i = 0; i < 2; i++) {
            System.out.println(i);
            for (Cell c : tempCells) {
                c.chopWalls(this, grid);
            }
        }
    }

    public Cell getCell(int x, int y, Map<Integer, Map<Integer, Cell>> grid){
        if (grid.get((int) x) != null)
            return grid.get((int) x).get((int) y);
        return null;
    }

    public Set<Entity> getBarriers(PVector p, float r){
        return Barriers.getEntities(p, r);
    }

    public void display() {
        sketch.noStroke();
        sketch.fill(0);
        sketch.beginShape();
        sketch.vertex(0 * scale, 1000 * scale);
        sketch.vertex(1000 * scale, 1000 * scale);
        sketch.vertex(1000 * scale, 0 * scale);
        sketch.vertex(0 * scale, 0 * scale);
        sketch.beginContour();
        for(PVector p : cave){
            sketch.vertex(p.x, p.y);
        }
        sketch.endContour();
        sketch.endShape(PConstants.CLOSE);
        
        for(ArrayList<PVector> pvecs : blocks){
            sketch.beginShape();
            for(PVector p : pvecs){
                sketch.vertex(p.x, p.y);
            }
            sketch.endShape(PConstants.CLOSE);
        }
        
        sketch.stroke(255);
        sketch.noFill();
        // Barriers.display(sketch);        
    }
}
