package com.example;

import java.util.ArrayList;

import com.example.System.Orbital;
import com.example.System.Planet;
import com.example.Utils.SystemLibrary;
import com.example.Utils.Zoomer;

import processing.core.PApplet;
import processing.event.MouseEvent;

/**
 * Hello world!
 *
 */
public class NBody extends PApplet {
    public static float simulationSpeed = 1000;
    public static final float gravitationalConstant = 0.000667f * (float) Math.pow(simulationSpeed, 2);
    public static int truMouseX;
    public static int truMouseY;
    
    public Orbital solar;
    public float speed = 0;
    

    public static void main(String[] args) {
        String[] processingArgs = { "MySketch" };
        NBody mySketch = new NBody();
        SystemLibrary.loadSystems();
        PApplet.runSketch(processingArgs, mySketch);
    }

    @Override
    public void settings() {
        size(1000, 800, P2D);
    }

    @Override
    public void setup() {
        solar = new Orbital(this, SystemLibrary.randomSys(1), 100);
        Zoomer.initialize(1f, this);
        noStroke();
    }

    @Override
    public void draw() {
        background(0);
        fill(255);
        Zoomer.mousePan();
        Zoomer.pushZoom();
        if (speed > 1)
            for (int i = 0; i < speed; i++) {
                solar.update();
            }
        else if ((frameCount % (int) (1 / speed)) == 0) {
            solar.update();
        }
        solar.display(this);
        Zoomer.popZoom();
        text(frameRate, 0, 10);
        text(solar.planetSet.size(), 0, 20);
        text(speed, 0, 30);
        // System.out.println(frameCount);
    }

    @Override
    public void mouseWheel(MouseEvent event) {
        Zoomer.mouseScale(event, 0.05f);
    }

    @Override
    public void keyPressed() {
        if (key == 't')
            solar.showTree = !solar.showTree;
        if (key == '/')
            speed += 5;
        if (key == '.')
            speed -= 5;
        if (key == ';')
            simulationSpeed += 1;
        if (key == 'l')
            simulationSpeed -= 1;
        if (key == 's')
            solar.update();
    }
}
