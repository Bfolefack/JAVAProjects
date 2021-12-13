package com.example.NetworkLib.Utils;

import java.util.ArrayList;

public class FitnessSelector<E> {
    private ArrayList<E> objs;
    private ArrayList<Double> scores;
    private double totalScore;

    public FitnessSelector(){
        objs = new ArrayList<>();
        scores = new ArrayList<>();
    }

    public void add(E e, double score){
        objs.add(e);
        scores.add(score);
        totalScore += score;
    }

    public E random(){
        return objs.get((int)(Math.random() * objs.size()));
    }

    public E weightedRandom(){
        double d =  Math.random() * totalScore;
        double c = 0;
        for (int i = 0; i < objs.size(); i++) {
            c += scores.get(i);
            if(c >= d){
                return objs.get(i);
            }
        }
        return null;
    }

    public void clear(){
        objs.clear();
        scores.clear();
        totalScore = 0;
    }
}
