package com.example.Objects.Mobs;

import java.util.HashSet;
import java.util.Set;

import com.example.VectorWars;
import com.example.Objects.Entity;
import com.example.Objects.Stats.Barrier;
import com.example.Objects.Stats.World;

import processing.core.PApplet;
import processing.core.PVector;

public class Ant extends Mob {

    private PVector randOffset;
    public int colony;
    private float fov;
    public World world;

    public Ant(float x, float y, float maxSp, float maxFor, float fric, int col, World w) {
        super(x, y, maxSp, maxFor, fric);
        colony = col;
        world = w;
        fov = 45;
        randOffset = new PVector();
        // TODO Auto-generated constructor stub
    }

    public void update(PApplet sketch) {
        // Stop Sketch for Debugging
        // TODO: Remove Debugging Feature
        if (sketch.mousePressed && PVector.dist(new PVector(VectorWars.truMouseX, VectorWars.truMouseY), pos) < 10) {
            System.out.println("Stopped");
        }

        Set<Entity> barriers = world.getBarriers(pos, 200);

        // Getting random vector
        randOffset.add(avoidBarriers(barriers, 75).mult(0.1f));
        randOffset.limit(2);
        PVector randy = wander(sketch, 0.1f, 5);

        // Avoiding WallClipping
        // PVector avoid = new PVector();
        PVector avoid = avoidSteer(barriers, 30);
        // avoid = PVector.sub(vel, avoid);
        if (avoid.mag() > 0) {
            acc.add(avoid.setMag(50));
            // super.update();
            // return;
        }
        // PVector p = new PVector();
        randy.add(randOffset);
        // randy.mult();
        acc.add(randy /* .setMag(5) */);
        super.update();
    }

    private PVector avoidBarriers(Set<Entity> barriers, int r) {
        Set<PVector> validBarriers = new HashSet<>();
        for (Entity b : barriers) {
            Barrier bar = (Barrier) b;
            float f = getAngle(b.pos);
            if (PVector.dist(pos, b.pos) < r)
                if (f > 0.3 && f < 0.6) {
                    validBarriers.add(b.pos);
                    bar.highlight = true;
                }
        }

        PVector total = new PVector();
        for (PVector p : validBarriers) {
            total.add(avoidSteer(p).mult((float) Math.pow(PVector.dist(p, pos) / r, 2)));
        }

        // for (PVector p : validBarriers) {
        //     total.add(p);
        // }

        if (total.mag() > 0) {
            total.div(validBarriers.size());
        } else {
            return new PVector();
        }
        // PVector p = avoidSteer(total);
        // float f = getAngle(p) + 1;
        // f = (float) Math.pow(f, 2);

        return total;
    }

    public void display(PApplet sketch) {
        // sketch.fill(toColor(colony, sketch));
        sketch.ellipse(pos.x, pos.y, 10, 10);
        sketch.line(pos.x, pos.y, pos.x + vel.x, pos.y + vel.y);
    }
}
