package com.example.NEATExpansion;

import com.example.Game.War;
import com.example.NEAT.Utils.NetworkDisplay;

import processing.core.PApplet;

public class AsteroidTournamentRound {
    War war;
    AsteroidActor p1;
    AsteroidActor p2;
    boolean previousP1Alive;
    boolean previousP2Alive;
    boolean over;
    NetworkDisplay p1Brain;
    NetworkDisplay p2Brain;
    int winner;
    int timeOutCounter;
    public static int timeOut = 3600;

    AsteroidTournamentRound(AsteroidActor p1_, AsteroidActor p2_){
        p1 = p1_;
        p2 = p2_;
        war = new War(null, null);
        p1.initialize(war, 1);
        p2.initialize(war, 2);
        war.p1 = p1.player;
        war.p2 = p2.player;
    }

    public void update(){
        timeOutCounter++;
        if(timeOutCounter > timeOut){
            over = true;
        }
        if(!over){
            war.update();
            p1.act();
            p2.act();
            if(p1.player.alive){
                p1.liveTick();
            }
            if(p2.player.alive){
                p2.liveTick();
            }
            if(previousP1Alive == true && p1.player.alive == false){
                p1.death();
                p2.kill();
            }
            if(previousP2Alive == true &&  p2.player.alive == false){
                p2.death();
                p1.kill();
            }
            previousP1Alive = p1.player.alive;
            previousP2Alive = p2.player.alive;
            if(p1.player.lives <= 0){
                over = true;
                winner = 2;
                p2.win();
                return;
            }
            if(p2.player.lives <= 0){
                over = true;
                winner = 1;
                p1.win();
                return;
            }
        }
    }

    public void display(PApplet sketch){
        if(p1Brain == null){
            p1Brain = new NetworkDisplay(450, 700);
            p2Brain = new NetworkDisplay(450, 700);
        }
        p1Brain.updateGenome(p1.brain);
        p2Brain.updateGenome(p2.brain);
        p1Brain.display(sketch, 20, 20);
        for (int i = 0; i < p1.player.lives; i++) {
            sketch.ellipse(i * 90 + 20, 720, 20, 20);
        }
        sketch.fill(255, 0, 0);
        sketch.rect(20, 750, p1.player.engineHeat * 500, 100);
        sketch.noFill();
        sketch.pushMatrix();
        sketch.translate(sketch.width - 500, 0);
            p2Brain.display(sketch, 10, 10);
            for (int i = 0; i < p2.player.lives; i++) {
                sketch.ellipse(i * 90 + 20, 720, 20, 20);
            }
        sketch.fill(255, 0, 0);
        sketch.rect(20, 750, p2.player.engineHeat * 500, 100);
        sketch.noFill();
        sketch.popMatrix();
        war.display(sketch);
    }

    public AsteroidActor getWinner(){
        if(winner == 0){
            p1.lose();
            p2.lose();
            if(p1.player.lives > p2.player.lives){
                return p1;
            } else {
                return p2;
            }
        } else if(winner == 1){
            p1.win();
            p2.lose();
            return p1;
        } else {
            p1.lose();
            p2.win();
            return p2;
        }
    }
}
