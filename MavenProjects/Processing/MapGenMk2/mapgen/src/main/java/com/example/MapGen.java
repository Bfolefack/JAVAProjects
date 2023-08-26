package com.example;

import processing.core.PApplet;

/**
 * Hello world!
 *
 */
public class MapGen extends PApplet
{
    long seed;
    OpenSimplexNoise noise;
    public static void main( String[] args )
    {
        String[] processingArgs = { "MySketch" };
        MapGen mySketch = new MapGen();
        try {
            PApplet.runSketch(processingArgs, mySketch);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void settings(){
        width = 1000;
        size(width, width/2);
    }

    public void setup () {
        seed = (long) (Math.random() * Integer.MAX_VALUE);
        //seed = 10000;
        //seed = 467221216;
        noise = new OpenSimplexNoise(seed);
        println(seed);

        noStroke();
        fill(0);
      }

      public void draw () {
        
    }

    public void mousePressed() {
        save("warped_globe_experiments_" + seed + "_" + frameCount + ".png");
    }
}