package com.example.Utils;

import java.util.HashSet;
import java.util.Set;

import com.example.VectorWars;
import com.example.Entities.Entity;

import processing.core.PVector;

public class QuadTree {
    int capacity;
    float x;
    float y;
    float w;
    float h;
    Set<Entity> entities;
    QuadTree[] subdivisions;
    boolean divided;

    public QuadTree(int c, float w_, float h_) {
        capacity = c;
        entities = new HashSet<>();
        x = 0;
        y = 0;
        w = w_;
        h = h_;
    }

    private QuadTree(int c, float x_, float y_, float w_, float h_) {
        capacity = c;
        entities = new HashSet<>();
        x = x_;
        y = y_;
        w = w_;
        h = h_;
    }

    public void display(VectorWars sketch) {
        sketch.strokeWeight(w / 100);
        // if(overlaps(new PVector(sketch.mouseX, sketch.mouseY), 100)){
        // sketch.stroke(0, 255, 0);
        // } else {
        // sketch.stroke(0);
        // }
        sketch.rect(x, y, w, h);
        if (divided)
            for (QuadTree qt : subdivisions) {
                qt.display(sketch);
            }
    }

    public Set<Entity> getEntities(PVector pos, float r) {
        Set<Entity> total = new HashSet<Entity>();
        if (overlaps(pos, r)) {
            for (Entity b : entities) {
                float d = PVector.dist(b.pos, pos);
                if (d < r && d > 0) {
                    total.add(b);
                }
            }
            if (divided) {
                for (QuadTree qt : subdivisions) {
                    total.addAll(qt.getEntities(pos, r));
                }
            }
        }
        return total;
    }

    public void insert(Entity b) {
        if (!contains(b.pos)) {
            return;
        }
        if (entities.size() < capacity) {
            entities.add(b);
            return;
        } else if (!divided) {
            subdivide();
            divided = true;
        }
        for (QuadTree qt : subdivisions) {
            qt.insert(b);
        }
    }

    private boolean contains(PVector pos) {
        if (pos.x > x && pos.x < x + w && pos.y > y && pos.y < y + h) {
            return true;
        }
        return false;
    }

    private void subdivide() {
        divided = true;
        subdivisions = new QuadTree[4];
        // northwest
        subdivisions[0] = new QuadTree(capacity, x, y, w / 2, h / 2);
        // northeast
        subdivisions[1] = new QuadTree(capacity, x + (w / 2), y, w / 2, h / 2);
        // southwest
        subdivisions[2] = new QuadTree(capacity, x, y + h / 2, w / 2, h / 2);
        // southeast
        subdivisions[3] = new QuadTree(capacity, x + (w / 2), y + (h / 2), w / 2, h / 2);
    }

    public boolean overlaps(PVector pos, float r) {
        float Xn = Math.max(x, Math.min(pos.x, x + w));
        float Yn = Math.max(y, Math.min(pos.y, y + h));
        float Dx = Xn - pos.x;
        float Dy = Yn - pos.y;
        return (Dx * Dx + Dy * Dy) <= r * r;
    }
}
