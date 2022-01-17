package com.example.Entities.EntityLib;

import processing.core.PApplet;
import processing.core.PVector;

public class Entity {
    public PVector pos;
    protected Entity(float x, float y){
        pos = new PVector(x, y);
    }
    public Entity(PVector p){
        pos = p;
    }

    public void display(PApplet p){}

    public void update(){}
}
