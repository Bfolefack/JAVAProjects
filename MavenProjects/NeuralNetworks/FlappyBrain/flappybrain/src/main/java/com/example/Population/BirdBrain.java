package com.example.Population;

import com.example.Game.Bird;
import com.example.Game.Pipe;
import com.example.NetworkLib.NeuralNetwork;

import processing.core.PApplet;

public class BirdBrain implements Comparable<BirdBrain> {
    public NeuralNetwork brain;
    public int score;
    Bird bird;
    public Double fitness;
    public boolean alive;

    public BirdBrain() {
        // [Bird Height, Bird Vel, Pipe Height, Pipe XPos]
        bird = new Bird();
        alive = true;
        brain = new NeuralNetwork(4, 8, 1, 2, 0);
        fitness = 0.0;
    }

    public BirdBrain(NeuralNetwork nn){
        bird = new Bird();
        alive = true;
        brain = nn;
        fitness = 0.0;
    }

    public BirdBrain(BirdBrain parent1, BirdBrain parent2) {
        alive = true;
        bird = new Bird();
        fitness = 0.0;

        brain = NeuralNetwork.crossover(parent1.brain, parent2.brain, 0.05);
        brain.mutate(0.05, 0.01);
    }

    public void update(Pipe pipe, PApplet app) {
        fitness += 1;
        if (bird.checkHit(pipe)) {
            fitness -= 10;
            alive = false;
        }
        if(alive && pipe.xPos == 100){
            score++;
        }
        bird.update(app);

        double jump = brain.guess(
                new double[] { bird.height / 800, (bird.vel + 13) / 26f, pipe.height / 800, pipe.xPos / 1000 })[0];
        if (jump > 0.5) {
            bird.jump();
        }
    }

    public void display(PApplet app) {
        bird.display(app);
    }

    @Override
    public int compareTo(BirdBrain o) {
        return fitness.compareTo(o.fitness);
    }

    public void save(String s) {
        try {
            brain.save(s);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
