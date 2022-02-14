package com.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import com.example.Game.War;
import com.example.Game.Entities.Bullet;
import com.example.Game.Entities.Player;

import processing.core.PApplet;

/**
 * Hello world!
 *
 */
public class SpaceWar extends PApplet
{
    War w;
    public static Set<Integer> keys;
    public static void main(String[] args) {
        String[] processingArgs = { "MySketch" };
        SpaceWar mySketch = new SpaceWar();
        PApplet.runSketch(processingArgs, mySketch);
    }

    public void settings(){
        size(1000, 800);
        keys = new TreeSet<>();
    }

    public void setup(){
        w = new War();

    }

    public void draw(){
        background(0);
        w.display(this);
        w.update();
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
