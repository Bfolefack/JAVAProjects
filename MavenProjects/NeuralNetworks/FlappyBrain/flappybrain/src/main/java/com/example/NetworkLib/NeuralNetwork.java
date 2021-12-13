package com.example.NetworkLib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class NeuralNetwork implements Serializable {
    public Matrix[] weights;
    public Matrix[] biases;

    public double learningRate;

    public NeuralNetwork(int inputSize, int hiddenSize, int outputSize, int hiddenLayers, double lr) {
        weights = new Matrix[hiddenLayers + 1];
        biases = new Matrix[hiddenLayers + 1];
        weights[0] = new Matrix(hiddenSize, inputSize);
        biases[0] = new Matrix(hiddenSize, 1);
        weights[weights.length - 1] = new Matrix(outputSize, hiddenSize);
        biases[biases.length - 1] = new Matrix(outputSize, 1);
        for (int i = 1; i < weights.length - 1; i++) {
            weights[i] = new Matrix(hiddenSize, hiddenSize);
            biases[i] = new Matrix(hiddenSize, 1);
        }
        learningRate = lr;
    }

    public double[] guess(double[] in) {
        Matrix layer = Matrix.fromArray(in);
        for (int i = 0; i < weights.length; i++) {
            layer = Matrix.multiply(weights[i], layer);
            layer.add(biases[i]);
            layer.activation();
        }
        return layer.toArray();
    }

    public double train(double[] in, double[] t) {
        // Feedforward and construct array of layerOutputs
        Matrix output = Matrix.fromArray(in);
        Matrix[] layerOutputs = new Matrix[weights.length + 1];
        for (int i = 0; i < weights.length; i++) {
            layerOutputs[i] = output;
            output = Matrix.multiply(weights[i], output);
            output.add(biases[i]);
            output.activation();
        }
        layerOutputs[layerOutputs.length - 1] = output;

        Matrix target = Matrix.fromArray(t);
        Matrix error = Matrix.subtract(target, output);
        double err = error.average();

        for (int i = layerOutputs.length - 1; i > 0; i--) {
            Matrix gradient = layerOutputs[i].deactivation();
            gradient.multiply(error);
            gradient.multiply(learningRate);
            Matrix innerLayerT = Matrix.transpose(layerOutputs[i - 1]);
            Matrix delta = Matrix.multiply(gradient, innerLayerT);

            weights[i - 1].add(delta);
            biases[i - 1].add(gradient);

            Matrix weightT = Matrix.transpose(weights[i - 1]);
            error = Matrix.multiply(weightT, error);
        }

        return Math.abs(err);
    }

    public void fit(double[][] inputValues, double[][] targetValues, int epochLength) {
        for (int i = 0; i < 100; i++) {
            double err = 0;
            for (int j = 0; j < epochLength; j++) {
                int rand = (int) (Math.random() * inputValues.length);
                err += train(inputValues[rand], targetValues[rand]);
            }
            err /= epochLength;
            System.out.println("Epoch " + i + " Absolute Error: " + (err));
            if (Math.abs(err) < 0.0001) {
                return;
            }
        }
    }

    public void save(String s) throws IOException, FileNotFoundException {
        File f = new File(s);
        // System.out.println(f.getAbsolutePath());
        f.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(f);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public static NeuralNetwork load(String s) throws IOException, ClassNotFoundException {
        File f = new File(s);
        System.out.println(f);
        FileInputStream fileInputStream = new FileInputStream(f);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        NeuralNetwork nn = (NeuralNetwork) objectInputStream.readObject();
        objectInputStream.close();
        return nn;
    }

    public static NeuralNetwork crossover(NeuralNetwork net1, NeuralNetwork net2, double crossoverFlipChance) {
        NeuralNetwork out = new NeuralNetwork(4, 8, 1, 2, 0);
        boolean flip = true;
        for (int i = 0; i < net1.weights.length; i++) {
            for (int j = 0; j < net1.weights[i].data.length; j++) {
                for (int k = 0; k < net1.weights[i].data[0].length; k++) {
                    if (Math.random() < crossoverFlipChance) {
                        flip = !flip;
                    }
                    if (flip) {
                        out.weights[i].data[j][k] = net1.weights[i].data[j][k];
                    } else {
                        out.weights[i].data[j][k] = net2.weights[i].data[j][k];
                    }
                }
            }
            for (int j = 0; j < net1.biases[i].data.length; j++) {
                if (Math.random() < crossoverFlipChance) {
                    flip = !flip;
                }
                if (flip) {
                    out.biases[i].data[j][0] = net1.biases[i].data[j][0];
                } else {
                    out.biases[i].data[j][0] = net1.biases[i].data[j][0];
                }
            }
        }

        return out;
    }

    public void mutate(double mutationRange, double mutationRate) {
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].data.length; j++) {
                for (int k = 0; k < weights[i].data[0].length; k++) {
                    if (Math.random() < mutationRate) {
                        weights[i].data[j][k] += ((Math.random() - 0.5) * 2) * mutationRange;
                    }
                }
            }
            for (int j = 0; j < biases[i].data.length; j++) {
                if (Math.random() < mutationRate) {
                    biases[i].data[j][0] += ((Math.random() - 0.5) * 2) * mutationRange;
                }
            }
        }
    }
}
