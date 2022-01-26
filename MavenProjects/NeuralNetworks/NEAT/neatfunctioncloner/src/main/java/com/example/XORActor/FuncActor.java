package com.example.XORActor;

import com.example.NEAT.Network.Genes.Genome;
import com.example.NEAT.Population.Actor;

public class FuncActor extends Actor{

    // XOR
    public static final double[][] in = new double[][] {{0, 0},{0, 1},{1, 0},{1, 1}};
    public static final double[] out = new double[] {-1, 1, 1, -1};
    //Circle
    // public static final double[][] in = new double[][] {{0, 0},{0, 1},{1, 0},{1, 1}, {0.5, 0},{0, 0.5},{1, 0.5},{0.5, 1}, {0.5, 0.5}};
    // public static final double[] out = new double[] {-1, -1, -1, -1, -1, -1, -1, -1, 1};
    private double score;
    private double attempts;

    public FuncActor() {
        super(2, 1, true);
    }

    public FuncActor(Genome b) {
        super(b);
    }

    @Override
    public double calculateFitness() {
        fitness = Math.pow((score/attempts) * 10, 4);
        return fitness;
    }

    @Override
    public void act() {
        // double[] in = new double[] {Math.random(), Math.random()};
        // double out = 0;
        // out = (Math.sqrt(Math.pow(in[0] - 0.5, 2) + Math.pow(in[1] - 0.5, 2)) * 4 - 1)/Math.pow(2, 0.5);
        // if(out < 0.4)
        //     out = -1;
        // else
        //     out = 1;
        // System.out.println(out);
        int rand = (int) (in.length * Math.random());
        if(rand > 8){
            rand = 8;
        }
        double result = brain.feedForward(in[rand])[0];
        score += Math.abs(out[rand] - result);
        attempts++;
        
    }

    public FuncActor breed(Actor b){
        Genome newBrain = Genome.crossover(brain, b.brain);
        newBrain.mutate();
        return new FuncActor(newBrain);
    }
    
}
