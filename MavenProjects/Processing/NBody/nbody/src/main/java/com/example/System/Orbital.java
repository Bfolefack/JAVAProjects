package com.example.System;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.example.Utils.QuadTree;

import processing.core.PApplet;

public class Orbital {
    QuadTree<Planet> planets;
    public Set<Planet> planetSet;
    Planet average;
    public boolean showTree;

    public Orbital(PApplet app, int number) {
        planets = new QuadTree<Planet>(25, Planet.deSpawnDist * 2, Planet.deSpawnDist * 2);
        planets.parent = true;
        planetSet = new HashSet<>();
        for (int i = 0; i < number; i++) {
            planetSet.add(new Planet(app));
        }
    }

    public Orbital(PApplet app, ArrayList<Planet> system) {
        planets = new QuadTree<Planet>(system.size(), Planet.deSpawnDist * 2, Planet.deSpawnDist * 2);
        planetSet = new HashSet<>();
        planets.parent = true;
        for (Planet p : system) {
            planetSet.add(p);
        }
    }

    public Orbital(PApplet app, ArrayList<Planet> system, int c) {
        planets = new QuadTree<Planet>(c, Planet.deSpawnDist * 2, Planet.deSpawnDist * 2);
        planetSet = new HashSet<>();
        planets.parent = true;
        for (Planet p : system) {
            planetSet.add(p);
        }
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
        // System.out.println(planetSet.size());
        Planet.largestPlanet = null;
        for (Planet p : planetSet) {
            Planet.largestPlanet = p;
            break;
        }

        for (Planet p : planetSet) {
            if (p != null && Planet.largestPlanet != null)
                if (p.mass >= Planet.largestPlanet.mass) {
                    Planet.largestPlanet = p;
                }
        }

        planets = new QuadTree<>(planets.capacity, Planet.largestPlanet.pos.x - Planet.deSpawnDist,
                Planet.largestPlanet.pos.y - Planet.deSpawnDist, 2 * Planet.deSpawnDist, 2 * Planet.deSpawnDist, null,
                "");

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
