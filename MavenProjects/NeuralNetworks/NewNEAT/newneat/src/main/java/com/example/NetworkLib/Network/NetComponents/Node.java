package com.example.NetworkLib.Network.NetComponents;

import java.util.HashSet;

import com.example.NetworkLib.Genes.NodeGene;

public class Node implements Comparable<Node>{

    public double value;
    public HashSet<Connection> inConnections;
    public double x;
    

    public Node(NodeGene n) {
        inConnections = new HashSet<>();
        x = n.x;
        value = 0;
    }

    public void evaluate(){
        for(Connection c : inConnections){
            c.evaluate();
        }
        value = activate(value);
    }

    private double activate(double x) {
        // return 1.0/(1 + Math.exp(-x));
        return Math.tanh(x);
    }

    @Override
    public int compareTo(Node o) {
        return ((Double) x).compareTo(o.x);
    }
}
