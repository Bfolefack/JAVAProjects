package com.example.Objects;

import processing.core.PVector;

public class Entity {
    public PVector pos;
    public EntityType type;
    public Entity(float x, float y){
        pos = new PVector(x, y);
    }
    public Entity(PVector p){
        pos = p;
    }
}
