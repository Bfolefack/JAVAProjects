package com.example.Number;

import processing.core.PApplet;
import processing.core.PImage;

public class Canvas {
    public float[] f;
    public PImage image;

    public Canvas() {
        f = new float[784];
        image = new PImage(28, 28);
        for (int i = 0; i < f.length; i++) {
            image.pixels[i] = (int) (f[i] * 255);
        }
    }

    public void update(PApplet p) {
        int mouseX = (p.mouseX - 250) / 18;
        int mouseY = (p.mouseY - 300) / 18;
        if (p.mousePressed) {
            if (mouseX > 27 || mouseX < 0 || mouseY > 27 || mouseY < 0) {
                return;
            }
            for (int k = -1; k < 2; k++) {
                for (int l = -1; l < 2; l++) {
                    // if (Math.abs(k) + Math.abs(l) < 2)
                        try {
                            f[(mouseY + k) * 28 + mouseX + l] += (1f / ((Math.abs(k) + Math.abs(l) + 1) * 4));
                        } catch (Exception e) {
                        }
                }
            }
        }
        for (int i = 0; i < f.length; i++) {
            image.pixels[i] = p.color((f[i] * 255));
        }
        image.updatePixels();
    }
}
