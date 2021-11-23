package com.example;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.example.NetworkLib.BabyNet;
import com.example.Number.Canvas;
import com.example.Number.Num;

import processing.core.PApplet;
import processing.event.MouseEvent;
import processing.opengl.PGraphicsOpenGL;

/**
 * Hello world!
 *
 */
public class NumRecognition extends PApplet {
    ArrayList<Num> numbers;
    int count;
    boolean canvasing;
    Canvas canvas;
    BabyNet bn;

    public static void main(String[] args) {
        String[] processingArgs = { "MySketch" };
        NumRecognition mySketch = new NumRecognition();
        try {
            PApplet.runSketch(processingArgs, mySketch);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    @Override
    public void settings() {
        size(1000, 800, P2D);
    }

    @Override
    public void setup() {
        ((PGraphicsOpenGL) g).textureSampling(3);
        numbers = new ArrayList<>();
        System.out.println("Building Dataset");
        try {
            Scanner scan = new Scanner(new File("src/data/mnist_train.csv"));
            int tempCount = 0;
            while (scan.hasNextLine()) {
                tempCount++;
                String s = scan.nextLine();
                Scanner scan2 = new Scanner(s);
                scan2.useDelimiter(",");
                int id = scan2.nextInt();
                float[] vals = new float[784];
                for (int i = 0; i < 784; i++) {
                    vals[i] = scan2.nextInt();
                }
                numbers.add(new Num(id, vals, this));
                scan2.close();
            }
            scan.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Training Network");
        bn = new BabyNet(784, 784, 10);
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 60; j++) {
                int rand = (int) (Math.random() * numbers.size());
                Num num = numbers.get(rand);
                bn.train(num.pixels, Num.toIntArr(num.identifier));
            }
            System.out.println((i + 1) + "% done");
        }
        fill(255);
        noStroke();
        textSize(30);
        textAlign(CENTER);
    }

    @Override
    public void draw() {
        background(0);
        if(!canvasing){
            image(numbers.get(count).image, 250, 300, 500, 500);
            Num num = numbers.get(count);
            float[] f = bn.guess(num.pixels);
            int tempCount = 0;
            for (int i = 0; i < width - (width / 10f); i += ((width - width / 10f) / 10f)) {
                fill(255);
                rect(i + width / 20f, 200, (width - width / 10f) / 11f, -1 * (180 * f[tempCount]));
                text(tempCount, i + width / 10f - 10, 250);
                fill(125);
                text((int) (f[tempCount] * 100) + "%", i + width / 10f - 10, 100);
                tempCount++;
            }
        } else {
            canvas.update(this);
            image(canvas.image, 250, 300, 500, 500);
            float[] f = bn.guess(canvas.f);
            int tempCount = 0;
            for (int i = 0; i < width - (width / 10f); i += ((width - width / 10f) / 10f)) {
                fill(255);
                rect(i + width / 20f, 200, (width - width / 10f) / 11f, -1 * (180 * f[tempCount]));
                text(tempCount, i + width / 10f - 10, 250);
                fill(125);
                text((int) (f[tempCount] * 100) + "%", i + width / 10f - 10, 100);
                tempCount++;
            }
        }
    }

    @Override
    public void keyPressed() {
        canvasing = false;
        count = (int) (Math.random() * numbers.size());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!canvasing)
            canvas = new Canvas();
        canvasing = true;
    }

}
