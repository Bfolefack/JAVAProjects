package com.example.NEAT.Utils;

import java.util.TreeMap;

import com.example.NEAT.Population.Actor;

public class FitnessSelector<E extends Actor> {
    public TreeMap<Double, E> map;
    public double totalFit;

    public FitnessSelector() {
        map = new TreeMap<>();
    }

    public void add(E actor) {
        if (actor != null) {
            totalFit += actor.fitness;
            map.put(actor.fitness + Math.random() / 100000, actor);
        }
        // map.remove(null);
    }

    public E getFittest() {
        return map.lastEntry().getValue();
    }

    public double averageFitness() {
        return totalFit / map.size();
    }

    public E randomFitSelect() {
        if (size() == 0) {
            System.out.println("RANDOM FIT SELECT ERROR");
            Thread.dumpStack();
            return null;
        }
        if (size() == 1)
            for (Double dub : map.keySet())
                return map.get(dub);

        double d = Math.random() * totalFit;
        double count = 0;
        for (Double dub : map.keySet()) {
            count += dub;
            if (count > d) {
                return map.get(dub);
            }
        }
        System.out.println("REACHED END OF RANDOM FIT SELECT ERROR");
        Thread.dumpStack();
        return null;
    }

    public E trueRandomSelect() {
        int num = (int) (Math.random() * map.size());
        int count = 0;
        for (E entity : map.values()) {
            if (count == num) {
                return entity;
            }
            count++;
        }
        return null;
    }

    public void clear() {
        map.clear();
        totalFit = 0;
    }

    public int size() {
        return map.size();
    }

    public void pollFirst() {
        if (size() > 1)
            totalFit -= map.pollFirstEntry().getKey();
    }
}
