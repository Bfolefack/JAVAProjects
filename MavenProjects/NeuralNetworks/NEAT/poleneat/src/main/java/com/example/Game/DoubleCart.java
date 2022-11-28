package com.example.Game;

import java.io.Serializable;

import javafx.scene.shape.Ellipse;
import processing.core.PApplet;

public class DoubleCart implements Serializable{
    public static final float gravity = -9.8f;
    public static final float timeStep = 0.005f;
    public static final float scale = 100;
    public static final float friction = 0.95f;
    public static final float variability = 0.001f;

    public Pole[] poles;

    public class Pole implements Serializable{
        public float poleLength = 4f;
        public float poleMass = 0.1f;

        public float polePos;
        public float poleVel;
        public float poleAcc;
        
        public Pole(){}

        public Pole(float l){
            poleLength = l;
        }

        public Pole(float l, float m){
            poleLength = l;
            poleMass = m;
        }

        public void update(float force) {
            poleAcc = (float) (((gravity * Math.sin(polePos)) + Math.cos(polePos)
                    * ((-force - poleMass * poleLength * Math.pow(poleVel, 2) * Math.sin(polePos))
                            / (poleMass + cartMass)))
                    / (poleLength
                            * ((4 / 3f) - ((poleMass * Math.pow(Math.cos(polePos), 2) / (cartMass + poleMass))))));
            poleVel += poleAcc * timeStep;
            poleVel *= friction;
            polePos += poleVel;
        }
    }

    public float cartMass = 1;

    public float cartPos;
    public float cartVel;
    public float cartAcc;

    public DoubleCart() {
        poles = new Pole[]{new Pole(2, 0.1f), new Pole(4, 0.2f)};
        for (int i = 0; i < poles.length; i++) {
            poles[i].polePos = (float) (Math.PI + (Math.random()-0.5) * variability);
        }
    }

    public DoubleCart(float cp, float pp) {
        cartPos = cp;
        poles = new Pole[2];
        for (int i = 0; i < poles.length; i++) {
            poles[i] = new Pole();
            poles[i].polePos = pp;
        }
    }

    public void update(float force) {
        cartAcc = 0;

        for (Pole pole : poles) {
            pole.update(force);
        }
        for (Pole pole : poles) {
            cartAcc += (float) ((force
                    + pole.poleMass * pole.poleLength
                            * (Math.pow(pole.poleVel, 2) * Math.sin(pole.polePos)
                                    - pole.poleAcc * Math.cos(pole.polePos)))
                    / (cartMass + pole.poleMass));

            if (pole.polePos > Math.PI * 2) {
                pole.polePos = 0;
            } else if (pole.polePos < 0) {
                pole.polePos = (float) Math.PI * 2;
            }
        }

        cartVel += cartAcc * timeStep;
        cartVel *= friction;
        cartPos += cartVel;

    }

    public void display(PApplet sketch) {
        sketch.pushMatrix();
        sketch.translate(cartPos * scale + sketch.width / 2, sketch.height / 2);
        sketch.rect(-scale * 2, 0, scale * 4, scale * 2);

        for (Pole pole : poles) {
            sketch.line(0, 0, (float) Math.sin(pole.polePos) * pole.poleLength * scale,
                    (float) Math.cos(pole.polePos) * pole.poleLength * scale);
            sketch.ellipse((float) Math.sin(pole.polePos) * pole.poleLength * scale,
                    (float) Math.cos(pole.polePos) * pole.poleLength * scale,
                    pole.poleMass * scale, pole.poleMass * scale);
        }
        sketch.popMatrix();
    }
}
