package com.example.NEAT.Population;

import java.io.Serializable;

import com.example.NEAT.Network.Genes.Genome;

public class Actor implements Comparable<Actor>, Serializable{
    public Genome brain;
    public double fitness;
    public boolean resetAfterRun;
    public int[] inputs;
    public Actor(int inputs, int outputs, boolean r){
        resetAfterRun = r;
        brain = new Genome(inputs, outputs, resetAfterRun);
    }

    public Actor(Genome b){
        brain = b;
    }

    public void setInputs(int[] in){
        inputs = in;
    }

    public void act(){
        Thread.dumpStack();
    }

    public void display(){
        Thread.dumpStack();
    }

    public Actor breed(Actor b){
        Genome newBrain = Genome.crossover(brain, b.brain);
        newBrain.mutate();
        return new Actor(newBrain);
    }

    public double calculateFitness(){
        fitness = -1;
        Thread.dumpStack();
        return 0;
    }

    @Override
    public int compareTo(Actor o) {
        return ((Double) fitness).compareTo(o.fitness);
    }
}
