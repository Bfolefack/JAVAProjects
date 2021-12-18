package com.example.Number;

import processing.core.PApplet;
import processing.core.PImage;

public class Num {
    public int identifier;
    public double[] pixels;
    public PImage image;

    public Num(int id, double[] pix, PApplet app){
        identifier = id;
        pixels = pix;
        image = new PImage(28, 28);
        for (int i = 0; i < pix.length; i++) {
            image.pixels[i] = app.color((float)pixels[i]);
        }
        for (int i = 0; i < pix.length; i++) {
            pixels[i]/=255;
        }
    }

    public static double[] toIntArr(int in){
        double[] out = new double[10];
        out[in] = 1;
        return out;
    }
}
