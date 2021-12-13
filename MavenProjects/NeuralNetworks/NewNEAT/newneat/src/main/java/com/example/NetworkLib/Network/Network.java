package com.example.NetworkLib.Network;

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;

import com.example.NetworkLib.Population;
import com.example.NetworkLib.Genes.ConnectionGene;
import com.example.NetworkLib.Genes.NodeGene;
import com.example.NetworkLib.Network.NetComponents.Connection;
import com.example.NetworkLib.Network.NetComponents.Node;
import com.example.NetworkLib.Utils.ConnectionGeneContainer;

public class Network {
    public HashSet<Connection> connections;
    public TreeMap<Integer, Node> inputNodes;
    public TreeMap<Integer, Node> outputNodes;
    public TreeSet<Node> hiddenNodes;

    public Network(HashMap<Integer, NodeGene> nodeGenes,
            ConnectionGeneContainer<Integer, Integer, ConnectionGene> connectionGenes, Population pop) {

        nodeGenes.clear();

        for (int i = 0; i < pop.inputs + pop.outputs; i++) {
            nodeGenes.put(i, pop.getNode(i));
        }

        for (ConnectionGene cg : connectionGenes) {
            if (cg.enabled) {
                if (!nodeGenes.keySet().contains(cg.in)) {
                    nodeGenes.put(cg.in, pop.getNode(cg.in));
                    nodeGenes.get(cg.in).outCount++;
                }
                if (!nodeGenes.keySet().contains(cg.out)) {
                    nodeGenes.put(cg.out, pop.getNode(cg.out));
                    nodeGenes.get(cg.out).inCount++;
                }
            }
        }
        // HashSet<Integer> hitList = new HashSet<Integer>();
        // for (int i : nodeGenes.keySet()) {
        //     NodeGene ng = nodeGenes.get(i);
        //     if (i > pop.outputs + pop.inputs)
        //         if (ng.inCount < 1 || ng.outCount < 1)
        //             hitList.add(i);
        // }
        // for (int i : hitList) {
        //     nodeGenes.remove(i);
        // }

        connections = new HashSet<>();
        inputNodes = new TreeMap<>();
        TreeMap<Integer, Node> hidNodes = new TreeMap<>();
        outputNodes = new TreeMap<>();
        for (NodeGene n : nodeGenes.values()) {
            if (n.innovationNumber < pop.inputs)
                inputNodes.put(n.innovationNumber, new Node(n));
            else if (n.innovationNumber < pop.inputs + pop.outputs)
                outputNodes.put(n.innovationNumber, new Node(n));
            else{
                // System.out.println("hiddens");
                hidNodes.put(n.innovationNumber, new Node(n));
            }
        }

        TreeMap<Integer, Node> allNodes = new TreeMap<>(inputNodes);
        allNodes.putAll(hidNodes);
        allNodes.putAll(outputNodes);
        for (ConnectionGene cg : connectionGenes) {
            Node in = allNodes.get(cg.in);
            Node out = allNodes.get(cg.out);
            if(out != null && in != null){
                Connection c = new Connection(in, out, cg.weight);
                connections.add(c);
                out.inConnections.add(c);
            }
        }
        hiddenNodes = new TreeSet<>(hidNodes.values());
    }


    public double[] evaluate(double[] in, Population p) {
        inputNodes.get(0).value = 1;
        for (int i = 1; i < p.inputs; i++) {
            inputNodes.get(i).value = in[i - 1];
        }
        for (Node n : hiddenNodes) {
            n.evaluate();
        }
        double[] out = new double[p.outputs];
        for (int i = 0; i < p.outputs; i++) {
            outputNodes.get(i + p.inputs).evaluate();
            out[i] = outputNodes.get(i + p.inputs).value;
        }
        return out;
    }
}
