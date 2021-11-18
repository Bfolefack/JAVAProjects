package com.example.World;

import com.example.Lenia;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

/**
 * Grid
 */
public class Grid {

    int color;
    public int width;
    public int height;
    PImage img;
    boolean smooth;
    float[][] grid;

    float birthMin;
    float birthMax;
    float survivalMin;
    float survivalMax;
    float kernelArea;

    float mu;
    float sigma;
    float gaussCoefficient;

    public float[][] kernel;

    public Grid(float[][] values) {
        grid = values;
        width = values.length;
        height = values[0].length;

        // gameOfLife Defaults
        // smooth = true;
        // kernel = getConwayKernel(1, 2);
        // survivalMin = 2/8f;
        // birthMin = 3/8f;
        // birthMax = 3/8f;
        // survivalMax = 3/8f;

        // smoothLife Defaults
        // smooth = true;
        // kernel = getConwayKernel(5, 10);
        // survivalMin = 0.25f;
        // birthMin = 0.3f;
        // birthMax = 0.4f;
        // survivalMax = 0.45f;

        // smoothLife Defaults
        // smooth = true;
        // kernel = getConwayKernel(7, 10);
        // survivalMin = 0.285f;
        // birthMin = 0.315f;
        // birthMax = 0.375f;
        // survivalMax = 0.463f;

        // Lenia Defaults
        smooth = false;
        mu = 0.34f;
        sigma = 0.031f;
        gaussCoefficient = 4.15f;
        kernel = getLeniaKernel(2, 15);

        // BEST LENIA
        // smooth = false;
        // mu = 0.36f;
        // sigma = 0.03f;
        // gaussCoefficient = 4;
        // kernel = getLeniaKernel(3, 15);

        img = new PImage();
    }

    public void display(PApplet sketch) {
        sketch.image(img, 0, 0);
    }

    public void display(PApplet sketch, float x, float y) {
        sketch.image(img, x, y);
    }

    public void fillArea(int x, int y) {
        if (smooth) {
            for (int i = -20; i <= 20; i++) {
                for (int j = -20; j <= 20; j++) {
                    PVector pv = getPixel(x + i, y + j);
                    grid[(int) pv.x][(int) pv.y] = (int) (Math.random() * 2);
                }
            }
        } else {
            int rad = 30;
            for (int i = -rad; i <= rad; i++) {
                for (int j = -rad; j <= rad; j++) {
                    PVector pv = getPixel(x + i, y + j);
                    grid[(int) pv.x][(int) pv.y] = (float)(Math.random());
                }
            }
        }
    }

    public void update(PApplet app) {
        float[][] newGrid = new float[width][height];
        int kl = kernel.length;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                float sum = 0;
                for (int k = 0; k < kl; k++) {
                    for (int l = 0; l < kl; l++) {
                        PVector pv = getPixel(i + k - kl / 2, j + l - kl / 2);
                        float val = kernel[k][l] * grid[(int) pv.x][(int) pv.y];
                        if (smooth) {
                            if (val > 0) {
                                val = 1;
                            } else {
                                val = 0;
                            }
                        }
                        sum += val;
                    }
                }
                float gr;
                if (smooth)
                    gr = conwayGrowthMap(sum, kernelArea);
                else
                    gr = leniaGrowthMap(sum, PConstants.PI * 7.5f * 7.5f) * 0.1f;
                float v = grid[i][j] + gr;
                if (smooth) {
                    if (v > 0) {
                        v = 1;
                    } else {
                        v = 0;
                    }
                    v = (int) v;
                } else {
                    if (v > 1) {
                        v = 1;
                    }
                    if (v < 0) {
                        v = 0;
                    }
                }

                newGrid[i][j] = v;
            }
        }
        grid = newGrid;
        app.delay(10);
        // for (int i = 0; i < kernel.length; i++) {
        // for (int j = 0; j < kernel[0].length; j++) {
        // PVector p = getPixel(i + Lenia.truMouseX, j + Lenia.truMouseY);
        // grid[(int) p.x][(int) p.y] = kernel[i][j];
        // }
        // }
        updateImage(app);
    }

    public void updateImage(PApplet app) {
        PImage newImg = new PImage(width, height);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                float f = grid[i][j];
                int color = 0;
                color = app.color(f * 255);
                newImg.pixels[j * width + i] = color;
            }
        }
        img = newImg;
    }

    public PVector getPixel(int x, int y) {
        if (x < 0) {
            x += width;
        }
        if (x > width - 1) {
            x -= width;
        }
        if (y < 0) {
            y += height;
        }
        if (y > height - 1) {
            y -= height;
        }
        return new PVector(x, y);
    }

    public float[][] getLeniaKernel(int innerRadius, int outerRadius) {
        float[][] f = new float[outerRadius * 2 + 1][outerRadius * 2 + 1];
        for (float r = innerRadius; r < outerRadius; r += 0.72) {
            float g = gaussian((r - innerRadius) / ((outerRadius - innerRadius)));
            for (int x = (int) -r; x <= r; x++) {
                int y = (int) Math.sqrt((r * r) - (x * x));

                f[x + outerRadius][y + outerRadius] = g;
                f[x + outerRadius][-y + outerRadius] = g;
                f[y + outerRadius][x + outerRadius] = g;
                f[-y + outerRadius][x + outerRadius] = g;
            }
        }
        for (int i = 0; i < f.length; i++) {
            for (int j = 0; j < f.length; j++) {
                kernelArea += f[i][j];
            }
        }
        return f;
    }

    public float[][] getConwayKernel(int innerRadius, int outerRadius) {
        float[][] f = new float[outerRadius * 2 + 1][outerRadius * 2 + 1];
        for (float r = innerRadius; r < outerRadius; r += 0.72) {
            for (int x = (int) -r; x <= r; x++) {
                int y = (int) Math.sqrt((r * r) - (x * x));

                f[x + outerRadius][y + outerRadius] = 1;
                f[x + outerRadius][-y + outerRadius] = 1;
                f[y + outerRadius][x + outerRadius] = 1;
                f[-y + outerRadius][x + outerRadius] = 1;
            }
        }
        for (int i = 0; i < f.length; i++) {
            for (int j = 0; j < f.length; j++) {
                kernelArea += f[i][j];
            }
        }
        return f;
    }

    public float leniaGrowthMap(float x, float divisor) {
        x /= divisor;
        // float mu = 0.14f;
        // float sigma = 0.03f;
        float l = Math.abs(x-mu);
        float k = 2*(sigma*sigma);
        return (2 * (float) Math.exp(-(l * l)/k) -  1);
    }

    public float conwayGrowthMap(float x, float divisor) {
        float f = x / divisor;
        if (f > 0) {
            int i = 1;
        }
        if (f <= birthMax && f >= birthMin) {
            return 1;
        }
        if (f <= survivalMax && f >= survivalMin) {
            return 0;
        }
        return -1;
    }

    public float gaussian(float x) {
        // float g = (float) ((1 / (gaussSigma * Math.sqrt(2 * PConstants.PI)))
        //         * (Math.exp(-((x - gaussMu) * (x - gaussMu)) / (2 * gaussSigma * gaussSigma))));
        float g = (float) Math.exp(1-(1/((gaussCoefficient *x) * (1 - x))));
        return g;
    }
}