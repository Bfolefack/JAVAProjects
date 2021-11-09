package com.example.Objects.Stats;

import com.example.Objects.Entity;

import processing.core.PVector;

public class Food extends Entity{

    public float value;

    public Food(float x, float y) {
        super(x, y);
    }

    public Food(PVector p) {
        super(p);
    }
}
