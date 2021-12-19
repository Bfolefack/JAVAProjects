package com.example.System;

import java.util.HashSet;

import com.example.Utils.QuadTree;

import processing.core.PApplet;

public class Orbital {
    QuadTree<Planet> planets;
    HashSet<Planet> planetSet;

    public Orbital(PApplet app, int number) {
        planets = new QuadTree<Planet>(50, app.width, app.height);
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
        planets.displayTree(app);

    }

    public void update() {
        float minX = Float.MAX_VALUE;
        float minY = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE;
        float maxY = Float.MIN_VALUE;

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

        planets = new QuadTree<>(planets.capacity, minX - 10, minY - 10, maxX - minX + 20, maxY - minY + 20);

        for (Planet p : planetSet) {
            planets.insert(p);
        }
        planets.update();
    }
}
