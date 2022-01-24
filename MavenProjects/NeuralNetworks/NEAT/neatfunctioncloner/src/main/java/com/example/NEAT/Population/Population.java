package com.example.NEAT.Population;

import java.util.Collection;
import java.util.HashSet;
import java.util.TreeSet;

import com.example.NEAT.Utils.Config;

public class Population<E extends Actor> {
    public TreeSet<Species<E>> species;
    public HashSet<E> actors;
    double bestFitness;
    double highScoreFitness;
    int populationSize;
    int generation;
    public E best;

    public Population(Collection<E> founders) {
        populationSize = founders.size();
        species = new TreeSet<>();
        actors = new HashSet<>();
        actors.addAll(founders);
        sortSpecies(actors);
    }

    public void act() {
        actors.remove(null);
        for (E e : actors) {
            e.act();
        }
    }

    public void generation() {
        generation++;
        calculateFitness();
        sortSpecies(actors);
        TreeSet<Species<E>> good = new TreeSet<>();
        for (Species s : species) {
            if (s.members.map.size() > 1 && s.stale < 15) {
                good.add(s);
                // System.out.println(s.members.map.size());
            }
            if (species.size() == 1)
                good.add(s);
        }
        species = good;
        bestFitness = 0;
        for (Species s : species) {
            s.cull();
            s.calcFitValues();
            if (s.bestFitness >= bestFitness) {
                bestFitness = s.bestFitness;
                best = (E) s.getFittest();
            }
            s.fitSharing();
        }

        if (bestFitness > highScoreFitness) {
            highScoreFitness = bestFitness;
        }
        System.out.println("GENERATION: " + generation);
        System.out.println("BEST FITNESS: " + bestFitness);
        System.out.println("HIGHSCORE FITNESS: " + highScoreFitness);
        System.out.println("SPECIES: " + species.size());
        HashSet<E> children = new HashSet<>();
        
        for (Species s : species) {
            children.addAll(s.reproduce(s.size() * (Config.inbreedingPercentage * 2)));
        }

        while (children.size() < populationSize) {
            children.add(((Species<E>) Config.getRandomObject(species))
                    .reproduce((Species<E>) Config.getRandomObject(species)));
        }
        actors = children;
    }

    private void calculateFitness() {
        for (E e : actors) {
            e.calculateFitness();
        }
    }

    public void sortSpecies(Collection<E> members) {
        for (Species s : species) {
            s.clear();
        }
        for (E member : members) {
            boolean added = false;
            for (Species<E> s : species) {
                if (s.add(member)) {
                    added = true;
                    break;
                }
            }
            if (!added)
                species.add(new Species<E>(member));
        }
    }
}
