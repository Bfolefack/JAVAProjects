package com.example.Objects.Mobs;

import java.util.HashSet;
import java.util.Set;

import com.example.Objects.Entity;

import processing.core.PApplet;
import processing.core.PVector;

public class Mob extends Entity {

    PVector vel, acc;
    int randID;
    float maxSpeed, maxForce, friction;

    public Mob(float x, float y, float ms, float mf, float f) {
        super(x, y);
        vel = new PVector(0, 0);
        acc = new PVector(0, 0);
        maxSpeed = ms;
        maxForce = mf;
        friction = f;
        randID = (int) (Math.random() * Integer.MAX_VALUE);
    }

    public Mob(PVector p, float ms, float mf, float f) {
        super(p);
        vel = new PVector(0, 0);
        acc = new PVector(0, 0);
        maxSpeed = ms;
        maxForce = mf;
        friction = f;
    }

    public PVector seek(PVector target) {
        PVector vec = PVector.sub(pos, target);
        vec.normalize();
        return vec;
    }

    public PVector seekSteer(PVector target) {
        PVector desired = PVector.sub(pos, target);
        float d = desired.mag();
        if (d < 20) {
            float m = (d / 20) * maxSpeed;
            desired.setMag(m);
        } else {
            desired.setMag(maxSpeed);
        }
        PVector steer = PVector.sub(desired.mult(-1), vel);
        steer.limit(maxForce);
        return steer;
    }

    public PVector seekVec(Set<PVector> target) {
        PVector total = new PVector();
        for (PVector p : target) {
            total.add(seek(p));
        }
        total.normalize();
        return total;
    }

    public PVector seek(Set<Entity> target) {
        Set<PVector> p = new HashSet<>();
        for(Entity e : target){
            p.add(e.pos);
        }
        return seekVec(p);
    }

    public PVector seekVecSteer(Set<PVector> target) {
        PVector total = new PVector();
        for (PVector p : target) {
            total.add(seekSteer(p));
        }
        if (target.size() > 0) {
            total.div(target.size());
        }
        total.limit(maxForce);
        return total;
    }

    public PVector seekSteer(Set<Entity> target) {
        Set<PVector> p = new HashSet<>();
        for(Entity e : target){
            p.add(e.pos);
        }
        return seekVecSteer(p);
    }

    

    public PVector seekVec(Set<PVector> target, float range) {
        PVector total = new PVector();
        for (PVector p : target) {
            if (PVector.dist(pos, p) < range)
                total.add(seek(p));
        }
        total.normalize();
        return total;
    }

    public PVector seek(Set<Entity> target, float range) {
        Set<PVector> p = new HashSet<>();
        for(Entity e : target){
            p.add(e.pos);
        }
        return seekVecSteer(p, range);
    }

    public PVector seekVecSteer(Set<PVector> target, float range) {
        PVector total = new PVector();
        for (PVector p : target) {
            if (PVector.dist(pos, p) < range)
                total.add(seekSteer(p));
        }
        if (target.size() > 0) {
            total.div(target.size());
        }
        total.limit(maxForce);
        return total;
    }

    public PVector seekSteer(Set<Entity> target, float range) {
        Set<PVector> p = new HashSet<>();
        for(Entity e : target){
            p.add(e.pos);
        }
        return seekVecSteer(p, range);
    }

    public PVector avoid(PVector target) {
        PVector vec = PVector.sub(target, pos);
        vec.normalize();
        return vec;
    }

    public PVector avoidSteer(PVector target) {
        PVector desired = PVector.sub(target, pos);
        float d = desired.mag();
        if (d < 20) {
            float m = (d / 20) * maxSpeed;
            desired.setMag(m);
        } else {
            desired.setMag(maxSpeed);
        }
        PVector steer = PVector.sub(desired.mult(-1), vel);
        steer.limit(maxForce);
        return steer;
    }

    public PVector avoidVec(Set<PVector> target) {
        PVector total = new PVector();
        for (PVector p : target) {
            total.add(avoid(p));
        }
        total.normalize();
        return total;
    }

    public PVector avoid(Set<Entity> target) {
        Set<PVector> p = new HashSet<>();
        for(Entity e : target){
            p.add(e.pos);
        }
        return avoidVec(p);
    }

    public PVector avoidVecSteer(Set<PVector> target) {
        PVector total = new PVector();
        for (PVector p : target) {
            total.add(avoidSteer(p));
        }
        if (target.size() > 0) {
            total.div(target.size());
        }
        total.limit(maxForce);
        return total;
    }

    public PVector avoidSteer(Set<Entity> target) {
        Set<PVector> p = new HashSet<>();
        for(Entity e : target){
            p.add(e.pos);
        }
        return avoidVecSteer(p);
    }

    public PVector avoidVec(Set<PVector> target, float range) {
        PVector total = new PVector();
        for (PVector p : target) {
            if (PVector.dist(pos, p) < range)
                total.add(avoid(p));
        }
        total.normalize();
        return total;
    }

    public PVector avoid(Set<Entity> target, float range) {
        Set<PVector> p = new HashSet<>();
        for(Entity e : target){
            p.add(e.pos);
        }
        return avoidVecSteer(p, range);
    }

    public PVector avoidVecSteer(Set<PVector> target, float range) {
        PVector total = new PVector();
        for (PVector p : target) {
            if (PVector.dist(pos, p) < range)
                total.add(seekSteer(p));
        }
        if (target.size() > 0) {
            total.div(target.size());
        }
        total.limit(maxForce);
        return total;
    }

    public PVector avoidSteer(Set<Entity> target, float range) {
        Set<PVector> p = new HashSet<>();
        for(Entity e : target){
            p.add(e.pos);
        }
        return avoidVecSteer(p, range);
    }

    public PVector wander(PApplet sketch, float jitter, float strength) {
        float f = (sketch.noise(randID, (sketch.frameCount * jitter)) * 2 - 1) * PApplet.TWO_PI;
        PVector vec = new PVector((float) Math.cos(f), (float) Math.sin(f));
        vec.setMag(strength);
        PVector off = PVector.add(pos ,vel.setMag(20));
        vec.add(off);
        // System.out.println(vec);
        // System.out.println(seek(vec));
        // System.out.println();
        return seekSteer(vec);
    }

    protected void update() {
        vel.add(acc);
        vel.limit(maxSpeed);
        pos.add(vel);
        vel.mult(friction);
        acc.set(0, 0);
    }

}
