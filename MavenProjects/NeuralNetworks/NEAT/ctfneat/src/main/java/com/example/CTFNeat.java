package com.example;

import java.util.HashSet;

import processing.core.PApplet;

/**
 * Hello world!
 *
 */
public class CTFNeat extends PApplet {
    HashSet<Character> keys = new HashSet<Character>();
    // public Player player = new Player(400, 400, 15, 0, 0.1f, 50);
    // public Hunter hunter = new Hunter(200, 400);
    // public Defender defender = new Defender(600, 400);
    ArenaManager arenaManager;
    public int speed = 1;
    public boolean display = true;

    public static class Save extends Thread {
        CTFNeat fb;

        public Save(CTFNeat flap) {
            fb = flap;
        }

        @Override
        public void run() {
            fb.arenaManager.saveArena();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        String[] processingArgs = { "MySketch" };
        CTFNeat mySketch = new CTFNeat();
        Runtime.getRuntime().addShutdownHook(new Save(mySketch));
        try {
            PApplet.runSketch(processingArgs, mySketch);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void settings() {
        size(1300, 800);
    }

    public void setup() {
        frameRate(60);
        arenaManager = new ArenaManager(1200);
        // arenaManager = ArenaManager.loadArena("G-381--463778.arena");
        noStroke();
    }

    public void draw() {
        background(200);
        for (int i = 0; i < speed; i++) {
            arenaManager.update();
        }
        if (display)
            arenaManager.display(this);
    }

    public void keyReleased() {
        keys.remove(key);
    }

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
        } else if (key == 'r') {
            speed = 1;
        } else if (key == 'd') {
            display = !display;
        } else if(key == 'q') {
            System.exit(0);
        }else if(key == 's') {
            arenaManager.saveArena();
        }
        speed = speed < 0 ? speed = 0 : speed;
        super.keyPressed();
    }
}
