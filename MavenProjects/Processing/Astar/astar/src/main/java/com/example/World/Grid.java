package com.example.World;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Grid
 */
public class Grid {

    int color;
    int width;
    int height;
    float[][] grid;

    public Grid(float[][] values){
        grid = values;
        width = values.length;
        height = values[0].length;
    }

    public void display(PApplet sketch){
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                sketch.stroke(255 - grid[i][j] * 255, 255 - grid[i][j] * 255, 255);
                sketch.point(i, j);
            }
        }
    }

    public PImage getImage(PApplet app){
        PImage img = new PImage(width, height);
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                float f = grid[i][j];
                if(f > 1){
                    img.pixels[j * width + i] = app.color(35, 35, 0);
                } else {
                    int color = 0;
                    if(f < 1/10f){
                        img.pixels[j * width + i] = 0x33B002;
                    } else if(f < 2/10f){
                        img.pixels[j * width + i] = 0x33B002;
                    } else if(f < 3/10f){
                        img.pixels[j * width + i] = 0x339E01;
                    } else if(f < 4/10f){
                        img.pixels[j * width + i] = 0x328800;
                    } else if(f < 5/10f){
                        img.pixels[j * width + i] = 0x5327300;
                    } else if(f < 6/10f){
                        img.pixels[j * width + i] = 0x506A04;
                    } else if(f < 7/10f){
                        img.pixels[j * width + i] = 0x656307;
                    } else if(f < 8/10f){
                        img.pixels[j * width + i] = 0x785D0A;
                    } else if(f < 9/10f){
                        img.pixels[j * width + i] = 0x8E560D;
                    } else if(f < 10/10f){
                        img.pixels[j * width + i] = 0x824D08;
                    }
                }
            }
        }
        return img;
    }
}