package com.example.System;

import processing.core.PApplet;
import processing.core.PVector;

public class Planet extends Entity{
    public PVector vel, acc;
    public float mass;

    public Planet(PApplet app) {
        super((float) Math.random() * app.width, (float) Math.random() * app.height);
        vel = new PVector((float) Math.random() * 10 - 5, (float) Math.random() * 10 - 5);
        acc = new PVector();
        mass = (float) (Math.random() * Math.random() * 200) + 5;
    }
    
    public void display(PApplet app){
        app.ellipse(pos.x, pos.y, mass/10, mass/10);
    }

    public void update(){
        pos.add(vel);
    }
}
