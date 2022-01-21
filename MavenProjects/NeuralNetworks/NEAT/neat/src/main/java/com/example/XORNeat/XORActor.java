package com.example.XORNeat;

import com.example.NEAT.Network.Genes.Genome;
import com.example.NEAT.Population.Actor;

public class XORActor extends Actor {
    double score;
    double attempts;
    public static final double[][] ins = { { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 } };
    public static final double[] outs = { -1, 1, 1, -1 };

    

    public XORActor(Genome b) {
        super(b);
    }



    public XORActor(int inputs, int outputs, boolean r) {
        super(inputs, outputs, r);
    }



    @Override
    public void act() {
        int rand = (int) (Math.random() * 4);
        double guess = brain.feedForward(ins[rand])[0];
        if(guess != 0){
            score+=0;
        }
        score += 1 - Math.abs(outs[rand]-guess)/2;
        attempts++;
    }

    @Override
    public double calculateFitness() {
        fitness = score/attempts;
        fitness = Math.pow(fitness * 100, 2);
        return fitness;
    }

    @Override
    public XORActor breed(Actor b) {
        Genome newBrain = Genome.crossover(brain, b.brain);
        // brain.mutate();
        newBrain.mutate();
        // Genome newBrain = brain;
        return new XORActor(newBrain);
    }
}
