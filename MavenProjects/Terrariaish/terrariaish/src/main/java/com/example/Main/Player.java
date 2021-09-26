package com.example.Main;

import com.example.*;

import processing.core.PApplet;
import processing.core.PVector;
import java.io.IOException;
import java.io.Serializable;

public class Player implements Serializable{
  transient World world;

  private float gravity;
  private float jumpStrength;
  private float floor;

  private boolean onFloor;
  private boolean prevOnFloor;

  private char prevKeyPressed;

  public PVector pos;
  private PVector vel;
  private PVector acc;
  public Player(World w) {
    floor = 200;
    gravity = 1.3f; 
    world = w;
    pos = new PVector(world.sketch.width/2, 50);
    acc = new PVector(0, 0);
    vel = new PVector(0, 0);
  }

  public void display(World w) {
    w.sketch.rect(w.sketch.width/2, w.sketch.height/2 - 30, 10, 30);
  }
  public void update() {
    floor = checkFloor();
    if (pos.y < floor) {
      acc.y = gravity;
      vel.mult(0.99f);
      vel.x *= 0.8;
    } else {
      pos.y = floor;
      if (vel.y > 0) {
        vel.y = 0;
      }
      if (world.sketch.keys.contains('w') || world.sketch.keys.contains(' ')) {
          vel.y += -25;
          world.sketch.key = '0';
      }
      vel.x *= 0.8;
    }
    if (world.sketch.keys.size() > 0) {
      if (world.sketch.keys.contains('a')) {
        acc.x += -2;
      }
      if (world.sketch.keys.contains('d')) {
        acc.x +=  2;
      }
    }
    prevKeyPressed = world.sketch.key;
    

    vel.add(acc);
    pos.add(vel);
    acc.set(0, 0);
  }

  private float checkFloor() {
    PVector tempPos = PVector.div(pos, 10);
    //TODO: Figure out how to find the floor
    return pos.x;
  }

  public PVector getPos() {
    return pos;
  }
}
