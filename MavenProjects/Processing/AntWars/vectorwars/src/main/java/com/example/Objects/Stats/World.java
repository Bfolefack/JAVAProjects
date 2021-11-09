package com.example.Objects.Stats;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.example.VectorWars;
import com.example.Objects.Entity;
import com.example.Utils.QuadTree;

import processing.core.PConstants;
import processing.core.PVector;
import processing.core.PApplet;

public class World {
    VectorWars sketch;
    public int width;
    int height;
    public float scale;
    QuadTree Barriers;
    public boolean flipped;
    // TODO: Remove This
    ArrayList<PVector> cave = new ArrayList<>();
    Set<ArrayList<PVector>> blocks = new HashSet<ArrayList<PVector>>();
    Map<Integer, Map<Integer, Cell>> bigGrid;
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
                boolean[] b = new boolean[4];
                for (int k = 0; k < 2; k++) {
                    for (int l = 0; l < 2; l++) {
                        float off = 0;
                        if (PVector.dist(new PVector(width/2, height/2), new PVector(i, j)) > (width/2f) - (width / 20f)) {
                            off = ((PVector.dist(new PVector(width/2, height/2), new PVector(i, j)) - ((width/2f) - (width / 20f)))/(width/20f));
                            off = PApplet.map(off, 0, 1, 0, 0.85f);
                        }
                        float noise = sketch.noise((i + k) * 0.025f, (j + l) * 0.025f,
                                ((i + k) * 0.025f * 0.5f + (j + l) * 0.025f * 0.5f));
                        // 00 = TL
                        // 10 = TR
                        // 01 = BL
                        // 11 = BR
                        if (noise < 0.45f + off) {
                            switch (k + "" + l) {
                            case "00":
                                b[0] = true;
                                break;
                            case "10":
                                b[1] = true;
                                break;
                            case "11":
                                b[2] = true;
                                break;
                            case "01":
                                b[3] = true;
                                break;
                            }
                        }
                    }
                }
                grid.get(i).put(j, new Cell(i, j, b));
            }
        }

        buildCave(grid);

        for (Map<Integer, Cell> m : grid.values()) {
            for (Cell c : m.values()) {
                c.display(sketch);
            }
        }
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
                cell.fillValues = new boolean[] { true, true, true, true };
            }
        }

        Map<Integer, Map<Integer, Cell>> newGrid = new HashMap<>();
        for (int i = 0; i < width - 1; i++) {
            newGrid.put(i, new HashMap<Integer, Cell>());
            for (int j = 0; j < height - 1; j++) {
                newGrid.get(i).put(j,
                        new Cell(i, j, new boolean[] { getCell(i, j, grid).filled, getCell(i, j + 1, grid).filled,
                                getCell(i + 1, j + 1, grid).filled, getCell(i + 1, j, grid).filled }));
            }
        }
        grid = newGrid;
        width--;
        height--;

        ArrayList<Set<Cell>> walls = new ArrayList<Set<Cell>>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (getCell(i, j, grid).border && !getCell(i, j, grid).active) {
                    borderFill(i, j, walls, grid);
                }
            }
        }

        Set<Set<Cell>> temp = new HashSet<>();
        for (Set<Cell> c : walls) {
            if (c.size() < 30) {
                temp.add(c);
            }
        }
        walls.removeAll(temp);

        Set<Cell> biggestWall = new HashSet<Cell>();
        for (Set<Cell> c : walls) {
            if (c.size() > biggestWall.size()) {
                biggestWall = c;
            }
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                getCell(i, j, grid).active = false;
            }
        }

        Set<Barrier> barrierPoints = new HashSet<>();
        // for (int i = 0; i < width; i++) {
        //     for (int j = 0; j < height; j++) {
        //         barrierPoints.add(new Barrier(getCell(i, j, grid).getBarrierPoint().mult(10)));
        //     }
        // }

        cave = checkBorders(biggestWall, barrierPoints, grid);

        blocks = new HashSet<>();
        for (Set<Cell> c : walls) {
            blocks.add(checkBorders(c, barrierPoints, grid));
        }
        bigGrid = grid;
        Barriers = new QuadTree(2, width * scale, height * scale);
        for(Barrier b : barrierPoints){
            Barriers.insert(b);
        }
        for (PVector p : cave) {
            Barriers.insert(new Barrier(p.x, p.y));
        }
        for (ArrayList<PVector> pv : blocks) {
            for (PVector p : pv) {
                Barriers.insert(new Barrier(p.x, p.y));
            }
        }
    }

    private ArrayList<PVector> checkBorders(Set<Cell> cells, Set<Barrier> barrierPoints, Map<Integer, Map<Integer, Cell>> grid) {
        Set<Cell> wall = new HashSet<Cell>();
        for (Cell c : cells) {
            if (c.border) {
                wall.add(c);
                // barrierPoints.add(new Barrier(c.getBarrierPoint().mult(scale)));
            }
        }

        Cell cell = null;
        for (Cell c : wall) {
            if (c.contours.size() >= 1) {
                cell = c;
                break;
            }
        }
        int color = sketch.color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
        Cell nextCell;
        ArrayList<PVector> poses = new ArrayList<>();
        while (cell != null) {
            cell.color = color;
            nextCell = cell.getNextWall(poses, grid, this, (int) scale);
            cell = nextCell;
        }

        return poses;
    }

    void floodFill(int x, int y, ArrayList<Set<Cell>> caverns, Map<Integer, Map<Integer, Cell>> grid) {
        Set<Cell> active = new HashSet<Cell>();
        Set<Cell> nextActive = new HashSet<Cell>();
        Set<Cell> cavern = new HashSet<Cell>();
        active.add(getCell(x, y, grid));
        while (active.size() > 0) {
            for (Cell c : active) {
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

    void borderFill(int x, int y, ArrayList<Set<Cell>> caverns, Map<Integer, Map<Integer, Cell>> grid) {
        Set<Cell> active = new HashSet<Cell>();
        Set<Cell> nextActive = new HashSet<Cell>();
        Set<Cell> cavern = new HashSet<Cell>();
        active.add(getCell(x, y, grid));
        while (active.size() > 0) {
            for (Cell c : active) {
                c.active = true;
            }
            nextActive = new HashSet<Cell>();
            for (Cell c : active) {
                c.fillBorders(this, nextActive, grid);
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

    public Cell getCell(int x, int y, Map<Integer, Map<Integer, Cell>> grid) {
        if (grid.get((int) x) != null)
            return grid.get((int) x).get((int) y);
        return null;
    }

    public Set<Entity> getBarriers(PVector p, float r) {
        return Barriers.getEntities(p, r);
    }

    public void display() {
        sketch.noStroke();
        sketch.fill(0);
        sketch.beginShape();
        if (flipped) {
            sketch.vertex(0 * scale, 0 * scale);
            sketch.vertex(width * scale, 0 * scale);
            sketch.vertex(width * scale, height * scale);
            sketch.vertex(0 * scale, height * scale);
        } else {
            sketch.vertex(0 * scale, height * scale);
            sketch.vertex(width * scale, height * scale);
            sketch.vertex(width * scale, 0 * scale);
            sketch.vertex(0 * scale, 0 * scale);
        }

        sketch.beginContour();
        for (PVector p : cave) {
            sketch.vertex(p.x, p.y);
        }
        sketch.endContour();
        sketch.endShape(PConstants.CLOSE);

        for (ArrayList<PVector> pvecs : blocks) {
            sketch.beginShape();
            for (PVector p : pvecs) {
                sketch.vertex(p.x, p.y);
            }
            sketch.endShape(PConstants.CLOSE);
        }

        sketch.fill(255, 0, 0);
        // for(Entity e : Barriers.getAll(new HashSet<Entity>())){
        //     Barrier b = (Barrier) e;
        //     if(b.highlight){
        //         b.display(sketch);
        //     }
        // }

        // sketch.stroke(255);
        // sketch.noFill();
        // Barriers.display(sketch);
    }
}
