package com.example.Population;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

import com.example.Game.Pipe;
import com.example.NetworkLib.NeuralNetwork;

import processing.core.PApplet;

public class Population {
    BirdBrain[] population;
    int generation = 0;

    public Population(int popSize) {
        population = new BirdBrain[popSize];
        for (int i = 0; i < population.length; i++) {
            population[i] = new BirdBrain();
        }
    }

    public void save(){
        for (int i = 0; i < population.length; i++) {
            population[i].save("src/data/Network#" + i + ".nn");
        }
    }

    public void load(){
        for (int i = 0; i < population.length; i++) {
            try {
                population[i] = new BirdBrain(NeuralNetwork.load("src/data/Network#" + i +".nn"));
            } catch (Exception e) {
                //TODO: handle exception
            }
        }
    }

    public boolean update(Pipe p, PApplet app) {
        boolean alive = false;
        for (int i = 0; i < population.length; i++) {
            population[i].update(p, app);
            if (population[i].alive) {
                alive = true;
            }
        }
        if (!alive) {
            generation();
            return true;
        }
        return false;
    }

    public void display(PApplet app){
        for (int i = 0; i < population.length; i++) {
            if (population[i].alive) {
                population[i].display(app);
            }
        }
    }

    public void generation() {
        generation++;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        System.out.println("\nGeneration " + generation + ": " + dtf.format(now));
        double greatest = 0;
        double least = Double.MAX_VALUE;
        double average = 0;
        int averageScore = 0;
        int greatestScore = 0;
        for (int i = 0; i < population.length; i++) {
            if (population[i].fitness > greatest) {
                greatest = population[i].fitness;
            }
            if (population[i].fitness < least) {
                least = population[i].fitness;
            }
            if(population[i].score > greatestScore){
                greatestScore = population[i].score;
            }
            average += population[i].fitness;
            averageScore += population[i].score;
        }
        average /= population.length;
        averageScore/=population.length;
        System.out.println("Smallest Fitness: " + least);
        System.out.println("Average Fitness: " + average);
        System.out.println("Greatest Fitness: " + greatest);
        int highscore = saveHighScore(greatestScore);
        System.out.println("Average Score: " + averageScore);
        if(highscore == greatestScore)
            System.out.println("New High Score!");
        System.out.println("Highscore: " + greatestScore);
        if (least < 0)
            for (int i = 0; i < population.length; i++) {
                population[i].fitness += Math.abs(least);
            }
        for (int i = 0; i < population.length; i++) {
            population[i].fitness/=((greatest + Math.abs(least))/1000);
        }
        ArrayList<BirdBrain> bbs = new ArrayList<>();
        for (int i = 0; i < population.length; i++) {
            for (int j = 0; j < (int) (double) population[i].fitness; j++) {
                bbs.add(population[i]);
            }
        }
        for (int i = 0; i < population.length; i++) {
            population[i] = new BirdBrain(bbs.get((int)(Math.random() * bbs.size())) , bbs.get((int)(Math.random() * bbs.size())));
        }
    }

    private int saveHighScore(int highscore) {
        try {
            File f = new File("src/data/HighScore.txt");
            Scanner scan = new Scanner(f);
            int high = scan.nextInt();
            if(high < highscore){
                f.createNewFile();
                FileWriter fw = new FileWriter(f);
                fw.write(highscore + "");
                fw.flush();
                fw.close();
                scan.close();
                return highscore;
            }
            scan.close();
            return high;
        } catch (Exception e) {
            //TODO: handle exception
        }
        return 0;
    }

    public void pipeCleared() {
        for(BirdBrain b : population){
            if(b.alive){
                b.fitness += 500;
            }
        }
    }
}
