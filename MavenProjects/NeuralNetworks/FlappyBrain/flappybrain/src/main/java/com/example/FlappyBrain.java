package com.example;

import java.util.TreeSet;

import com.example.Game.Bird;
import com.example.Game.Pipe;
import com.example.Population.Population;

import processing.core.PApplet;

/**
 * Hello world!
 *
 */
public class FlappyBrain extends PApplet {

    TreeSet<Pipe> pipes;
    Population population;
    Bird b = new Bird();
    int count;
    int speed = 1;

    public static class Save extends Thread{
        FlappyBrain fb;
        public Save(FlappyBrain flap){
            fb = flap;
        }
        @Override
        public void run() {
            fb.population.save();
            System.exit(0);
        }
    }
    public static void main(String[] args) {
        String[] processingArgs = { "MySketch" };
        FlappyBrain mySketch = new FlappyBrain();
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
    }

    public void setup() {
        frameRate(144);
        pipes = new TreeSet<>();
        pipes.add(new Pipe((float) (Math.random() * (height - 200) + 100), width));
        // noStroke();
        textSize(50);
        population = new Population(1000);
        population.load();
    }

    public void draw() {
        background(0, 255, 255);
        for(int i = 0; i < speed; i++){
        // System.out.println(frameCount);
            if (count % 120 == 0) {
                pipes.add(new Pipe((float) (Math.random() * (height - 200) + 100), width));
            }
            if (pipes.first().xPos < 0) {
                pipes.pollFirst();
                population.pipeCleared();
            }
            for(Pipe p : pipes){
                p.update();
            }

            if(population.update(pipes.first(), this)){
                pipes.clear();
                pipes.add(new Pipe((float) (Math.random() * (height - 200) + 100), width));
                count = 1;
            }
            count++;
        }
        fill(0, 255, 0);
        for (Pipe pipe : pipes) {
            pipe.display(this);
        }
        fill(255, 255, 0);
        population.display(this);
    }

    @Override
    public void keyPressed() {
        if(key == '.' && speed > 1){
            speed--;
        }
        if(key == '/'){
            speed++;
        }
    }
}
