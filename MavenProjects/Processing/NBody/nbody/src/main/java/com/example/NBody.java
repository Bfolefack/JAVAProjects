package com.example;

import com.example.System.Orbital;
import com.example.Utils.Zoomer;

import processing.core.PApplet;
import processing.event.MouseEvent;

/**
 * Hello world!
 *
 */
public class NBody extends PApplet {
    public static final float gravitationalConstant = 0.5f;
    public static int truMouseX;
    public static int truMouseY;
    public Orbital solar;

    public static void main(String[] args) {
        String[] processingArgs = { "MySketch" };
        NBody mySketch = new NBody();
        PApplet.runSketch(processingArgs, mySketch);
    }

    @Override
    public void settings() {
        size(1000, 800);
    }

    @Override
    public void setup() {
        solar = new Orbital(this, 5000);
        Zoomer.initialize(1, this);
        noStroke();
    }

    @Override
    public void draw() {
        background(0);
        fill(255);
        Zoomer.mousePan();
        Zoomer.pushZoom();
        solar.update();
        solar.display(this);
        Zoomer.popZoom();
        text(frameRate, 0, 10);
        System.out.println(frameCount);
    }

    @Override
    public void mouseWheel(MouseEvent event) {
        Zoomer.mouseScale(event, 0.05f);
    }

    @Override
    public void keyPressed(){
        solar.showTree = !solar.showTree;
    }
}
