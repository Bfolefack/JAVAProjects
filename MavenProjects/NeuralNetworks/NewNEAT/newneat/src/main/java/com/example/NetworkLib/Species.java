package com.example.NetworkLib;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.TreeSet;

import com.example.NetworkLib.Genes.Genome;
import com.example.NetworkLib.Utils.Config;

public class Species implements Comparable<Species>{
    public HashSet<Genome> population;
    public Genome representative;

    public Species(Genome founder){
        representative = founder;
        population = new HashSet<>();
        population.add(founder);
    }

    public Collection<Genome> refresh(Collection<Genome> genes){
        HashSet<Genome> adopted = new HashSet<>();
        population = new HashSet<>();
        for(Genome g : genes){
            if(Genome.isSameSpecies(representative, g)){
                adopted.add(g);
                population.add(g);
            }
        }

        double greatestFitness = -1;
        for(Genome g : population){
            if(g.fitness > greatestFitness){
                greatestFitness =  g.fitness;
                representative = g;
            }
        }
        return adopted;
    }

    public int cull(){
        TreeSet<Genome> selectedPopulation = new TreeSet<Genome>(new Comparator<Genome>() {
            @Override
            public int compare(Genome o1, Genome o2) {
                return ((Double)(o1.fitness)).compareTo(o2.fitness);
            }
        });
        if(population.size() == 0){
            return 0;
        }
        if(population.size() == 1){
            if(Math.random() < 1 - Config.POPULATION_CULL_PERCENTAGE){
                return 1;
            }
            return 0;
        }
        selectedPopulation.addAll(population);
        for(int i = 0; i < population.size() * Config.POPULATION_CULL_PERCENTAGE; i++){
            selectedPopulation.pollFirst();
        }
        population = new HashSet<>(selectedPopulation);
        return population.size();
    }

    @Override
    public int compareTo(Species o) {
        return ((Integer)population.size()).compareTo(o.population.size());
    }


}
