package com.example.Boids;

import java.util.HashMap;
import java.util.Set;

import com.example.QuadBoids;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Boid implements Comparable<Boid> {
    public PVector pos;
    PVector vel;
    PVector acc;
    public PVector hash;
    public boolean predator;

    public static HashMap<String, String> typeMap = new HashMap<String, String>();
    public static HashMap<String, String> inverseTypeMap = new HashMap<String, String>();
    Population population;
    float viewRadius;
    int flock;
    int sketchWidth, sketchHeight;
    float size;
    float randID;
    float facing;
    float prevFacing;
    float trueFacing;
    float FOV = -0.5f;
    String type;

    float separationCoefficient = 3;
    float cohesionCoefficient = 1;
    float directionCoefficient = 2;

    static {
        typeMap.put("scissor", "rock");
        typeMap.put("rock", "paper");
        typeMap.put("paper", "scissor");

        inverseTypeMap.put("scissor", "paper");
        inverseTypeMap.put("rock", "scissor");
        inverseTypeMap.put("paper", "rock");
    }

    public Boid(float x, float y, int f, Population pop, int s, boolean p) {
        pos = new PVector(x, y);
        hash = new PVector((int) (pos.x / 20), (int) (pos.y / 20));
        vel = new PVector();
        acc = new PVector();
        size = s;
        viewRadius = size * 5;
        flock = f;

        population = pop;
        sketchWidth = population.sketch.width;
        sketchHeight = population.sketch.height;
        predator = p;
        randID = (float) (Math.random() * 1000000);
    }

    public Boid(float x, float y, int f, Population pop, int s, boolean p, String t) {
        pos = new PVector(x, y);
        hash = new PVector((int) (pos.x / 20), (int) (pos.y / 20));
        vel = new PVector();
        acc = new PVector();
        size = s;
        viewRadius = size * 5;
        flock = f;
        type = t;

        population = pop;
        sketchWidth = population.sketch.width;
        sketchHeight = population.sketch.height;
        predator = p;
        randID = (float) (Math.random() * 1000000);
    }

    private Set<Boid> getBoidNeighbors() {
        return population.getBoidNeighbors(pos.x, pos.y, viewRadius);
    }

    public void display(QuadBoids qb) {
        // System.out.println(pos);
        qb.fill(toColor(flock, qb));
        qb.ellipse(pos.x, pos.y, size, size);
        // qb.stroke(toColor(flock, qb));
        // qb.line(pos.x, pos.y, pos.x + acc.x * 10, pos.y + acc.y * 10);
    }

    int toColor(int col, QuadBoids qb) {
        return qb.color((col / 1000000) * 0.255f, ((col % 1000000) / 1000) * 0.255f, (col % (flock / 1000)) * 0.255f);
    }

    public void update(QuadBoids qb) {
        Set<Boid> neighbors = getBoidNeighbors();
        neighbors.remove(this);
        if (population.method == 1) {
            if (neighbors.size() > 20 && viewRadius > 2) {
                viewRadius -= (size * 0.1);
            } else if (viewRadius < size * 5) {
                viewRadius += (size * 0.1);
            }
        } else {
            viewRadius = size * 5;
        }
        {
            acc.add(direction(neighbors).mult(size * directionCoefficient));
            acc.add(separation(neighbors).mult(size * separationCoefficient));
            acc.add(attraction(neighbors).mult(size * cohesionCoefficient));
        }
        if (acc.mag() == 0) {
            float f = (qb.noise(randID, (qb.frameCount * 0.05f)) * 2 - 1) * PApplet.TWO_PI;
            acc = new PVector((float) Math.cos(f), (float) Math.sin(f));
            acc.setMag(size / 2);
        }
        vel.add(acc);
        if (!predator)
            vel.limit(size / 2f);
        else
            vel.limit(size / 1.5f);
        pos.add(vel);
        acc.set(0, 0);
        vel.mult(0.95f);
        if (pos.x > sketchWidth) {
            pos.x = 0;
        }
        if (pos.y > sketchHeight) {
            pos.y = 0;
        }
        if (pos.x < 0) {
            pos.x = sketchWidth;
        }
        if (pos.y < 0) {
            pos.y = sketchHeight;
        }
        hash = new PVector((int) (pos.x / (viewRadius)), (int) (pos.y / (viewRadius)));
    }

    private PVector attraction(Set<Boid> neighbors) {
        int count = 0;
        int predatorCount = 0;
        if (neighbors.size() > 0) {
            PVector predatorTotal = new PVector();
            PVector total = new PVector();
            for (Boid b : neighbors) {
                if (predator) {
                    if (checkAngle(b.pos) > FOV) {
                        if (b.flock == flock) {
                            if (PVector.dist(b.pos, pos) < viewRadius) {
                                total.add(PVector.sub(b.pos, pos).div(PVector.dist(pos, b.pos)));
                                count++;
                            }
                        } else {
                            if (PVector.dist(b.pos, pos) < viewRadius) {
                                predatorTotal.add(PVector.sub(b.pos, pos).div(PVector.dist(pos, b.pos)));
                                predatorCount++;
                            }
                        }
                    }
                } else if (type != null) {
                    if (checkAngle(b.pos) > FOV) {
                        if (b.flock == flock) {
                            if (PVector.dist(b.pos, pos) < viewRadius) {
                                total.add(PVector.sub(b.pos, pos).div(PVector.dist(pos, b.pos)));
                                count++;
                            }
                        } else if (b.type == inverseTypeMap.get(type)) {
                            if (PVector.dist(b.pos, pos) < viewRadius) {
                                predatorTotal.add(PVector.sub(b.pos, pos).div(PVector.dist(pos, b.pos)));
                                predatorCount++;
                            }
                        }
                    }
                }
                if (b.flock == flock && checkAngle(b.pos) > FOV) {
                    if (PVector.dist(b.pos, pos) < viewRadius) {
                        total.add(PVector.sub(b.pos, pos).div(PVector.dist(pos, b.pos)));
                        count++;
                    }
                }
            }

            if (predatorTotal.mag() > 0) {
                if (predatorCount < 1) {
                    System.out.println("oof");
                }
                predatorTotal.div(predatorCount);
                return predatorTotal.normalize();
            }
            if (total.mag() > 0) {
                total.div(count);
                return total.normalize();
            }
        }
        return new PVector();
    }

    private PVector separation(Set<Boid> neighbors) {
        int count = 0;
        if (neighbors.size() > 0) {
            PVector total = new PVector();
            for (Boid b : neighbors) {
                if (PVector.dist(pos, b.pos) < size * 1.5) {
                    total.add(PVector.sub(pos, b.pos).div((float) PVector.dist(pos, b.pos)));
                    count++;
                } else if (b.predator && checkAngle(b.pos) > FOV && b.flock != flock) {
                    if (PVector.dist(pos, b.pos) < viewRadius) {
                        total.add(PVector.sub(pos, b.pos).div((float) PVector.dist(pos, b.pos)).mult(10));
                        count += 10;
                    }
                } else if (type != null && b.type == typeMap.get(type) && checkAngle(b.pos) > FOV) {
                    if (PVector.dist(pos, b.pos) < viewRadius) {
                        total.add(PVector.sub(pos, b.pos).div((float) PVector.dist(pos, b.pos)).mult(10));
                        count += 10;
                    }
                }
            }
            if (count > 0) {
                total.div(count);
                return total.normalize();
            }
        }
        return new PVector();
    }

    private PVector direction(Set<Boid> neighbors) {
        int count = 0;
        if (neighbors.size() > 0) {
            PVector total = new PVector();
            for (Boid b : neighbors) {
                if (b.flock == flock && checkAngle(b.pos) > FOV)
                    if (PVector.dist(b.pos, pos) < viewRadius) {
                        total.add(b.vel);
                        count++;
                    }
            }
            if (count > 0)
                total.div(count);
            total.normalize();
            return total;
        }
        return new PVector();
    }

    private float checkAngle(PVector pos2) {
        PVector diff = PVector.sub(pos2, pos);
        PVector tempVel = vel;
        diff.normalize();
        tempVel.normalize();
        return PVector.dot(diff, tempVel);
    }

    @Override
    public int compareTo(Boid o) {
        // TODO Auto-generated method stub
        return toString().compareTo(o.toString());
    }

    public void getHash(int hashSize) {
        hash = new PVector((int) (pos.x / hashSize), (int) (pos.y / hashSize));
    }
}
