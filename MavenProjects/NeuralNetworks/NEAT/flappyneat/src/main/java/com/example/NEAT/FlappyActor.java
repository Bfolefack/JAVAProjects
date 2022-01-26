package com.example.NEAT;

import com.example.FlappyNeat;
import com.example.Game.Bird;
import com.example.NEAT.Network.Genes.Genome;
import com.example.NEAT.Population.Actor;

public class FlappyActor extends Actor {

    public static FlappyNeat app;

    public boolean alive;
    public float aliveCounter;
    public float pipeCounter;
    public Bird bird;

    public FlappyActor(boolean r) {
        super(4, 1, r);
        bird = new Bird();
        alive = true;
    }

    public FlappyActor(Genome b) {
        super(b);
        bird = new Bird();
        alive = true;
    }

    @Override
    public void act() {
        if (alive) {
            if (!bird.checkSafe(app.nextPipe)) {
                alive = false;
            }
            if (alive == true) {
                aliveCounter++;
            }
            if (app.pipeCleared) {
                pipeCounter++;
            }
            double[] ins = { (double) bird.height / app.height, (double) bird.vel / 15,
                    (double) app.nextPipe.height / app.height, (double) app.nextPipe.xPos / app.width };
            double choice = brain.feedForward(ins)[0];
            if (choice > 0)
                bird.jump();
            bird.update(app);
        }
    }

    @Override
    public FlappyActor breed(Actor b) {
        Genome newBrain = Genome.crossover(brain, b.brain);
        newBrain.mutate();
        return new FlappyActor(newBrain);
    }

    @Override
    public void display() {
        if (alive) {
            bird.display(app);
        }
    }

    @Override
    public double calculateFitness() {
        fitness = Math.pow(0.01 * pipeCounter + 0.001 * aliveCounter, 4);
        return fitness;
    }

    @Override
    public void setInputs(int[] in) {
        // TODO Auto-generated method stub

    }
}
