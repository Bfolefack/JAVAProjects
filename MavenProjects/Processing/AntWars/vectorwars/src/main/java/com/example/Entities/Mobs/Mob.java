package com.example.Entities.Mobs;

import java.util.Set;

import com.example.Entities.Entity;

import processing.core.PVector;

public class Mob extends Entity {

    PVector vel, acc;
    float maxSpeed, friction;

    public Mob(float x, float y, float ms, float f) {
        super(x, y);
        vel = new PVector(0, 0);
        acc = new PVector(0, 0);
        maxSpeed = ms;
        friction = f;
    }

    public Mob(PVector p, float ms, float f) {
        super(p);
        vel = new PVector(0, 0);
        acc = new PVector(0, 0);
        maxSpeed = ms;
        friction = f;
    }

    public PVector seek(PVector target) {
        PVector vec = PVector.sub(pos, target);
        vec.normalize();
        return vec;
    }

    public PVector seek(Set<PVector> target) {
        PVector total = new PVector();
        for (PVector p : target) {
            total.add(seek(p));
        }
        total.normalize();
        return total;
    }

    public PVector seek(Set<PVector> target, float range) {
        PVector total = new PVector();
        for (PVector p : target) {
            if (PVector.dist(pos, p) < range)
                total.add(seek(p));
        }
        total.normalize();
        return total;
    }

    public PVector avoid(PVector target) {
        PVector vec = PVector.sub(target, pos);
        vec.normalize();
        return vec;
    }

    public PVector avoid(Set<PVector> target) {
        PVector total = new PVector();
        for (PVector p : target) {
            total.add(avoid(p));
        }
        total.normalize();
        return total;
    }

    public PVector avoid(Set<PVector> target, float range) {
        PVector total = new PVector();
        for (PVector p : target) {
            if (PVector.dist(pos, p) < range)
                total.add(avoid(p));
        }
        total.normalize();
        return total;
    }

    public void update() {
        vel.add(acc);
        vel.limit(maxSpeed);
        pos.add(vel);
        vel.mult(friction);
        acc.set(0, 0);
    }

}
