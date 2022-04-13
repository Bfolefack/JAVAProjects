package com.example.Game.Actors;

import com.example.Game.Cart;
import com.example.Game.DoubleCart;
import com.example.NEAT.Network.Genes.Genome;
import com.example.NEAT.Population.Actor;
import com.example.NEAT.Utils.NetworkDisplay;

import javafx.scene.transform.Translate;
import processing.core.PApplet;

public class SinglePoleVelActor extends Actor{

    Cart cart;
    public static PApplet app;
    public boolean alive;
    NetworkDisplay nd;
    public boolean displayed;

    public SinglePoleVelActor(Genome b) {
        super(b);
        cart = new Cart();
        alive = true;
    }

    public SinglePoleVelActor(int inputs, int outputs, boolean r) {
        super(inputs, outputs, r);
        cart = new Cart();
        alive = true;
    }

    @Override
    public void setInputs(int[] in) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void act() {
        if(alive){
            // double[] in = new double[]{cart.cartVel * 10, cart.poleVel * 10};
            // double[] in = new double[]{cart.cartPos / 5, cart.polePos - Math.PI};
            double[] in = new double[]{cart.poleVel * 10};
            cart.update(sgn(brain.feedForward(in)[0]));
            fitness++;
            if(cart.polePos < Math.PI/2f || cart.polePos > 3 * Math.PI/2 || Math.abs(cart.cartPos) > 5)
                alive = false;
        }
    }

    private int sgn(double d){
        if(d <= 0){
            return -1;
        } else if(d > 0){
            return 1;
        } else { 
            return 0;
        }
    }

    @Override
    public void display() {
        app.fill(255);
        app.strokeWeight(2);
        cart.display(app);

        if(nd == null){
            nd = new NetworkDisplay(350, 200);
        }
        nd.updateGenome(brain);
        nd.display(app, cart.cartPos * 100 + app.width/2 - 175, app.height/2);
        displayed = true;
    }

    @Override
    public Actor breed(Actor b) {
        Genome g = Genome.crossover(brain, b.brain);
        g.mutate();


        return new SinglePoleVelActor(g);
    }

    @Override
    public double calculateFitness() {
        fitness = fitness * fitness;
        return fitness;
    }
    
}
