package com.example;

import com.example.NetworkLib.Population;

/**
 * Hello world!
 *
 */
public class NEAT 
{
    public static void main( String[] args )
    {
        double[][] XORInputs = new double[][]{{0, 0},{1, 0},{0, 1},{1, 1}};
        double[][] XOROutputs = new double[][]{{-1},{1},{1},{-1}};
        Population p = new Population(2, 1, 1000);
        for (int i = 0; i < 1000; i++) {
            System.out.println("Generation " + i + ":");
            for (int j = 0; j < 100; j++) {
                int rand = (int) (Math.random() * XORInputs.length);
                p.XORTrain(XORInputs[rand], XOROutputs[rand]);
            }
            p.generation();
        }
    }
}
