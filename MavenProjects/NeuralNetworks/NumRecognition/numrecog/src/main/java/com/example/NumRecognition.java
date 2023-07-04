package com.example;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.example.NetworkLib.NeuralNetwork;
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
    static NeuralNetwork nn;

    public static class OnExit extends Thread{
        public void run(){
            try {
                // NumRecognition.nn.save("src/data/");
                // System.out.println("Saved NN");
            } catch (Exception e) {
                //TODO: handle exception
            }
        }
    }

    public static void main(String[] args) {
        String[] processingArgs = { "MySketch" };
        NumRecognition mySketch = new NumRecognition();
        Runtime.getRuntime().addShutdownHook(new OnExit());
        try {
            PApplet.runSketch(processingArgs, mySketch);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        // Runtime.getRuntime().addShutdownHook(hook);
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
            Scanner scan = new Scanner(new File("src/data/mnist_test.csv"));
            while (scan.hasNextLine()) {
                String s = scan.nextLine();
                Scanner scan2 = new Scanner(s);
                scan2.useDelimiter(",");
                int id = scan2.nextInt();
                double[] vals = new double[784];
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
        // try {
        //     nn = NeuralNetwork.load("src/data/Network.nn");
        // } catch (Exception e) {
        //     //TODO: handle exception
        // }
        nn = new NeuralNetwork(784, 400, 10, 2, 0.03);
        nn.learningRate = 0.005;
        int count = 0;
        while(true){
            count++;
            double err = 0;
            for (int j = 0; j < 600; j++) {
                int rand = (int) (Math.random() * numbers.size());
                Num num = numbers.get(rand);
                err += nn.train(num.pixels, Num.toIntArr(num.identifier));
            }
            err /= 600;
            System.out.println("Generation: " + (count));
            System.out.println("Absolute Error: " + err);
            System.out.println();
            if (err < 0.03) {
                break;
            }
        }
        try {
            nn.save("src/data/Network2");
        } catch (Exception e) {
            e.printStackTrace();
        }
        fill(255);
        noStroke();
        textSize(30);
        textAlign(CENTER);
    }

    @Override
    public void draw() {
        background(0);
        fill(255);
        rect(225, 275, 550, 550);
        if (!canvasing) {
            image(numbers.get(count).image, 250, 300, 500, 500);
            Num num = numbers.get(count);
            double[] f = nn.guess(num.pixels);
            int tempCount = 0;
            for (int i = 0; i < width - (width / 10f); i += ((width - width / 10f) / 10f)) {
                fill(255);
                rect(i + width / 20f, 200, (width - width / 10f) / 11f, -1 * (180 * (float) f[tempCount]));
                text(tempCount, i + width / 10f - 10, 250);
                fill(125);
                text((int) (f[tempCount] * 100) + "%", i + width / 10f - 10, 100);
                tempCount++;
            }
        } else {
            canvas.update(this);
            image(canvas.image, 250, 300, 500, 500);
            double[] f = nn.guess(canvas.d);
            int tempCount = 0;
            for (int i = 0; i < width - (width / 10f); i += ((width - width / 10f) / 10f)) {
                fill(255);
                rect(i + width / 20f, 200, (width - width / 10f) / 11f, -1 * (180 * (float) f[tempCount]));
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
        if(key == 's'){
            save("src/data/images/" + (Math.random() + "").substring(2) + ".png");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!canvasing)
            canvas = new Canvas();
        canvasing = true;
    }
}
