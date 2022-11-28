package com.example.Game.Entities.Players;

import com.example.Game.Entities.Player;

import processing.core.PVector;

public class Hunter extends Player {
    public Hunter(float xPos, float yPos, float maxSpeed, float facing, float maxRotationalSpeed, float size) {
        super(xPos, yPos, maxSpeed, facing, maxRotationalSpeed, size);
    }

    public Hunter(float xPos, float yPos){
        super(xPos, yPos, 10, 0, (float)Math.PI/8, 20);
    }

    public Hunter(PVector pos){
        super(pos, 10, 0, (float)Math.PI/8, 20);
    }

    @Override
    public void move(float rotation, float speed) {
        super.move(rotation, speed);
    }
}
