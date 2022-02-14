package com.example.Game.Entities.EntityLib;

import processing.core.PApplet;
import processing.core.PVector;

public class Entity {
    public PVector pos;
    public float size;
    protected Entity(float x, float y){
        pos = new PVector(x, y);
    }
    public Entity(PVector p){
        pos = p;
    }

    public void display(PApplet p){}

    public void update(){}
}
