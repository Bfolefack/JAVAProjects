package com.example;

import java.util.HashSet;

import com.example.NEAT.Population.Population;
import com.example.XORActor.XORActor;

import processing.core.PApplet;

/**
 * Hello world!
 *
 */
public class FunctionCloner extends PApplet {
    Population<XORActor> pop;

    public static void main(String[] args) {
        String[] processingArgs = { "MySketch" };
        FunctionCloner mySketch = new FunctionCloner();
        PApplet.runSketch(processingArgs, mySketch);
    }

    public void settings() {
        size(500, 500);
    }

    @Override
    public void setup() {
        HashSet<XORActor> temp = new HashSet<>();
        for (int i = 0; i < 200; i++) {
            temp.add(new XORActor());
        }
        pop = new Population<>(temp);
    }

    @Override
    public void draw() {
        for (int i = 0; i < 50; i++) {
            pop.act();
        }

        pop.generation();

        XORActor best = pop.best;
        if (best != null)
            for (int i = 0; i <= 50; i++)
                for (int j = 0; j <= 50; j++) {
                    double d = best.brain.feedForward(new double[] { i / 50.0, j / 50.0 })[0];
                    fill((float) (d + 1)/2 * 255);
                    rect(i * 10, j * 10, 10, 10);
                }
    }
}
