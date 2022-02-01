package com.example.Game;

import java.io.Serializable;

import processing.core.PApplet;

public class Bird implements Serializable{
    public float height;
    public float vel;
    // public static float jumpMultiplier = 1;
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
        vel = -13 * 1.2f;
    }

    public boolean checkSafe(Pipe p) {
        if(p == null || p.xPos - 50 > 100){
            return checkSafe();
        }
        float h = p.height;   
        return (height > h - 100 && height < h + 100);
    }

    public boolean checkSafe(){
        return (height > 1 && height < 799);
    }
}
