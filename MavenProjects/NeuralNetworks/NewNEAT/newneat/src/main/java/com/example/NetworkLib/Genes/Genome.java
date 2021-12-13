package com.example.NetworkLib.Genes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

import javax.swing.text.StyleContext.SmallAttributeSet;

import com.example.NetworkLib.Population;
import com.example.NetworkLib.Network.Network;
import com.example.NetworkLib.Utils.Config;
import com.example.NetworkLib.Utils.ConnectionGeneContainer;

public class Genome {
    ConnectionGeneContainer<Integer, Integer, ConnectionGene> connections = new ConnectionGeneContainer<>();
    public Network network;
    public double fitness;
    HashMap<Integer, NodeGene> nodes;
    Population pop;

    public Genome(Population population) {
        nodes = new HashMap<>();
        connections = new ConnectionGeneContainer<>();
        pop = population;
        for (int i = 0; i < pop.inputs + pop.outputs; i++) {
            nodes.put(i, pop.getNode(i));
        }
        fitness = 0.000001 * Math.random();
    }

    public void mutate() {
        network = constructNetwork();
        if (Math.random() < Config.MUTATE_LINK_CHANCE) {
            mutateLink();
        }
        if (Math.random() < Config.MUTATE_NODE_CHANCE) {
            mutateNode();
        }
        if (Math.random() < Config.PERTURB_WEIGHT_CHANCE) {
            perturbWeight();
        }
        if (Math.random() < Config.SCRAMBLE_WEIGHT_CHANCE) {
            scrambleWeight();
        }
        if (Math.random() < Config.DISABLE_CONNECTION_CHANCE) {
            disable();
        }
        if (Math.random() < Config.ENABLE_CONNECTION_CHANCE) {
            enable();
        }
        if (Math.random() < Config.DELETE_NODE_CHANCE) {
            deleteNode();
        }
        network = constructNetwork();
    }

    public Network constructNetwork() {
        return new Network(nodes, connections, pop);
    }

    private void mutateLink() {
        ArrayList<Integer> temp = new ArrayList<>(nodes.keySet());
        int nod1 = temp.get((int) (Math.random() * temp.size()));
        int nod2 = temp.get((int) (Math.random() * temp.size()));
        ConnectionGene cg = pop.getConnection(nod1, nod2);
        if (!connections.hashes.keySet().contains(cg.hashCode())) {
            connections.add(cg);
        }
    }

    private void mutateNode() {
        if (connections.size() > 0) {
            ConnectionGene cg = connections.getRandom();
            cg.enabled = false;
            NodeGene in = pop.getNode(cg.in);
            NodeGene out = pop.getNode(cg.out);
            NodeGene middle = pop.getNode(in.x, out.x);

            nodes.put(middle.innovationNumber, middle);
            ConnectionGene newFirst = pop.getConnection(in.innovationNumber, middle.innovationNumber);
            ConnectionGene newLast = pop.getConnection(middle.innovationNumber, out.innovationNumber);
            newFirst.weight = 1;
            newLast.weight = cg.weight;
            connections.add(newFirst);
            connections.add(newLast);
        }
    }

    private void deleteNode() {
        ArrayList<Integer> temp = new ArrayList<>(nodes.keySet());
        int node = temp.get((int) (Math.random() * temp.size()));
        if (node <= pop.inputs + pop.outputs)
            return;
        HashSet<ConnectionGene> hitList = new HashSet<>();
        for (ConnectionGene cg : connections) {
            if (cg.in == node || cg.out == node) {
                hitList.add(cg);
            }
        }
        connections.removeAll(hitList);
    }

    private void perturbWeight() {
        if (connections.size() > 0)
            connections.getRandom().weight *= ((1 - Config.WEIGHT_PERTURBATION_RANGE / 2)
                    + (Math.random() * Config.WEIGHT_PERTURBATION_RANGE));
    }

    private void scrambleWeight() {
        if (connections.size() > 0)
            connections.getRandom().weight = (Math.random() * Config.RANDOM_wEIGHT_RANGE)
                    - Config.RANDOM_wEIGHT_RANGE / 2;
    }

    private void disable() {
        ArrayList<ConnectionGene> cga = new ArrayList<>(connections.hashes.values());
        Collections.shuffle(cga);
        for (ConnectionGene c : cga) {
            if (c.enabled) {
                c.enabled = false;
            }
        }
    }

    private void enable() {
        ArrayList<ConnectionGene> cga = new ArrayList<>(connections.hashes.values());
        Collections.shuffle(cga);
        for (ConnectionGene c : cga) {
            if (!c.enabled) {
                c.enabled = true;
            }
        }
    }

    public double[] evaluate(double[] in) {
        network = constructNetwork();
        return network.evaluate(in, pop);
    }

    public static Genome crossover(Genome bigGene, Genome smallGene) {
        if (smallGene == bigGene)
            return bigGene;

        if (smallGene.fitness > bigGene.fitness) {
            Genome temp = smallGene;
            smallGene = bigGene;
            bigGene = temp;
        }

        Genome child = new Genome(bigGene.pop);

        TreeSet<Integer> innovations = new TreeSet<>(bigGene.connections.innovations.keySet());
        innovations.addAll(smallGene.connections.innovations.keySet());
        for (Integer i : innovations) {
            ConnectionGene cg1 = bigGene.connections.getByInnovation(i);
            ConnectionGene cg2 = smallGene.connections.getByInnovation(i);
            ConnectionGene trait = null;

            if (cg1 != null && cg2 != null) {
                if (cg1.enabled && cg2.enabled) {
                    if (Math.random() < Config.DOMINANT_GENE_INHERITANCE_CHANCE) {
                        trait = new ConnectionGene(cg1);
                    } else {
                        trait = new ConnectionGene(cg2);
                    }
                } else if (cg1.enabled) {
                    trait = new ConnectionGene(cg1);
                } else if (cg2.enabled) {
                    trait = new ConnectionGene(cg2);
                }
            } else {
                if (cg1 != null) {
                    trait = new ConnectionGene(cg1);
                    if (!cg1.enabled && Math.random() < Config.GENE_REENABLE_CHANCE) {
                        trait.enabled = true;
                    }
                }
            }
            if (trait != null) {
                child.connections.add(trait);
            }
        }

        return child;
    }

    public static boolean isSameSpecies(Genome g1, Genome g2) {
        TreeSet<Integer> innov1 = new TreeSet<>(g1.connections.innovations.keySet());
        TreeSet<Integer> innov2 = new TreeSet<>(g2.connections.innovations.keySet());
        int innov1Limit = 0;
        if (innov1.size() > 0)
            innov1Limit = innov1.pollLast();
        int innov2Limit = 0;
        if (innov2.size() > 0)
            innov2Limit = innov2.pollLast();
        TreeSet<Integer> innovations = new TreeSet<>(innov1);
        innovations.addAll(innov2);

        int matching = 0;
        int disjoint = 0;
        int excess = 0;
        double weight = 0;

        for (Integer i : innovations) {
            if (i <= innov1Limit && i <= innov2Limit) {
                ConnectionGene c1 = g1.connections.getByInnovation(i);
                ConnectionGene c2 = g2.connections.getByInnovation(i);
                if (c1 != null && c2 != null) {
                    matching++;
                    weight += Math.abs(c1.weight - c2.weight);
                } else {
                    disjoint++;
                }
            } else
                excess++;
        }

        int N = innovations.size();

        double delta = 0;
        if (N != 0)
            delta = (Config.EXCESS_COEFFICIENT * excess) / N + (Config.DISJOINT_COEFFICIENT * disjoint) / N
                    + ((Config.WEIGHT_COEFFICIENT * weight) / (matching > 1 ? matching : 1));
        // System.out.println(delta);
        return delta < Config.SPECIES_CUTOFF;
    }
}