package com.example.Game.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.example.Game.Entities.Bullet;
import com.example.Game.Entities.EntityLib.Entity;

import processing.core.PApplet;
import processing.core.PVector;

public class QuadTree<E extends Entity> {
    public static QuadTree<Entity> bigTree;

    public int capacity;

    private float x;
    private float y;
    private float w;
    private float h;

    public String corner;

    Set<E> entities;
    Set<Entity> affectingPlanets;
    ArrayList<QuadTree<E>> subdivisions;
    public boolean parent;
    boolean divided;

    QuadTree<E> parentTree;

    private Entity representative;

    public QuadTree(int c, float x_, float y_, float w_, float h_) {
        capacity = c;
        entities = new HashSet<>();
        x = x_;
        y = y_;
        w = w_;
        h = h_;
        parent = true;
    }

    public QuadTree(int c, float x_, float y_, float w_, float h_, QuadTree<E> pt, String cor) {
        capacity = c;
        entities = new HashSet<>();
        x = x_;
        y = y_;
        w = w_;
        h = h_;
        parentTree = pt;
        corner = cor;
    }

    public void displayTree(PApplet sketch) {
        sketch.pushStyle();
        // sketch.fill((int) (Math.random() * 255));
        sketch.noFill();
        sketch.stroke(255);
        sketch.strokeWeight(10);
        sketch.rect(x, y, w, h);
        // sketch.line(x, y, x + w, y);
        // sketch.line(x + w, y, x + w, y + h);
        // sketch.line(x + w, y + h, x, y + h);
        // sketch.line(x, y + h, x, y);
        // if (!divided) {
        //     sketch.fill(255, 0, 0);
        //     if(representative != null)
        //     representative.display(sketch);
        // }
        if (divided)
            for (QuadTree<E> qt : subdivisions) {
                qt.displayTree(sketch);
            }
        sketch.popStyle();
    }

    public void display(PApplet p) {
        for (E e : entities) {
            e.display(p);
        }
        if (divided)
            for (QuadTree<E> qt : subdivisions) {
                qt.display(p);
            }
    }

    public Set<Entity> update() {
        Set<Entity> out = new HashSet<>();
        for (E e : entities) {
            e.update();
        }
        if (divided)
            for (QuadTree<E> qt : subdivisions) {
                out.addAll(qt.update());
            }
        return out;
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

    public boolean insert(E b) {
        if(b == null)
            return false;
        if (!contains(b.pos)) {
            return false;
        }
        if (entities.size() < capacity && !divided) {
            entities.add(b);
            return true;
        } else if (!divided) {
            divided = true;
            subdivide();
            return insert(b);
        } else {
            for (QuadTree<E> qt : subdivisions) {
                if (qt.insert(b)) {
                    return true;
                }
            }
        }
        return false;
    }

    // public boolean remove(E e) {
    // Boolean checkMerge = false;
    // if (entities.remove(e)) {
    // return true;
    // } else if (divided) {
    // for (QuadTree<E> qt : subdivisions) {
    // checkMerge = qt.remove(e);
    // if (checkMerge) {
    // break;
    // }
    // }
    // // if (checkMerge) {
    // // boolean merge = false;
    // // for (QuadTree<E> qt : subdivisions) {
    // // if (!qt.checkFilled()) {
    // // merge = true;
    // // }
    // // }
    // // if (merge) {
    // // subdivisions = null;
    // // divided = false;
    // // }
    // // }
    // }
    // return false;
    // }

    private boolean checkFilled() {
        if (entities.size() > 0) {
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
        if (pos.x >= x && pos.x <= x + w && pos.y >= y && pos.y <= y + h) {
            return true;
        }
        return false;
    }

    private void subdivide() {
        divided = true;
        subdivisions = new ArrayList<>(4);
        // northwest
        subdivisions.add(new QuadTree<E>(capacity, x, y, w / 2, h / 2, this, "00"));
        // northeast
        subdivisions.add(new QuadTree<E>(capacity, x + (w / 2), y, w / 2, h / 2, this, "01"));
        // southwest
        subdivisions.add(new QuadTree<E>(capacity, x, y + h / 2, w / 2, h / 2, this, "10"));
        // southeast
        subdivisions.add(new QuadTree<E>(capacity, x + (w / 2), y + (h / 2), w / 2, h / 2, this, "11"));
        for (E e : entities) {
            insert(e);
        }
        entities.clear();
    }

    public boolean overlaps(PVector pos, float r) {
        float Xn = Math.max(x, Math.min(pos.x, x + w));
        float Yn = Math.max(y, Math.min(pos.y, y + h));
        float Dx = Xn - pos.x;
        float Dy = Yn - pos.y;
        return (Dx * Dx + Dy * Dy) <= r * r;
    }

    public Set<E> getAll() {
        HashSet<E> out = new HashSet<>();
        out.addAll(getAll(out));
        return out;
    }

    public Set<E> getAll(Set<E> e) {
        e.addAll(entities);
        if (divided) {
            for (QuadTree<E> q : subdivisions) {
                q.getAll(e);
            }
        }
        return e;
    }

    public void insertAll(Collection<? extends E> eSet) {
        for(E e : eSet){
            insert(e);
        }
    }
}