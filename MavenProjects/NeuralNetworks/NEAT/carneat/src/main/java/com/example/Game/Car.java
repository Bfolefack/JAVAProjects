package com.example.Game;

import java.io.Serializable;

import com.example.Game.Track.TrackType;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Car implements Serializable {
    public PVector pos;
    public PVector vel;
    public PVector acc;
    public float maxSpeed;
    public float maxForce;
    public float size;
    public float rotation;
    public int points;
    public boolean alive = true;

    public LinearFormula[] sight;
    public PVector[] sightPoints;
    public TrackType[] sightPointTypes;

    public static final float reverseSpeed = 0.05f;
    public static final float speed = 1f;
    public static final float turnSpeed = (float) (Math.PI / 32);
    public static final float friction = 0.025f;
    public static final float sightRange = 60;

    public static boolean showLines;
    public static boolean showTarget;

    public transient Track track;
    public int target;
    LinearFormula targetFormula;

    // public Car(float x, float y, float size, float maxSpeed, float maxForce, Track t) {
    //     this.pos = new PVector(x, y);
    //     this.vel = new PVector(0, 0);
    //     this.acc = new PVector(0, 0);
    //     this.size = size;
    //     this.maxSpeed = maxSpeed;
    //     this.maxForce = maxForce;
    //     track = t;
    //     // rotation = getRotation(targetFormula);
    // }

    public Car(float size, float maxSpeed, float maxForce, Track t) {
        int randIndex = t.randomCheckPointIndex;
        this.pos = t.checkPoints.get(randIndex).originPoint.copy();
        this.vel = new PVector(0, 0);
        this.acc = new PVector(0, 0);
        this.size = size;
        this.maxSpeed = maxSpeed;
        this.maxForce = maxForce;
        PVector augh = PVector.sub(t.checkPoints.get(randIndex < t.checkPoints.size() - 1 ? randIndex + 1: 0).originPoint.copy(), pos);
        this.rotation = (float) Math.atan2(augh.y, augh.x);
        track = t;
        int ind = t.randomCheckPointIndex;
        target = ind + 2 < t.checkPoints.size() ? ind + 2 : 1;
        targetFormula = track.checkPoints.get(target);
        sight = new LinearFormula[5];
        // rotation = getRotation(track.checkPoints.get(0));
    }

    private float getRotation(LinearFormula tf) {
        if (tf == null) {
            return 0;
        }
        // return ((float) Math.atan(tf.m) + (float) Math.PI / 2) * (float) (tf.m < 0 ? -1 : 1);
        float ang = (float) Math.atan(tf.m) + (float) Math.PI / 2;
        // System.out.println(ang);
        ang = (tf.originPoint.y > tf.b ? ang * -1 : ang);
        return ang;
    }

    public void update(float throttle, float steering) {

        clampRotation();

        // slipperyMotion(throttle, steering);

        carMotion(throttle, steering);

        checkCheckpoints();

        updateSight();
    }

    private void carMotion(float throttle, float steering) {
        throttle = (throttle < 0) ? throttle * reverseSpeed : throttle * speed;
        float steerAngle = steering * turnSpeed;
        if (vel.mag() > maxSpeed * 0.01f)
            rotation += steerAngle;
        PVector rearWheel = new PVector(pos.x - (size / 2) * (float) Math.cos(rotation),
                pos.y - (size / 2) * (float) Math.sin(rotation));
        PVector frontWheel = new PVector(pos.x + (size / 2) * (float) Math.cos(rotation),
                pos.y + (size / 2) * (float) Math.sin(rotation));
        PVector acc = PVector.sub(frontWheel, rearWheel).setMag(throttle * maxForce);
        acc.limit(maxSpeed * maxForce);
        vel.add(acc);

        float traction = .15f;
        if (vel.mag() > maxSpeed * 0.666f) {
            traction = 0.075f;
        }
        vel.lerp(PVector.fromAngle(rotation).setMag(vel.mag()), traction).setMag(vel.mag());
        vel.mult(1 - friction);
        vel.limit(maxSpeed);
        if (vel.mag() < maxSpeed / 50)
            vel.setMag(0);
        pos.add(vel);

    }

    private void slipperyMotion(float throttle, float steering) {
        throttle = (throttle < 0) ? throttle * reverseSpeed : throttle * speed;
        rotation += steering * turnSpeed;
        acc = PVector.fromAngle(rotation).mult(throttle);
        acc.limit(maxForce);
        vel.add(acc);
        vel.limit(maxSpeed).mult(1 - (friction + (throttle < 0 ? 0.4f : 0)));
        pos.add(vel);
    }

    private void clampRotation() {
        if (rotation < -Math.PI) {
            rotation += (float) Math.PI * 2;
        } else if (rotation > Math.PI) {
            rotation += (float) -Math.PI * 2;
        }
    }

    private void updateSight() {
        sight = new LinearFormula[5];
        for (float i = (float) (-2 * Math.PI / 3); i <= ((Math.PI * 2) / 3f) + 0.01; i += (float) (Math.PI / 3)) {
            sight[(int) (i / (Math.PI / 3)) + 2] = new LinearFormula((float) Math.tan(rotation + i), pos.x, pos.y);
        }

        sightPoints = new PVector[6];
        sightPointTypes = new TrackType[5];
        try {
            if (track.grid[(int) pos.x][(int) pos.y] == TrackType.GRASS
                    || track.grid[(int) pos.x][(int) pos.y] == TrackType.WALL) {
                alive = false;
            }

            for (int i = 0; i < sight.length; i++) {
                if (i < (((sight.length) / 2) + (Math.abs(rotation) < Math.PI / 2 ? 0 : 1))) {
                    sightPoints[i] = PVector.fromAngle((float) (Math.PI + Math.atan(sight[i].m))).mult(sightRange);
                } else {
                    sightPoints[i] = PVector.fromAngle((float) (Math.atan(sight[i].m))).mult(sightRange);
                }
                getSightPoint(i);
            }
            sightPoints[5] = sight[sight.length / 2].intersect(targetFormula).sub(pos);
            sightPoints[5].limit(sightRange);
            if (PVector.dot(sightPoints[sight.length / 2], sightPoints[5]) < 0) {
                sightPoints[5] = sightPoints[sight.length / 2];
            }
        } catch (Exception e) {
            System.out.println("WTAF?");
        }

        // for (int i = 0; i < sight.length; i++) {
        // if(i < ((sight.length/2) + (Math.abs(rotation) < Math.PI/2 ? 0 : 1))){
        // sightPoints[i] = PVector.fromAngle((float)(Math.PI +
        // Math.atan(sight[i].m))).mult(sightRange);
        // } else {
        // sightPoints[i] =
        // PVector.fromAngle((float)(Math.atan(sight[i].m))).mult(sightRange);
        // }
        // };
    }

    private void getSightPoint(int i) {
        float offset = 0.1f;

        PVector intersect = new LinearFormula(pos.x, pos.y, pos.x + sightPoints[i].x, pos.y + sightPoints[i].y)
                .intersect(targetFormula);
        intersect.sub(pos);
        while (true) {
            int posX = (int) (sightPoints[i].x + pos.x);
            int posY = (int) (sightPoints[i].y + pos.y);
            if (posX < 0 || posX >= track.width || posY < 0 || posY >= track.height) {
                sightPoints[i] = new PVector(0, 0);
                break;
            } else {
                TrackType t = track.grid[posX][posY];
                if (t == TrackType.GRASS) {
                    if (sightPoints[i].mag() < sightRange / 100f) {
                        sightPoints[i] = new PVector(0, 0);
                        break;
                    }
                    sightPoints[i].mult(1 - offset);
                    sightPointTypes[i] = t;
                } else if (t == TrackType.ROAD || t == TrackType.CHECKPOINT) {
                    if (sightPoints[i].mag() < sightRange) {
                        sightPoints[i].mult(1 + offset);
                    } else {
                        sightPointTypes[i] = TrackType.ROAD;
                        break;
                    }
                } else if (t == TrackType.WALL) {
                    sightPointTypes[i] = t;
                    break;
                } else {
                    System.out.println("U WOT M8?");
                    break;
                }
            }
        }
        // if (intersect.mag() < sightPoints[i].mag() && PVector.dot(intersect,
        // sightPoints[i]) > 0) {
        // sightPoints[i] = intersect;
        // sightPointTypes[i] = TrackType.CHECKPOINT;
        // }
        if (sightPointTypes[i] == null) {
            sightPointTypes[i] = TrackType.GRASS;
        }
    }

    private void checkCheckpoints() {
        if (Math.abs(targetFormula.m) < 1) {
            if (PVector.dist(pos, new PVector(pos.x, targetFormula.getY(pos.x))) < size
                    && PVector.dist(pos, targetFormula.originPoint) < Track.trackWidth) {
                target++;
                if (target >= track.checkPoints.size()) {
                    target = 0;
                }
                targetFormula = track.checkPoints.get(target);
            }
        } else {
            if (PVector.dist(pos, new PVector(targetFormula.getX(pos.y), pos.y)) < size) {
                target++;
                if (target >= track.checkPoints.size()) {
                    target = 0;
                }
                targetFormula = track.checkPoints.get(target);
            }
        }
    }

    public void display(PApplet p) {
        if (showTarget) {
            p.fill(255, 0, 0);
            p.ellipse(targetFormula.originPoint.x, targetFormula.originPoint.y, 10, 10);
        }

        p.pushMatrix();

        p.translate(pos.x, pos.y);
        p.rotate(rotation);

        p.fill(255, 0, 0);
        p.rectMode(PConstants.CENTER);
        p.rect(0, 0, size, size / 2);
        // p.beginShape();
        // p.vertex(0, 0);
        // p.vertex(-size/2, -size/2);
        // p.vertex(0, size);
        // p.vertex(size/2, -size/2);
        // p.endShape();

        p.rotate(-rotation);

        p.strokeWeight(1);
        if (showLines && sightPoints != null) {
            p.stroke(0);
            for (int i = 0; i < sight.length + 1; i++) {
                p.line(0, 0, sightPoints[i].x, sightPoints[i].y);
                p.ellipse(sightPoints[i].x, sightPoints[i].y, 10, 10);
            }
        }

        p.popMatrix();
    }
}
