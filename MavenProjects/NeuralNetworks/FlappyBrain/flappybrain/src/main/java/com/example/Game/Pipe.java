package com.example.Game;

import processing.core.PApplet;

public class Pipe implements Comparable<Pipe>{
    public float height;
    public Float xPos;
    public Pipe(float h, float x){
        height = h;
        xPos = x;
    }

    public void update(){
        xPos -= 5;
    }

    public void display(PApplet app){
        app.rect(xPos - 50, 0, 100, height - 100);
        app.rect(xPos - 50, height + 100, 100, 1000);
    }

    @Override
    public int compareTo(Pipe o) {
        return xPos.compareTo(o.xPos);
    }
}
