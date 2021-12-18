package com.example.Objects.Stats;

import com.example.VectorWars;
import com.example.Objects.Entity;

import processing.core.PVector;

public class Barrier extends Entity {
    // TODO: Remove this
    public boolean highlight;

    public Barrier(float x, float y) {
        super(x, y);
    }

    public Barrier(PVector barrierPoint) {
        super(barrierPoint);
    }

    public void display(VectorWars sketch) {
        sketch.ellipse(pos.x, pos.y, 5, 5);
        highlight = false;
    }
}
