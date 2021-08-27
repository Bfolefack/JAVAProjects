public class Network {
  
  int input_nodes;
  int hidden_nodes;
  int output_nodes;
  
  Matrix weights_ih;
  Matrix weights_ho;
  Matrix bias_h;
  Matrix bias_o;
  
  float learning_rate = 0.01f;
  Network(int in_nodes, int hid_nodes, int out_nodes) {
      input_nodes = in_nodes;
      hidden_nodes = hid_nodes;
      output_nodes = out_nodes;

      weights_ih = new Matrix(hidden_nodes, input_nodes);
      weights_ho = new Matrix(output_nodes, hidden_nodes);
      weights_ih.randomize();
      weights_ho.randomize();

      bias_h = new Matrix(hidden_nodes, 1);
      bias_o = new Matrix(output_nodes, 1);
      bias_h.randomize();
      bias_o.randomize();
    }

  float[] guess(float[] input_array) {

    // Generating the Hidden Outputs
    Matrix inputs = Matrix.fromArray(input_array);
    Matrix hidden = Matrix.multiply(weights_ih, inputs);
    hidden.sum(bias_h);
    // activation function!
    hidden.activation();

    // Generating the output's output!
    Matrix output = Matrix.multiply(weights_ho, hidden);
    output.sum(bias_o);
    output.activation();

    // Sending back to the caller!
    return output.toArray();
  }

 void train(float[] input_array, float[] target_array) {
    // Generating the Hidden Outputs
    Matrix inputs = Matrix.fromArray(input_array);
    Matrix hidden = Matrix.multiply(weights_ih, inputs);
    hidden.sum(bias_h);
    // activation function!
    hidden.activation();

    // Generating the output's output!
    Matrix outputs = Matrix.multiply(weights_ho, hidden);
    outputs.sum(bias_o);
    outputs.activation();

    // Convert array to matrix object
    Matrix targets = Matrix.fromArray(target_array);

    // Calculate the error
    // ERROR = TARGETS - OUTPUTS
    Matrix output_errors = Matrix.diff(targets, outputs);

    // Matrix gradient = outputs * (1 - outputs);
    // Calculate gradient
    Matrix gradients = Matrix.dActivation(outputs);
    gradients.hadamard(output_errors);
    gradients.multiply(learning_rate);


    // Calculate deltas
    Matrix hidden_T = Matrix.transpose(hidden);
    Matrix weight_ho_deltas = Matrix.multiply(gradients, hidden_T);

    // Adjust the weights by deltas
    weights_ho.sum(weight_ho_deltas);
    // Adjust the bias by its deltas (which is just the gradients)
    bias_o.sum(gradients);

    // Calculate the hidden layer errors
    Matrix who_t = Matrix.transpose(weights_ho);
    Matrix hidden_errors = Matrix.multiply(who_t, output_errors);

    // Calculate hidden gradient
    Matrix hidden_gradient = Matrix.dActivation(hidden);
    hidden_gradient.hadamard(hidden_errors);
    hidden_gradient.multiply(learning_rate);

    // Calcuate input->hidden deltas
    Matrix inputs_T = Matrix.transpose(inputs);
    Matrix weight_ih_deltas = Matrix.multiply(hidden_gradient, inputs_T);

    weights_ih.sum(weight_ih_deltas);
    // Adjust the bias by its deltas (which is just the gradients)
    bias_h.sum(hidden_gradient);

    // outputs.print();
    // targets.print();
    // error.print();
  }
  
    void print() {
    System.out.println("weights:");
      weights_ih.print();
      weights_ho.print();


    System.out.println("bias:");
      bias_h.print();
      bias_o.print();
  }

}