package com.example;

import processing.core.PApplet;

/**
 * Hello world!
 *
 */
public class FactorioSolver extends PApplet
{
    public static void main(String[] passedArgs) {
        String[] processingArgs = { "MySketch" };
        FactorioSolver mySketch = new FactorioSolver();
        PApplet.runSketch(processingArgs, mySketch);
    }

    public void settings() {
        size(1500, 700);
        // smooth(3);
    }
}
