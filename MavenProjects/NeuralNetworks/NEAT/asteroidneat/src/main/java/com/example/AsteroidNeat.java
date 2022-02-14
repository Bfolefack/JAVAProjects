package com.example;

import com.example.NEATExpansion.AsteroidTournamentRunner;

import processing.core.PApplet;

/**
 * Hello world!
 *
 */
public class AsteroidNeat extends PApplet {
    public AsteroidTournamentRunner tournament;
    private int speed = 1;
    private boolean display = true;

    public static void main(String[] args) {
        String[] processingArgs = { "MySketch" };
        AsteroidNeat mySketch = new AsteroidNeat();
        PApplet.runSketch(processingArgs, mySketch);
    }

    @Override
    public void settings() {
        size(1800, 800);
    }

    @Override
    public void setup() {
        noFill();
        stroke(255);
        tournament = new AsteroidTournamentRunner(64);
    }

    @Override
    public void draw() {
        background(0);
        for (int i = 0; i < speed; i++) {
            tournament.update();
        }
        if (display)
            tournament.display(this);
    }

    @Override
    public void keyPressed() {
        if (key == '.' && speed > 0) {
            speed--;
        }
        if (key == '/') {
            speed++;
        }
        if (key == '\'') {
            speed += 50;
        }
        if (key == ';' && speed > 0) {
            speed -= 50;
        }
        if (key == 'd') {
            display = !display;
        }
        if (key == 'r') {
            speed = 0;
        }
        if (key == 's') {
            tournament.pop.savePopulation();
        }
    }
}
