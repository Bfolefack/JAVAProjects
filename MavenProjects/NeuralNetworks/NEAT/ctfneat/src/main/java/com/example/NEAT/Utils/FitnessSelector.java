package com.example.NEAT.Utils;

import java.io.Serializable;
import java.util.TreeMap;

import com.example.NEAT.Population.Actors.Actor;

public class FitnessSelector<E extends Actor> implements Serializable{
    public TreeMap<Double, E> map;
    public double totalFit;

    public FitnessSelector() {
        map = new TreeMap<>();
    }

    public void add(E actor) {
        if (actor != null) {
            totalFit += actor.batchFitness;
            map.put(actor.batchFitness + Math.random() / 100000, actor);
        }
        // map.remove(null);
    }

    public void remove(E actor) {
        if (actor != null) {
            totalFit -= actor.batchFitness;
            map.remove(actor.batchFitness);
        }
    }

    public E getFittest() {
        if (map.lastEntry() != null)
            return map.lastEntry().getValue();
        return null;
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
        double count = totalFit;
        for (Double dub : map.keySet()) {
            count -= dub;
            if (count < d) {
                return map.get(dub);
            }
        }
        System.out.println("REACHED END OF RANDOM FIT SELECT ERROR");
        // System.out.println(totalFit);
        // System.out.println(d);
        // Thread.dumpStack();
        return map.lastEntry().getValue();
    }

    public E trueRandomSelect() {
        System.out.println("TRUE RANDOM SELECT ERROR");
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

    public void makePositive() {
        double lowestFitnesss = map.pollFirstEntry().getKey();
        if(lowestFitnesss < 0) {
            for (Double key : map.keySet()) {
                map.put(key - (lowestFitnesss - 0.001), map.get(key));
            }
            totalFit -= lowestFitnesss;
        }
    }
}