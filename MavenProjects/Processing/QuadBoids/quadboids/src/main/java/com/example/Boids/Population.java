package com.example.Boids;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.example.QuadBoids;
import com.example.Algorithms.QuadTree;
import processing.core.PVector;

public class Population {
    public Set<Boid> boids;
    QuadBoids sketch;
    QuadTree qt;
    Map<String, HashSet<Boid>> map;
    public int method = 0;
    int boidSize = 5;
    public boolean showTree;

    public Population(QuadBoids q) {
        sketch = q;
        qt = new QuadTree(4, sketch.width, sketch.height);
        boids = new HashSet<>();
    }

    public Set<Boid> getBoidNeighbors(float x, float y, float r) {
        Set<Boid> neighbors = new HashSet<>();
        // BruteForce: O(n^2)
        if (method == 0)
            for (Boid b : boids) {
                float d = PVector.dist(new PVector(x, y), b.pos);
                if (d <= r && d > 0)
                    neighbors.add(b);
            }

        // QuadTree: O(n*log(n))
        if (method == 1)
            return qt.getBoids(new PVector(x, y), r);

        // SpatialHashing
        if (method == 2) {
            PVector hash = new PVector((int) (x / (boidSize * 5)), (int) (y / (boidSize * 5)));
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    Set<Boid> temp = map.get((hash.x + i) + "," + (hash.y + j));
                    if (temp != null) {
                        neighbors.addAll(temp);
                    }
                }
            }
        }
        return neighbors;
    }

    public void update() {
        if (method == 1) {
            qt = new QuadTree(1, sketch.width, sketch.height);

            for (Boid b : boids) {
                qt.insert(b);
            }
        }

        if (method == 2) {
            map = new HashMap<>();
            for (Boid b : boids) {
                if (map.get(b.hash.x + "," + b.hash.y) != null) {
                    map.get(b.hash.x + "," + b.hash.y).add(b);
                } else {
                    map.put(b.hash.x + "," + b.hash.y, new HashSet<Boid>());
                    map.get(b.hash.x + "," + b.hash.y).add(b);
                }
            }
        }

        for (Boid b : boids) {
            b.update(sketch);
        }
    }

    public void display() {
        sketch.noFill();
        sketch.stroke(0);
        if (showTree)
            qt.display(sketch);
        // sketch.ellipse(sketch.mouseX, sketch.mouseY, 200, 200);
        sketch.noStroke();
        for (Boid b : boids) {
            b.display(sketch);
        }
    }

    public void addBoid(float x, float y, int f) {
        boids.add(new Boid(x, y, f, this, boidSize));
    }
}
