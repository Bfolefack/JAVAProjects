package com.example.NetworkLib.Network.NetComponents;

import com.example.NetworkLib.Population;
import com.example.NetworkLib.Genes.ConnectionGene;

public class Connection {
    Node inNode;
    Node outNode;
    double weight;

    public Connection(Node in, Node out, double w){
        inNode = in;
        outNode = out;
        weight = w;
    }

    public void evaluate(){
        outNode.value += inNode.value * weight;
    }
}
