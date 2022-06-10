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
        frameRate(144);
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
                case 'r':
                    for (int i = 0; i < 25; i++)
                        population.addBoid((mouseX), mouseY, 300300400, false, "rock");
                    break;
                case 'p':
                    for (int i = 0; i < 25; i++)
                        population.addBoid((mouseX), mouseY, 899845800, false, "paper");
                    break;
                case 's':
                    for (int i = 0; i < 25; i++)
                        population.addBoid((mouseX), mouseY, 500500500, false, "scissors");
                    break;
                default:
                    for (int i = 0; i < 25; i++)
                        if (flocks.get(key) != null) {
                            population.addBoid((mouseX), mouseY, flocks.get(key), key == 'p');
                        } else {
                            println(key);
                            flocks.put(key, key == 'p' ? 999000000 : (int) (Math.random() * 1000000000));
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
