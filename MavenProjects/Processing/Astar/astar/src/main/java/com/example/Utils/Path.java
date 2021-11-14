package com.example.Utils;

import java.util.ArrayList;
import java.util.Stack;

import processing.core.PApplet;
import processing.core.PVector;

public class Path {

    ArrayList<PVector> path = new ArrayList<>();

    public Path(Stack<PVector> pStack) {
        while(!pStack.empty()){
            path.add(pStack.pop());
        }
    }

    public void display(PApplet sketch){
        sketch.strokeWeight(10);
        sketch.stroke(0, 255, 0);
        for(int i = 0; i < path.size() - 1; i++){
            sketch.line(path.get(i).x, path.get(i).y, path.get(i + 1).x, path.get(i + 1).y);
        }
    }
    
}
