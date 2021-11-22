package com.example.Network;

import java.io.Serializable;

import com.example.Matrix.Matrix;

public class NeuralNetwork implements Serializable {
    Matrix[] weights;
    Matrix[] biases;

    public float learningRate;

    public NeuralNetwork(int inputSize, int hiddenSize, int outputSize, int hiddenLayers, float lr) {
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

    public float[] guess(float[] in) {
        Matrix layer = Matrix.fromArray(in);
        for (int i = 0; i < weights.length; i++) {
            layer = Matrix.multiply(weights[i], layer);
            layer.add(biases[i]);
            layer.sigmoid();
        }
        return layer.toArray();
    }

    public float train(float[] in, float[] t){
        //Feedforward and construct array of layerOutputs
        Matrix output = Matrix.fromArray(in);
        Matrix[] layerOutputs = new Matrix[weights.length + 1];
        for (int i = 0; i < weights.length; i++) {
            layerOutputs[i] = output;
            output = Matrix.multiply(weights[i], output);
            output.add(biases[i]);
            output.sigmoid();
        }
        layerOutputs[layerOutputs.length - 1] = output;

        Matrix target = Matrix.fromArray(t);
        Matrix error = Matrix.subtract(target, output);
        float err = error.average();

        for(int i = layerOutputs.length - 1; i > 0; i--){
            Matrix gradient = layerOutputs[i].dsigmoid();
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

    public void fit(float[][] inputValues, float[][] targetValues, int epochLength){
        for (int i = 0; i < 100; i++) {
            double err = 0;
            for (int j = 0; j < epochLength; j++) {
                int rand = (int) (Math.random() * inputValues.length);
                err += train(inputValues[rand], targetValues[rand]);
            }
            err /= epochLength;
            System.out.println("Epoch " + i + " Absolute Error: " + (err));
            if(Math.abs(err) < 0.001){
                return;
            }
        }
    }
}
