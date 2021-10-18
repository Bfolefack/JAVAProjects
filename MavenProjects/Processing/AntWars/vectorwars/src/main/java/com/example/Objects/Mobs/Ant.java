package com.example.Objects.Mobs;

import java.util.HashSet;

import com.example.Objects.Entity;
import com.example.Objects.Stats.World;

import processing.core.PApplet;
import processing.core.PVector;

public class Ant extends Mob{

    public int colony;
    public World world;

    public Ant(float x, float y, float maxSp, float maxFor, float fric, int col, World w) {
        super(x, y, maxSp, maxFor, fric);
        colony = col;
        world = w;
        //TODO Auto-generated constructor stub
    }


    public void update(PApplet sketch){
        PVector p = wander(sketch, 0.05f, 5);
        acc.add(p.setMag(5));
        acc.add(avoidSteer(world.getBarriers(pos, 20)).mult(10));
        super.update();
    }

    public void display(PApplet sketch){
        sketch.fill(toColor(colony, sketch));
        sketch.ellipse(pos.x, pos.y, 10, 10);
        sketch.line(pos.x, pos.y, pos.x + vel.x, pos.y + vel.y);
    }

    private int toColor(int col, PApplet pap) {
        return pap.color((col / 1000000) * 0.255f, ((col % 1000000) / 1000) * 0.255f, (col % (1000)) * 0.255f);
    }

    private float getAngle(PVector pos2){
        PVector diff = PVector.sub(pos2, pos);
        PVector tempVel = vel;
        diff.normalize();
        tempVel.normalize();
        return PVector.dot(diff, tempVel);
    }    
}
