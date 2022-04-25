package com.example.Game;

import java.io.Serializable;

import processing.core.PVector;

public class LinearFormula implements Serializable{
    public float m;
    float b;
    PVector originPoint;

    public LinearFormula() {
    }

    public LinearFormula(float x1, float y1, float x2, float y2) {
        m = (y2 - y1) / (x2 - x1);
        b = y1 - m * x1;
        originPoint = new PVector(x1, y1);
    }

    public LinearFormula(float m, float b) {
        this.m = m;
        this.b = b;
        originPoint = new PVector(0, b);
    }

    public LinearFormula(float m, float x, float y) {
        this.m = m;
        this.b = y - m * x;
        originPoint = new PVector(x, y);
    }

    public float getY(float x) {
        return m * x + b;
    }

    public float getX(float y) {
        return (y - b) / m;
    }

    public LinearFormula getNormal(float x) {
        if(Float.isNaN(m) || Float.isNaN(b)){
            return new LinearFormula();
        }
        return new LinearFormula(-1 / m, x, getY(x));
    }

    public static LinearFormula getNormal(float x1, float y1, float x2, float y2) {
        return new LinearFormula(x1, y1, x2, y2).getNormal(x1);
    }

    public PVector intersect(LinearFormula ln){
        float x = (ln.b - b) / (m - ln.m);
        float y = m * x + b;
        if(Float.isNaN(x) || Float.isNaN(y)){
            return new PVector(0, 0);
        }
        return new PVector(x, y);
    }
}
