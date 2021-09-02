package com.example.Network;

import java.util.Random;

import org.ejml.simple.SimpleMatrix;

public class Network {
    private int inputNum, hiddenSize, hiddenLayers, outputNum;
    private float learningRate;
    public float error;
    private SimpleMatrix[] weights;
    private SimpleMatrix[] biases;

    public Network(int _i, int _hs, int _o, int _hl, float _lr){
        inputNum = _i;
        outputNum = _o;
        hiddenSize = _hs;
        hiddenLayers = _hl;
        learningRate = _lr;

        weights = new SimpleMatrix[hiddenLayers + 1];
        weights[0] = SimpleMatrix.random_FDRM(hiddenSize, inputNum, 0, 1, new Random());
        for (int i = 1; i < weights.length - 1; i++) {
            weights[i] = SimpleMatrix.random_FDRM(hiddenSize, hiddenSize, 0, 1, new Random());
        }
        weights[weights.length - 1] = SimpleMatrix.random_FDRM(outputNum, hiddenSize, 0, 1, new Random());

        biases = new SimpleMatrix[hiddenLayers + 1];
        for (int i = 0; i < biases.length - 1; i++) {
            biases[i] = SimpleMatrix.random_FDRM(hiddenSize, 1, 0, 1, new Random());
        }
        biases[biases.length - 1] = SimpleMatrix.random_FDRM(outputNum, 1, 0, 1, new Random());
    }

    public float[] guess(float[] inputArr) {
        SimpleMatrix inputs = matrixFromArr(inputArr);
        SimpleMatrix hidden = weights[0].mult(inputs);
        hidden = hidden.plus(biases[0]);
        activate(hidden);
        for(int i = 1; i < weights.length; i++) {
            hidden = weights[i].mult(hidden);
            hidden.plus(biases[i]);
            activate(hidden);
        }
        return matrixToArray(hidden);
    }

    public float train(float[] inputArr, float[] targetsArray) {
        SimpleMatrix[] hiddens = new SimpleMatrix[weights.length];
        SimpleMatrix inputs = matrixFromArr(inputArr);
        activate(inputs);
        hiddens[0] = inputs;
        SimpleMatrix outputs = weights[0].mult(inputs);
        outputs = outputs.plus(biases[0]);
        activate(outputs);
        for(int i = 1; i < weights.length; i++){
            hiddens[i] = outputs;
            outputs = weights[i].mult(outputs);
            outputs = outputs.plus(biases[i]);
            activate(outputs);
        }
        SimpleMatrix targets = matrixFromArr(targetsArray);
        SimpleMatrix outputError = targets.minus(outputs);
        // outputError = outputError.elementMult(outputError).scale(0.5);
        error = avg(outputError);
        // System.out.println(targets);
        // System.out.println(outputs);
        // System.out.println(outputError);
        // System.out.println("\n\n");
        SimpleMatrix[] errors = new SimpleMatrix[biases.length];
        errors[errors.length - 1] = outputError;

        SimpleMatrix[] gradients = new SimpleMatrix[biases.length];

        for(int i = errors.length - 2; i >= 0; i--){
            SimpleMatrix tran = weights[i + 1].transpose();
            errors[i] = tran.mult(errors[i + 1]);
        }

        for(int i = gradients.length - 1; i >= 0; i--){
            if(i == gradients.length - 1){
                gradients[i] = deactivate(outputs);
            } else {
                gradients[i] = deactivate(hiddens[i + 1]);
            }
            gradients[i] = gradients[i].elementMult(errors[i]);
            gradients[i] = gradients[i].scale(learningRate);

            SimpleMatrix delta = gradients[i].mult(hiddens[i].transpose());

            weights[i] = weights[i].plus(delta);
            biases[i] = biases[i].plus(gradients[i]);
        }

        return error;
    }

    private SimpleMatrix matrixFromArr(float[] inputArr){
        return new SimpleMatrix(new float[][] {inputArr}).transpose();
    }

    private void activate(SimpleMatrix sm){
        for(int i = 0; i < sm.numRows(); i++){
            for(int j = 0; j < sm.numCols(); j++){
                // sm.set(i, j, Math.tanh(sm.get(i, j)));
                sm.set(i, j, (float)(1/(1 + Math.exp(-sm.get(i, j)))));
            }
        }
    }

    private SimpleMatrix deactivate(SimpleMatrix sm){
        SimpleMatrix temp = new SimpleMatrix(sm.numRows(),sm.numCols());
        for(int i = 0; i < sm.numRows(); i++){
            for(int j = 0; j < sm.numCols(); j++){
                float f = (float) sm.get(i, j);
                temp.set(i, j, f * (1 - f));
                // temp.set(i, j, (1 - (f * f)));
            }
        }
        return temp;
    }

    private float[] matrixToArray(SimpleMatrix sm){
        float[] temp = new float[sm.numRows()];
        for(int i = 0; i < sm.numRows(); i++){
            temp[i] = (float) sm.get(i, 0);
        }
        return temp;
    }

    private float avg(SimpleMatrix sm){
        float[] f = matrixToArray(sm);
        float total = 0;
        for(int i = 0; i < f.length; i++){
            total += f[i];
        }
        return total/f.length;
    }
}
