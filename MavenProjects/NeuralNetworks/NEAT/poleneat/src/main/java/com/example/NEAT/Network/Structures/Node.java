package com.example.NEAT.Network.Structures;

import java.io.Serializable;
import java.util.HashSet;

import com.example.NEAT.Network.Genes.ConnectionGene;

public class Node implements Comparable<Node>, Serializable {
    public int idNum;

    public double inputSum;
    public double activatedSum;

    public int xPos;

    public HashSet<ConnectionGene> outgoingConnections;

    public Node(int num, int x) {
        idNum = num;
        xPos = x;
        outgoingConnections = new HashSet<>();
    }

    public void clear() {
        outgoingConnections.clear();
        inputSum = 0;
        activatedSum = 0;
    }

    public void softClear(){ 
        outgoingConnections.clear();
        inputSum = 0;
    }

    public double evaluate() {
        activatedSum = activate();
        for (ConnectionGene cg : outgoingConnections) {
            if (cg.enabled)
                cg.out.inputSum += activatedSum * cg.weight;
        }
        return activatedSum;
    }

    private double activate() {
        return Math.tanh(inputSum);
    }

    @Override
    public int compareTo(Node o) {
        return ((Integer) idNum).compareTo(o.idNum);
    }

    @Override
    public String toString() {
        return idNum + "";
    }

    public Node copy() {
        return new Node(idNum, xPos);
    }
}
