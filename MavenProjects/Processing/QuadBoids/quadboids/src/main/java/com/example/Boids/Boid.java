package com.example.Boids;

import java.util.Set;

import com.example.QuadBoids;

import processing.core.PApplet;
import processing.core.PVector;

public class Boid implements Comparable<Boid>{
    public PVector pos;
    PVector vel;
    PVector acc;
    public PVector hash;
    Population population;
    float viewRadius;
    int flock;
    int sketchWidth, sketchHeight;
    float size;
    float randID;
    float FOV = 0f;

    public Boid(float x, float y, int f, Population pop, int s) {
        pos = new PVector(x, y);
        hash = new PVector((int) (pos.x/20), (int) (pos.y/20));
        vel = new PVector();
        acc = new PVector();
        size = s;
        viewRadius = size * 5;
        flock = f;
        population = pop;
        sketchWidth = population.sketch.width;
        sketchHeight = population.sketch.height;
        randID = (float) (Math.random() * 1000000);
    }

    private Set<Boid> getBoidNeighbors() {
        return population.getBoidNeighbors(pos.x, pos.y, viewRadius);
    }

    public void display(QuadBoids qb) {
        // System.out.println(pos);
        qb.fill(toColor(flock, qb));
        qb.ellipse(pos.x, pos.y, size, size);
    }

    int toColor(int col, QuadBoids qb) {

        return qb.color((col / 1000000) * 0.255f, ((col % 1000000) / 1000) * 0.255f, (col % (flock / 1000)) * 0.255f);
    }

    public void update(QuadBoids qb) {
        Set<Boid> neighbors = getBoidNeighbors();
        neighbors.remove(this);
        if(population.method == 1){
            if(neighbors.size() > 20 && viewRadius > 2){
                viewRadius -= (size * 0.1);
            } else if(viewRadius < size * 5){
                viewRadius += (size * 0.1);
            }
        } else {
            viewRadius = size * 5;
        }
        acc.add(direction(neighbors).mult(size));
        acc.add(separation(neighbors).mult(size * 2f));
        acc.add(attraction(neighbors).mult(size));
        if (acc.mag() == 0) {
            float f = (qb.noise(randID, (qb.frameCount * 0.05f)) * 2 - 1) * PApplet.TWO_PI;
            acc = new PVector((float) Math.cos(f), (float) Math.sin(f));
            acc.setMag(size/2);
        }
        vel.add(acc);
        vel.limit(size/2);
        pos.add(vel);
        acc.set(0, 0);
        vel.mult(0.95f);
        if (pos.x > sketchWidth) {
            pos.x = 0;
        }
        if (pos.y > sketchHeight) {
            pos.y = 0;
        }
        if (pos.x < 0) {
            pos.x = sketchWidth;
        }
        if (pos.y < 0) {
            pos.y = sketchHeight;
        }
        hash = new PVector((int) (pos.x/(viewRadius)), (int) (pos.y/(viewRadius)));
    }

    private PVector attraction(Set<Boid> neighbors) {
        if (neighbors.size() > 0) {
            PVector total = new PVector();
            for (Boid b : neighbors) {
                if (b.flock == flock && checkAngle(b.pos) > FOV){
                    if(PVector.dist(b.pos, pos) < viewRadius)
                    total.add(PVector.sub(b.pos, pos).div(PVector.dist(pos, b.pos)));
                }
            }
            if(total.mag() > 0){
                total.div(neighbors.size());
                return total.normalize();
            }
        }
        return new PVector();
    }

    private PVector separation(Set<Boid> neighbors) {
        if (neighbors.size() > 0) {
            PVector total = new PVector();
            for (Boid b : neighbors) {
                if(PVector.dist(pos, b.pos) < size * 1.5)
                    total.add(PVector.sub(pos, b.pos).div((float)PVector.dist(pos, b.pos)));
            }
            if(total.mag() > 0){
                total.div(neighbors.size());
                return total.normalize();
            }
        }
        return new PVector();
    }

    private PVector direction(Set<Boid> neighbors) {
        if (neighbors.size() > 0) {
            PVector total = new PVector();
            for (Boid b : neighbors) {
                if (b.flock == flock  && checkAngle(b.pos) > FOV)
                    if(PVector.dist(b.pos, pos) < viewRadius)
                        total.add(b.vel);
            }
            total.div(neighbors.size());
            total.normalize();
            return total;
        }
        return new PVector();
    }

    private float checkAngle(PVector pos2){
        PVector diff = PVector.sub(pos2, pos);
        PVector tempVel = vel;
        diff.normalize();
        tempVel.normalize();
        return PVector.dot(diff, tempVel);
    }

    @Override
    public int compareTo(Boid o) {
        // TODO Auto-generated method stub
        return toString().compareTo(o.toString());
    }
}
