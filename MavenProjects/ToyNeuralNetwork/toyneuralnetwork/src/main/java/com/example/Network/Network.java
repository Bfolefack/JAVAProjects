package com.example.Network;

import java.util.Random;

import org.ejml.simple.SimpleMatrix;

public class Network {
  
  int input_nodes;
  int hidden_nodes;
  int output_nodes;
  float error;

  SimpleMatrix weights_ih;
  SimpleMatrix weights_ho;
  SimpleMatrix bias_h;
  SimpleMatrix bias_o;
  
  float learning_rate = 0.01f;
  public Network(int in_nodes, int hid_nodes, int out_nodes) {
      input_nodes = in_nodes;
      hidden_nodes = hid_nodes;
      output_nodes = out_nodes;

      weights_ih = SimpleMatrix.random_FDRM(hidden_nodes, input_nodes, 0, 1, new Random());
      weights_ho = SimpleMatrix.random_FDRM(output_nodes, hidden_nodes, 0, 1, new Random());
      bias_h = SimpleMatrix.random_FDRM(hidden_nodes, 1, 0, 1, new Random());
      bias_o = SimpleMatrix.random_FDRM(output_nodes, 1, 0, 1, new Random());
  }

  public float[] guess(float[] input_array) {
    // Generating the Hidden Outputs
    SimpleMatrix inputs = matrixFromArr(input_array);
    SimpleMatrix hidden = weights_ih.mult(inputs);
    hidden = hidden.plus(bias_h);
    // activation function!
    activate(hidden);

    // Generating the output's output!
    SimpleMatrix output = weights_ho.mult(hidden);
    output = output.plus(bias_o);
    activate(output);

    // Sending back to the caller!
    return matrixToArray(output);
  }

 public float train(float[] input_array, float[] target_array) {
    // Generating the Hidden Outputs
    SimpleMatrix inputs = matrixFromArr(input_array);
    SimpleMatrix hidden =  weights_ih.mult(inputs);
    hidden = hidden.plus(bias_h);
    // activation function!
    activate(hidden);

    // Generating the output's output!
    SimpleMatrix outputs = weights_ho.mult(hidden);
    outputs = outputs.plus(bias_o);
    activate(outputs);

    // Convert array to matrix object
    SimpleMatrix targets = matrixFromArr(target_array);

    // Calculate the error
    // ERROR = TARGETS - OUTPUTS
    SimpleMatrix output_errors = targets.minus(outputs);

    // Matrix gradient = outputs * (1 - outputs);
    // Calculate gradient
    SimpleMatrix gradients = deactivate(outputs);
    gradients.elementMult(output_errors);
    gradients = gradients.scale(learning_rate);
    error = (float) output_errors.get(0, 0);


    // Calculate deltas
    SimpleMatrix hidden_T = hidden.transpose();
    SimpleMatrix weight_ho_deltas = gradients.mult(hidden_T);

    // Adjust the weights by deltas
    weights_ho = weights_ho.plus(weight_ho_deltas);
    // Adjust the bias by its deltas (which is just the gradients)
    bias_o = bias_o.plus(gradients);

    // Calculate the hidden layer errors
    SimpleMatrix who_t = weights_ho.transpose();
    SimpleMatrix hidden_errors =who_t.mult(output_errors);

    // Calculate hidden gradient
    SimpleMatrix hidden_gradient = deactivate(hidden);
    hidden_gradient = hidden_gradient.elementMult(hidden_errors);
    hidden_gradient = hidden_gradient.scale(learning_rate);

    // Calcuate input->hidden deltas
    SimpleMatrix inputs_T = inputs.transpose();
    SimpleMatrix weight_ih_deltas = hidden_gradient.mult(inputs_T);

    weights_ih = weights_ih.plus(weight_ih_deltas);
    // Adjust the bias by its deltas (which is just the gradients)
    bias_h.plus(hidden_gradient);

    // outputs.print();
    // targets.print();
    //error.print();
    return error;
  }
  
  public void print() {
    System.out.println("weights:");
    weights_ih.print();
    weights_ho.print();


    System.out.println("bias:");
    bias_h.print();
    bias_o.print();
  }

  private SimpleMatrix matrixFromArr(float[] inputArr){
    return new SimpleMatrix(new float[][] {inputArr}).transpose();
  }

  private void activate(SimpleMatrix sm){
    for(int i = 0; i < sm.numRows(); i++){
      for(int j = 0; j < sm.numCols(); j++){
        sm.set(i, j, Math.tanh(sm.get(i, j)));
        // sm.set(i, j, (float)(1/(1 + Math.exp(-sm.get(i, j)))));
      }
    }
  }

  private SimpleMatrix deactivate(SimpleMatrix sm){
    SimpleMatrix temp = new SimpleMatrix(sm.numRows(),sm.numCols());
    for(int i = 0; i < sm.numRows(); i++){
      for(int j = 0; j < sm.numCols(); j++){
        float f = (float) sm.get(i, j);
        // temp.set(i, j, f * (1 - f));
        temp.set(i, j, (1 - (f * f)));
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