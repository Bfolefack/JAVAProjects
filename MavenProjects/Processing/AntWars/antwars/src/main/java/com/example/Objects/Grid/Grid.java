package com.example.Objects.Grid;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.example.AntWars;

import processing.core.PConstants;
import processing.core.PImage;

public class Grid {
    Map<Integer, Map<Integer, Cell>> grid;
    PImage image;
    int gridWidth;
    int gridHeight;
    AntWars sketch;

    Grid(int x, int y, AntWars s, float d, float sc) {
        gridWidth = x;
        gridHeight = y;
        sketch = s;
        grid = new HashMap<Integer, Map<Integer, Cell>>();
        // for (int i = 0; i < gridWidth; i++) {
        //     grid.put(i, new HashMap<Integer, Cell>());
        //     for (int j = 0; j < 100; j++) {
        //         grid.get(i).put(j, new Cell(i, j));
        //     }
        // }
        image = sketch.createImage(gridWidth, gridHeight, PConstants.RGB);
        image.loadPixels();
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                float noise = sketch.noise(i * sc, j * sc, (i * sc * 0.5f + j * sc * 0.5f));
                if (noise < d) {
                    // if (noise(i * sc, j * sc) < 0.45 || noise(i * sc, j * sc) > 0.65) {
                    grid.get(i).put(j, new Cell(i, j, CellStates.EMPTY));
                } else {
                    grid.get(i).put(j, new Cell(i, j, CellStates.FILLED));
                    if (noise > 0.625
                            && sketch.noise(i * sc * 8, j * sc * 8, (i * sc * 0.5 * 8 + j * sc * 0.5 * 8)) < 0.4) {
                        grid.get(i).put(j, new Cell(i, j, CellStates.FOOD));
                    }
                }
            }
        }
        buildCave();
        Set<Set<Cell>> caverns = new TreeSet<Set<Cell>>();
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                if (grid.get(i).get(j).state != CellStates.FILLED && grid.get(i).get(j).ID == 0) {
                    floodFill(i, j);
                }
            }
        }
        Set<Cell> biggest = new TreeSet<Cell>());
        for(Set<Cell>  cs: caverns){
            cs.
        }
        for (int i = 0; i < caverns.size(); i++) {
            if (caverns.get(i).size() > biggest.size()) {
                biggest = caverns.get(i);
            }
        }
        for (int i = 0; i < caverns.size(); i++) {
            if (caverns.get(i) != biggest) {
                for (Cell c : caverns.get(i)) {
                    c.state = CellStates.FILLED;
                }
            }
        }
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                grid[i][j].update();
            }
        }
    }

    void display() {
        if (frameCount % shutterSpeed == 0 && ants.size() > 1 && saveTimelapse) {
            map.save(timeLapseID + "/" + (frameCount / shutterSpeed) + ".jpg");
        }
        float slice = (frameCount % shutterSpeed) / (shutterSpeed * 1.0);
        int count = (int) (slice * map.pixels.length);
        for (int i = (int) (slice * gridHeight); i < (int) (slice * gridHeight) + gridHeight / shutterSpeed; i++) {
            for (int j = 0; j < gridWidth; j++) {
                grid[j][i].display();
                map.pixels[count] = grid[j][i].currColor;
                count++;
            }
        }
        map.updatePixels();
        if (showFarm) {
            image(map, 0, 0, gridWidth * gridScale, gridHeight * gridScale);
        }

        // for (int i = 0; i < gridWidth/20; i++) {
        // for (int j = 0; j < gridHeight/20; j++) {
        // chunks[i][j].clear();
        // }
        // }
    }

    void update() {
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                if (grid[i][j].active) {
                    grid[i][j].update();
                }
            }
        }
    }

    void floodFill(int x, int y) {
        ArrayList<Cell> active = new ArrayList<Cell>();
        ArrayList<Cell> nextActive = new ArrayList<Cell>();
        ArrayList<Cell> cavern = new ArrayList<Cell>();
        int id = (int) random(1, Integer.MAX_VALUE);
        active.add(getCell(x, y));
        while (active.size() > 0) {
            nextActive = new ArrayList<Cell>();
            for (Cell c : active) {
                c.floodFill(this, nextActive, id);
                cavern.add(c);
            }
            active = (ArrayList) nextActive.clone();
        }
        caverns.add(cavern);
    }

    void buildCave() {
        Random randy = new Random(seed);
        ArrayList<Cell> tempCells = new ArrayList<Cell>();
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                tempCells.add(grid[i][j]);
            }
        }
        Collections.shuffle(tempCells, randy);
        for (int i = 0; i < 30; i++) {
            println(i);
            for (Cell c : tempCells) {
                c.buildCave(this);
            }
        }
        for (int i = 0; i < 10; i++) {
            println(i);
            for (Cell c : tempCells) {
                c.smoothWalls(this);
            }
        }
    }

    Cell getCell(float x_, float y_) {
        int x = (int) x_;
        int y = (int) y_;
        if (x >= 0 && y >= 0 && x < gridWidth && y < gridHeight) {
            return grid[x][y];
        }
        return null;
    }
}
