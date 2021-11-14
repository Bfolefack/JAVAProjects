package com.example;

import com.example.Utils.GridQuadTree;
import com.example.Utils.NavTree;
import com.example.Utils.Path;
import com.example.Utils.Zoomer;
import com.example.World.Grid;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.opengl.PGraphicsOpenGL;
import processing.event.MouseEvent;

/**
 * Hello world!
 *
 */
public class AStar extends PApplet {

    public static int truMouseY;
    public static int truMouseX;

    public static final int gridWidth = 2048;
    public static final int gridHeight = 2048;

    public Grid grid;

    public boolean showTree;

    public NavTree nav;
    public PVector start;
    public PVector end;

    Path p = null;

    GridQuadTree qt;
    PImage img;

    public static void main(String[] args) {
        String[] processingArgs = { "MySketch" };
        AStar mySketch = new AStar();
        try {
            PApplet.runSketch(processingArgs, mySketch);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void settings() {
        size(1000, 800, P2D);
        Zoomer.initialize(1, this);
    }

    public void setup() {
        frameRate(144);
        ((PGraphicsOpenGL) g).textureSampling(3);
        long seed = (long) (Math.random() * Long.MAX_VALUE);
        System.out.println(seed);
        noiseDetail(8, 0.4f);
        noiseSeed(seed);
        float[][] moist = new float[gridWidth][gridHeight];
        for (int i = 0; i < moist.length; i++) {
            for (int j = 0; j < moist[0].length; j++) {
                moist[i][j] = (float) Math.pow(noise(i * 0.005f, j * 0.005f), 1.5);
            }
        }
        noiseSeed(seed / 2);
        for (int i = 0; i < moist.length; i++) {
            for (int j = 0; j < moist[0].length; j++) {
                if (noise(i * 0.0075f, j * 0.0075f) > 0.55)
                    moist[i][j] = Integer.MAX_VALUE;
            }
        }
        grid = new Grid(moist);
        img = grid.getImage(this);

        qt = new GridQuadTree(moist);
        nav = new NavTree(qt);
        start = new PVector();
        end = new PVector();
    }

    public void draw() {
        background(0);
        Zoomer.mousePan();
        fill(0);
        Zoomer.pushZoom();
        image(img, 0, 0, gridWidth, gridHeight);
        // nav.display(this);
        if(p != null){
            p.display(this);
        }
        noFill();
        if (showTree)
            qt.display(this);
        Zoomer.popZoom();
        text(frameRate, 0, 20);
        text(GridQuadTree.count, 0, 40);
    }

    public void mouseWheel(MouseEvent event) {
        Zoomer.mouseScale(event, 0.05f);
    }

    public void keyPressed() {
        switch (key) {
        case 't':
            showTree = !showTree;
            break;
        case 's':
            start = new PVector(truMouseX, truMouseY);
            break;
        case 'e':
            end = new PVector(truMouseX, truMouseY);
            p = nav.getPath(start, end);
            break;
        default:
            break;
        }
    }
}
