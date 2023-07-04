package com.example;

import java.util.HashSet;

import javax.lang.model.util.ElementScanner6;

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
    public int speed = 0;

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

        //size(1000, 1000);
        // cart = new Cart(0, PI + (float)(Math.random() * 0.0001f));
        // bcs = new BasicCartSolver(1000);
        fullScreen();
        HashSet<DoublePoleVelActor> hs = new HashSet<>();
        for (int i = 0; i < 100; i++) {
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
        fill(0);
        textSize(30);
        textAlign(LEFT, TOP);
        text("Generation: " + pop.generation, 10, 0);
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
                        display = 3;
                        break;
                    case 3:
                        display = 0;
                        break;
                }
                break;
            case 'r':
                speed = 1;
                display = 2;
                break;
            case 'g':
                pop.generation();
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
        int aliveCount = 0;
        DoublePoleVelActor last = null;
        for (DoublePoleVelActor DPVA : pop) {
            
            if (DPVA.alive) {
                aliveCount++;
                switch (PoleNEAT.display) {
                    case 1:
                        if (!anyAlive && !DPVA.displayed){
                            DPVA.display();
                            DPVA.displayNetwork=true;
                        }
                        break;
                    case 2:
                        if (!DPVA.displayed){
                            DPVA.display();
                            last = DPVA;
                        }
                    
                        break;
                    case 3:
                        if (aliveCount < 10 && !DPVA.displayed){
                            DPVA.display();
                            DPVA.displayNetwork=true;
                        }
                        break;
                }
                anyAlive = true;
            }
        }
        if(last != null){
            last.displayNetwork=true;
            last.display();
        }
        if (!anyAlive)
            pop.generation();
    }
}
