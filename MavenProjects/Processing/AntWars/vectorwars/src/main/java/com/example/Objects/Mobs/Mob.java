package com.example.Objects.Mobs;

import java.util.HashSet;
import java.util.Set;

import com.example.Objects.Entity;

import processing.core.PApplet;
import processing.core.PVector;

public class Mob extends Entity {

    PVector vel, acc;
    int randID;
    float wanderDir;
    float maxSpeed, maxForce, friction;

    protected Mob(float x, float y, float ms, float mf, float f) {
        super(x, y);
        vel = new PVector(0, 0);
        acc = new PVector(0, 0);
        maxSpeed = ms;
        maxForce = mf;
        friction = f;
        randID = (int) (Math.random() * Integer.MAX_VALUE);
        wanderDir = (float) Math.random() * PApplet.TWO_PI;
    }

    protected Mob(PVector p, float ms, float mf, float f) {
        super(p);
        vel = new PVector(0, 0);
        acc = new PVector(0, 0);
        maxSpeed = ms;
        maxForce = mf;
        friction = f;
        wanderDir = (float) Math.random() * PApplet.TWO_PI;
    }

    protected PVector seek(PVector target) {
        PVector vec = PVector.sub(pos, target);
        vec.normalize();
        return vec;
    }

    protected PVector seekSteer(PVector target) {
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

    protected PVector seekVec(Set<PVector> target) {
        PVector total = new PVector();
        for (PVector p : target) {
            total.add(seek(p));
        }
        total.normalize();
        return total;
    }

    protected PVector seek(Set<? extends Entity> target) {
        Set<PVector> p = new HashSet<>();
        for(Entity e : target){
            p.add(e.pos);
        }
        return seekVec(p);
    }

    protected PVector seekVecSteer(Set<PVector> target) {
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

    protected PVector seekSteer(Set<? extends Entity> target) {
        Set<PVector> p = new HashSet<>();
        for(Entity e : target){
            p.add(e.pos);
        }
        return seekVecSteer(p);
    }

    

    protected PVector seekVec(Set<PVector> target, float range) {
        PVector total = new PVector();
        for (PVector p : target) {
            if (PVector.dist(pos, p) < range)
                total.add(seek(p));
        }
        total.normalize();
        return total;
    }

    protected PVector seek(Set<? extends Entity> target, float range) {
        Set<PVector> p = new HashSet<>();
        for(Entity e : target){
            p.add(e.pos);
        }
        return seekVecSteer(p, range);
    }

    protected PVector seekVecSteer(Set<PVector> target, float range) {
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

    protected PVector seekSteer(Set<? extends Entity> target, float range) {
        Set<PVector> p = new HashSet<>();
        for(Entity e : target){
            p.add(e.pos);
        }
        return seekVecSteer(p, range);
    }

    protected PVector avoid(PVector target) {
        PVector vec = PVector.sub(target, pos);
        vec.normalize();
        return vec;
    }

    protected PVector avoidSteer(PVector target) {
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

    protected PVector avoidVec(Set<PVector> target) {
        PVector total = new PVector();
        for (PVector p : target) {
            total.add(avoid(p));
        }
        total.normalize();
        return total;
    }

    protected PVector avoid(Set<? extends Entity> target) {
        Set<PVector> p = new HashSet<>();
        for(Entity e : target){
            p.add(e.pos);
        }
        return avoidVec(p);
    }

    protected PVector avoidVecSteer(Set<PVector> target) {
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

    protected PVector avoidSteer(Set<? extends Entity> target) {
        Set<PVector> p = new HashSet<>();
        for(Entity e : target){
            p.add(e.pos);
        }
        return avoidVecSteer(p);
    }

    protected PVector avoidVec(Set<PVector> target, float range) {
        PVector total = new PVector();
        for (PVector p : target) {
            if (PVector.dist(pos, p) < range)
                total.add(avoid(p));
        }
        total.normalize();
        return total;
    }

    protected PVector avoid(Set<? extends Entity> target, float range) {
        Set<PVector> p = new HashSet<>();
        for(Entity e : target){
            p.add(e.pos);
        }
        return avoidVecSteer(p, range);
    }

    protected PVector avoidVecSteer(Set<PVector> target, float range) {
        PVector total = new PVector();
        for (PVector p : target) {
            if (PVector.dist(pos, p) < range)
                total.add(avoidSteer(p));
        }
        if (target.size() > 0) {
            total.div(target.size());
        }
        total.limit(maxForce);
        return total;
    }

    protected PVector avoidSteer(Set<? extends Entity> target, float range) {
        Set<PVector> p = new HashSet<>();
        for(Entity e : target){
            p.add(e.pos);
        }
        return avoidVecSteer(p, range);
    }

    protected PVector wander(PApplet sketch, float jitter, float strength) {
        float f = (sketch.noise(randID, (sketch.frameCount * jitter)) * 2 - 1) * 0.1f;
        wanderDir += f;
        PVector vec = new PVector((float) Math.cos(wanderDir), (float) Math.sin(wanderDir));
        // vec.add(new PVector(vel.x, vel.y).setMag(maxSpeed));
        vec.setMag(maxSpeed);
        return seek(vec.setMag(maxSpeed).add(pos)).setMag(maxSpeed);
    }

    protected Set<? extends Entity> withinAngle(Set<? extends Entity> in, float f){
        Set<Entity> out = new HashSet<Entity>();
        for(Entity e : in){
            if(getAngle(e.pos) > f){
                out.add(e);
            }
        }
        return out;
    }

    protected float getAngle(PVector pos2) {
        PVector diff = PVector.sub(pos2, pos);
        PVector tempVel = vel;
        diff.normalize();
        tempVel.normalize();
        return PVector.dot(diff, tempVel);
    }

    @Override
    public void update() {
        vel.add(acc);
        vel.limit(maxSpeed);
        pos.add(vel);
        vel.mult(friction);
        acc.set(0, 0);
    }

}