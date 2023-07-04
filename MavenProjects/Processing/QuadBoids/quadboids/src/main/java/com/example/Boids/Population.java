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
    public QuadBoids sketch;
    private QuadTree qt;
    private Map<String, HashSet<Boid>> map;
    public int method = 0;
    private int boidSize = 10;
    private int hashSize;
    public boolean showTree;

    public float prevAvgFrameRate;
    public float avgFrameRate;
    public float trend;
    public int frameSampleInterval;

    public Population(QuadBoids q) {
        sketch = q;
        qt = new QuadTree(4, sketch.width, sketch.height);
        boids = new HashSet<>();
        hashSize = boidSize * 5;

        frameSampleInterval = 30;
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
            PVector hash = new PVector((int) (x / (hashSize)), (int) (y / (hashSize)));
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
                b.getHash(hashSize);
                Set<Boid> get = map.get(b.hash.x + "," + b.hash.y);
                if (get != null) {
                    get.add(b);
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
        if (showTree) {
            if (method == 1) {
                qt.display(sketch);
            } else if (method == 2) {
                for (int i = 0; i < (sketch.width > sketch.height ? sketch.width : sketch.height); i += hashSize) {
                    sketch.line(0, i, sketch.width, i);
                    sketch.line(i, 0, i, sketch.height);
                }
            }
        }
        // sketch.ellipse(sketch.mouseX, sketch.mouseY, 200, 200);
        sketch.noStroke();
        for (Boid b : boids) {
            b.display(sketch);
        }
        avgFrameRate += sketch.frameRate;
        if (method == 2){
            if (sketch.frameCount % frameSampleInterval == 0) {
                if (avgFrameRate > prevAvgFrameRate) {
                    if (trend > 0) {
                        trend += (Math.abs(hashSize) > 1 ? hashSize * 0.1 : 0.1);
                    } else {
                        trend -= (Math.abs(hashSize) > 1 ? hashSize * 0.1 : 0.1);;
                    }
                } else {
                    if (trend > 0) {
                        trend -= (Math.abs(hashSize) > 1 ? hashSize * 0.1 : 0.1);;
                    } else {
                        trend += (Math.abs(hashSize) > 1 ? hashSize * 0.1 : 0.1);;
                    }
                }
                hashSize += trend;
                if(hashSize < boidSize * 5){
                    hashSize = boidSize * 5;
                }
                prevAvgFrameRate = avgFrameRate;
                avgFrameRate = 0;
            }
        } else {
            trend = 0;
            avgFrameRate = 0;
            prevAvgFrameRate = 0;
        }
    }

    public void addBoid(float x, float y, int f, boolean predator) {
        boids.add(new Boid(x, y, f, this, boidSize, predator));
    }
}
