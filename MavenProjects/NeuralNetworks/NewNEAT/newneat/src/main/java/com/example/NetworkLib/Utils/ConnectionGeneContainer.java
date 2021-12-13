package com.example.NetworkLib.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import com.example.NetworkLib.Genes.ConnectionGene;

public class ConnectionGeneContainer<H extends Integer, I extends Integer, G extends ConnectionGene> implements Iterable<ConnectionGene>{
    public HashMap<Integer, ConnectionGene> hashes;
    public HashMap<Integer, ConnectionGene> innovations;

    public ConnectionGeneContainer(){
        hashes = new HashMap<>();
        innovations = new HashMap<>();
    }

    public ConnectionGene getByHash(int hashCode) {
        return hashes.get(hashCode);
    }

    public ConnectionGene getByInnovation(int innov){
        return innovations.get(innov);
    }

    public int size(){
        if(hashes.size() == innovations.size())
            return hashes.size();
        return 0;
    }

    public void add(ConnectionGene cg){
        hashes.put(cg.hashCode(), cg);
        ConnectionGene test = innovations.put(cg.innovationNumber, cg);
        if(test != null){
            System.err.println("ConnectionOverwriteException");
            Thread.dumpStack();
        }
    }

    public void addAll(Collection<ConnectionGene> cgc){
        for(ConnectionGene cg : cgc){
            add(cg);
        }
    }

    public ConnectionGene getRandom(){
        return (new ArrayList<ConnectionGene>(hashes.values()).get((int)(Math.random() * hashes.size())));
    }

    @Override
    public Iterator<ConnectionGene> iterator() {
        return hashes.values().iterator();
    }

    public boolean contains(ConnectionGene cg) {
        return hashes.values().contains(cg);
    }

    public void removeAll(Collection<ConnectionGene> remove) {
        hashes.values().removeAll(remove);
        innovations.values().removeAll(remove);
    }

    
}
