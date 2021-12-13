package com.example.Game;

import processing.core.PApplet;

public class Bird {
    public float height;
    public float vel;
    public Bird(){
        height = 100;
        vel = 0;
    }

    public void update(PApplet app){
        height += vel;
        vel += 0.7f;
        if(height > app.height){
            height = app.height;
        }
        if(height < 0){
            height = 0;
            vel = 0;
        }
    }

    public void display(PApplet app){
        app.ellipse(100, height, 30, 30);
    }

    public void jump(){
        vel = -13;
    }

    public boolean checkHit(Pipe p) {
        if(p == null || p.xPos - 50 > 100){
            return checkHit();
        }
        float h = p.height;   
        return (height < h - 100 || height > h + 100);
    }

    public boolean checkHit(){
        return height < 1 || height > 799;
    }
}
