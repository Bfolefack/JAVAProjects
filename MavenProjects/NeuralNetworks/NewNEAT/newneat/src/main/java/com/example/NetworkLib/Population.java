package com.example.NetworkLib;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

import com.example.NetworkLib.Genes.ConnectionGene;
import com.example.NetworkLib.Genes.Genome;
import com.example.NetworkLib.Genes.NodeGene;
import com.example.NetworkLib.Utils.Config;
import com.example.NetworkLib.Utils.ConnectionGeneContainer;
import com.example.NetworkLib.Utils.FitnessSelector;

public class Population {
    public int populationSize;
    public int inputs;
    public int outputs;
    HashSet<Genome> population;
    TreeSet<Species> species;
    private HashMap<Integer, NodeGene> nodes;
    private ConnectionGeneContainer<Integer, Integer, ConnectionGene> connections;

    public Population(int in, int out, int pop) {
        nodes = new HashMap<>();
        connections = new ConnectionGeneContainer<>();
        species = new TreeSet<>();
        inputs = in + 1;
        outputs = out;
        populationSize = pop;
        population = new HashSet<>();
        for (int i = 0; i < inputs + outputs; i++) {
            if (i < inputs)
                nodes.put(i, new NodeGene(i, 0));
            else
                nodes.put(i, new NodeGene(i, 1));
        }
        for (int i = 0; i < populationSize; i++) {
            population.add(new Genome(this));
        }
        generation();
    }

    public void XORTrain(double[] in, double[] out) {
        for (Genome g : population) {
            double result = g.evaluate(in)[0];
            double d = (2 - (Math.abs(out[0] - result)));
            if(d < 0){
                System.out.println(d);
            }
            g.fitness += d * d;
        }
    }

    public void generation() {
        HashSet<Genome> newGen = new HashSet<>();
        FitnessSelector<Genome> fs = new FitnessSelector<>();
        for (Species s : species) {
            population.removeAll(s.refresh(population));
        }
        while (population.size() > 0) {
            Genome founder = null;
            for (Genome g : population) {
                founder = g;
                break;
            }
            Species s = new Species(founder);
            population.removeAll(s.refresh(population));
            species.add(s);
        }
        HashSet<Species> hitList = new HashSet<>();
        for (Species s : species) {
            s.cull();
            if (s.population.size() < 1) {
                hitList.add(s);
            }
        }
        species.removeAll(hitList);
        HashSet<Genome> selectedPopulation = new HashSet<Genome>();
        for (Species s : species) {
            selectedPopulation.addAll(s.population);
        }
        double greatestFitness = -9999999999.0;
        Genome greatestGenome = null;
        for (Genome g : selectedPopulation) {
            if (g.fitness >= greatestFitness) {
                greatestFitness = g.fitness;
                greatestGenome = g;
            }
            fs.add(g, g.fitness);
        }
        System.out.println("Greatest Fitness: " + greatestFitness);
        if (greatestGenome != null)
            if (greatestGenome.network != null) {
                System.out.println("[0, 0]:" + greatestGenome.evaluate(new double[] { 0, 0 })[0]);
                System.out.println("[1, 0]:" + greatestGenome.evaluate(new double[] { 1, 0 })[0]);
                System.out.println("[0, 1]:" + greatestGenome.evaluate(new double[] { 0, 1 })[0]);
                System.out.println("[1, 1]:" + greatestGenome.evaluate(new double[] { 1, 1 })[0]);
            }

        for (int i = 0; i < populationSize; i++) {
            Genome g = Genome.crossover(fs.weightedRandom(), fs.weightedRandom());
            g.mutate();
            newGen.add(g);
        }
        population = newGen;
    }

    public NodeGene getNode(double x1, double x2) {
        return createNode(nodes.size(), (x1 + x2) / 2);
    }

    public NodeGene getNode(int get) {
        if (nodes.containsKey(get)) {
            NodeGene n = nodes.get(get);
            return new NodeGene(n.innovationNumber, n.x);
        }
        return null;
    }

    private NodeGene createNode(int num, double x) {
        nodes.put(num, new NodeGene(num, x));
        return nodes.get(num);
    }

    public ConnectionGene getConnection(int in, int out) {
        ConnectionGene cg = connections.getByHash(ConnectionGene.hashCode(in, out));
        if (cg != null) {
            return new ConnectionGene(cg.innovationNumber, cg.in, cg.out);
        }
        return createConnectionGene(in, out);
    }

    private ConnectionGene createConnectionGene(int in, int out) {
        ConnectionGene cg = new ConnectionGene(connections.size(), in, out);
        connections.add(cg);
        return cg;
    }
}
