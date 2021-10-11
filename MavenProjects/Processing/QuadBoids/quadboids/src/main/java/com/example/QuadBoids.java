package com.example;

import java.util.Map;
import java.util.TreeMap;

import com.example.Boids.*;

import processing.core.PApplet;

public class QuadBoids extends PApplet {
    Population population;
    Map<Character, Integer> flocks;

    public static void main(String[] passedArgs) {
        String[] processingArgs = { "MySketch" };
        QuadBoids mySketch = new QuadBoids();
        PApplet.runSketch(processingArgs, mySketch);
    }

    public void settings() {
        size(1500, 700);
        smooth(3);
    }

    public void setup() {
        flocks = new TreeMap<>();
        population = new Population(this);
        noStroke();
        // Boid b = new Boid(width/2, height/2,(int) (Math.random() * 1000000000), new
        // Population(this));
        // b.display(this);
    }

    public void draw() {
        if (keyPressed) {

            switch (key) {
                case 't':
                    population.showTree = !population.showTree;
                    break;
                case '1':
                    population.method = 0;
                    break;
                case '2':
                    population.method = 1;
                    break;
                case '3':
                    population.method = 2;
                    break;
                default:
                    for (int i = 0; i < 100; i++)
                        if (flocks.get(key) != null) {
                            population.addBoid((mouseX), mouseY, flocks.get(key));
                        } else {
                            flocks.put(key, (int) (Math.random() * 1000000000));
                        }
                    break;
            }
        }
        background(255);
        fill(0);
        text(population.boids.size(), 0, 10);
        text(frameRate, 0, 20);
        // System.out.println(population.boids);
        population.display();
        population.update();
    }
}
