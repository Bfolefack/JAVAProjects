package com.example.NEAT.Utils;

import java.util.Collection;

public class Config {
    public static final int AdditionTimeoutCounter = 1000000;

    //Crossover
    public static final double inheritDominantChance = 0.5;
    public static final double geneReenableChance = 0.6;
    public static final double inbreedingPercentage = 0.8;
    
    //Speciation
    public static final double MismatchCoefficient = 1;
    public static final double weightDiffCoefficient = 0.5;
    public static final double CompatibilityThreshold = 0.75;

    //Mutation
    public static final double perturbWeightChance = 0.06;
    public static final double scrambleWeightChance = 0.03;
    public static final double pointMutationChance = 0.02;
    public static final double addConnectionChance = 0.075;
    public static final double addNodeChance = 0.03;

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
