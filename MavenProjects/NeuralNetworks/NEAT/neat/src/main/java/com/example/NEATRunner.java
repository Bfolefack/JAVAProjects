package com.example;

import java.util.Arrays;
import java.util.HashSet;

import com.example.NEAT.Network.Genes.Genome;
import com.example.NEAT.Population.Actor;
import com.example.NEAT.Population.Population;
import com.example.NEAT.Population.Species;
import com.example.XORNeat.XORActor;

/**
 * Hello world!
 *
 */
public class NEATRunner {
    public static void main(String[] args) {
        System.out.println("★Hello World!★");
        HashSet<XORActor> inits = new HashSet<>();
        for(int i = 0; i < 100; i++){
            inits.add(new XORActor(4, 1, true));
        }
        Population<XORActor> pop = new Population<XORActor>(inits);
        while(true){
            long start = System.currentTimeMillis();
            for (int i = 0; i < 100; i++) {
                pop.act();
            }
            System.out.println("ACT EXECUTION TIME: " + (System.currentTimeMillis() - start));
            start = System.currentTimeMillis();
            pop.generation();
            System.out.println("GENERATION EXECUTION TIME: " + (System.currentTimeMillis() - start));
        }
        // Genome gigi = new Genome(2, 1, true);
        // Genome jojo = new Genome(2, 1, true);
        // for (int i = 0; i < 500; i++) {
        //     if (Math.random() < 0.01)
        //         gigi.addNode();
        //     if (Math.random() < 0.05) {
        //         gigi.addConnection();
        //     }
        //     if (Math.random() < 0.01)
        //         jojo.addNode();
        //     if (Math.random() < 0.05) {
        //         jojo.addConnection();
        //     }
        // }

        // System.out.println(Arrays.toString(gigi.feedForward(new double[] { 0, 0 })));
        // System.out.println(Arrays.toString(gigi.feedForward(new double[] { 0, 1 })));
        // System.out.println(Arrays.toString(gigi.feedForward(new double[] { 1, 0 })));
        // System.out.println(Arrays.toString(gigi.feedForward(new double[] { 1, 1 })));
        // System.out.println();
        // System.out.println(Arrays.toString(jojo.feedForward(new double[] { 0, 0 })));
        // System.out.println(Arrays.toString(jojo.feedForward(new double[] { 0, 1 })));
        // System.out.println(Arrays.toString(jojo.feedForward(new double[] { 1, 0 })));
        // System.out.println(Arrays.toString(jojo.feedForward(new double[] { 1, 1 })));
        // System.out.println();
        // System.out.println(Arrays.toString(gigi.feedForward(new double[] { 0, 0 })));
        // System.out.println(Arrays.toString(gigi.feedForward(new double[] { 0, 1 })));
        // System.out.println(Arrays.toString(gigi.feedForward(new double[] { 1, 0 })));
        // System.out.println(Arrays.toString(gigi.feedForward(new double[] { 1, 1 })));
        // System.out.println();
        // System.out.println(Arrays.toString(jojo.feedForward(new double[] { 0, 0 })));
        // System.out.println(Arrays.toString(jojo.feedForward(new double[] { 0, 1 })));
        // System.out.println(Arrays.toString(jojo.feedForward(new double[] { 1, 0 })));
        // System.out.println(Arrays.toString(jojo.feedForward(new double[] { 1, 1 })));

        // Actor gigigi = new Actor(0, 0, false);
        // gigigi.brain = gigi;
        // Actor jojojo = new Actor(0, 0, false);
        // jojojo.brain = jojo;
        // Species<Actor> spec = new Species<Actor>(gigigi);
        // spec.isRelated(jojojo);

        // Genome jiji = Genome.crossover(gigi, jojo);
        // System.out.println();
        // System.out.println(Arrays.toString(jiji.feedForward(new double[] { 0, 0 })));
        // System.out.println(Arrays.toString(jiji.feedForward(new double[] { 0, 1 })));
        // System.out.println(Arrays.toString(jiji.feedForward(new double[] { 1, 0 })));
        // System.out.println(Arrays.toString(jiji.feedForward(new double[] { 1, 1 })));
    }
}