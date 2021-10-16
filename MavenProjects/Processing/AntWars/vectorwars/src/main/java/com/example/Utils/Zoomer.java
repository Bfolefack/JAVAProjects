package com.example.Utils;

import com.example.VectorWars;

import processing.event.*;

public class Zoomer {
    public static float scale;
    public static float xPan;
    public static float yPan;
    static VectorWars sketch;
    static boolean initialized;

    public static void initialize(float _s, VectorWars p) {
        sketch = p;
        xPan = sketch.width / 2;
        yPan = sketch.height / 2;
        scale = _s;
        initialized = true;
    }

    public static void edgePan() {
        if (initialized) {
            if (sketch.mouseX > sketch.width - 75) {
                xPan += 10;
            } else if (sketch.mouseX < 75) {
                xPan -= 10;
            }

            if (sketch.mouseY > sketch.height - 75) {
                yPan += 10;
            } else if (sketch.mouseY < 75) {
                yPan -= 10;
            }
        }
    }

    public static void pushZoom() {
        if (initialized) {
            VectorWars.truMouseX = (int) ((sketch.mouseX + xPan - sketch.width / 2) / scale);
            VectorWars.truMouseY = (int) ((sketch.mouseY + yPan - sketch.height / 2) / scale);
            sketch.pushMatrix();
            sketch.translate(-xPan, -yPan);
            sketch.translate(sketch.width / 2, sketch.height / 2);
            sketch.scale(scale);
        }
    }

    public static void popZoom() {
        if (initialized) {
            sketch.popMatrix();
        }
    }

    public static void keyScale() {
        if (initialized) {
            if (sketch.keyPressed) {
                if (sketch.key == 'w') {
                    scale *= 1.02;
                    xPan *= 1.02;
                    yPan *= 1.02;
                }
                if (sketch.key == 's') {
                    scale *= .98;
                    xPan *= .98;
                    yPan *= .98;
                }
            }
        }
    }

    public static void mousePan() {
        if (initialized) {
            if (sketch.mousePressed) {
                xPan += (sketch.pmouseX - sketch.mouseX);
                yPan += (sketch.pmouseY - sketch.mouseY);
            }
        }
    }

    //
    // MUST BE PLACED INSIDE mouseWheel() METHOD
    public static void mouseScale(MouseEvent event, float scaleScale){ 
        if(initialized){
            float scaleAmt = 1 + (scaleScale * event.getCount());
            scale *= scaleAmt;
            xPan *= scaleAmt;
            yPan *= scaleAmt;
        }
    }
}
