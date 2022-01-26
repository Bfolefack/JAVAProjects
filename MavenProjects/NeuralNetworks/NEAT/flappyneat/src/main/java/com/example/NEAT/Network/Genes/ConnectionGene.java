package com.example.NEAT.Network.Genes;

import java.io.Serializable;

import com.example.NEAT.Network.Structures.Node;

public class ConnectionGene implements Serializable { 
    public Node in;
    public Node out;
    public double weight;
    public boolean enabled;
    public ConnectionGene(Node i, Node o, double w){
        in = i;
        out = o;
        weight = w;
        enabled = true;
    }

    public ConnectionGene(Node i, Node o, double w, boolean e){
        in = i;
        out = o;
        weight = w;
        enabled = e;
    }

    public void perturbWeight(){
        weight *= (0.9 + Math.random() * 0.2);
    }

    public void scrambleWeight(){
        weight = Math.random() * 4 - 2;
    }

    public void enableDisable(){
        enabled = !enabled;
    }

    public ConnectionGene copy(){
        ConnectionGene output = new ConnectionGene(in.copy(), out.copy(), weight, enabled);
        return output;
    }

    @Override
    public String toString() {
        return in.idNum + "," + out.idNum;
    }

    public ConnectionGene copy(boolean e) {
        ConnectionGene output = new ConnectionGene(in.copy(), out.copy(), weight, e);
        return output;
    }
}
