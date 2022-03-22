package com.example;

import com.example.Game.Cart;
import com.example.Game.BasicCartSolver.BasicCartSolver;

import processing.core.PApplet;
import processing.event.KeyEvent;

/**
 * Hello world!
 *
 */
public class PoleNEAT extends PApplet {
    Cart cart;
    BasicCartSolver bcs;
    public static boolean display = true;
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
        bcs = new BasicCartSolver(1000);
    }

    @Override
    public void setup() {
    }

    @Override
    public void draw() {
        background(255);
        // System.out.println((frameCount % 30 - 15)/15f);
        for (int i = 0; i < speed; i++) {
            bcs.update(this);
        }
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
                display = !display;
                break;
            case 'r':
                speed = 1;
                break;
        }
        if (speed < 0) {
            speed = 0;
        }
    }
}
