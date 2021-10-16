package com.example;

import com.example.Entities.Stats.World;
import com.example.Utils.Zoomer;

import processing.core.PApplet;
import processing.event.MouseEvent;

public class VectorWars extends PApplet{

    public static int truMouseX;
    public static int truMouseY;
    World w;

    public static void main(String[] args) {
        String[] processingArgs = { "MySketch" };
        VectorWars mySketch = new VectorWars();
        PApplet.runSketch(processingArgs, mySketch);
    }

    public void settings(){
        size(1000, 750);
    }

    public void setup(){
        fill(0);
        // noStroke();
        frameRate(5000);
        Zoomer.initialize(0.1f, this);
        w = new World(this, 1000, 1000, 10);
    }

    public void draw(){
        background(125);
        Zoomer.mousePan();
        Zoomer.pushZoom();
        w.display();
        line(0, 0, 10000, 0);
        line(0, 0, 0, 10000);
        line(0, 10000, 10000, 10000);
        line(10000, 0, 10000, 10000);
        Zoomer.popZoom();
        text(frameRate, 0, 20);
    }

    public void mouseWheel(MouseEvent e){
        Zoomer.mouseScale(e, 0.05f);
    }

}
