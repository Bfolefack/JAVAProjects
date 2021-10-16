package com.example.Entities.Stats;

import com.example.Entities.Entity;
import com.example.Entities.EntityType;

public class Barrier extends Entity {

    public Barrier(float x, float y) {
        super(x, y);
        type = EntityType.BARRIER;
    }

}
