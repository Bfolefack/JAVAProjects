package com.example.Objects.Stats.Pheromones;

import com.example.Objects.Entity;

import processing.core.PApplet;
import processing.core.PVector;

public class Pheromone extends Entity{
    public float value;
    float decayRate;
    int color;

	public Pheromone(PVector p, int c, float v, float dr) {
		super(p);
        color = c;
        value = v;
        decayRate = dr;
	}

	public Pheromone(float x, float y, int c, float v, float dr) {
		super(x, y);
        color = c;
        value = v;
        decayRate = dr;
	}

    public void strengthen(float f){
        value += f;
        if(value > 1){
            value = 0;
        }
    }

    @Override
    public void update(){
        value *= decayRate;
    }    

    @Override
    public void display(PApplet p){
        p.ellipse(pos.x, pos.y, value * 20, value * 20);
    }
}
