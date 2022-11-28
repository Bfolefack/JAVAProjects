package com.example.Game.Entities.Players;

import com.example.Game.Arena;
import com.example.Game.Entities.Player;
import com.example.NEAT.Population.Actors.DefenderActor;

import processing.core.PApplet;
import processing.core.PVector;

public class Defender extends Player {
    public int fireCooldown;
    public static int fireCooldownMax = 30;

    public Defender(float xPos, float yPos, float maxSpeed, float facing, float maxRotationalSpeed, float size) {
        super(xPos, yPos, maxSpeed, facing, maxRotationalSpeed, size);
    }

    public Defender(float xPos, float yPos) {
        super(xPos, yPos, 5, 0, (float) Math.PI / 16, 40);
    }

    public Defender(PVector pos) {
        super(pos, 5, 0, (float) Math.PI / 16, 40);
    }

    @Override
    public void draw(PApplet app) {
        draw(app, 0, 0, 255);
    }

    public void move(float rotation, float speed) {
        super.move(rotation, speed);
        if (fireCooldown > 0)
            fireCooldown--;
    }

    public void fire(Arena arena, DefenderActor shooter) {
        if (fireCooldown <= 0) {
            fireCooldown = fireCooldownMax;
            arena.createBullet(pos, facing, shooter);
        }
    }
}
