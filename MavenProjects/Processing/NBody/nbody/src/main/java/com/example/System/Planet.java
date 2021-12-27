package com.example.System;

import java.util.Set;

import com.example.NBody;

import processing.core.PApplet;
import processing.core.PVector;

public class Planet extends Entity {
    public PVector vel, acc;
    public float mass;
    public static final float maxInitVel = 50;
    public static final float startRange = 5;
    boolean collided;

    public Planet(PApplet app) {
        // super((float) Math.random() * 10, (float) Math.random() * 10);
        super((float) (Math.random() * app.width * startRange), (float) (Math.random() * app.height * startRange));
        vel = new PVector((float) Math.random() * maxInitVel - maxInitVel / 2,
                (float) Math.random() * maxInitVel - maxInitVel / 2);
        acc = new PVector();
        mass = (float) (Math.random() * Math.random() * 20);
    }

    public Planet(float x, float y, float m) {
        super(x, y);
        vel = new PVector();
        acc = new PVector();
        mass = m;
    }

    public Planet(Planet planet) {
        super(planet.pos);
        mass = planet.mass;
        vel = planet.vel.copy();
        acc = new PVector();
    }

    public Planet(PVector pos, PVector vel, float f) {
        super(pos);
        this.vel = vel;
        mass = f;
    }

    public void display(PApplet app) {
        app.ellipse(pos.x, pos.y, mass / 2, mass / 2);
    }

    public Planet update(Set<Planet> bodies) {
        if (!collided) {
            Planet newPlanet = new Planet(this);
            for (Planet p : bodies) {
                if (p != null)
                    if (!(p.pos.equals(newPlanet.pos))) {
                        float dist = (PVector.dist(newPlanet.pos, p.pos));
                        if (dist < newPlanet.mass / 2 + p.mass / 2 && !p.collided) {
                            p.collided = true;
                            collided = true;
                            return collide(newPlanet, p);
                        }
                        dist = (dist < 10 ? 10 : dist);
                        newPlanet.acc.add(PVector.sub(p.pos, newPlanet.pos)
                                .setMag(((NBody.gravitationalConstant * newPlanet.mass * p.mass) / (dist * dist))
                                        / newPlanet.mass));
                    }
            }
            newPlanet.vel.add(newPlanet.acc);
            newPlanet.pos.add(newPlanet.vel);
            return newPlanet;
        }
        return null;
    }

    public static Planet collide(Planet p1, Planet p2){
        Planet out = new Planet(PVector.add(p1.pos.mult(p1.mass), p2.pos.mult(p2.mass)).div(p1.mass + p2.mass), PVector.add(p1.vel.mult(p1.mass), p2.vel.mult(p2.mass)).div(p1.mass + p2.mass), p1.mass + p2.mass);
        return out;
    }
}
