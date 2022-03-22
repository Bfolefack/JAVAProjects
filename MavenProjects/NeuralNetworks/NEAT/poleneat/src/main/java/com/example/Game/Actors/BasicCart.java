package com.example.Game.BasicCartSolver;

import com.example.Game.Cart;

import processing.core.PApplet;

public class BasicCart {
    public float[] k;

    public Cart cart;

    public float fitness;

    public boolean alive;

    public BasicCart(){
        alive = true;
        k = new float[4];
        k[0] = (float)((Math.random() * 2) - 1);
        k[1] = (float)((Math.random() * 2) - 1);
        k[2] = (float)((Math.random() * 2) - 1);
        k[3] = (float)((Math.random() * 2) - 1);
        cart = new Cart();
    }

    public BasicCart(float[] k_) {
        alive = true;
        k = k_;
        cart = new Cart();
    }

    public boolean update(){
        if(alive){
            cart.update(getForce(cart));
            fitness++;
            if(cart.polePos < Math.PI/2f || cart.polePos > 3 * Math.PI/2 || Math.abs(cart.cartPos) > 5)
                alive = false;
        }
        return alive;
    }

    public void display (PApplet p){
        if(alive)
            cart.display(p);
    }

    private float getForce(Cart c) {
        return sgn(k[0] * c.cartPos + k[1] * c.cartVel + k[2] * c.polePos + k[3] * c.poleVel);
    }

    private int sgn(float f){
        if(f <= 0){
            return -1;
        } else if(f > 0){
            return 1;
        } else { 
            return 0;
        }
    }

    public static BasicCart breed(BasicCart p1, BasicCart p2) {
        float[] k = new float[4];
        for (int i = 0; i < k.length; i++) {
            if(Math.random() < 0.5){
                k[i] = p1.k[i];
            } else {
                k[i] = p2.k[i];
            }
        }
        return new BasicCart(k);
    }

    public BasicCart mutate(float f) {
        for (int i = 0; i < k.length; i++) {
            if(Math.random() < f){
                k[i] *= (1 + ((0.1 * Math.random()) - 0.1));
            }
            if(Math.random() < f * 0.1f){
                k[i] = (float) (Math.random() * 2 - 1);
            }
        }
        return this;
    }
}
