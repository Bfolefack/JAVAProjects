package com.example.Game.Actors;

import com.example.Game.Cart;
import com.example.Game.DoubleCart;
import com.example.NEAT.Network.Genes.Genome;
import com.example.NEAT.Population.Actor;
import com.example.NEAT.Utils.NetworkDisplay;

import processing.core.PApplet;

public class DoublePoleVelActor extends Actor {

    DoubleCart cart;
    transient public static PApplet app;
    public boolean alive;
    transient NetworkDisplay nd;
    public boolean displayed;

    public DoublePoleVelActor(Genome b) {
        super(b);
        cart = new DoubleCart();
        alive = true;
    }

    public DoublePoleVelActor(int inputs, int outputs, boolean r) {
        super(inputs, outputs, r);
        cart = new DoubleCart();
        alive = true;
    }

    @Override
    public void setInputs(int[] in) {
        // TODO Auto-generated method stub

    }

    @Override
    public void act() {
        if (alive) {
            // double[] in = new double[]{cart.cartVel * 10, cart.poleVel * 10};
            // double[] in = new double[]{cart.cartPos / 5, cart.polePos - Math.PI};
            double[] in = new double[] {cart.poles[0].polePos - Math.PI,/* cart.poles[0].poleVel * 10, */
                                        cart.poles[1].polePos - Math.PI,/* cart.poles[1].poleVel * 10,  */
                                        cart.cartPos / 5 /*, cart.cartVel * 10  */
            };
            
            float out = (float) brain.feedForward(in)[0];
            if(out > 1){
                out = 1;
            } else if(out < -1){
                out = -1;
            }
            cart.update((float)(out));
            fitness++;
            for (int i = 0; i < cart.poles.length; i++) {
                if (cart.poles[i].polePos < Math.PI / 2f || cart.poles[i].polePos > 3 * Math.PI / 2 || Math.abs(cart.cartPos) > 5)
                alive = false;
            }
        }
    }

    private int sgn(double d) {
        if (d <= 0) {
            return -1;
        } else if (d > 0) {
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

        if (nd == null) {
            nd = new NetworkDisplay(350, 200);
        }
        nd.updateGenome(brain);
        nd.display(app, cart.cartPos * 100 + app.width / 2 - 175, app.height / 2);
        displayed = true;
    }

    @Override
    public Actor breed(Actor b) {
        Genome g = Genome.crossover(brain, b.brain);
        g.mutate();

        return new DoublePoleVelActor(g);
    }

    @Override
    public double calculateFitness() {
        fitness = fitness * fitness;
        return fitness;
    }

}
