package com.example.System;

import java.util.HashSet;
import java.util.Set;

import com.example.Utils.QuadTree;

import processing.core.PApplet;

public class Orbital {
    QuadTree<Planet> planets;
    Set<Planet> planetSet;
    Planet average;
    public boolean showTree;

    public Orbital(PApplet app, int number) {
        planets = new QuadTree<Planet>(25, app.width, app.height);
        planets.parent = true;
        planetSet = new HashSet<>();
        for (int i = 0; i < number; i++) {
            planetSet.add(new Planet(app));
        }
        update();
    }

    public void display(PApplet app) {
        app.noStroke();
        app.fill(255);
        planets.display(app);
        app.stroke(0, 255, 0);
        app.noFill();
        if (showTree)
            planets.displayTree(app);

    }

    public void update() {
        System.out.println(planetSet.size());
        float minX = Integer.MAX_VALUE;
        float minY = Integer.MAX_VALUE;
        float maxX = Integer.MIN_VALUE;
        float maxY = Integer.MIN_VALUE;

        planetSet.remove(null);
        for (Planet p : planetSet) {
            if (p.pos.x < minX)
                minX = p.pos.x;
            if (p.pos.y < minY)
                minY = p.pos.y;
            if (p.pos.x > maxX)
                maxX = p.pos.x;
            if (p.pos.y > maxY)
                maxY = p.pos.y;
        }

        planets = new QuadTree<>(planets.capacity, minX - 1, minY - 1, maxX - minX + 2, maxY - minY + 2, null, "");

        QuadTree.bigTree = planets;
        planets.parent = true;
        for (Planet p : planetSet) {
            planets.insert(p);
        }
        planets.setRepresentative();
        planets.getAffectingGravitationalBodies();
        planetSet = planets.update();
    }
}
