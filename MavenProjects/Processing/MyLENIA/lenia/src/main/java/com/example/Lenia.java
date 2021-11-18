package com.example;

import com.example.Utils.Zoomer;
import com.example.World.Grid;

import processing.core.PApplet;
import processing.event.MouseEvent;
import processing.opengl.PGraphicsOpenGL;

/**
 * Hello world!
 *
 */
public class Lenia extends PApplet 
{
    public static int truMouseX;
    public static int truMouseY;
    Grid grid;
    Grid kernel;

    public static void main(String[] args) {
        String[] processingArgs = { "MySketch" };
        Lenia mySketch = new Lenia();
        try {
            PApplet.runSketch(processingArgs, mySketch);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    @Override
    public void settings(){
        size(1000, 800, P2D);
    }

    @Override
    public void setup(){
        ((PGraphicsOpenGL)g).textureSampling(3);
        Zoomer.initialize(1, this);
        float[][] f = new float[250][250];
        for(int i = 0; i < f.length ; i++){
            for (int j = 0; j < f[0].length; j++) {
                f[i][j] = (float) Math.random() * 0.75f;
            }
        }
        grid = new Grid(f);
        grid.updateImage(this);
        kernel = new Grid(grid.kernel);
        kernel.updateImage(this);
    }

    @Override
    public void draw(){
        Zoomer.mousePan();
        background(255, 255, 0);
        Zoomer.pushZoom();
        grid.display(this);
        kernel.display(this, grid.width + 10, 0);
        if(keyPressed){
            grid.fillArea(truMouseX, truMouseY);
        }
        Zoomer.popZoom();
        fill(255);
        text(frameRate, 0, 10);
        grid.update(this);
    }


    @Override
    public void mouseWheel(MouseEvent event){
        Zoomer.mouseScale(event, 0.05f);
    }
}
