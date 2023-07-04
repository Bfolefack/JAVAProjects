package com.example.Utils;

import java.util.ArrayList;

import com.example.NBody;
import com.example.System.Planet;

import processing.core.PVector;

public class SystemLibrary {
    public static ArrayList<Planet> earthMoon = new ArrayList<>();
    public static ArrayList<Planet> solar = new ArrayList<>();

    public static void loadSystems(){
        //GC: 0.0000000667f
        earthMoon.add(new Planet(0, 0, 0, 0, kiloToYotta(5.9722e24))); //Earth
        earthMoon.add(new Planet(384400/1000, 0, 0, 1.022f/1000, kiloToYotta(7.34767309e22))); //Moon

        solar.add(new Planet(0, 0, 0, 0, kiloToYottaPlus6(1.9855e30))); //Sun
        solar.add(new Planet(57909175/100000, 0, 0, 47.8725f/1000, kiloToYottaPlus6(3.302e23))); //Mercury
        solar.add(new Planet(108208930/100000, 0, 0, 35.0214f/1000, kiloToYottaPlus6(4.8690e24))); //Venus
        solar.add(new Planet(149597890/100000, 0, 0, 29.7859f/1000, kiloToYottaPlus6(5.972e24))); //Earth
        solar.add(new Planet(149597890/100000, 384400/100000, 1.022f/1000, 29.7859f/1000, kiloToYottaPlus6(7.34767309e22))); //Moon
        solar.add(new Planet(227936640/100000, 0, 0, 24.1309f/1000, kiloToYottaPlus6(6.4191e23))); //Mars
        solar.add(new Planet(778412010/100000, 0, 0, 13.0697f/1000, kiloToYottaPlus6(1.8987e27))); //Jupiter
        solar.add(new Planet(1426725400/100000, 0, 0, 9.6724f/1000, kiloToYottaPlus6(5.6851e26))); //Saturn
        solar.add(new Planet((int)(2870972200l/100000), 0, 0, 6.8352f/1000, kiloToYottaPlus6(8.6849e25))); //Uranus
        solar.add(new Planet((int)(4498252900l/100000), 0, 0, 5.4778f/1000, kiloToYottaPlus6(1.0244e26))); //Neptune
    }

    public static float kiloToYotta(double kilo) {
        kilo /= 1e21;
        return (float)kilo;
    }

    public static float kiloToYottaPlus6(double kilo) {
        kilo /= 1e27;
        return (float)kilo;
    }

    public static ArrayList<Planet> randomSys(float range){
        ArrayList<Planet> out = new ArrayList<>();
        Planet star = new Planet(0, 0, 0, 0, kiloToYottaPlus6(1 + (Math.random() * 0.1) *  1e30));
        out.add(star);
        for(int i = 0; i < 100; i++){
            double dist = Math.random() *  1000000000 * range;
            double angle = Math.random() * Math.PI * 2;
            double mass = Math.pow(10, 16 + Math.pow(Math.random(), 2) * 4);
            PVector pos = new PVector((float)(dist * Math.cos(angle)), (float)(dist * Math.sin(angle)));
            double velMag = Math.sqrt((star.mass + kiloToYotta(mass))* NBody.G / (dist)) * 500 * Math.random();

            System.out.println(velMag);
            PVector vel = new PVector((float)(velMag * Math.sin(angle)), (float)(velMag * Math.cos(angle)));
            Planet plan = new Planet(pos.x/100000, pos.y/100000, 0, 0, kiloToYotta(mass));
            plan.vel = vel;
            out.add(plan);
        }
        return out;
    }

}
