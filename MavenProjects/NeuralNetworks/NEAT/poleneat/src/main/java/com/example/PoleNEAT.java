package com.example;

import java.util.HashSet;

import com.example.Game.DoubleCart;
import com.example.Game.Actors.DoublePoleVelActor;
import com.example.Game.Actors.SinglePoleVelActor;
import com.example.Game.BasicCartSolver.BasicCartSolver;
import com.example.NEAT.Population.Population;

import processing.core.PApplet;
import processing.event.KeyEvent;

/**
 * Hello world!
 *
 */
public class PoleNEAT extends PApplet {
    // DoubleCart dc;
    // BasicCartSolver bcs;
    Population<DoublePoleVelActor> pop;
    public static int display = 2;
    public int speed = 1;

    public static class Save extends Thread {
        PoleNEAT fb;

        public Save(PoleNEAT flap) {
            fb = flap;
        }

        @Override
        public void run() {
            // fb.pop.savePopulation();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        String[] processingArgs = { "MySketch" };
        PoleNEAT mySketch = new PoleNEAT();
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

        size(1000, 1000);
        // cart = new Cart(0, PI + (float)(Math.random() * 0.0001f));
        // bcs = new BasicCartSolver(1000);
        HashSet<DoublePoleVelActor> hs = new HashSet<>();
        for (int i = 0; i < 200; i++) {
            hs.add(new DoublePoleVelActor(3, 1, false));
        }
        // dc = new DoubleCart();
        pop = new Population<>(hs);
    }

    @Override
    public void setup() {
    }

    @Override
    public void draw() {
        for (DoublePoleVelActor dpva : pop) {
            dpva.displayed = false;
        }
        background(255);
        // System.out.println((frameCount % 30 - 15)/15f);
        for (int i = 0; i < speed; i++) {
            // bcs.update(this);
            // dc.update(0);
            neatUpdate();
        }
        // dc.display(this);
        // bcs.displayed = false;
        // cart.update((frameCount % 30 - 15)/7f);
        // cart.display(this);
    }

    @Override
    public void keyPressed() {
        switch (key) {
            case '/':
                speed++;
                break;
            case '.':
                speed--;
                break;
            case '\'':
                speed += 50;
                break;
            case ';':
                speed -= 50;
                break;
            case 'd':
                switch (display) {
                    case 0:
                        display = 1;
                        break;
                    case 1:
                        display = 2;
                        break;
                    case 2:
                        display = 0;
                        break;
                }
                break;
            case 'r':
                speed = 1;
                break;
        }
        if (speed < 0) {
            speed = 0;
        }
    }

    public void neatUpdate(){
        boolean anyAlive = false;
        pop.act();
        DoublePoleVelActor.app = this;
        for (DoublePoleVelActor DPVA : pop) {
            if (DPVA.alive) {
                switch (PoleNEAT.display) {
                    case 1:
                        if (!anyAlive && !DPVA.displayed)
                            DPVA.display();
                        break;
                    case 2:
                        if (!DPVA.displayed)
                            DPVA.display();
                        break;
                }
                anyAlive = true;
            }
        }
        if (!anyAlive)
            pop.generation();
    }
}
