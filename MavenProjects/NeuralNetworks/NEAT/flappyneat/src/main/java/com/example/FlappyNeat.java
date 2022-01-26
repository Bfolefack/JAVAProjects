package com.example;

import java.util.HashSet;
import java.util.TreeSet;

import com.example.Game.Bird;
import com.example.Game.Pipe;
import com.example.NEAT.FlappyActor;
import com.example.NEAT.Population.Population;
import com.example.NEAT.Utils.NetworkDisplay;

import processing.core.PApplet;

/**
 * Hello world!
 *
 */
public class FlappyNeat extends PApplet {
    private TreeSet<Pipe> pipes;
    public Population<FlappyActor> pop;
    public Pipe nextPipe;
    public boolean pipeCleared;
    public static NetworkDisplay nd;
    private long count;
    private int speed = 1;
    private boolean display = true;
    private int highscore = 0;


    public static class Save extends Thread{
        FlappyNeat fb;
        public Save(FlappyNeat flap){
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
        FlappyNeat mySketch = new FlappyNeat();
        Runtime.getRuntime().addShutdownHook(new Save(mySketch));
        try {
            PApplet.runSketch(processingArgs, mySketch);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void settings() {
        size(1000, 800);
        FlappyActor.app = this;
    }

    public void setup() {
        frameRate(144);
        pipes = new TreeSet<>();
        pipes.add(new Pipe((float) (Math.random() * (height - 200) + 100), width));
        // HashSet<FlappyActor> fa = new HashSet<>();
        // for (int i = 0; i < 100; i++) {
        //     fa.add(new FlappyActor(true));
        // }
        // pop = new Population<>(fa);
        pop = new Population<>("2022.01.25.20.29.56");
        // noStroke();
        nd = new NetworkDisplay(width/2, height/2);
        textSize(50);
    }

    public void draw() {
        background(0, 255, 255);
        nextPipe = pipes.first();
        for (int i = 0; i < speed; i++) {
            // System.out.println(frameCount);
            if (count % 120 == 0) {
                pipes.add(new Pipe((float) (Math.random() * (height - 200) + 100), width));
            }
            if (pipes.first().xPos < 0) {
                pipes.pollFirst();
                pipeCleared = true;
            } else
                pipeCleared = false;
            for (Pipe p : pipes) {
                p.update();
            }
            pop.act();
            count++;
        }
        if (display) {
            fill(0, 255, 0);
            for (Pipe pipe : pipes) {
                pipe.display(this);
            }
            fill(255, 255, 0);
            pop.display();
            nd.display(this, width/2, 20);
        }
        for (FlappyActor f : pop) {
            if (f.alive){
                nd.updateGenome(f.brain);
                return;
            }
        }
        System.out.println("\n\n");;
        if (pop.best != null)
            if (pop.best.pipeCounter > highscore) {
                highscore = (int) pop.best.pipeCounter;
                System.out.println("NEW HIGHSCORE: " + highscore);
            } else {
                System.out.println("HIGHSCORE: " + highscore);
            }
        else
            System.out.println("HIGHSCORE: " + highscore);
        System.out.println("\n\n");

        pipes.clear();
        pipes.add(new Pipe((float) (Math.random() * (height - 200) + 100), width));
        count = 1;

        pop.generation();
    }

    @Override
    public void keyPressed() {
        if (key == '.' && speed > 0) {
            speed--;
        }
        if (key == '/') {
            speed++;
        }
        if (key == 'd') {
            display = !display;
        }
        if(key == 'r'){
            speed = 0;
        }
    }
}
