package com.example;

import java.util.HashSet;

import com.example.Game.Game2048;
import com.example.NEAT.Actor2048;
import com.example.NEAT.Population.Actor;
import com.example.NEAT.Population.Population;

import processing.core.PApplet;

/**
 * Hello world!
 *
 */
public class Neat2048 extends PApplet {

    Game2048 game;
    Population<Actor2048> pop;
    int speed = 0;
    boolean display = true;

    public static class Save extends Thread {
        Neat2048 n2048;

        public Save(Neat2048 game) {
            n2048 = game;
        }

        @Override
        public void run() {
            n2048.pop.savePopulation();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        String[] processingArgs = { "MySketch" };
        Neat2048 mySketch = new Neat2048();
        Runtime.getRuntime().addShutdownHook(new Save(mySketch));
        try {
            PApplet.runSketch(processingArgs, mySketch);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    @Override
    public void settings() {
        Actor2048.app = this;
        size(1000, 500);
    }

    @Override
    public void setup() {
        HashSet<Actor2048> coll = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            coll.add(new Actor2048(16, 4, true));
        }
        pop = new Population<>(coll);
        pop.generation();
    }

    @Override
    public void draw() {
        background(155);
        for (int i = 0; i < speed; i++) {
            pop.act();
        }
        for (Actor2048 act : pop) {
            if (act.alive) {
                if (display)
                    act.display();
                return;
            }
        }
        pop.generation();
    }

    @Override
    public void keyPressed() {
        switch (key) {
            case 'd':
                display = !display;
                break;
            case 'r':
                speed = 0;
                break;
            case '/':
                speed++;
                break;
            case '.':
                if (speed >= 0)
                    speed--;
                break;
            default:
                break;
        }
    }
}
