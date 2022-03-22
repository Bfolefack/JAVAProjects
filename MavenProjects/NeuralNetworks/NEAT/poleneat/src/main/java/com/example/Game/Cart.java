package com.example.Game;

import javafx.scene.shape.Ellipse;
import processing.core.PApplet;

public class Cart {
    public static final float gravity = -9.8f;
    public static final float timeStep = 0.01f;
    public static final float scale = 100;
    public static final float friction = 0.95f;

    public float poleLength = 1.5f;
    public float poleMass = 0.1f;
    public float cartMass = 1;

    public float polePos;
    public float poleVel;
    public float poleAcc;

    public float cartPos;
    public float cartVel;
    public float cartAcc;

    public Cart() {
        polePos = (float) (Math.PI + Math.random() * 0.001);
    }

    public Cart(float cp, float pp) {
        cartPos = cp;
        polePos = pp;
    }

    public void update(float force) {
        poleAcc = (float) (((gravity * Math.sin(polePos)) + Math.cos(polePos)
                * ((-force - poleMass * poleLength * Math.pow(poleVel, 2) * Math.sin(polePos)) / (poleMass + cartMass)))
                / (poleLength * ((4 / 3f) - ((poleMass * Math.pow(Math.cos(polePos), 2) / (cartMass + poleMass))))));
        cartAcc = (float)((force + poleMass*poleLength*(Math.pow(poleVel, 2) * Math.sin(polePos) - poleAcc * Math.cos(polePos)))/(cartMass+poleMass));
        poleVel += poleAcc * timeStep;
        poleVel *= friction;
        polePos += poleVel;
        cartVel += cartAcc * timeStep;
        cartVel *= friction;
        cartPos += cartVel;
        if(polePos > Math.PI * 2){
            polePos = 0;
        } else if(polePos < 0){
            polePos = (float)Math.PI * 2;
        }
    }

    public void display(PApplet sketch){
        sketch.pushMatrix();
        sketch.translate(cartPos * scale + sketch.width/2, sketch.height/2);
        sketch.rect(-scale, -50, 200, scale);
        sketch.line(0, 0, (float) Math.sin(polePos) * poleLength * scale, (float) Math.cos(polePos) * poleLength * scale);
        sketch.ellipse((float) Math.sin(polePos) * poleLength * scale, (float) Math.cos(polePos) * poleLength * scale, poleMass * scale, poleMass * scale);
        sketch.popMatrix();
    }
}
