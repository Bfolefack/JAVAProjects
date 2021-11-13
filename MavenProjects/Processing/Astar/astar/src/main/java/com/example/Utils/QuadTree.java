package com.example.Utils;

import java.util.HashSet;
import java.util.Set;

import com.example.AStar;

import processing.core.PVector;

import static com.example.Utils.QuadTree.Direction.LEFT;
import static com.example.Utils.QuadTree.Direction.RIGHT;
import static com.example.Utils.QuadTree.Direction.UP;
import static com.example.Utils.QuadTree.Direction.DOWN;

public class QuadTree {

    public static int count = 0;
    int capacity;
    int x;
    int y;
    int w;
    int h;
    float value;
    QuadTree parentTree;
    QuadTree[] subdivisions;
    float[][] grid;
    boolean parent;
    boolean divided;

    public QuadTree(float[][] g) {
        x = 0;
        y = 0;
        grid = g.clone();
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g[0].length; j++) {
                if(g[i][j] < 0.4){
                    g[i][j] = 1;
                } else {
                    g[i][j] += 1;
                }
            }
        }
        w = grid.length;
        h = grid[0].length;
        subdivide();
        parent = true;
    }

    private QuadTree(float[][] g, int x_, int y_, int corn1, int corn2, QuadTree pt) {
        count++;
        x = x_;
        y = y_;
        w = g.length/2;
        h = g[0].length/2;
        parentTree = pt;
        grid = new float[g.length/2][g[0].length/2];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                try {
                    grid[i][j] = g[i + corn1 * w][j + corn2 * h];
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void display(AStar sketch) {
        sketch.strokeWeight(w / 100f);
        sketch.rect(x, y, w, h);
        if (divided)
            for (QuadTree qt : subdivisions) {
                qt.display(sketch);
            }
    }

    public float getValue() {
        if (!divided) {
            return value;
        }
        return -1;
    }

    private boolean contains(PVector pos) {
        if (pos.x > x && pos.x < x + w && pos.y > y && pos.y < y + h) {
            return true;
        }
        return false;
    }

    private void subdivide() {
        float avg = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                avg += grid[i][j];
            }
        }
        avg/=(grid.length * grid[0].length);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if(Math.abs(avg - grid[i][j]) > 0.02){
                    subdivisions = new QuadTree[4];
                    // northwest
                    subdivisions[0] = new QuadTree(grid, x, y, 0, 0, this);
                    // northeast
                    subdivisions[1] = new QuadTree(grid, x + (w / 2), y, 1, 0, this);
                    // southwest
                    subdivisions[2] = new QuadTree(grid, x, y + (h / 2), 0, 1, this);
                    // southeast
                    subdivisions[3] = new QuadTree(grid, x + (w / 2), y + (h / 2), 1, 1, this);
                    value = -1;
                    divided = true;
                    for(QuadTree qt : subdivisions){
                        qt.subdivide();
                    }
                    return;
                }
            }
        }
        value = avg; 
        divided = false;
    }

    public boolean overlaps(PVector pos, float r) {
        float Xn = Math.max(x, Math.min(pos.x, x + w));
        float Yn = Math.max(y, Math.min(pos.y, y + h));
        float Dx = Xn - pos.x;
        float Dy = Yn - pos.y;
        return (Dx * Dx + Dy * Dy) <= r * r;
    }

    public boolean overlaps(int i, int j) {
        if (i > x && i < x + w && j > y && j < y + h) {
            return true;
        }
        return false;
    }

    public float[][] getAll() {
        if (parent) {
            return grid;
        }
        return null;
    }

    
    public enum Direction{
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    public class DoubleDir implements Comparable<DoubleDir> {
        Direction dir1;
        Direction dir2;

        DoubleDir(Direction d1, Direction d2){
            dir1 = d1;
            dir2 = d2;
        }

        @Override
        public int compareTo(DoubleDir dd){
            if((dd.dir1 == dir1 && dd.dir2 == dd.dir2) || (dd.dir2 == dir1 && dd.dir1 == dd.dir2)){
                return 0;
            }
            return -1;
        }

        public boolean contains(Direction d){
            if(dir1 == d || dir2 == d){
                return true;
            }
            return false;
        }

    }
}
