package com.example;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.example.Data.XORData;
import com.example.Network.*;

import org.ejml.simple.SimpleMatrix;

/**
 * Hello world!
 *
 */
public class NetworkLibrary 
{
    public static void main( String[] args )
    {
        XORData[] data = {
            new XORData(0, 0, 0),
            new XORData(1, 0, 1),
            new XORData(0, 1, 1),
            new XORData(1, 1, 0),
        };
        ArrayList<Integer> choices = new ArrayList<Integer>();
        choices.add(0);
        choices.add(1);
        choices.add(2);
        choices.add(3);
        Network nn = new Network(2, 4, 1, 2, 0.01f);
        for(int i = 0; i < 10000; i++){
            float avgError = 0;
            for(int j : choices){
                avgError += Math.abs(nn.train(data[j].inputs, data[j].outputs));
                System.out.println(nn.error);
            }
            System.out.println(avgError/choices.size());
            if(avgError/choices.size() < 0.01){
                break;
            }
            Collections.shuffle(choices);
        }

        System.out.println(Arrays.toString(nn.guess(new float[]{0, 0})));
        System.out.println(Arrays.toString(nn.guess(new float[]{1, 0})));
        System.out.println(Arrays.toString(nn.guess(new float[]{0, 1})));
        System.out.println(Arrays.toString(nn.guess(new float[]{1, 1})));
    }
}
