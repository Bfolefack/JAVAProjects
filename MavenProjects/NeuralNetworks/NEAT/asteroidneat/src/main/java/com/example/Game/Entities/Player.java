package com.example.Game.Entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.TreeMap;
import java.util.TreeSet;

import com.example.Game.War;
import com.example.Game.Entities.EntityLib.Entity;
import com.example.Game.Entities.EntityLib.Mobs.Mob;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Player extends Mob implements Serializable{

    // UP, DOWN, LEFT, RIGHT, SHOOT
    public static final int[] P1Controls = { 38, 40, 37, 39, 16 };
    public static final int[] P2Controls = { 87, 83, 65, 68, 32 };

    public int size;

    public int[] controls;
    public float facing;
    public float turnSpeed;

    public boolean manual;

    public int respawnTimer;
    public int respawnTime = 120;

    public int fireCooldown = 100;
    public int fireRate;

    public float engineHeat;

    public int lives = 5;

    public boolean alive;

    public TreeMap<Float, Bullet> nearestBullets;

    public PVector startPos;

    War war;

    public Player(float x, float y, float ms, float mf, int s, float f, int fr, War w) {
        super(x, y, ms, mf, 1);
        startPos = new PVector(x, y);
        size = s;
        facing = f;
        fireRate = fr;
        turnSpeed = (float) Math.PI / 30;
        war = w;
        alive = true;
        nearestBullets = new TreeMap<>();
    }

    public Player(float x, float y, float ms, float mf, int playerNum, int s, float f, int fr, War w) {
        super(x, y, ms, mf, 1);
        size = s;
        facing = f;
        switch (playerNum) {
            case 1:
                controls = P1Controls;
                break;
            case 2:
                controls = P2Controls;
                break;
        }

        nearestBullets = new TreeMap<>();
        turnSpeed = (float) Math.PI / 30;
        fireRate = fr;
        war = w;
        alive = true;
    }

    // public void update() {
    //     boolean[] actions = new boolean[controls.length];
    //     for (int i = 0; i < controls.length; i++) {
    //         if (SpaceWar.keys.contains(controls[i]))
    //             actions[i] = true;
    //     }
    //     if (alive) {
    //         move(war, actions);
    //         if (collide(war.tree.getEntities(pos, size))) {
    //             respawnTimer = 0;
    //             alive = false;
    //             float ang = (float) (Math.random() * Math.PI * 2);
    //             pos = new PVector((float) Math.cos(ang), (float) Math.sin(ang))
    //                     .mult((float) ((war.bounds - 40) * Math.random() + 40));
    //         }
    //     } else {
    //         respawnTimer++;
    //         move(war, actions);
    //         if (respawnTimer >= respawnTime) {
    //             alive = true;
    //             lives--;
    //             vel = new PVector();
    //             acc = new PVector();
    //         }
    //     }
    // }

    public void update(War war, boolean[] actions) {

        if (alive) {
            move(war, actions);
            if (collide(war.tree.getEntities(pos, size))) {
                respawnTimer = 0;
                alive = false;
                float ang = (float) (Math.random() * Math.PI * 2);
                // pos = new PVector((float) Math.cos(ang), (float) Math.sin(ang))
                //         .mult((float) ((war.bounds - 40) * Math.random() + 40));
                pos = startPos.copy();
                vel = new PVector();
                acc = new PVector();
            }
        } else {
            respawnTimer++;
            move(war, actions);
            if (respawnTimer >= respawnTime) {
                alive = true;
                lives--;
                vel = new PVector();
                acc = new PVector();
                engineHeat = 0;
            }
        }
    }

    private boolean collide(Collection<Entity> ents) {
        boolean ded = false;
        if(engineHeat > 1){
            return true;
        }
        if (pos.mag() < war.blackHoleSize)
            return true;
        for (Entity e : ents) {
            if (PVector.dist(pos, e.pos) < size / 2 + e.size / 2) {
                if (e instanceof Bullet) {
                    ((Bullet) e).collide();
                    nearestBullets.put(PVector.dist(pos, e.pos), (Bullet) e);
                }
                if (e instanceof Player)
                    ((Player) e).collide();
                ded = true;
            }
        }
        return ded;
    }

    public void collide() {
        respawnTimer = 0;
        alive = false;
        float ang = (float) (Math.random() * Math.PI * 2);
        // pos = new PVector((float) Math.cos(ang), (float) Math.sin(ang))
        //         .mult((float) ((war.bounds - 40) * Math.random() + 40));
        pos = startPos.copy();
    }

    private void move(War war, boolean[] actions) {
        if (alive)
            if (actions[0]) {
                float ang = PVector.angleBetween(new PVector((float) Math.cos(facing), (float) Math.sin(facing)), vel);
                engineHeat += 0.005;
                if (Math.cos(ang) * vel.mag() < maxSpeed / 2 && alive)
                    acc = new PVector((float) Math.cos(facing) * maxSpeed * maxForce,
                            (float) Math.sin(facing) * maxSpeed * maxForce);
            }
        if (alive)
            if (actions[1]) {
                vel.mult(0.99f);
            }
        if (actions[2]) {
            facing -= turnSpeed;
            engineHeat += 0.0025;
        }
        if (actions[3]) {
            facing += turnSpeed;
            engineHeat += 0.0025;
        }
        if (alive)
            if (actions[4] && fireCooldown > fireRate) {
                war.bullets.add(new Bullet(pos.x + (float) (Math.cos(facing) * size / 1.5) + vel.x,
                        pos.y + (float) (Math.sin(facing) * size / 1.5) + vel.y, vel.x, vel.y, facing, war));
                fireCooldown = 0;
                engineHeat += 0.025;
            }
        if (alive) {
            acc.add(war.gravitation(pos).mult(maxSpeed));
            super.update();
        }
        if (PApplet.dist(pos.x, pos.y, 0, 0) > war.bounds) {
            pos.mult(-1);
        }
        fireCooldown++;
        engineHeat *= 0.997;
    }

    public void display(PApplet sketch) {
        
        if(!alive) {
            if ((int) (respawnTimer / 20) % 2 != 0)
                return;
        }
        sketch.pushMatrix();
        sketch.translate(pos.x, pos.y);
        sketch.rotate(facing - (float) (Math.PI / 2));
        drawShip(sketch);
        // sketch.ellipse(0, 0, size/2, size/2);
        sketch.popMatrix();
        sketch.stroke(255);
    }

    public void drawShip(PApplet sketch) {
        sketch.beginShape();
        sketch.vertex(-0.25f * size, -0.25f * size);
        sketch.vertex(0, 0.5f * size);
        sketch.vertex(0.25f * size, -0.25f * size);
        sketch.vertex(0, -0.125f * size);
        sketch.endShape(PConstants.CLOSE);
    }
}
