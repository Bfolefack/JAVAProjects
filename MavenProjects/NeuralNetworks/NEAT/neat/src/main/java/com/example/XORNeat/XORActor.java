package com.example.XORNeat;

import com.example.NEAT.Network.Genes.Genome;
import com.example.NEAT.Population.Actor;

public class XORActor extends Actor {
    double score;
    double attempts;
    // public static final double[][] ins = { { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 }
    // };
    // public static final double[] outs = { -1, 1, 1, -1 };
    public static final double[][] ins = {
            { 0, 0, 0, 0 },
            { 1, 0, 0, 0 },
            { 0, 1, 0, 0 },
            { 1, 1, 0, 0 },
            { 0, 0, 1, 0 },
            { 1, 0, 1, 0 },
            { 0, 1, 1, 0 },
            { 1, 1, 1, 0 },
            { 0, 0, 0, 1 },
            { 1, 0, 0, 1 },
            { 0, 1, 0, 1 },
            { 1, 1, 0, 1 },
            { 0, 0, 1, 1 },
            { 1, 0, 1, 1 },
            { 0, 1, 1, 1 },
            { 1, 1, 1, 1 },};
    public static final double[] outs = {-1,-1,-1,-1,-1, 1,-1,-1,-1,-1, 1,-1,-1,-1,-1,-1};

    public XORActor(Genome b) {
        super(b);
    }

    public XORActor(int inputs, int outputs, boolean r) {
        super(inputs, outputs, r);
    }

    @Override
    public void act() {
        int rand = (int) (Math.random() * outs.length);
        int multiplier = 1;
        if(rand == 5 || rand == 10)
            multiplier *= 5;
        double guess = brain.feedForward(ins[rand])[0];
        score += (1 - Math.abs(outs[rand] - guess) / 2) * multiplier;
        attempts++;
    }

    @Override
    public double calculateFitness() {
        fitness = score / attempts;
        fitness = Math.pow(fitness * 2, 10);
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
