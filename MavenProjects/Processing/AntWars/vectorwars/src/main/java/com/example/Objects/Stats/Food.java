package com.example.Objects.Stats;

import com.example.Objects.Entity;

import processing.core.PApplet;
import processing.core.PVector;

public class Food extends Entity{

    public float value;

    public Food(float x, float y, float v) {
        super(x, y);
        value =  v;
    }

    public Food(PVector p, float v) {
        super(p);
        value = v;
    }

    @Override
    public void display(PApplet p){
        p.ellipse(pos.x, pos.y, value, value);
    }
}
