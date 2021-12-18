package com.example;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.example.Objects.Mobs.Ant;
import com.example.Objects.Mobs.Colony;
import com.example.Objects.Stats.World;
import com.example.Utils.Zoomer;

import processing.core.PApplet;
import processing.event.MouseEvent;

public class VectorWars extends PApplet {

    public static int truMouseX;
    public static int truMouseY;
    public static final String boundKeys = "rRfFkKt,.";
    public World world;
    // TODO: Delete this
    Set<Ant> ants = new HashSet<>();
    Map<Character, Colony> colonies = new HashMap<>();
    private boolean displayTree;

    public static void main(String[] args) {
        String[] processingArgs = { "MySketch" };
        VectorWars mySketch = new VectorWars();
        PApplet.runSketch(processingArgs, mySketch);
    }

    public void settings() {
        size(800, 800);
    }

    public void setup() {
        fill(0);
        // noStroke();
        frameRate(144);
    }

    public void draw() {
        if (frameCount == 2) {
            onLoad();
        } else if (frameCount > 2) {
            background(125);
            Zoomer.mousePan();
            Zoomer.pushZoom();
            noStroke();
            world.display();
            if (keyPressed) {
                if (boundKeys.indexOf(key) == -1)
                    if (colonies.get(key) != null) {
                        colonies.get(key).addAnt(truMouseX, truMouseY);
                    } else {
                        colonies.put(key, new Colony(truMouseX, truMouseY, (int) (Math.random() * 1000000000), world));
                    }
            }
            
            for (Colony c : colonies.values()) {
                c.display(this);
                c.update(this);
            }
            if (displayTree) {
                stroke(255);
                noFill();
                world.barriers.displayTree(this);
            }
            Zoomer.popZoom();
            text(frameRate, 0, 20);
        }
    }

    private void onLoad() {
        world = new World(this, 1000, 1000, 20);
        Zoomer.initialize(width / (world.width * world.scale), this);
    }

    public void mouseWheel(MouseEvent e) {
        Zoomer.mouseScale(e, 0.05f);
    }

    public void keyPressed() {
        switch (key) {
        case 'F':
        case 'f':
            world.flipped = !world.flipped;
            break;
        case 'R':
        case 'r':
            Zoomer.initialize(width / (world.width * world.scale), this);
            break;
        case ',':
            if (frameRate > 60) {
                frameRate(60 * 0.8f);
            } else {
                frameRate(frameRate * 0.8f);
            }
            break;
        case '.':
            if (frameRate > 60) {
                frameRate(60 * 1.2f);
            } else {
                frameRate(frameRate * 1.2f);
            }
            break;
        case 't':
            displayTree = !displayTree;
            break;
        }
    }

}
