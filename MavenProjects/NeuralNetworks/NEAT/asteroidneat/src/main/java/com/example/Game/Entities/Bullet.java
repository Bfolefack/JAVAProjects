package com.example.Game.Entities;

import com.example.Game.War;
import com.example.Game.Entities.EntityLib.Mobs.Mob;

import processing.core.PApplet;
import processing.core.PVector;

public class Bullet extends Mob{
    public int age;
    War war;

    public Bullet(float x, float y, float vx, float vy, float facing, War w) {
        super(x, y, 10, 0, 1);
        size = 2.5f;
        vel = new PVector((float)Math.cos(facing) * maxSpeed, (float)Math.sin(facing) * maxSpeed);
        war = w;
    }

    public void update(){
        age++;
        if(pos.mag() < war.blackHoleSize/2){
            age = Integer.MAX_VALUE;
        }
        acc.add(war.gravitation(pos).mult(maxSpeed * 5)); 
        if(PVector.dist(pos, new PVector(0, 0)) > war.bounds){
            pos.mult(-1);
        }
        super.update();
    }

    public void display(PApplet sketch){
        sketch.ellipse(pos.x, pos.y, 5, 5);
    }

    public void collide() {
        age = Integer.MAX_VALUE;
    }
    
}
