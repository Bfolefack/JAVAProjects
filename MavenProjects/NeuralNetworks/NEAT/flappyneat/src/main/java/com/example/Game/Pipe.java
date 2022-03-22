package com.example.Game;

import processing.core.PApplet;

public class Pipe implements Comparable<Pipe>{
    public float height;
    public Float xPos;
    public static int pipeGap = 150;
    public static int pipeWidth = 25;
    public Pipe(float h, float x){
        height = h;
        xPos = x;
    }

    public void update(){
        xPos -= 5;
    }

    public void display(PApplet app){
        app.rect(xPos - pipeWidth/2, 0, pipeWidth, height - pipeGap/2);
        app.rect(xPos - pipeWidth/2, height + pipeGap/2, pipeWidth, 1000);
    }

    @Override
    public int compareTo(Pipe o) {
        return xPos.compareTo(o.xPos);
    }
}
