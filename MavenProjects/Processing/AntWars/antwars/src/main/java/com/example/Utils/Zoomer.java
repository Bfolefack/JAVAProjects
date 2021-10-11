package com.example.Utils;

import com.example.AntWars;

import processing.event.*;

public class Zoomer {
    public float scale;
    public float xPan;
    public float yPan;
    AntWars sketch;

    public Zoomer(float _s, AntWars p) {
        sketch = p;
        xPan = sketch.width / 2;
        yPan = sketch.height / 2;
        scale = _s;
    }

    void edgePan() {
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

    public void pushZoom() {
        AntWars.truMouseX = (int) ((sketch.mouseX + xPan - sketch.width / 2) / scale);
        AntWars.truMouseY = (int) ((sketch.mouseY + yPan - sketch.height / 2) / scale);
        sketch.pushMatrix();
        sketch.translate(-xPan, -yPan);
        sketch.translate(sketch.width / 2, sketch.height / 2);
        sketch.scale(scale);
    }

    public void popZoom() {
        sketch.popMatrix();
    }

    void keyScale() {
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

    public void mousePan() {
        if (sketch.mousePressed) {
            xPan += (sketch.pmouseX - sketch.mouseX);
            yPan += (sketch.pmouseY - sketch.mouseY);
        }
    }

    //
    // MUST BE PLACED INSIDE mouseWheel() METHOD
    public void mouseScale(MouseEvent event, float scaleScale) {
        float scaleAmt = 1 + (scaleScale * event.getCount());
        scale *= scaleAmt;
        xPan *= scaleAmt;
        yPan *= scaleAmt;
    }
}
