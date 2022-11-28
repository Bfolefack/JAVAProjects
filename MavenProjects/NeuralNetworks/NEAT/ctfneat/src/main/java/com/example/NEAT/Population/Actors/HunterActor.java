package com.example.NEAT.Population.Actors;

import com.example.Game.Arena;
import com.example.Game.Entities.Bullet;
import com.example.Game.Entities.Players.Defender;
import com.example.Game.Entities.Players.Hunter;
import com.example.NEAT.Network.Genes.GeneLibrary;
import com.example.NEAT.Network.Genes.Genome;

import processing.core.PApplet;

public class HunterActor extends Actor{

    public static GeneLibrary hunterLibrary = new GeneLibrary();
    public transient Hunter hunter;
    public transient Arena arena;
    public transient double wins, loses;
    public double ancestorFitness;
    public int rounds;


    public HunterActor(int inputs, int outputs, boolean r) {
        super(inputs, outputs, r, hunterLibrary);
    }

    public HunterActor(Genome b) {
        super(b);
    }

    @Override
    public void setInputs(int[] in) {}

    @Override
    public void act() {
        if(alive){
            Defender defender1 = arena.getDefender1().defender;
            Defender defender2 = arena.getDefender2().defender;
            Bullet nearestBullet = arena.getNearestBullet(hunter.pos);
            //Inputs: AbsoluteX, AbsoluteY, Facing,
            //        defender1RelativeX, defender1RelativeY, defender1Facing,
            //        defender2RelativeX, defender2RelativeY, defender2Facing
            //        nearestBulletRelativeX, nearestBulletRelativeY, nearestBulletFacing
            //        arenaTargetRelativeX, arenaTargetRelativeY
            //Outputs: Move, Rotation
            double[] inputs = new double[14];
            inputs[0] = hunter.pos.x/arena.width;
            inputs[1] = hunter.pos.y/arena.height;
            inputs[2] = hunter.facing/Math.PI;
            inputs[3] = (defender1.pos.x - hunter.pos.x)/arena.width;
            inputs[4] = (defender1.pos.y - hunter.pos.y)/arena.height;
            inputs[5] = defender1.facing/Math.PI;
            inputs[6] = (defender2.pos.x - hunter.pos.x)/arena.width;
            inputs[7] = (defender2.pos.y - hunter.pos.y)/arena.height;
            inputs[8] = defender2.facing/Math.PI;
            inputs[9] = (nearestBullet.pos.x - hunter.pos.x)/arena.width;
            inputs[10] = (nearestBullet.pos.y - hunter.pos.y)/arena.height;
            inputs[11] = nearestBullet.facing/Math.PI;
            inputs[12] = (arena.target.x - hunter.pos.x)/arena.width;
            inputs[13] = (arena.target.y - hunter.pos.y)/arena.height;
            double[] outputs = brain.feedForward(inputs);
            float move = (float)outputs[0];
            float rotation = (float)outputs[1];
            hunter.move(rotation, move);

        }
    }

    @Override
    public void display() {}

    @Override
    public Actor breed(Actor b) {
        Genome newBrain = Genome.crossover(brain, b.brain);
        newBrain.mutate();
        HunterActor next = new HunterActor(newBrain);
        next.ancestorFitness = (batchFitness + b.batchFitness)/2;
        return next;
    }

    @Override
    public double calculateFitness() {
        if(rounds == 0){
            return 0;
        }
        batchFitness = Math.pow(100 * (wins/rounds), 3);
        return batchFitness;
    }

    @Override
    public void normalizeFitness() {}

    @Override
    public void epoch() {
        hunter = null;
        arena = null;
        alive = true;
    }

    @Override
    public Actor clone() {
        HunterActor r = new HunterActor(brain.copy());
        r.batchFitness = batchFitness;
        return r;
    }

    public void draw(PApplet p) {
        if(hunter != null && alive){
            hunter.draw(p);
        }
    }

    public void win(double in) {
        wins += in;
        rounds++;
        alive = false;
    }

    public void lose(double in) {
        loses += in;
        rounds++;
        alive = false;
    }
    
}
