package com.example;

import javax.xml.soap.Detail;

import processing.core.PApplet;
import processing.core.PVector;
import processing.event.MouseEvent;

/**
 * Hello world!
 *
 */
public class MainSphere extends PApplet {

    public float xRotation;
    public float yRotation;
    public float scale = 1;
    PVector[][] globe;
    PVector[][][] cubeGlobe;

    public static void main(String[] args) {
        String[] processingArgs = { "MySketch" };
        MainSphere mySketch = new MainSphere();
        PApplet.runSketch(processingArgs, mySketch);
    }

    @Override
    public void settings() {
        size(1000, 800, P3D);
    }

    @Override
    public void setup() {
        // genRadialGlobe(200, 200);
        genCubeSphere(200);
    }

    @Override
    public void draw() {
        background(0);
        translate(width / 2, height / 2, scale);
        // scale(scale);
        rotateX(-yRotation);
        rotateY(xRotation);
        // strokeWeight(0.1f);
        stroke(255);
        fill(0);
        // radialSphere(200);
        cubeSphere(1000);
        if (mousePressed) {
            xRotation += (mouseX - pmouseX) * 0.01f * (360f / width);
            yRotation += (mouseY - pmouseY) * 0.01f * (360f / height);
        }
    }

    public void stockSphere() {
        sphere(200);
    }

    public void genRadialGlobe(int detail, float r) {
        globe = new PVector[detail + 1][detail + 1];

        for (int i = 0; i <= detail; i++) {
            float lon = map(i, 0, detail, 0, 2 * PI);
            for (int j = 0; j <= detail; j++) {
                // float noise = getNoise(i, j, detail) * 100;
                float lat = map(j, 0, detail, 0, PI);
                float noiseDetail = 2f;
                OpenSimplexNoise n = new OpenSimplexNoise();
                float noise = 0;
                float rad = 50;

                // noise += getNoise(i, j, lat, lon, n, rad, noiseDetail);
                // noiseDetail *= 2;
                // rad /= 2;
                // noise += getNoise(i, j, lat, lon, n, rad, noiseDetail);
                // noiseDetail *= 2;
                // rad /= 2;
                // noise += getNoise(i, j, lat, lon, n, rad, noiseDetail);
                // noiseDetail *= 2;
                // rad /= 2;
                // noise += getNoise(i, j, lat, lon, n, rad, noiseDetail);
                // noiseDetail *= 2;
                // rad /= 2;
                // noise += getNoise(i, j, lat, lon, n, rad, noiseDetail);
                float x = (r + noise) * sin(lat) * cos(-lon);
                float y = (r + noise) * sin(lat) * sin(-lon);
                float z = (r + noise) * cos(lat);
                globe[i][j] = new PVector(x, y, z);
            }
        }
    }

    public void radialSphere(int detail) {
        // stroke(0);
        // scale(rad);

        for (int i = 0; i < detail; i++) {
            beginShape(TRIANGLE_STRIP);
            float lon = map(i, 0, detail, -PI, PI);
            for (int j = 1; j < detail; j++) {
                // if (i <= frameCount % 10)
                // continue;
                PVector p1 = globe[i][j];
                // fill((p1.z - cos(j)) * (255/20), 255 - (p1.z - cos(j)) * (255/20), 0);
                PVector p2 = globe[i + 1][j];
                vertex(p1.x, p1.y, p1.z);
                vertex(p2.x, p2.y, p2.z);
            }
            endShape();
        }

        PVector sum = new PVector();
        for (int i = 0; i < detail + 1; i++) {
            sum.add(globe[i][1]);
        }
        sum.div(detail);

        beginShape(TRIANGLE_FAN);
        vertex(sum.x, sum.y, sum.z);
        for (int i = 0; i < detail + 1; i++) {
            PVector p = globe[i][1];
            vertex(p.x, p.y, p.z);
        }
        endShape();

        sum = new PVector();
        for (int i = 0; i < detail + 1; i++) {
            sum.add(globe[i][detail - 1]);
        }
        sum.div(detail);

        beginShape(TRIANGLE_FAN);
        vertex(sum.x, sum.y, sum.z);
        for (int i = 0; i < detail + 1; i++) {
            PVector p = globe[i][detail - 1];
            vertex(p.x, p.y, p.z);
        }
        endShape();
    }

    public void genCubeSphere(int detail) {
        cubeGlobe = new PVector[6][detail + 1][detail + 1];
        for (int i = 0; i <= detail; i++) {
            for (int j = 0; j <= detail; j++) {
                float x = (i / (float) detail) - 0.5f;
                float y = (j / (float) detail) - 0.5f;
                cubeGlobe[0][i][j] = new PVector(x, y, 0.5f);
                cubeGlobe[1][i][j] = new PVector(0.5f, x, y);
                cubeGlobe[2][i][j] = new PVector(x, y, -0.5f);
                cubeGlobe[3][i][j] = new PVector(-0.5f, x, y);
                cubeGlobe[4][i][j] = new PVector(x, 0.5f, y);
                cubeGlobe[5][i][j] = new PVector(x, -0.5f, y);
            }
        }

        OpenSimplexNoise n = new OpenSimplexNoise((int) (Math.random() * Integer.MAX_VALUE));
        for (PVector[][] paa : cubeGlobe) {
            for (PVector[] pa : paa) {
                for (PVector p : pa) {
                    float noise = 0;
                    float rad = 0f;
                    float nD = 2f;
                    noise += Math.pow((float) ((n.eval(nD * p.x, nD * p.y, nD * p.z) + 1) / 2), 1) * rad;
                    nD *= 2;
                    rad /= 4;
                    noise += Math.pow((float) ((n.eval(nD * p.x, nD * p.y, nD * p.z) + 1) / 2), 1) * rad;
                    nD *= 2;
                    rad /= 2;
                    noise += Math.pow((float) ((n.eval(nD * p.x, nD * p.y, nD * p.z) + 1) / 2), 1) * rad;
                    nD *= 2;
                    rad /= 2;
                    noise += Math.pow((float) ((n.eval(nD * p.x, nD * p.y, nD * p.z) + 1) / 2), 1) * rad;
                    nD *= 2;
                    rad /= 2;
                    noise += Math.pow((float) ((n.eval(nD * p.x, nD * p.y, nD * p.z) + 1) / 2), 1) * rad;
                    p.normalize().add(new PVector(p.x, p.y, p.z).mult(noise));
                }
            }
        }
    }

    public void cubeSphere(float scale) {
        // noStroke();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < cubeGlobe[0].length - 1; j++) {
                beginShape(QUAD_STRIP);
                for (int k = 0; k < cubeGlobe[0].length; k++) {
                    PVector p = cubeGlobe[i][j][k];
                    // fill(600 * (PVector.dist(p, new PVector(0, 0, 0)) - 1), 0, 255);
                    // if (j == 0 || k == 0 || j == cubeGlobe[0].length - 2 || k == cubeGlobe[0].length - 1)
                    //     fill(0, 255, 0);
                    PVector p2 = cubeGlobe[i][j + 1][k];
                    if (p != null)
                        vertex(p.x * scale, p.y * scale, p.z * scale);
                    if (p2 != null)
                        vertex(p2.x * scale, p2.y * scale, p2.z * scale);
                }
                endShape();
            }
        }
    }

    public float getNoise(float i, float j, float lat, float lon, OpenSimplexNoise n, float rad, float noiseDetail) {
        // return (float) Math.pow(
        // (n.eval(lat * noiseDetail, sin(lon) * noiseDetail, cos(lon) * noiseDetail,
        // sin(lat) * noiseDetail) + 1)
        // / 2,
        // 2) * rad;
        return (float) (float) Math.pow((n.eval(noiseDetail * sin(lat) * cos(lon),
                noiseDetail * sin(lat) * sin(lon), noiseDetail * cos(lat)) + 1) / 2, 4) * rad;
    }

    float getNoise(int index2, int index, float detail) {
        float lat = abs((detail / 2) - index) * 2;
        float angle = ((TWO_PI) / detail) * index;
        float angle2 = (TWO_PI) / (detail) * index2;
        float cosAngle2 = cos(angle2);
        float sinAngle2 = sin(angle2);
        float nD = 0.5f;
        return ((float) noise(angle * nD, (cosAngle2) * nD, (sinAngle2) * nD));
    }

    @Override
    public void mouseWheel(MouseEvent e) {
        scale += e.getCount() * 20f;
    }
}
