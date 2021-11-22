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
        float[][] XORin = { { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 } };
        float[][] XORtar = { { 0 }, { 1 }, { 1 }, { 0 } };

        NeuralNetwork nn = new NeuralNetwork(2, 8, 1, 4, 0.01f);

        nn.fit(XORin, XORtar, 10000);

        System.out.println(Arrays.toString(XORin[0]) + ": " + Arrays.toString(nn.guess(XORin[0])));
        System.out.println(Arrays.toString(XORin[1]) + ": " + Arrays.toString(nn.guess(XORin[1])));
        System.out.println(Arrays.toString(XORin[2]) + ": " + Arrays.toString(nn.guess(XORin[2])));
        System.out.println(Arrays.toString(XORin[3]) + ": " + Arrays.toString(nn.guess(XORin[3])));


        System.out.println("\n\n");
        BabyNet bbn = new BabyNet(2, 2, 1);

        // for (int i = 0; i < 1000; i++) {
        //     float err = 0;
        //     for (int j = 0; j < 10000; j++) {
        //         int rand = (int) (Math.random() * 4);
        //         err += bbn.train(XORin[rand], XORtar[rand]);
        //     }
        //     System.out.println(i + ": " + (err / 100));
        // }

        // System.out.println(Arrays.toString(XORin[0]) + ": " + Arrays.toString(bbn.guess(XORin[0])));
        // System.out.println(Arrays.toString(XORin[1]) + ": " + Arrays.toString(bbn.guess(XORin[1])));
        // System.out.println(Arrays.toString(XORin[2]) + ": " + Arrays.toString(bbn.guess(XORin[2])));
        // System.out.println(Arrays.toString(XORin[3]) + ": " + Arrays.toString(bbn.guess(XORin[3])));
    }
}