package com.example.Network;

import com.example.Matrix.Matrix;

public class BabyNet {
    Matrix weights_ih, weights_ho, bias_h, bias_o;
    public double learningRate = 0.03f;

    public BabyNet(int i, int h, int o) {
        weights_ih = new Matrix(h, i);
        weights_ho = new Matrix(o, h);

        bias_h = new Matrix(h, 1);
        bias_o = new Matrix(o, 1);
    }

    public double[] guess(double[] in) {
        Matrix input = Matrix.fromArray(in);
        Matrix hidden = Matrix.multiply(weights_ih, input);
        hidden.add(bias_h);
        hidden.activation();

        Matrix output = Matrix.multiply(weights_ho, hidden);
        output.add(bias_o);
        output.activation();
        return output.toArray();
    }

    public double train(double[] in, double[] t) {
        // Feeding Forward
        Matrix input = Matrix.fromArray(in);
        Matrix hidden = Matrix.multiply(weights_ih, input);
        hidden.add(bias_h);
        hidden.activation();

        Matrix output = Matrix.multiply(weights_ho, hidden);
        output.add(bias_o);
        output.activation();

        double err;
        //Backpropogation
        Matrix target = Matrix.fromArray(t);
        
        Matrix error = Matrix.subtract(target, output);
        err = error.average();
        Matrix gradient = output.deactivation();
        gradient.multiply(error);
        gradient.multiply(learningRate);

        Matrix hidden_T = Matrix.transpose(hidden);
        Matrix who_delta = Matrix.multiply(gradient, hidden_T);

        weights_ho.add(who_delta);
        bias_o.add(gradient);

        Matrix who_T = Matrix.transpose(weights_ho);
        Matrix hidden_errors = Matrix.multiply(who_T, error);

        Matrix h_gradient = hidden.deactivation();
        h_gradient.multiply(hidden_errors);
        h_gradient.multiply(learningRate);

        Matrix i_T = Matrix.transpose(input);
        Matrix wih_delta = Matrix.multiply(h_gradient, i_T);

        weights_ih.add(wih_delta);
        bias_h.add(h_gradient);

        return err;
    }
}
