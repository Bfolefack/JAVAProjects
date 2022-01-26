package com.example;

import java.util.HashSet;

import com.example.NEAT.Population.Population;
import com.example.XORActor.FuncActor;

import processing.core.PApplet;

/**
 * Hello world!
 *
 */
public class FunctionCloner extends PApplet {
    Population<FuncActor> pop;

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
        noStroke();
        HashSet<FuncActor> temp = new HashSet<>();
        for (int i = 0; i < 200; i++) {
            temp.add(new FuncActor());
        }
        pop = new Population<>(temp);
        // pop = new Population<FuncActor>("566486");
    }

    @Override
    public void draw() {
        for (int i = 0; i < 100; i++) {
            pop.act();
        }

        pop.generation();
        

        // System.out.println("stahp");
        FuncActor bestest = pop.best;
        if (bestest != null)
            for (int i = 0; i <= 50; i++)
                for (int j = 0; j <= 50; j++) {
                    double d = bestest.brain.feedForward(new double[] { i / 50.0, j / 50.0 })[0];
                    fill((float) (d + 1)/2 * 255);
                    rect(i * 10, j * 10, 10, 10);
                }
    }

    @Override
    public void keyPressed() {
        if(key  == 's'){
            pop.savePopulation();
        }
    }
}
