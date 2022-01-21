package com.example.NEAT.Network.Genes;

import java.net.InterfaceAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

import com.example.NEAT.Network.Structures.Node;
import com.example.NEAT.Utils.Config;

public class Genome {
    public static HashMap<Integer, HashMap<Integer, Integer>> nodeInnovations;
    public static HashMap<Integer, HashMap<Integer, Integer>> connectionInnovations;
    public static int nextNode;
    public static int nextConnection;

    public TreeMap<Integer, TreeSet<Node>> network;
    public HashMap<Integer, ConnectionGene> connections;
    public HashMap<Integer, Node> nodes;

    public static int maxInputs = 8;
    public static int maxOutputs = 2;

    public int inputs;
    public int outputs;

    public int layers = 2;

    public boolean resetAfterRun;

    static {
        connectionInnovations = new HashMap<>();
        nodeInnovations = new HashMap<>();
        nextNode += maxInputs + maxOutputs + 1;
    }

    public Genome(int in, int out, boolean reset) {
        resetAfterRun = reset;

        inputs = in;
        outputs = out;

        nodes = new HashMap<>();
        connections = new HashMap<>();
        nodes.put(0, new Node(0, 0)); // Bias Node

        for (int i = 1; i < inputs + 1; i++) {
            nodes.put(i, new Node(i, 0));
        }

        for (int j = 0; j < outputs; j++) {
            nodes.put(nodes.size(), new Node(nodes.size(), 1));
        }
    }

    public Genome(int in, int out, boolean reset, int lay) {
        resetAfterRun = reset;

        inputs = in;
        outputs = out;

        nodes = new HashMap<>();
        connections = new HashMap<>();
        nodes.put(0, new Node(0, 0)); // Bias Node

        for (int i = 1; i < inputs + 1; i++) {
            nodes.put(i, new Node(i, 0));
        }

        for (int j = 0; j < outputs; j++) {
            nodes.put(nodes.size(), new Node(nodes.size(), lay - 1));
        }

        layers = lay;
    }

    public double[] feedForward(double[] inputValues) {
        if (resetAfterRun) {
            clear();
        }
        generateNetwork();
        ArrayList<Double> out = null;
        nodes.get(0).inputSum = 1;
        for (int i = 1; i < inputs + 1; i++) {
            nodes.get(i).inputSum = inputValues[i - 1];
        }
        for (int layer : network.keySet()) {
            if (layer == layers - 1)
                out = new ArrayList<>();
            for (Node n : network.get(layer)) {
                if (out != null)
                    out.add(n.evaluate());
                else
                    n.evaluate();
            }
        }
        if (out != null) {
            Object[] dub = out.toArray();
            if (dub.length != outputs) {
                System.out.println("ERROR: TOO MANY OUTPUTS");
                Thread.dumpStack();
            }

            double[] fin = new double[dub.length];
            for (int i = 0; i < dub.length; i++) {
                fin[i] = (double) dub[i];
            }
            return fin;
        }
        return new double[] {-1};
    }

    private void clear() {
        for (Node n : nodes.values()) {
            n.clear();
        }
    }

    public void connectNodes() {

        for (Node n : nodes.values()) {
            n.outgoingConnections.clear();
        }
        for (ConnectionGene cg : connections.values()) {
            cg.in.outgoingConnections.add(cg);
        }

    }

    public void generateNetwork() {

        connectNodes();

        network = new TreeMap<>();
        ArrayList<Integer> cgs = new ArrayList<>();
        cgs.addAll(connections.keySet());

        for (Integer i : cgs) {
            connections.put(i, new ConnectionGene(nodes.get(connections.get(i).in.idNum),
                    nodes.get(connections.get(i).out.idNum), connections.get(i).weight));
        }

        for (Node n : nodes.values()) {
            if (network.get(n.xPos) == null) {
                network.put(n.xPos, new TreeSet<Node>());
            }
            network.get(n.xPos).add(n);
        }
    }

    private void addNode(Node n) {
        int idNum = n.idNum;
        Node newNode = n.copy();
        if (newNode.xPos >= layers - 1) {
            layers++;
            for (Node nn : nodes.values()) {
                if (nn.xPos >= newNode.xPos) {
                    nn.xPos++;
                }
            }
            nodes.put(idNum, newNode);
        }
    }

    public void addNode() {
        if (connections.size() < 1) {
            addConnection();
        }
        int count = 0;
        ConnectionGene og = (ConnectionGene) Config.getRandomObject(connections.values());
        while (!og.enabled || og.in.xPos >= layers - 1) {
            count++;
            if (count > Config.AdditionTimeoutCounter) {
                System.out.println("ERROR: NODE CREATION TIMEOUT");
                Thread.dumpStack();
                return;
            }
            og = (ConnectionGene) Config.getRandomObject(connections.values());
        }
        Integer idNum = null;
        boolean innovation = true;
        HashMap<Integer, Integer> n = nodeInnovations.get(og.in.idNum);
        if (n != null) {
            Integer n2 = n.get(og.out.idNum);
            if (n2 != null)
                idNum = n2;
        }
        if (idNum == null) {
            if (nodeInnovations.get(og.in.idNum) == null)
                nodeInnovations.put(og.in.idNum, new HashMap<Integer, Integer>());
            nodeInnovations.get(og.in.idNum).put(og.out.idNum, nextNode);
            idNum = nextNode;
            nextNode++;
        }

        Node newNode;
        if (og.in.xPos < og.out.xPos)
            newNode = new Node(idNum, og.in.xPos + 1);
        else
            newNode = new Node(idNum, og.out.xPos - 1);

        if (newNode.xPos == og.out.xPos) {
            layers++;
            for (Node nn : nodes.values()) {
                if (nn.xPos >= newNode.xPos) {
                    nn.xPos++;
                }
            }
            nodes.put(idNum, newNode);
        } else if (newNode.xPos == og.in.xPos) {
            nodes.put(idNum, newNode);
            layers++;
            for (Node nn : nodes.values()) {
                if (nn.xPos >= newNode.xPos) {
                    nn.xPos++;
                }
            }
        } else {
            nodes.put(idNum, newNode);
        }

        addConnection(og.in.idNum, newNode.idNum, 1);
        addConnection(newNode.idNum, og.out.idNum, og.weight);

        connectNodes();
    }

    public void addConnection() {

        Node inNode = (Node) Config.getRandomObject(nodes.values());
        Node outNode = (Node) Config.getRandomObject(nodes.values());
        boolean isGood = false;
        boolean innovation = false;
        int count = 0;
        // Check if the connection is valid
        while (true) {
            isGood = true;
            // Check if the nodes are in the same layer
            if (inNode.xPos == outNode.xPos || inNode.xPos >= layers - 1 || outNode.xPos <= 0) {
                isGood = false;
            } else {
                // Checks if the connection is a new innovation
                Integer innovationNum = null;
                HashMap<Integer, Integer> get = connectionInnovations.get(inNode.idNum);
                if (get != null)
                    innovationNum = get.get(outNode.idNum);
                if (innovationNum == null) {
                    innovation = true;
                    isGood = true;
                    break;
                }
                // Check if the connection already exists in the network
                if (connections.get(innovationNum) != null) {
                    isGood = false;
                }
            }
            if (isGood)
                break;
            inNode = (Node) Config.getRandomObject(nodes.values());
            outNode = (Node) Config.getRandomObject(nodes.values());
            if (count > Config.AdditionTimeoutCounter) {
                // System.out.println("ERROR: CONNECTION CREATION TIMEOUT");
                // Thread.dumpStack();
                return;
            }
            count++;
        }

        if (innovation) {
            if (connectionInnovations.get(inNode.idNum) == null)
                connectionInnovations.put(inNode.idNum, new HashMap<Integer, Integer>());
            connectionInnovations.get(inNode.idNum).put(outNode.idNum, nextConnection);
            connections.put(nextConnection, new ConnectionGene(inNode, outNode, Math.random() * 4 - 2));
            nextConnection++;
        } else {
            connections.put(connectionInnovations.get(inNode.idNum).get(outNode.idNum),
                    new ConnectionGene(inNode, outNode, Math.random() * 4 - 2));
        }
        connectNodes();

    }

    public void addConnection(int in, int out, double w) {
        Node inNode = nodes.get(in);
        Node outNode = nodes.get(out);
        boolean innovation = false;
        int count = 0;
        // Check if the connection is valid
        // Check if the nodes are in the same layer
        if (inNode.xPos == outNode.xPos) {
            return;
        } else {
            // Checks if the connection is a new innovation
            Integer innovationNum = null;
            HashMap<Integer, Integer> get = connectionInnovations.get(inNode.idNum);
            if (get != null)
                innovationNum = get.get(outNode.idNum);
            if (innovationNum == null) {
                innovation = true;
            }
        }

        if (innovation) {
            if (connectionInnovations.get(inNode.idNum) == null)
                connectionInnovations.put(inNode.idNum, new HashMap<Integer, Integer>());
            connectionInnovations.get(inNode.idNum).put(outNode.idNum, nextConnection);
            connections.put(nextConnection, new ConnectionGene(inNode, outNode, w));
            nextConnection++;
        } else {
            connections.put(connectionInnovations.get(inNode.idNum).get(outNode.idNum),
                    new ConnectionGene(inNode, outNode, w));
        }
    }

    

    public static Genome crossover(Genome top, Genome bottom) {
        Genome child = new Genome(top.inputs, top.outputs, top.resetAfterRun, top.layers);
        child.clear();
        child.layers = top.layers;

        HashMap<Integer, ConnectionGene> connections = new HashMap<>();

        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.addAll(top.connections.keySet());
        numbers.addAll(bottom.connections.keySet());
        if (top.connections.size() == 0) {
            return top;
        }
        int topLimit = Collections.max(top.connections.keySet());

        for (Integer i : numbers) {
            boolean enabled = false;
            if (i <= topLimit) {
                ConnectionGene topGene = top.connections.get(i);
                ConnectionGene bottomGene = bottom.connections.get(i);
                if (topGene != null && bottomGene != null) {
                    if (!topGene.enabled || !bottomGene.enabled) {
                        if (Math.random() < Config.geneReenableChance)
                            enabled = true;
                    } else
                        enabled = true;
                    if (Math.random() < Config.inheritDominantChance)
                        child.connections.put(i, topGene.copy(enabled));
                    else
                        child.connections.put(i, bottomGene.copy(enabled));
                } else if (topGene != null) {
                    // if (!topGene.enabled) {
                    // if (Math.random() < Config.geneReenableChance) {
                    // enabled = true;
                    // }
                    // } else
                    // enabled = true;
                    child.connections.put(i, topGene.copy());
                }
            } else {
                break;
            }
        }

        for (Node n : top.nodes.values()) {        
            // child.addNode(n);
            child.nodes.put(n.idNum, n.copy());
        }
        

        child.generateNetwork();

        return child;
    }

    

    public void mutate() {
        if (Math.random() < Config.addNodeChance)
            addNode();
        if (Math.random() < Config.addConnectionChance)
            addConnection();
        if (connections.size() > 0) {
            if (Math.random() < Config.pointMutationChance)
                ((ConnectionGene) Config.getRandomObject(connections.values())).enableDisable();
            if (Math.random() < Config.scrambleWeightChance)
                ((ConnectionGene) Config.getRandomObject(connections.values())).scrambleWeight();
            if (Math.random() < Config.perturbWeightChance)
                ((ConnectionGene) Config.getRandomObject(connections.values())).perturbWeight();
        }
        generateNetwork();
    }
}