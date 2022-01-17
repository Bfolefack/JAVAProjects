package com.example.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import com.example.System.Entity;
import com.example.System.Planet;

import processing.core.PApplet;
import processing.core.PVector;

public class QuadTree<E extends Planet> {
    public static QuadTree<Planet> bigTree;

    public int capacity;

    private float x;
    private float y;
    private float w;
    private float h;

    public String corner;

    Set<E> entities;
    Set<Planet> affectingPlanets;
    ArrayList<QuadTree<E>> subdivisions;
    public boolean parent;
    boolean divided;

    QuadTree<E> parentTree;

    private Planet representative;

    public QuadTree(int c, float w_, float h_) {
        capacity = c;
        entities = new HashSet<>();
        x = 0;
        y = 0;
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
        sketch.noFill();
        sketch.strokeWeight(w/10000);
        sketch.pushMatrix();
        sketch.scale(100);
        sketch.rect(x/100, y/100, w/100, h/100);
        sketch.popMatrix();
        // sketch.line(x, y, x + w, y);
        // sketch.line(x + w, y, x + w, y + h);
        // sketch.line(x + w, y + h, x, y + h);
        // sketch.line(x, y + h, x, y);
        if (!divided) {
            sketch.fill(255, 0, 0);
            if(representative != null)
            representative.display(sketch);
        }
        if (divided)
            for (QuadTree<E> qt : subdivisions) {
                qt.displayTree(sketch);
            }
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

    public Set<Planet> update() {
        Set<Planet> out = new HashSet<>();
        for (E e : entities) {
            out.add(e.update(affectingPlanets));
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

    public Set<? extends Planet> getPlanets(float x, float y, float width) {
        if (contains(new PVector(x, y))) {
            if (!divided) {
                return entities;
            } else if (w <= width) {
                HashSet<E> out = new HashSet<>(entities);
                for (QuadTree<E> qt : subdivisions) {
                    qt.getAll(out);
                }
                return out;
            }
        }
        return null;
    }

    public Set<? extends Planet> getRepresentativePlanets(float x, float y, float width, float height) {
        HashSet<PVector> vecSet = new HashSet<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                vecSet.add(new PVector(x + width / 2 + width * i, y + height / 2 + height * j));
            }
        }
        HashSet<Planet> reps = new HashSet<>();
        if (divided)
            for (QuadTree<E> qt : subdivisions) {
                qt.getRepresentativePlanets(reps, vecSet, width);
            }
        return reps;
    }

    public void getRepresentativePlanets(Set<Planet> plan, HashSet<PVector> vecSet, float width) {
        boolean contains = false;
        if (!divided) {
            for (PVector p : vecSet) {
                if (contains(p)) {
                    contains = true;
                }
            }
            if (!contains) {
                plan.add(representative);
            }
            return;
        } else if (w <= width) {
            for (PVector p : vecSet) {
                if (contains(p)) {
                    contains = true;
                }
            }
            if (contains) {
                return;
            }
        }
        for (QuadTree<E> qt : subdivisions) {
            qt.getRepresentativePlanets(plan, vecSet, width);
        }
    }

    public void setRepresentative() {
        if (!divided) {
            float mass = 0;
            float x = 0;
            float y = 0;
            for (Planet p : entities) {
                mass += p.mass;
                x += p.pos.x * p.mass;
                y += p.pos.y * p.mass;
            }
            x /= mass;
            y /= mass;
            if (mass > 0)
                representative = new Planet(x, y, mass, true);
        } else {
            for (QuadTree<E> qt : subdivisions) {
                qt.setRepresentative();
            }
        }
    }

    public void getAffectingGravitationalBodies() {
        if (!divided) {
            affectingPlanets = new HashSet<>();
            affectingPlanets.addAll(entities);
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    getPlanets(x + w / 2 + w * i, y + h / 2 + h * i, w);
                }
            }
            affectingPlanets.addAll(bigTree.getRepresentativePlanets(x, y, w, h));
        } else {
            for (QuadTree<E> qt : subdivisions) {
                qt.getAffectingGravitationalBodies();
            }
        }
    }
}