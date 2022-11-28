package com.example.Game.Entities;

import com.example.NEAT.Population.Actors.DefenderActor;

import processing.core.PApplet;
import processing.core.PVector;

public class Bullet extends Player{
    public DefenderActor shooter;

    public Bullet(PVector pos, float facing, DefenderActor shooter) {
        super(pos.copy(), 30, facing, 0, 10);
        this.shooter = shooter;
    }

    public Bullet(PVector pos, float maxSpeed, float facing, float maxRotationalSpeed, float size) {
        super(pos.copy(), maxSpeed, facing, maxRotationalSpeed, size);
    }

    public Bullet(float xPos, float yPos, float maxSpeed, float facing, float maxRotationalSpeed, float size) {
        super(xPos, yPos, maxSpeed, facing, maxRotationalSpeed, size);
    }

    @Override
    public void draw(PApplet app) {
        draw(app, 255, 255, 125);
    }

    public void move() {
        move(0, 1);
    }


    @Override
    public void draw(PApplet app, int r, int g, int b) {
        app.pushMatrix();
        app.translate(pos.x, pos.y);
        app.rotate(facing);
        app.fill(r, g, b);
        app.ellipse(0, 0, size, size);
        app.popMatrix();
    }    
}
