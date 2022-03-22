package com.example.Game.BasicCartSolver;

import java.util.HashSet;
import java.util.TreeMap;

import com.example.PoleNEAT;

import processing.core.PApplet;

public class BasicCartSolver {
    public HashSet<BasicCart> population;
    public int generation = 1;

    public BasicCartSolver(int populationSize) {
        population = new HashSet<>();
        for (int i = 0; i < populationSize; i++) {
            population.add(new BasicCart());
        }
    }

    public void update(PApplet p) {
        boolean anyAlive = false;
        for (BasicCart basicCart : population) {
            if (basicCart.update()) {
                if (!anyAlive && PoleNEAT.display)
                    basicCart.display(p);
                anyAlive = true;
            }
        }
        if(!anyAlive)
            generation();
    }

    private void generation() {
        generation++;
        double total = 0;
        TreeMap<Double, BasicCart> map = new TreeMap<>();
        
        for (BasicCart basicCart : population) {
            double temp = Math.pow(basicCart.fitness + Math.random(), 2);
            total += temp;
            map.put(temp, basicCart);
        }
        for (int i = 0; i < population.size()/2; i++) {
            total -= map.pollFirstEntry().getKey();
        }
        System.out.println("GENERATION " + generation + ":");
        System.out.println("GREATEST FITNESS: " +  Math.sqrt(map.lastKey()));
        System.out.println();
        HashSet<BasicCart> nextGen = new HashSet<>();
        for (int i = 0; i < population.size(); i++) {
            nextGen.add(BasicCart.breed(getWeightedRandom(total, map), getWeightedRandom(total, map)).mutate(0.1f));
        }
        population = nextGen;
    }

    public BasicCart getWeightedRandom(double total, TreeMap<Double, BasicCart> map){
        double temp = Math.random() * total;
        double count = 0;
        for (Double d : map.keySet()) {
            count += d;
            if(count > temp){
                return map.get(d);
            }
        }
        return null;
    }
}
