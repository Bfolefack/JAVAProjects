package com.example.Objects.Stats;

import com.example.VectorWars;
import com.example.Objects.Entity;
import com.example.Objects.EntityType;

import processing.core.PVector;

public class Barrier extends Entity {
    // TODO: Remove this
    public boolean highlight;

    public Barrier(float x, float y) {
        super(x, y);
        type = EntityType.BARRIER;
    }

    public Barrier(PVector barrierPoint) {
        super(barrierPoint);
        type = EntityType.BARRIER;
    }

    public void display(VectorWars sketch) {
        sketch.ellipse(pos.x, pos.y, 5, 5);
        highlight = false;
    }
}
