package com.example.Objects.Mobs;

import java.util.HashSet;
import java.util.Set;

import com.example.Objects.Entity;
import com.example.Objects.Stats.World;

import processing.core.PApplet;

public class Colony extends Entity{

    public Set<Ant> ants;
    public int color;
    private World world;
    public float food;

    public Colony(float x, float y, int col, World w) {
        super(x, y);
        world = w;
        color = col;
        food = 0;
        ants = new HashSet<>();
    }

    public void display(PApplet app){
        int color = toColor(this.color, app);
        app.fill(app.red(color) * 0.75f, app.green(color) * 0.75f, app.blue(color) * 0.75f);
        app.triangle(pos.x - 30, pos.y + 30, pos.x + 30, pos.y + 30, pos.x, pos.y - 30);
        app.fill(color);
        for(Ant a : ants){
            a.display(app);
        }
    }

    public void update(PApplet app){
        for(Ant a  : ants){
            a.update(app);
        }
        if(food > 50){
            addAnt(pos.x, pos.y);
            food = 0;
        }
    }

    public void addAnt(float x, float y) {
        ants.add(new Ant(x, y, 3, 2f, 0.95f, this, world));
    }

    

    private int toColor(int col, PApplet sketch) {
        return sketch.color((col / 1000000) * 0.255f, ((col % 1000000) / 1000) * 0.255f, (col % (1000)) * 0.255f);
    }
    
}
