package com.example.Entities;

import com.example.Entities.EntityLib.Mobs.Mob;

import processing.core.PApplet;
import processing.core.PVector;

public class Bullet extends Mob{

    protected Bullet(float x, float y, float facing) {
        super(x, y, 20, 0, 1);
        acc = new PVector((float)Math.cos(facing) * maxSpeed, (float)Math.sin(facing) * maxSpeed);
    }

    public void update(){
        super.update();
    }

    public void display(PApplet sketch){
        sketch.ellipse(pos.x, pos.y, 5, 5);
    }
    
}
