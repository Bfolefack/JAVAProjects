package com.example.Game.Entities;

import processing.core.PApplet;
import processing.core.PVector;

public class Player {
    public PVector pos;
    public float maxSpeed;
    public float facing;
    public float maxRotationalSpeed;
    public float size;
    
    protected Player(PVector pos, float maxSpeed, float facing, float maxRotationalSpeed, float size) {
        this.pos = pos;
        this.maxSpeed = maxSpeed;
        this.facing = facing;
        this.maxRotationalSpeed = maxRotationalSpeed;
        this.size = size;
    }
    protected Player(float xPos, float yPos, float maxSpeed, float facing, float maxRotationalSpeed, float size) {
        this.pos = new PVector(xPos, yPos);
        this.maxSpeed = maxSpeed;
        this.facing = facing;
        this.maxRotationalSpeed = maxRotationalSpeed;
        this.size = size;
    }

    protected void move(float rotation, float speed) {
        rotation = (float) (rotation > 1 ? 1 : rotation < -1 ? -1 : rotation);
        speed = (float) (speed > 1 ? 1 : speed < -1 ? -1 : speed < 0 ? speed * 0.25 : speed);
        if(rotation != 0){
            facing += rotation * maxRotationalSpeed;
            facing = (float) (facing > Math.PI ? -Math.PI : (facing < -Math.PI ? Math.PI : facing));
        }
        PVector vel = new PVector((float)(maxSpeed * speed * Math.cos(facing)), (float)(maxSpeed * speed * Math.sin(facing)));
        pos.add(vel);
    }
    
    public void draw(PApplet app) {
        draw(app, 255, 0, 0);
    }



    public void draw(PApplet app, int r, int g, int b) {
        app.pushMatrix();
        app.translate(pos.x, pos.y);
        app.rotate(facing);
        app.fill(r, g, b);
        app.ellipse(0, 0, size, size);
        app.fill(0);
        app.ellipse(size * 0.25f, size * 0.25f, size/10, size/10);
        app.ellipse(size * 0.25f, size * -0.25f, size/10, size/10);
        app.popMatrix();
    }
}
