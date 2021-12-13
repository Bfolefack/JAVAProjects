package com.example.NetworkLib.Genes;

import java.util.Collection;
import java.util.HashSet;

public class NodeGene {
    public int innovationNumber;
    public int inCount;
    public int outCount;
    public double x;
    private HashSet<ConnectionGene> outgoingConnections;

    public NodeGene(int i, double x_) {
        innovationNumber = i;
        x = x_;
    }


    public void addConnections(ConnectionGene cg){
        outgoingConnections.add(cg);
    }

    public Collection<ConnectionGene> getConnections(){
        return outgoingConnections;
    }

    @Override
    public int hashCode() {
        return innovationNumber;
    }
}
