package com.example.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.example.System.Entity;

import processing.core.PApplet;
import processing.core.PVector;

public class QuadTree<E extends Entity> {
    public int capacity;
    private float x;
    private float y;
    private float w;
    private float h;

    Set<E> entities;
    ArrayList<QuadTree<E>> subdivisions;
    public boolean parent;
    boolean divided;

    public QuadTree(int c, float w_, float h_) {
        capacity = c;
        entities = new HashSet<>();
        x = 0;
        y = 0;
        w = w_;
        h = h_;
        parent = true;
    }

    public QuadTree(int c, float x_, float y_, float w_, float h_) {
        capacity = c;
        entities = new HashSet<>();
        x = x_;
        y = y_;
        w = w_;
        h = h_;
    }

    public void displayTree(PApplet sketch) {
        sketch.strokeWeight(w / 100);
        sketch.rect(x, y, w, h);
        if (divided)
            for (QuadTree<E> qt : subdivisions) {
                qt.displayTree(sketch);
            }
    }

    public void display(PApplet p) {
        for(E e : entities){
            e.display(p);
        }
        if(divided)
            for(QuadTree<E> qt : subdivisions){
                qt.display(p);
            }
    }


    public void update() {
        for(E e : entities){
            e.update();
        }
        if(divided)
            for(QuadTree<E> qt : subdivisions){
                qt.update();
            }
    }

    public Set<E> getEntities(PVector pos, float r) {
        Set<E> total = new HashSet<E>();
        if (overlaps(pos, r)) {
            for (E b : entities) {
                float d = PVector.dist(b.pos, pos);
                if (d < r && d > 0) {
                    total.add(b);
                }
            }
            if (divided) {
                for (QuadTree<E> qt : subdivisions) {
                    total.addAll(qt.getEntities(pos, r));
                }
            }
        }
        return total;
    }

    public void insert(E b) {
        if (!contains(b.pos)) {
            return;
        }
        if (entities.size() < capacity) {
            entities.add(b);
        } else if (!divided) {
            subdivide();
            divided = true;
        } else {
            for (QuadTree<E> qt : subdivisions) {
                qt.insert(b);
            }
        }
    }

    public boolean remove(E e) {
        Boolean checkMerge = false;
        if (entities.remove(e)) {
            return true;
        } else if (divided) {
            for (QuadTree<E> qt : subdivisions) {
                checkMerge = qt.remove(e);
                if (checkMerge) {
                    break;
                }
            }
            // if (checkMerge) {
            //     boolean merge = false;
            //     for (QuadTree<E> qt : subdivisions) {
            //         if (!qt.checkFilled()) {
            //             merge = true;
            //         }
            //     }
            //     if (merge) {
            //         subdivisions = null;
            //         divided = false;
            //     }
            // }
        }
        return false;
    }

    private boolean checkFilled() {
        if(entities.size() > 0){
            return true;
        }
        if (divided) {
            for (QuadTree<E> qt : subdivisions) {
                if (!qt.checkFilled()) {
                    return false;
                }
            }
        }
        return false;
    }

    private boolean contains(PVector pos) {
        if (pos.x > x && pos.x < x + w && pos.y > y && pos.y < y + h) {
            return true;
        }
        return false;
    }

    private void subdivide() {
        divided = true;
        subdivisions= new ArrayList<>(4);
        // northwest
        subdivisions.add(new QuadTree<E>(capacity, x, y, w / 2, h / 2));
        // northeast
        subdivisions.add(new QuadTree<E>(capacity, x + (w / 2), y, w / 2, h / 2));
        // southwest
        subdivisions.add(new QuadTree<E>(capacity, x, y + h / 2, w / 2, h / 2));
        // southeast
        subdivisions.add(new QuadTree<E>(capacity, x + (w / 2), y + (h / 2), w / 2, h / 2));
    }

    public boolean overlaps(PVector pos, float r) {
        float Xn = Math.max(x, Math.min(pos.x, x + w));
        float Yn = Math.max(y, Math.min(pos.y, y + h));
        float Dx = Xn - pos.x;
        float Dy = Yn - pos.y;
        return (Dx * Dx + Dy * Dy) <= r * r;
    }

    public Set<E> getAll(Set<E> e) {
        e.addAll(entities);
        if (divided) {
            for (QuadTree<E> q : subdivisions) {
                q.getAll(e);
            }
        }
        if (parent)
            return e;
        else
            return null;
    }
}
