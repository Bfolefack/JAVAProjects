package com.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import com.example.Entities.Bullet;
import com.example.Entities.Player;

import processing.core.PApplet;

/**
 * Hello world!
 *
 */
public class AsteroidWars extends PApplet
{
    public Player P1;
    public Player P2;
    public Set<Bullet> bullets;
    public static Set<Integer> keys;
    public static void main(String[] args) {
        String[] processingArgs = { "MySketch" };
        AsteroidWars mySketch = new AsteroidWars();
        PApplet.runSketch(processingArgs, mySketch);
    }

    public void settings(){
        size(1000, 800);
        keys = new TreeSet<>();
    }

    public void setup(){
        bullets = new HashSet<>();
        P1 = new Player(width/2, height/2, 5, 0.01f, 1);
        P2 = new Player(width/2, height/2, 5, 0.01f, 2);
    }

    public void draw(){
        background(0);
        P1.update(this);
        P1.display(this);
        P2.update(this);
        P2.display(this);
        for(Bullet b : bullets){
            b.update();
            b.display(this);
        }
        System.out.println(keys);
    }

    public void keyPressed(){
        keys.add(keyCode);
        System.out.println(keys);
    }

    public void keyReleased(){
        keys.remove(keyCode);
    }
}
