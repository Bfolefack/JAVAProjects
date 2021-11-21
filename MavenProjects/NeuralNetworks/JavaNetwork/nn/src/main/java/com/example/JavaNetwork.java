package com.example;

import java.util.Arrays;

import com.example.Matrix.Matrix;
import com.example.Network.BabyNet;

/**
 * Hello world!
 *
 */
public class JavaNetwork {
    public static void main(String[] args) {
        float[][] XORin = { { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 } };
        float[][] XORtar = { { 0 }, { 1 }, { 1 }, { 0 } };

        BabyNet bn = new BabyNet(2, 4, 1);

        for (int i = 0; i < 10000; i++) {
            float err = 0;
            for (int j = 0; j < 100; j++) {
                int rand = (int) (Math.random() * 4);
                err += bn.train(XORin[rand], XORtar[rand]);
            }
            System.out.println(i + ": " + (err / 100));
        }

        System.out.println(Arrays.toString(XORin[0]) + ": " + Arrays.toString(bn.guess(XORin[0])));
        System.out.println(Arrays.toString(XORin[1]) + ": " + Arrays.toString(bn.guess(XORin[1])));
        System.out.println(Arrays.toString(XORin[2]) + ": " + Arrays.toString(bn.guess(XORin[2])));
        System.out.println(Arrays.toString(XORin[3]) + ": " + Arrays.toString(bn.guess(XORin[3])));
    }
}