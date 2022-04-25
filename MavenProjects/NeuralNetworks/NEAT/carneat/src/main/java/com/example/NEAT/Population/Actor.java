package com.example.NEAT.Population;

import java.io.Serializable;

import com.example.NEAT.Network.Genes.Genome;

public abstract class Actor implements Comparable<Actor>, Serializable{
    public Genome brain;
    public double fitness;
    public boolean resetAfterRun;
    public boolean alive = true;
    public int[] inputs;
    public Actor(int inputs, int outputs, boolean r){
        resetAfterRun = r;
        brain = new Genome(inputs, outputs, resetAfterRun);
    }

    public Actor(Genome b){
        brain = b;
    }

    public abstract void setInputs(int[] in);

    public abstract void act();

    public abstract void display();

    public  abstract Actor breed(Actor b);

    public abstract double calculateFitness();

    public abstract Actor clone();

    @Override
    public int compareTo(Actor o) {
        return ((Double) fitness).compareTo(o.fitness);
    }
}
