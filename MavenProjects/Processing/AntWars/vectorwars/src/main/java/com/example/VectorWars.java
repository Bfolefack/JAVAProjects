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
        frameRate(144);
        w = new World(this, 1000, 1000, 20);
        Zoomer.initialize(width/(w.width * w.scale), this);
    }

    public void draw(){
        background(125);
        Zoomer.mousePan();
        Zoomer.pushZoom();
        strokeWeight(0.1f);
        w.display();
        if(keyPressed && key == 'a'){
            ants.add(new Ant(truMouseX, truMouseY, 5, 2f, 0.95f, 999999999, w));
        }
        for(Ant a : ants){
            a.update(this);
            a.display(this);
        }
        Zoomer.popZoom();
        text(frameRate, 0, 20);
    }

    public void mouseWheel(MouseEvent e){
        Zoomer.mouseScale(e, 0.05f);
    }

    public void keyPressed(){
        if(key == 'f' || key == 'F'){
            w.flipped = !w.flipped;
        }
        if(key == 'r' || key == 'R'){
            Zoomer.initialize(width/(w.width * w.scale), this);
        }
        if(key == ','){
            if(frameRate > 60){
                frameRate(60 * 0.8f);
            } else {
                frameRate(frameRate * 0.8f);
            }
        } else if (key == '.'){
            if(frameRate > 60){
                frameRate(60 * 1.2f);
            } else {
                frameRate(frameRate * 1.2f);
            }
        }
    }

}
