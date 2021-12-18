package com.example.Objects.Mobs;

import java.util.HashSet;
import java.util.Set;

import com.example.VectorWars;
import com.example.Objects.Entity;
import com.example.Objects.Stats.Barrier;
import com.example.Objects.Stats.Food;
import com.example.Objects.Stats.World;
import com.example.Objects.Stats.Pheromones.Pheromone;

import processing.core.PApplet;
import processing.core.PVector;

public class Ant extends Mob {

    private PVector randOffset;
    private PVector pheromoneLook; //TODO: REMOVE THIS
    public Colony nest;
    private float fov;
    public World world;
    public int state;
    public static final float pheromoneDecay = 0.999f;
    // 0 == seeking food
    // 1 == returning home

    public Ant(float x, float y, float maxSp, float maxFor, float fric, Colony n, World w) {
        super(x, y, maxSp, maxFor, fric);
        pheromoneLook = new PVector();
        world = w;
        fov = 45;
        randOffset = new PVector();
        nest = n;
        // TODO Auto-generated constructor stub
    }

    public void update(PApplet sketch) {
        // Stop Sketch for Debugging
        // TODO: Remove Debugging Feature
        if (sketch.mousePressed && PVector.dist(new PVector(VectorWars.truMouseX, VectorWars.truMouseY), pos) < 10) {
            System.out.println("Stopped");
        }

        Set<Barrier> barriers = world.getBarriers(pos, 80);

        Set<Pheromone> seekingPheromones = null;
        Set<Pheromone> depositingPheromones = null;
        if (state == 0) {
            Set<Food> food = world.food.getEntities(pos, 200); 
            seekingPheromones = world.foodPheromones.getEntities(pos, 200);
            depositingPheromones = world.homePheromones.getEntities(pos, 200);
            if (food.size() > 0) {
                Food nearestFood = null;
                float nearestFoodDist = Float.MAX_VALUE;
                for (Food f : food) {
                    float dist = PVector.dist(pos, f.pos);
                    if (dist < nearestFoodDist) {
                        nearestFood = f;
                        nearestFoodDist = dist;
                    }
                }
                if (nearestFoodDist < 5) {
                    nearestFood.value -= 10;
                    if (nearestFood.value < 0) {
                        world.food.remove(nearestFood);
                    }
                    state = 1;
                    vel.mult(-1);
                    return;
                } else {
                    acc.add(seekSteer(nearestFood.pos));
                }
                super.update();
                return;
            }
            
        } else if (state == 1) {
            float dist = PVector.dist(pos, nest.pos);
            if (dist < 200) {
                if (dist < 10) {
                    nest.food += 10;
                    state = 0;
                    vel.mult(-1);
                    return;
                }
                acc.add(seekSteer(nest.pos));
                super.update();
                return;
            }
            seekingPheromones = world.homePheromones.getEntities(pos, 200);
            depositingPheromones = world.foodPheromones.getEntities(pos, 200);
        }

        Pheromone nearestPheromone = null;
        float nearestPheromoneDist = Float.MAX_VALUE;
        for (Pheromone p : depositingPheromones) {
            float dist = PVector.dist(pos, p.pos);
            if (dist < nearestPheromoneDist) {
                nearestPheromone = p;
                nearestPheromoneDist = dist;
            }
        }
        if (nearestPheromone == null) {
            if (state == 0) {
                world.homePheromones.insert(new Pheromone(pos.x, pos.y, 0x0000FF, 1, pheromoneDecay));
            } else if (state == 1) {
                world.foodPheromones.insert(new Pheromone(pos.x, pos.y, 0xFF0000, 1, pheromoneDecay));
            }
        } else if (nearestPheromoneDist < 50) {
            nearestPheromone.value = 1;
        } else {
            if (state == 0) {
                world.homePheromones.insert(new Pheromone(pos.x, pos.y, 0x0000FF, 1, pheromoneDecay));
            } else if (state == 1) {
                world.foodPheromones.insert(new Pheromone(pos.x, pos.y, 0xFF0000, 1, pheromoneDecay));
            }
        }

        // Getting random vector
        PVector pheromoneSeek = seekPheromones(seekingPheromones);
        if (pheromoneSeek.mag() > 5) {
            acc.add(pheromoneSeek);
            update();
            return;
        }
        randOffset.add(avoidBarriers(barriers, 75).mult(1));
        randOffset.limit(maxSpeed * 2);
        PVector randy = wander(sketch, 0.1f, 5);
        randy.add(randOffset);
        acc.add(randy);

        // Avoiding WallClipping
        // PVector avoid = new PVector();
        PVector avoid = avoidSteer(barriers, 30);
        // avoid = PVector.sub(vel, avoid);
        if (avoid.mag() > 0) {
            acc.add(avoid.setMag(50));
            // super.update();
            // return;
        }
        super.update();
    }

    private PVector avoidBarriers(Set<? extends Entity> barriers, int r) {
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
        // total.add(p);
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
        sketch.ellipse(pos.x + pheromoneLook.x, pos.y + pheromoneLook.y, 50, 50);
        pheromoneLook = new PVector(1000000, 1000000);
        // sketch.line(pos.x, pos.y, pos.x + vel.x, pos.y + vel.y);
    }

    public PVector seekPheromones(Set<Pheromone> pheromones) {
        if (pheromones.size() > 0) {
            PVector pheromoneAverage = new PVector();
            int count = 0;
            for (Pheromone p : pheromones) {
                if (PVector.dot(PVector.sub(p.pos, pos), vel) > 0.5) {
                    pheromoneAverage.add(PVector.sub(p.pos, pos).mult(p.value));
                    count++;
                }
            }
            if(count > 0){
                pheromoneAverage.setMag(20);
                PVector diff = PVector.sub(pheromoneLook, pheromoneAverage);
                if(pheromoneLook.mag() < 1){
                    pheromoneLook = pheromoneAverage;
                } else {
                    pheromoneLook.add(pheromoneAverage.mult(0.1f));
                }
                
                return seekSteer(PVector.add(pheromoneLook, pos));
            }
        }
        pheromoneLook = new PVector();
        return new PVector();
    }
}
