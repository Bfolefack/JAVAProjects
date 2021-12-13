package com.example.NetworkLib.Genes;

import com.example.NetworkLib.Utils.Config;

public class ConnectionGene {
    public int innovationNumber;
    public int in;
    public int out;
    public double weight;
    public boolean enabled;

    public ConnectionGene(int innov, int i, int o, double w){
        innovationNumber = innov;
        in = i;
        out = o;
        weight = w;
        enabled = true;
    }

    public ConnectionGene(int innov, int i, int o){
        innovationNumber = innov;
        in = i;
        out = o;
        enabled = true;
        weight = (Math.random() * Config.RANDOM_wEIGHT_RANGE) - Config.RANDOM_wEIGHT_RANGE/2;
    }

    public ConnectionGene(ConnectionGene cg) {
        innovationNumber = cg.innovationNumber;
        in = cg.in;
        out = cg.out;
        weight = cg.weight;
        enabled = cg.enabled;
    }

    @Override
    public int hashCode(){
        return in * Config.MAX_NODES + out;
    }

    public static int hashCode(int in, int out){
        return in * Config.MAX_NODES + out;
    }
}
