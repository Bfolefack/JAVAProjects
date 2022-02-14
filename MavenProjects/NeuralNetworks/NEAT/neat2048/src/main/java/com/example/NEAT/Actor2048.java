package com.example.NEAT;

import java.io.Serializable;
import java.util.TreeMap;

import com.example.Neat2048;
import com.example.Game.Game2048;
import com.example.NEAT.Network.Genes.Genome;
import com.example.NEAT.Population.Actor;
import com.example.NEAT.Utils.NetworkDisplay;

public class Actor2048 extends Actor implements Serializable {

    public static Neat2048 app;
    public int turns;
    transient public NetworkDisplay nd;
    private Game2048 game;
    private float invalidMoves;
    public boolean alive = true;

    public Actor2048(Genome b) {
        super(b);
        alive = true;
        game = new Game2048();
    }

    public Actor2048(int inputs, int outputs, boolean r) {
        super(inputs, outputs, r);
        game = new Game2048();
    }

    @Override
    public void setInputs(int[] in) {
    }

    @Override
    public void act() {
        if (game.dead < 1) {
            double[] ins = new double[16];
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    ins[i * 4 + j] = game.board[i][j];
                }
            }

            int big = 0;
            int small = 500;
            for (int i = 0; i < ins.length; i++) {
                if (ins[i] > big) {
                    big = (int) ins[i];
                }
                if (ins[i] < small) {
                    small = (int) ins[i];
                }
            }

            for (int i = 0; i < ins.length; i++) {
                if (ins[i] != 0){
                    if (small > 0)
                        ins[i] = ((Math.log(ins[i]) / Math.log(2)) - (Math.log(small) / Math.log(2)))
                                / ((Math.log(big) / Math.log(2)) - (Math.log(small) / Math.log(2)));
                    else
                        ins[i] = ((Math.log(ins[i]) / Math.log(2))) / ((Math.log(big) / Math.log(2)));
                } else {
                    ins[i] = -1;
                }
            }

            double[] outs = brain.feedForward(ins);
            TreeMap<Double, Integer> outVals = new TreeMap<>();
            for (int i = 0; i < outs.length; i++) {
                outVals.put(outs[i] + Math.random() * 0.00000001, i);
            }
            while (outVals.size() > 0) {
                int i = outVals.pollLastEntry().getValue();
                boolean success = false;
                switch (i) {
                    case 0:
                        success = game.moveUp();
                        break;
                    case 1:
                        success = game.moveDown();
                        break;
                    case 2:
                        success = game.moveLeft();
                        break;
                    case 3:
                        success = game.moveRight();
                        break;
                }
                if (success)
                    break;
                else
                    invalidMoves++;
            }
            if (outVals.size() <= 0) {
                alive = false;
                game.dead = 2;
            }
            turns++;
        } else {
            alive = false;
        }
    }

    @Override
    public void display() {
        game.display(app);
        if (nd == null) {
            nd = new NetworkDisplay(500, 500);
        }
        nd.updateGenome(brain);
        nd.display(app, 500, 0);
    }

    @Override
    public Actor2048 breed(Actor b) {
        Genome newBrain = Genome.crossover(brain, b.brain);
        newBrain.mutate();
        return new Actor2048(newBrain);
    }

    @Override
    public double calculateFitness() {
        if (!brain.tooManyOutputs)
            // fitness = Math.pow((Math.pow(game.score, 2) / Math.pow((invalidMoves == 0 ? 1 : invalidMoves), 1)), 2)/100000;
            fitness = (Math.pow(game.maxValue(), 3) + Math.pow(game.score, 2)/2)/(invalidMoves == 0 ? 1 : invalidMoves);
        else
            fitness = 0 + Math.random() * 0.0000000001;
        if(game.maxValue() >= 2048){
            System.out.println("\n\n");
            System.out.println("2048!!!");
            System.out.println("\n\n");
        }
        if(game.maxValue() >= 1024){
            System.out.println("\n\n");
            System.out.println("1024");
            System.out.println("\n\n");
        }
        return fitness;
    }

}
