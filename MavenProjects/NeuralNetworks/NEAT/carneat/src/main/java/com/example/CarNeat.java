package com.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;
import java.util.TreeMap;

import com.example.Game.Car;
import com.example.Game.Track;
import com.example.NEAT.Population.CarActor;
import com.example.NEAT.Population.Population;
import com.example.NEAT.Utils.NetworkDisplay;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * Hello world!
 *
 */
public class CarNeat extends PApplet {
    public Car car;
    Track track;
    int displayType = 2;

    HashSet<Character> keys = new HashSet<Character>();
    public static Population<CarActor> pop;

    NetworkDisplay nd;
    int speed = 1;

    public static class Save extends Thread {
        CarNeat fb;

        public Save(CarNeat flap) {
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
        CarNeat mySketch = new CarNeat();
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
        size(1000, 800);
    }

    @Override
    public void setup() {
        track = new Track(1, this);
        CarActor.app = this;
        track.display(this);
        car = new Car(15, 5f, 0.25f, track);
        car.pos = new PVector(width/2, height/2);
        // newPopulation(200);
        loadPopulation("2022.04.21.15.44.57");
        nd = new NetworkDisplay(width - 20, height / 4);
    }

    private void loadPopulation(String string) {
        pop = new Population<>(string);
        for (CarActor carActor : pop) {
            carActor.car.track = track;
        }
    }

    private void newPopulation(int size) {
        HashSet<CarActor> inputs = new HashSet();
        for (int i = 0; i < size; i++) {
            inputs.add(new CarActor(14, 2, false, track));
        }
        pop = new Population<>(inputs);
    }

    @Override
    public void draw() {
        track.display(this);
        textSize(10);
        text((int) frameRate, 20, height - 10);

        // playerControl();

        if (displayType == 2)
            pop.display();

        CarActor ca = pop.alive();
        if (displayType == 1 && ca != null) {
            ca.display();
        }
        nd.updateGenome(ca.brain);
        nd.display(this, 10, 0);
        for (int i = 0; i < speed; i++) {
            pop.act();
            ca = pop.alive();
            if (ca == null) {
                CarActor.nextTrack = new Track(this);
                track = CarActor.nextTrack;
                pop.generation();
                break;
            }
        }
    }

    private void playerControl() {
        float throttle = 0;
        float rotation = 0;
        if (keys.contains('w')) {
            throttle += 1;
        }
        if (keys.contains('s')) {
            throttle += -1;
        }
        if (keys.contains('a')) {
            rotation += -1;
        }
        if (keys.contains('d')) {
            rotation += 1;
        }
        car.update(throttle, rotation);
        car.display(this);
    }

    @Override
    public void keyPressed() {
        keys.add(key);
        if (key == '/') {
            speed++;
        } else if (key == '.') {
            speed--;
        } else if (key == '\'') {
            speed += 50;
        } else if (key == ';') {
            speed -= 50;
        } else if (key == 's') {
            pop.savePopulation();
        } else if (key == 'l') {
            Car.showLines = !Car.showLines;
        } else if (key == 't') {
            Car.showTarget = !Car.showTarget;
        } else if (key == 'r') {
            speed = 1;
        } else if (key == 'd') {
            displayType += 1;
            displayType = displayType > 2 ? 0 : displayType;
        }
        speed = speed < 0 ? speed = 0 : speed;
        super.keyPressed();
    }

    @Override
    public void keyReleased() {
        keys.remove(key);
        super.keyReleased();
    }
}
