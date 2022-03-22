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

    public float yRotation;
    public float xRotation;
    public float scale = 1;
    public float noiseRad = 0.1f;
    public float noiseScale = 1f;
    public float oceanPercent = 0.5f;
    PVector[][] globe;
    PVector[][][] cubeGlobe;
    public static final float a = 0.525731112119133606f;
    public static final float b = 0.850650808352039932f;
    PVector[] icosahedron = new PVector[] {
        new PVector(-a, 0, b),
        new PVector(a, 0, b),
        new PVector(-a, 0, -b),
        new PVector(a, 0, -b),
        new PVector(0, b, a),
        new PVector(0, b, -a),
        new PVector(0, -b, a),
        new PVector(0, -b, -a),
        new PVector(b, a, 0),
        new PVector(-b, a, 0),
        new PVector(b, -a, 0),
        new PVector(-b, -a, 0),
    };

    long seed = (long) (Math.random() * Long.MAX_VALUE);
    OpenSimplexNoise layer1 = new OpenSimplexNoise(seed);
    OpenSimplexNoise layer2 = new OpenSimplexNoise(seed / 2);
    OpenSimplexNoise layer3 = new OpenSimplexNoise(seed / 3);
    OpenSimplexNoise layer4 = new OpenSimplexNoise(seed / 4);
    OpenSimplexNoise layer5 = new OpenSimplexNoise(seed / 5);
    OpenSimplexNoise layer6 = new OpenSimplexNoise(seed / 6);
    OpenSimplexNoise layer7 = new OpenSimplexNoise(seed / 7);
    OpenSimplexNoise layer8 = new OpenSimplexNoise(seed / 8);

    public static void main(String[] args) {
        System.out.println(Math.pow(a, 2) + Math.pow(b, 2));
        String[] processingArgs = { "MySketch" };
        MainSphere mySketch = new MainSphere();
        PApplet.runSketch(processingArgs, mySketch);
    }

    @Override
    public void settings() {
        size(1000, 800, P3D);
        System.out.println(seed);
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
        rotateX(-xRotation);
        rotateY(yRotation);
        // strokeWeight(0.1f);
        stroke(0);
        fill(255);
        // radialSphere(200);
        cubeSphere(1000);
        // icosahedronSphere(500);
        if (mousePressed) {
            yRotation += (mouseX - pmouseX) * 0.01f * (360f / width);
            xRotation += (mouseY - pmouseY) * 0.01f * (360f / height);
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

        // OpenSimplexNoise n = new OpenSimplexNoise((int) (Math.random() *
        // Integer.MAX_VALUE));
        for (PVector[][] paa : cubeGlobe) {
            for (PVector[] pa : paa) {
                for (PVector p : pa) {
                    p.normalize();
                    float noise = getNoise(p.x, p.y, p.z);
                    // if(noise > 0.9){
                    // System.out.println(noise);
                    // }
                    p.add(new PVector(p.x, p.y, p.z).mult(noise * noiseRad));
                }
            }
        }
    }

    public PVector sphericalToRadial(float lat, float lon) {
        float r = 1;
        float x = (r) * sin(lat) * cos(-lon);
        float y = (r) * sin(lat) * sin(-lon);
        float z = (r) * cos(lat);
        return new PVector(x, y, z);
    }

    public float getNoise(float x, float y, float z) {
        float noise = 0;

        noise += layer1.eval(x * 2 * noiseScale, y * 2 * noiseScale, z * 2 * noiseScale) * 0.5;
        noise += layer2.eval(x * 4 * noiseScale, y * 4 * noiseScale, z * 4 * noiseScale) * 0.25;
        noise += layer3.eval(x * 8 * noiseScale, y * 8 * noiseScale, z * 8 * noiseScale) * 0.125;
        noise += layer4.eval(x * 16 * noiseScale, y * 16 * noiseScale, z * 16 * noiseScale) * 0.0625;
        noise += layer5.eval(x * 32 * noiseScale, y * 32 * noiseScale, z * 32 * noiseScale) * 0.03125;
        noise += layer6.eval(x * 64 * noiseScale, y * 64 * noiseScale, z * 64 * noiseScale) * 0.015625;
        noise += layer7.eval(x * 128 * noiseScale, y * 128 * noiseScale, z * 128 * noiseScale) * 0.0078125;
        noise = (noise + 1) / 2;
        noise = (float) Math.pow(noise, 1.25);

        return (noise < oceanPercent ? 0 : (noise - oceanPercent) * 1 / (1 - oceanPercent));
    }

    public void cubeSphere(float scale) {
        noStroke();
        // PVector target = sphericalToRadial(lat, lon)
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < cubeGlobe[0].length - 1; j++) {
                beginShape(QUAD_STRIP);
                for (int k = 0; k < cubeGlobe[0].length; k++) {
                    PVector p = cubeGlobe[i][j][k];
                    if (PVector.dist(p.copy().normalize(), sphericalToRadial(-yRotation, (float)-Math.PI * 2 *  xRotation)) < 0.1f) {
                        fill(255, 0, 0);
                    } else if (PVector.dist(p, new PVector(0, 0, 0)) <= 1.0 + noiseRad / 100)
                        fill(0, 0, 255);
                    else {
                        fill(0, 255 - 255 / noiseRad * (PVector.dist(p, new PVector(0, 0, 0)) - 1f), 0);
                        // System.out.println(PVector.dist(p, new PVector(0, 0, 0)));
                    }
                    if(j == 0 || k == 0 || j == cubeGlobe[0].length - 1 || k == cubeGlobe[0].length - 1)
                        fill(255, 255, 0);
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

    public void icosahedronSphere(float scale){
        strokeWeight(5);
        beginShape(TRIANGLES);
        for (int i = 0; i < icosahedron.length - 2; i++) {
            vertex(PVector.mult(icosahedron[i], scale));
            vertex(PVector.mult(icosahedron[i + 1], scale));
            vertex(PVector.mult(icosahedron[i + 2], scale));
            vertex(PVector.mult(icosahedron[i], scale));
        }
        endShape();
        // stroke(255);
        // strokeWeight(50);
        for (PVector vec : icosahedron) {
            point(vec.x * scale, vec.y * scale, vec.z * scale);
        }
    }

    // @Override
    public void vertex(PVector p) {
        vertex(p.x, p.y, p.z);
    }
}
