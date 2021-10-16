package com.example.Objects.Grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.example.AntWars;
import com.example.Objects.Agents.Ant;

import processing.core.PConstants;
import processing.core.PImage;

public class Grid {
    Map<Integer, Map<Integer, Cell>> grid;
    Set<Ant> ants;
    Set<Cell> activeCells;
    Set<Cell> nextActiveCells;
    PImage image;
    int gridWidth;
    int gridHeight;
    int seed;
    AntWars sketch;
    private int shutterSpeed;
    private boolean saveTimelapse;
    private boolean showFarm;
    private int gridScale;

    public Grid(int x, int y, AntWars s, float d, float sc) {

        shutterSpeed = 20;
        showFarm = true;
        gridScale = 10;
        nextActiveCells = new HashSet<>();

        gridWidth = x;
        gridHeight = y;
        sketch = s;
        grid = new HashMap<Integer, Map<Integer, Cell>>();
        // for (int i = 0; i < gridWidth; i++) {
        // grid.put(i, new HashMap<Integer, Cell>());
        // for (int j = 0; j < 100; j++) {
        // grid.get(i).put(j, new Cell(i, j));
        // }
        // }
        seed = (int) (Math.random() * Integer.MAX_VALUE);
        image = sketch.createImage(gridWidth, gridHeight, PConstants.RGB);
        image.loadPixels();
        for (int i = 0; i < gridWidth; i++) {
            grid.put(i, new HashMap<Integer, Cell>());
            for (int j = 0; j < gridHeight; j++) {
                float noise = sketch.noise(i * sc, j * sc, (i * sc * 0.5f + j * sc * 0.5f));
                if (noise < d) {
                    // if (noise(i * sc, j * sc) < 0.45 || noise(i * sc, j * sc) > 0.65) {
                    grid.get(i).put(j, new Cell(i, j, CellStates.FILLED));
                } else {
                    grid.get(i).put(j, new Cell(i, j, CellStates.EMPTY));
                    if (noise > d + 0.15 && sketch.noise(i * sc * 8, j * sc * 8, (i * sc * 0.5f * 8 + j * sc * 0.5f * 8)) < 0.37f) {
                        grid.get(i).put(j, new Cell(i, j, CellStates.FOOD));
                    }
                }
            }
        }
        buildCave();
        ArrayList<Set<Cell>> caverns = new ArrayList<Set<Cell>>();
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                if (getCell(i, j).state != CellStates.FILLED && getCell(i, j).ID == 0) {
                    floodFill(i, j, caverns);
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
                cell.state = CellStates.FILLED;
            }
        }
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                getCell(i, j).update(sketch, nextActiveCells);
            }
        }
    }

    public void display() {
        if (sketch.frameCount % shutterSpeed == 0 /* && ants.size() > 1 */ && saveTimelapse) {
            image.save(1 + "/" + (sketch.frameCount / shutterSpeed) + ".jpg");
        }
        float slice = (sketch.frameCount % shutterSpeed) / (shutterSpeed * 1.0f);
        int count = (int) (slice * image.pixels.length);
        for (int i = (int) (slice * gridHeight); i < (int) (slice * gridHeight) + gridHeight / shutterSpeed; i++) {
            for (int j = 0; j < gridWidth; j++) {
                getCell(j, i).display();
                image.pixels[count] = getCell(j, i).currColor;
                count++;
            }
        }
        image.updatePixels();
        if (showFarm) {
            sketch.image(image, 0, 0, gridWidth * gridScale, gridHeight * gridScale);
        }
    }

    public void update() {
        activeCells = nextActiveCells;
        nextActiveCells = new HashSet<>();
        for(Cell c : activeCells){
            c.update(sketch, nextActiveCells);
        }
    }

    void floodFill(int x, int y, ArrayList<Set<Cell>> caverns) {
        Set<Cell> active = new HashSet<Cell>();
        Set<Cell> nextActive = new HashSet<Cell>();
        Set<Cell> cavern = new HashSet<Cell>();
        int id = (int) (Math.random() * Integer.MAX_VALUE);
        active.add(getCell(x, y));
        while (active.size() > 0) {
            nextActive = new HashSet<Cell>();
            for (Cell c : active) {
                c.floodFill(this, nextActive, id);
                cavern.add(c);
            }
            active = nextActive;
        }
        caverns.add(cavern);
    }

    void buildCave() {
        // Random randy = new Random(seed);
        Set<Cell> tempCells = new HashSet<Cell>();
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                tempCells.add(getCell(i, j));
            }
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            for (Cell c : tempCells) {
                c.buildCave(this);
            }
        }
        for (int i = 0; i < 5; i++) {
            System.out.println(i);
            for (Cell c : tempCells) {
                c.smoothWalls(this);
            }
        }
    }

    Cell getCell(float x_, float y_) {
        if (grid.get((int) x_) != null)
            return grid.get((int) x_).get((int) y_);
        return null;
    }
}
