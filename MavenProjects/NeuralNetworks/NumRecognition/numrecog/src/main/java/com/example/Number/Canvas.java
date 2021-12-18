package com.example.Number;

import processing.core.PApplet;
import processing.core.PImage;

public class Canvas {
    public double[] d;
    public PImage image;

    public Canvas() {
        d = new double[784];
        image = new PImage(28, 28);
        for (int i = 0; i < d.length; i++) {
            image.pixels[i] = (int) (d[i] * 255);
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
                            d[(mouseY + k) * 28 + mouseX + l] += (1f / ((Math.abs(k) + Math.abs(l) + 1) * 4));
                        } catch (Exception e) {
                        }
                }
            }
        }
        for (int i = 0; i < d.length; i++) {
            if(d[i] > 1)
                d[i] = 1;
        }
        for (int i = 0; i < d.length; i++) {
            image.pixels[i] = p.color((float)(d[i] * 255));
        }
        image.updatePixels();
    }
}
