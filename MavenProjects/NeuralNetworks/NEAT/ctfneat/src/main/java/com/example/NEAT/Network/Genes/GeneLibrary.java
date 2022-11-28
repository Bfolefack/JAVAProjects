package com.example.NEAT.Network.Genes;

import java.io.Serializable;
import java.util.HashMap;

import com.example.NEAT.Network.Structures.Node;
import com.example.NEAT.Utils.Config;

public class GeneLibrary implements Serializable {
    public HashMap<Integer, HashMap<Integer, Integer>> nodeInnovations;
    public HashMap<Integer, HashMap<Integer, Integer>> connectionInnovations;
    public int nextNode;
    public int nextConnection;

    public int maxInputs = Config.inNodes;
    public int maxOutputs = Config.outNodes;

    public GeneLibrary() {
        connectionInnovations = new HashMap<>();
        nodeInnovations = new HashMap<>();
        nextNode += maxInputs + maxOutputs;
    }

    public int getConnectionInnovation(int inNode, int outNode) {
        if (connectionInnovations.containsKey(inNode)) {
            if (connectionInnovations.get(inNode).containsKey(outNode)) {
                return connectionInnovations.get(inNode).get(outNode);
            }
        }
        return -1;
    }

    public int getNodeID(int inID, int outID) {
        HashMap<Integer, Integer> n = nodeInnovations.get(inID);
        if (n != null) {
            Integer n2 = n.get(outID);
            if (n2 != null)
                return n2;
        }
        if (nodeInnovations.get(inID) == null)
            nodeInnovations.put(inID, new HashMap<Integer, Integer>());
        nodeInnovations.get(inID).put(outID, nextNode);
        nextNode++;
        return nextNode;
    }

    public boolean isInnovation(int inID, int outID) {
        return getConnectionInnovation(inID, outID) == -1;
    }

    public ConnectionGene createConnectionGene(Node inNode, Node outNode, double weight) {
        if (isInnovation(inNode.idNum, outNode.idNum)) {
            if (connectionInnovations.get(inNode.idNum) == null)
                connectionInnovations.put(inNode.idNum, new HashMap<Integer, Integer>());
            connectionInnovations.get(inNode.idNum).put(outNode.idNum, nextConnection);
            nextConnection++;
            // System.out.println("CREATED CONNECTION: " + inNode.idNum + "," + outNode.idNum + "," + nextConnection);
            return new ConnectionGene(inNode, outNode, weight);
        }
        System.out.println("NotInnovation: " + inNode.idNum + "," + outNode.idNum);
        return null;
    }
}