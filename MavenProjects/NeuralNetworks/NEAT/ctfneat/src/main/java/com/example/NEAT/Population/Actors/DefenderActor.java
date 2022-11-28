package com.example.NEAT.Population.Actors;

import java.io.Serializable;

import com.example.Game.Arena;
import com.example.Game.Entities.Players.Defender;
import com.example.Game.Entities.Players.Hunter;
import com.example.NEAT.Network.Genes.GeneLibrary;
import com.example.NEAT.Network.Genes.Genome;

import processing.core.PApplet;
import processing.core.PVector;

public class DefenderActor extends Actor{

    public static GeneLibrary defenderLibrary = new GeneLibrary();
    public transient Defender defender;
    public transient Arena arena;
    public transient double wins, loses;
    public double ancestorFitness;
    public int rounds;

    public DefenderActor(int inputs, int outputs, boolean r) {
        super(inputs, outputs, r, defenderLibrary);
    }

    public DefenderActor(Genome b) {
        super(b);
    }

    @Override
    public void setInputs(int[] in) {}

    @Override
    public void act() {
        if(alive){
            Defender otherDefender = arena.getDefender1() == this ? arena.getDefender2().defender : arena.getDefender1().defender;
            Hunter hunter = arena.getHunter().hunter;
            //Inputs: AbsoluteX, AbsoluteY, Facing, FireCooldown, 
            //        otherDefenderRelativeX, otherDefenderRelativeY, otherDefenderFacing,
            //        hunterRelativeX, hunterRelativeY, hunterFacing
            //        arenaTargetRelativeX, arenaTargetRelativeY
            //Outputs: Move, Rotation, Fire
            double[] inputs = new double[12];
            inputs[0] = defender.pos.x/arena.width;
            inputs[1] = defender.pos.y/arena.height;
            inputs[2] = defender.facing/Math.PI;
            inputs[3] = ((double)defender.fireCooldown)/Defender.fireCooldownMax;
            inputs[4] = (otherDefender.pos.x - defender.pos.x)/arena.width;
            inputs[5] = (otherDefender.pos.y - defender.pos.y)/arena.height;
            inputs[6] = otherDefender.facing/Math.PI;
            inputs[7] = (hunter.pos.x - defender.pos.x)/arena.width;
            inputs[8] = (hunter.pos.y - defender.pos.y)/arena.height;
            inputs[9] = hunter.facing/Math.PI;
            inputs[10] = (arena.target.x - defender.pos.x)/arena.width;
            inputs[11] = (arena.target.y - defender.pos.y)/arena.height;
            double[] outputs = brain.feedForward(inputs);
            float move = (float)outputs[0];
            float rotation = (float)outputs[1];
            boolean fire = outputs[2] > 0;
            defender.move(rotation, move);
            if(fire){
                defender.fire(arena, this);
            }
        }
    }

    @Override
    public void display() {}

    @Override
    public Actor breed(Actor b) {
        Genome newBrain = Genome.crossover(brain, b.brain);
        newBrain.mutate();
        DefenderActor next = new DefenderActor(newBrain);
        next.ancestorFitness = (batchFitness + b.batchFitness)/2;
        return next;
    }

    @Override
    public double calculateFitness() {
        if(wins + loses == 0){
            return 0;
        }
        batchFitness = Math.pow(100 * (wins/rounds), 3);
        return batchFitness;
    }

    @Override
    public void normalizeFitness() {}

    @Override
    public void epoch() {
        defender = null;
        arena = null;
        alive = true;
    }

    @Override
    public Actor clone() {
        DefenderActor r = new DefenderActor(brain.copy());
        r.batchFitness = batchFitness;
        return r;
    }

    public void draw(PApplet p) {
        if(defender != null && alive){
            defender.draw(p);
        }
    }

    public void win(double in) {
        wins += in;
        alive = false;
        rounds++;
    }

    public void lose(double in) {
        loses += in;
        alive = false;
        rounds++;
    }

    public void kill() {
        wins += 0.5;
    }
    
}
