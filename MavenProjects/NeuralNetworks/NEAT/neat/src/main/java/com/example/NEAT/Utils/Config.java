package com.example.NEAT.Utils;

import java.util.Collection;

public class Config {
    public static final int AdditionTimeoutCounter = 1000000;

    //Crossover
    public static final double inheritDominantChance = 0.6;
    public static final double geneReenableChance = 0.75;
    
    //Speciation
    public static final double ExcessCoefficient = 1;
    public static final double weightDiffCoefficient = 0.5;
    public static final double ComptibilityThreshold = 2.5;

    //Mutation
    public static final double perturbWeightChance = 0.1;
    public static final double scrambleWeightChance = 0.08;
    public static final double pointMutationChance = 0.05;
    public static final double addConnectionChance = 0.1;
    public static final double addNodeChance = 0.02;

    public static Object getRandomObject(Collection from) {
        if (from.size() == 0)
            return null;
        double rand = Math.random() * from.size();
        int i = (int) (rand);
        int count = 0;
        for (Object o : from) {
            if (count == i)
                return o;
            count++;
        }
        System.out.println("ERROR: GET RANDOM OBJECT FAILURE");
        return null;
    }
}
