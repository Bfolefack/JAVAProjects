package com.example.NEATExpansion;

import java.io.Serializable;

import com.example.Game.War;
import com.example.Game.Entities.Bullet;
import com.example.Game.Entities.Player;
import com.example.NEAT.Network.Genes.Genome;
import com.example.NEAT.Population.Actor;

import processing.core.PApplet;
import processing.core.PVector;

public class AsteroidActor extends Actor implements Serializable{
    transient public Player player;
    int playerNum;
    transient War war;
    private int deathCount;
    private int winCount;
    private int maxRound;
    private boolean champion;
    private int liveTicks;
    private int loseCount;
    private int killCount;

    public static float maxSpeed = 7.5f;
    public static float maxForce = 0.005f;
    public static int size = 40;
    public static float turnSpeed = (float) Math.PI / 30;
    public static int respawnTime = 120;
    public static int fireRate = 30;

    public AsteroidActor(Genome b) {
        super(b);
    }

    public AsteroidActor(boolean r) {
        super(18, 5, r);
    }

    @Override
    public void setInputs(int[] in) {
        // TODO Auto-generated method stub
    }

    public void initialize(War w, int num) {
        war = w;
        playerNum = num;
        if (num == 1)
            player = new Player((float) (w.bounds * 0.75), 0, maxSpeed, maxForce, size, -(float) Math.PI, fireRate, w);
        else if (num == 2)
            player = new Player((float) (w.bounds * -0.75), 0, maxSpeed, maxForce, size, 0, fireRate, w);
    }

    @Override
    public void act() {
        if (player != null) {
            PVector pos = player.pos;
            PVector vel = player.vel;
            PVector enemyPos = new PVector();
            PVector enemyVel = new PVector();
            float enemyFacing = 0;
            if (playerNum == 1) {
                enemyPos = war.p2.pos;
                enemyVel = war.p2.vel;
                enemyFacing = (float) ((war.p2.facing % (2 * Math.PI)) / Math.PI);
            } else {
                enemyPos = war.p1.pos;
                enemyVel = war.p1.vel;
                enemyFacing = (float) ((war.p1.facing % (2 * Math.PI)) / Math.PI);
            }
            Bullet b = war.getNearestBullet(player.pos);
            PVector nearestBulletPos = b.pos;
            PVector nearestBulletVel = b.vel;
            float facing = (float) ((player.facing % (2 * Math.PI)) / Math.PI);
            float health = player.lives / 5f;
            float fire = (float) player.fireCooldown / player.fireRate;
            float respawn = (float) player.respawnTimer / player.respawnTime;
            enemyPos = PVector.sub(pos, enemyPos);
            if (b.pos.mag() != 0)
                nearestBulletPos = PVector.sub(pos, nearestBulletPos);
            double[] ins = new double[] { pos.x / war.bounds, pos.y / war.bounds, vel.x / 5, vel.y / 5,
                    enemyPos.x / war.bounds, enemyPos.y / war.bounds, enemyVel.x / 5, enemyVel.y / 5,
                    nearestBulletPos.x / war.bounds, nearestBulletPos.y / war.bounds, nearestBulletVel.x / 5,
                    nearestBulletVel.y / 5, facing, enemyFacing, health, fire, respawn, (player.engineHeat - 0.5f) * 2};
            double[] outs = brain.feedForward(ins);
            boolean[] actions = new boolean[5];
            for (int i = 0; i < actions.length; i++) {
                if (outs[i] > 0) {
                    actions[i] = true;
                }
            }
            player.update(war, actions);
        }
    }

    public void display(PApplet p) {
        if (playerNum == 1)
            p.stroke(200, 200, 255);
        else
            p.stroke(255, 200, 200);
        player.display(p);
        p.stroke(255);
    }

    @Override
    public Actor breed(Actor b) {
        Genome newBrain = Genome.crossover(brain, b.brain);
        newBrain.mutate();
        return new AsteroidActor(newBrain);
    }

    @Override
    public double calculateFitness() {
        fitness = Math.pow(Math.pow(((double)killCount/(deathCount == 0.0 ? 0.1 : deathCount)), 2) * (loseCount != 0 ? winCount/loseCount : winCount * 2), 3); /* 5 * ((winCount - loseCount) > 0 ? winCount - loseCount : 0.2)*/
        return fitness;
    }

    public void death() {
        deathCount++;
    }

    public void win() {
        winCount++;
    }

    public void setMaxRound(int round) {
        maxRound = round;
    }

    public void champion() {
        champion = true;
    }

    @Override
    public void display() {
        int i = 1 / 0;
    }

    public void liveTick() {
        liveTicks++;
    }

    public void lose() {
        loseCount++;
    }

    public void kill() {
        killCount++;
    }

}
