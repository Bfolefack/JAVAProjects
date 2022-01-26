package com.example.NEAT.Population;

import java.util.Collection;
import java.util.HashSet;

import com.example.NEAT.Network.Genes.Genome;
import com.example.NEAT.Utils.Config;
import com.example.NEAT.Utils.FitnessSelector;

public class Species<E extends Actor> implements Comparable<Species> {
    FitnessSelector<E> members = new FitnessSelector<>();
    E representative;
    double bestFitness;
    double bestGenerationFitness;
    double averageFitness;
    int stale = 0;

    public Species(E rep) {
        representative = rep;
        members.add(rep);
        bestFitness = rep.fitness;
        averageFitness = rep.fitness;
    }

    public boolean add(E interloper) {
        if (interloper != null)
            if (isRelated(interloper)) {
                members.add(interloper);
                return true;
            }
        return false;
    }

    public boolean isRelated(E interloper) {
        float mismatch = getMismatch(representative.brain, interloper.brain);
        float matchingWeightDiff = getWeightDiff(representative.brain, interloper.brain);

        float bigBoyFlattener = interloper.brain.connections.size() - 20;
        if (bigBoyFlattener < 1) {
            bigBoyFlattener = 1;
        }

        return Config.CompatibilityThreshold > Config.MismatchCoefficient * mismatch / bigBoyFlattener
                + matchingWeightDiff * Config.weightDiffCoefficient;
    }

    private static float getMismatch(Genome g1, Genome g2) {
        HashSet<Integer> matching = new HashSet<>();
        matching.addAll(g1.connections.keySet());
        matching.addAll(g2.connections.keySet());
        HashSet<Integer> disjoint = new HashSet<>(matching);
        matching.retainAll(g1.connections.keySet());
        matching.retainAll(g2.connections.keySet());
        disjoint.removeAll(matching);
        return disjoint.size();
    }

    private static float getWeightDiff(Genome g1, Genome g2) {
        if (g1.connections.size() == 0 || g2.connections.size() == 0) {
            return 0;
        }

        float matching = 0;
        float diff = 0;

        HashSet<Integer> genes = new HashSet<>(g1.connections.keySet());
        genes.addAll(g2.connections.keySet());
        genes.retainAll(g1.connections.keySet());
        genes.retainAll(g2.connections.keySet());

        for (int i : genes) {
            matching++;
            diff += Math.abs(g1.connections.get(i).weight - g2.connections.get(i).weight);
        }

        if (matching == 0) {
            return 500;
        }

        return diff / matching;
    }

    @Override
    public int compareTo(Species o) {
        return ((Double) bestGenerationFitness).compareTo(o.bestGenerationFitness);
    }

    public E getRandom() {
        if (size() > 0)
            return members.randomFitSelect();
        System.out.println("MEMBERS EMPTY ERRROR");
        Thread.dumpStack();
        return null;
    }

    public void clear() {
        members.clear();
    }

    public void cull() {
        if (members.size() < 2) {
            stale = 600000;
            return;
        }
        int cull = members.size() / 2;
        if (members.size() == 0)
            return;
        for (int i = 0; i < cull; i++) {
            if (members.size() > 1)
                members.pollFirst();
        }
        representative = members.getFittest();
    }

    public void calcFitValues() {
        if (size() > 0) {
            averageFitness = members.averageFitness();
            double tempFitness = members.getFittest().fitness;
            bestGenerationFitness = tempFitness + Math.random()/10000;
            if (tempFitness < bestFitness) {
                stale++;
            } else {
                bestFitness = tempFitness;
                stale = 0;
            }
        }
    }

    public void fitSharing() {
        for (E e : members.map.values()) {
            // e.fitness /= members.size();
        }

    }

    public Collection<E> reproduce(double size) {
        HashSet<E> newGen = new HashSet<>();
        for (int i = 0; i < size; i++) {
            E e = reproduce();
            if (e == null) {
                i--;
                continue;
            }
            newGen.add(reproduce());
        }
        return newGen;
    }

    private E reproduce() {
        E top = getRandom();
        E bottom = getRandom();
        if (bottom.fitness > top.fitness) {
            E temp = top;
            top = bottom;
            bottom = temp;
        }
        return (E) top.breed(bottom);
    }

    public E reproduce(Species<E> other) {
        try {
            E top = getRandom();
            E bottom = other.getRandom();
            if (bottom.fitness > top.fitness) {
                E temp = top;
                top = bottom;
                bottom = temp;
            }
            return (E) top.breed(bottom);
        } catch (Exception e) {
            return null;
        }

    }

    public E getFittest() {
        return members.getFittest();
    }

    public double size() {
        return members.size();
    }

}
