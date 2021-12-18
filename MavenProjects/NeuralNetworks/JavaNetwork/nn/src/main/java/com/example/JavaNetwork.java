package com.example;

import java.util.Arrays;

import com.example.Matrix.Matrix;
import com.example.Network.BabyNet;
import com.example.Network.NeuralNetwork;

/**
 * Hello world!
 *
 */
public class JavaNetwork {
    public static void main(String[] args) {
        double[][] XORin = { { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 } };
        double[][] XORtar = { { 1 }, { -1 }, { -1 }, { 1 } };

        NeuralNetwork nn = new NeuralNetwork(2, 16, 1, 4, 0.005f);


        nn.fit(XORin, XORtar, 10000);

        // NeuralNetwork nn = null;
        // try {
        //     nn = NeuralNetwork.load("src/main/Network.nn");
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

        try {
            nn.save("src/main/");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(Arrays.toString(XORin[0]) + ": " + Arrays.toString(nn.guess(XORin[0])));
        System.out.println(Arrays.toString(XORin[1]) + ": " + Arrays.toString(nn.guess(XORin[1])));
        System.out.println(Arrays.toString(XORin[2]) + ": " + Arrays.toString(nn.guess(XORin[2])));
        System.out.println(Arrays.toString(XORin[3]) + ": " + Arrays.toString(nn.guess(XORin[3])));

        System.out.println("\n\n");
    }
}