package com.example.System;

import java.util.ArrayList;
import java.util.Set;

import com.example.NBody;

import processing.core.PApplet;
import processing.core.PVector;

public class Planet extends Entity {
    public PVector vel, acc;
    public float mass;
    public float size;
    public static final float maxInitVel = 0;
    public static final float startRange = 1;
    // public static final float deSpawnDist = 20000;
    public static final float deSpawnDist = 2000000000;
    public static Planet largestPlanet;
    boolean collided;

    public Planet(PApplet app) {
        // super((float) Math.random() * 10, (float) Math.random() * 10);
        super((float) (Math.random() * app.width * startRange), (float) (Math.random() * app.height * startRange));
        vel = new PVector((float) Math.random() * maxInitVel - maxInitVel / 2,
                (float) Math.random() * maxInitVel - maxInitVel / 2);
        acc = new PVector();
        mass = (float) (Math.random() * Math.random() * 50) + 10;
        size = getSize();
        if (largestPlanet == null)
            largestPlanet = this;
    }

    public Planet(float x, float y, float m) {
        super(x, y);
        vel = new PVector();
        acc = new PVector();
        mass = m;
        size = getSize();
    }

    public Planet(float posX, float posY, float velX, float velY, float m) {
        super(posX, posY);
        vel = new PVector(velX * NBody.simulationSpeed, velY * NBody.simulationSpeed);
        acc = new PVector();
        mass = m;
        size = getSize();
    }

    public Planet(Planet planet) {
        super(planet.pos);
        mass = planet.mass;
        vel = planet.vel.copy();
        acc = new PVector();
        size = getSize();
    }

    public Planet(PVector pos, PVector vel, float f) {
        super(pos);
        this.vel = vel;
        mass = f;
        size = getSize();
    }

    public Planet(float x, float y, float m, boolean b) {
        super(x, y);
        vel = new PVector();
        acc = new PVector();
        mass = m;
        size = getSize();
    }

    public float getSize() {
        float f = (float) Math.pow(mass, 0.5) * 1000;
        if (f > 1000) {
            f = 1000;
        }
        return f;
    }

    public void display(PApplet app) {
        app.ellipse(pos.x, pos.y, size, size);
    }

    public Planet update(Set<Planet> bodies) {
        if (!collided && largestPlanet != null) {
            if (PVector.dist(pos, largestPlanet.pos) > deSpawnDist) {
                return null;
            }
            ArrayList<Planet> colliders = new ArrayList<>();
            Planet newPlanet = new Planet(this);
            for (Planet p : bodies) {
                if (p != null)
                    if (!p.collided)
                        if (!(p.pos.equals(newPlanet.pos))) {
                            float dist = (PVector.dist(newPlanet.pos, p.pos));
                            if (dist < size / 2 + p.size / 2 && !p.collided) {
                                if (mass > p.mass){
                                    //return collide(this, p);
                                }

                            }
                            if (colliders.size() < 1) {
                                dist = (dist < 10 ? 10 : dist);
                                newPlanet.acc.add(PVector.sub(p.pos, newPlanet.pos)
                                        .setMag(((NBody.G * newPlanet.mass * p.mass)
                                                / (dist * dist))
                                                / newPlanet.mass));
                            }
                        }
            }
            if (colliders.size() > 0) {
                //return collide(colliders);
            }
            newPlanet.vel.add(newPlanet.acc);
            newPlanet.pos.add(newPlanet.vel);
            return newPlanet;
        }
        return this;
    }

    public Planet collide(ArrayList<Planet> colliders) {
        Planet out = new Planet(this);
        for (Planet planet : colliders) {
            planet.collided = true;
            out = collide(out, planet);
        }
        return out;
    }

    public static Planet collide(Planet p1, Planet p2) {
        Planet out = new Planet(PVector.add(p1.pos.mult(p1.mass), p2.pos.mult(p2.mass)).div(p1.mass + p2.mass),
                PVector.add(p1.vel.mult(p1.mass), p2.vel.mult(p2.mass)).div(p1.mass + p2.mass), p1.mass + p2.mass);
        return out;
    }
}
