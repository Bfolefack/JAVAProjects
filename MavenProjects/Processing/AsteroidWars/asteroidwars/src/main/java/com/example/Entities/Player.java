package com.example.Entities;

import com.example.AsteroidWars;
import com.example.Entities.EntityLib.Mobs.Mob;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Player extends Mob {

    // UP, DOWN, LEFT, RIGHT, SHOOT
    public static final int[] P1Controls = { 38, 40, 37, 39, 16 };
    public static final int[] P2Controls = {87, 83, 65, 68, 32};
    int size = 50;

    public int[] controls;
    public float facing;

    public Player(float x, float y, float ms, float mf, int playerNum) {
        super(x, y, ms, mf, 1);
        switch (playerNum) {
            case 1:
                controls = P1Controls;
                break;
            case 2:
                controls = P2Controls;
                break;
        }
    }

    public void update(AsteroidWars sketch){
        float s = 0.05f;
        if(AsteroidWars.keys.contains(controls[0])){
            acc = new PVector((float)Math.cos(facing) * maxSpeed * s, (float)Math.sin(facing) * maxSpeed * s);
        } else if(AsteroidWars.keys.contains(controls[1])){
            acc = new PVector((float)Math.cos(facing) * maxSpeed * s, (float)Math.sin(facing) * maxSpeed * s).mult(-1);
        }
        if(AsteroidWars.keys.contains(controls[2])){
            facing -= (float) Math.PI/30;
        }
        if(AsteroidWars.keys.contains(controls[3])){
            facing += (float) Math.PI/30;
        }
        if(AsteroidWars.keys.contains(controls[4])){
            sketch.bullets.add(new Bullet(pos.x, pos.y, facing));
        }
        super.update();
        if(pos.x > sketch.width){
            pos.x = 0;
        }
        if(pos.y > sketch.height){
            pos.y = 0;
        }
        if(pos.x < 0){
            pos.x = sketch.width;
        }
        if(pos.y < 0){
            pos.y = sketch.height;
        }
    }

    public void display(PApplet sketch) {
        sketch.pushMatrix();
        sketch.translate(pos.x, pos.y);
        sketch.rotate(facing - (float) (Math.PI / 2));
        sketch.beginShape();
        sketch.vertex(-0.5f * size, -0.5f * size);
        sketch.vertex(0, 0.5f * size);
        sketch.vertex(0.5f * size, -0.5f * size);
        sketch.vertex(0, -0.25f * size);
        sketch.endShape(PConstants.CLOSE);
        sketch.popMatrix();
    }

}
