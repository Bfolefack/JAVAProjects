package com.example;

import processing.core.PApplet;
import processing.event.MouseEvent;

import java.util.HashSet;
import java.util.Set;

import com.example.Objects.Agents.*;
import com.example.Objects.Grid.Grid;
import com.example.Utils.Zoomer;

/**
 * Hello world!
 *
 */
public class AntWars extends PApplet {
    Zoomer zoomer;
    public static int truMouseX;
    public static int truMouseY;
    public int seed;
    public int gridScale = 1;
    private Set<Colony> colonies;
    private Grid farm;
    private Ant followerAnt;
    private boolean showAnts;
    public static void main(String[] args) {
        String[] processingArgs = { "MySketch" };
        AntWars mySketch = new AntWars();
        PApplet.runSketch(processingArgs, mySketch);
    }

    public void settings() {
        size(1000, 1000);
    }

    public void setup() {
        frameRate(1000);
        noiseDetail(4, (float) 0.5);
        frameRate(144);
        if (seed == 0) {
            seed = (int) random(Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        colonies = new HashSet<Colony>();
        randomSeed(seed);
        noiseSeed(seed);
        farm = new Grid(1000, 1000, this,  0.45f, 0.025f);
        noStroke();
        zoomer = new Zoomer(1.0f / gridScale, this);
    }

    public void draw() {
        background(155);
        text(frameRate, 0, 10);
        zoomer.pushZoom();
        zoomer.mousePan();
        farm.display();
        farm.update();
        for(Colony c : colonies){
            if(showAnts)
                c.display(this);
            c.update(this, farm);
        }
        if (followerAnt != null) {
            zoomer.xPan = followerAnt.pos.x * zoomer.scale;
            zoomer.yPan = followerAnt.pos.y * zoomer.scale;
        }
        zoomer.popZoom();
    }

    public void mouseWheel(MouseEvent event) {
        zoomer.mouseScale(event, 0.1f);
    }
}
