package com.example.Utils;

import java.util.ArrayList;

import com.example.System.Planet;

import processing.core.PVector;

public class SystemLibrary {
    public static ArrayList<Planet> earthMoon = new ArrayList<>();
    public static ArrayList<Planet> solar = new ArrayList<>();

    public static void loadSystems(){
        //GC: 0.0000000667f
        earthMoon.add(new Planet(0, 0, 0, 0, kiloToYotta(5.9722*Math.pow(10, 24)))); //Earth
        earthMoon.add(new Planet(384400/1000, 0, 0, 1.022f/1000, kiloToYotta(7.34767309*Math.pow(10, 22)))); //Moon

        solar.add(new Planet(0, 0, 0, 0, kiloToYottaPlus6(1.9855f * Math.pow(10, 30)))); //Sun
        solar.add(new Planet(57909175/100000, 0, 0, 47.8725f/1000, kiloToYottaPlus6(3.302 * Math.pow(10, 23)))); //Mercury
        solar.add(new Planet(108208930/100000, 0, 0, 35.0214f/1000, kiloToYottaPlus6(4.8690 * Math.pow(10, 24)))); //Venus
        solar.add(new Planet(149597890/100000, 0, 0, 29.7859f/1000, kiloToYottaPlus6(5.972 * Math.pow(10, 24)))); //Earth
        solar.add(new Planet(149597890/100000, 384400/100000, 1.022f/1000, 29.7859f/1000, kiloToYottaPlus6(7.34767309*Math.pow(10, 22)))); //Moon
        solar.add(new Planet(227936640/100000, 0, 0, 24.1309f/1000, kiloToYottaPlus6(6.4191 * Math.pow(10, 23)))); //Mars
        solar.add(new Planet(778412010/100000, 0, 0, 13.0697f/1000, kiloToYottaPlus6(1.8987 * Math.pow(10, 27)))); //Jupiter
        solar.add(new Planet(1426725400/100000, 0, 0, 9.6724f/1000, kiloToYottaPlus6(5.6851 * Math.pow(10, 26)))); //Saturn
        solar.add(new Planet((int)(2870972200l/100000), 0, 0, 6.8352f/1000, kiloToYottaPlus6(8.6849 * Math.pow(10, 25)))); //Uranus
        solar.add(new Planet((int)(4498252900l/100000), 0, 0, 5.4778f/1000, kiloToYottaPlus6(1.0244 * Math.pow(10, 26)))); //Neptune
    }

    public static float kiloToYotta(double kilo) {
        kilo /= (Math.pow(10, 21));
        return (float)kilo;
    }

    public static float kiloToYottaPlus6(double kilo) {
        kilo /= (Math.pow(10, 27));
        return (float)kilo;
    }

    public static ArrayList<Planet> randomSys(float range){
        ArrayList<Planet> out = new ArrayList<>();
        Planet star = new Planet(0, 0, 0, 0, kiloToYottaPlus6(1.9855f * Math.pow(10, 30)));
        out.add(star);
        for(int i = 0; i < 1000; i++){
            double dist = Math.random() *  1000000000 * range;
            double angle = Math.random() * Math.PI * 2;
            double mass = Math.pow(10, 16 + Math.pow(Math.random(), 2) * 4);
            PVector pos = new PVector((float)(dist * Math.cos(angle)), (float)(dist * Math.sin(angle)));
            out.add(new Planet(pos.x/100000, pos.y/100000, 0, 0, kiloToYotta(mass)));
        }
        return out;
    }

}
