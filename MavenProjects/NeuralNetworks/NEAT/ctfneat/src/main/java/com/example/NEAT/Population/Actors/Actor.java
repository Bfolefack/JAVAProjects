package com.example.NEAT.Population.Actors;

import java.io.Serializable;

import com.example.NEAT.Network.Genes.GeneLibrary;
import com.example.NEAT.Network.Genes.Genome;

public abstract class Actor implements Comparable<Actor>, Serializable{
    public Genome brain;
    protected double epochFitness;
    public double batchFitness;
    public boolean resetAfterRun;
    public boolean alive = true;
    public int[] inputs;
    public Actor(int inputs, int outputs, boolean r, GeneLibrary gl) {
        resetAfterRun = r;
        brain = new Genome(inputs, outputs, resetAfterRun, gl);
    }

    public Actor(Genome b){
        brain = b;
    }

    public abstract void setInputs(int[] in);

    public abstract void act();

    public abstract void display();

    public  abstract Actor breed(Actor b);

    public abstract double calculateFitness();

    public abstract void normalizeFitness();

    public abstract void epoch();

    public abstract Actor clone();

    @Override
    public int compareTo(Actor o) {
        int comp =  ((Double) batchFitness).compareTo(o.batchFitness);
        return comp == 0 ? 1 : comp;
    }
}
