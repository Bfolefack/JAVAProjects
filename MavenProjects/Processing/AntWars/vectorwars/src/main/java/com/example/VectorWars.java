package com.example;

import java.util.HashSet;
import java.util.Set;

import com.example.Objects.Mobs.Ant;
import com.example.Objects.Stats.World;
import com.example.Utils.Zoomer;

import processing.core.PApplet;
import processing.event.MouseEvent;

public class VectorWars extends PApplet{

    public static int truMouseX;
    public static int truMouseY;
    public World w;
    //TODO: Delete this
    Set<Ant> ants = new HashSet<>();

    public static void main(String[] args) {
        String[] processingArgs = { "MySketch" };
        VectorWars mySketch = new VectorWars();
        PApplet.runSketch(processingArgs, mySketch);
    }

    public void settings(){
        size(800, 800);
    }

    public void setup(){
        fill(0);
        // noStroke();
        frameRate(60);
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
        for(Ant a : ants){
            a.update(this);
            a.display(this);
        }
        Zoomer.popZoom();
        if(keyPressed && key == 'a'){
            ants.add(new Ant(truMouseX, truMouseY, 10, 2, 0.95f, 999999999, w));
        }
        text(frameRate, 0, 20);
    }

    public void mouseWheel(MouseEvent e){
        Zoomer.mouseScale(e, 0.05f);
    }

}
