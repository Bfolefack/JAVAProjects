package com.example.Game;

import java.util.HashMap;
import java.util.HashSet;

import com.example.Game.Entities.Bullet;
import com.example.Game.Entities.Player;
import com.example.Game.Entities.EntityLib.Entity;
import com.example.Game.Utils.QuadTree;

import processing.core.PApplet;
import processing.core.PVector;

public class War {
    public Player p1;
    public Player p2;
    public HashSet<Bullet> bullets;
    public HashMap<Integer, HashMap<Integer, HashSet<Entity>>> spatialHash;
    public int spatialHashSize = 0;
    public float bounds;
    public float blackHoleStrength = 0.0001f;
    // public float blackHoleStrength = 1f;
    public QuadTree<Entity> tree;
    public float blackHoleSize = 20;

    public War(){
        bounds = 400;
        tree = new QuadTree<>(4, -bounds, -bounds, bounds * 2, bounds * 2);
        bullets = new HashSet<>();
        p1 = new Player(bounds * 0.75f, 0, 7.5f, .005f, 1, 40, -(float)Math.PI, 30, this);
        p2 = new Player(bounds * -0.75f, 0, 7.5f, .005f, 2, 40, 0, 30, this);
    }

    public War(Player p1_, Player p2_){
        bounds = 400;
        tree = new QuadTree<>(4, -bounds, -bounds, bounds * 2, bounds * 2);
        bullets = new HashSet<>();
        p1 = p1_;
        p2 = p2_;
    }

    public void update() {
        tree = new QuadTree<>(4, -bounds, -bounds, bounds * 2, bounds * 2);
        tree.insert(p1);
        tree.insert(p2);
        tree.insertAll(bullets);
        p1.update();
        p2.update();
        HashSet<Bullet> temp = new HashSet<>();
        for (Bullet b : bullets) {
            if (b.age > 300) {
                temp.add(b);
            }
        }
        bullets.removeAll(temp);
        for (Bullet b : bullets) {
            b.update();
        }
    }

    public void display(PApplet sketch) {
        sketch.pushMatrix();
        sketch.translate(sketch.width / 2, sketch.height / 2);
        sketch.noFill();
        sketch.stroke(255);
        sketch.ellipse(0, 0, bounds * 2, bounds * 2);
        sketch.stroke(200, 255, 200);
        p1.display(sketch);     
        sketch.stroke(255, 200, 200);                
        p2.display(sketch);
        for (Bullet b : bullets) {
            b.display(sketch);
        }
        sketch.fill(255, 255, 0);
        sketch.noStroke();
        sketch.ellipse(0, 0, 40, 40);
        // tree.displayTree(sketch);
        sketch.popMatrix();
    }

    public PVector gravitation(PVector pos){
        return (PVector.mult(pos, -1).setMag(blackHoleStrength/(PApplet.dist(pos.x, pos.y, 0, 0) > 1 ? (float)Math.pow(PApplet.dist(pos.x, pos.y, 0, 0)/bounds, 2) : 1)));
    }

    public Bullet getNearestBullet(PVector pos) {
        HashSet<Entity> entities = new HashSet<>(tree.getEntities(pos, p1.size * 4));
        Bullet bullet = new Bullet(0, 0, 0, 0, 0, this);
        float smallestDist = bounds * 4;
        for (Entity entity : entities) {
            if(entity instanceof Bullet){
                if(PVector.dist(pos, entity.pos) < smallestDist){
                    bullet = (Bullet) entity;
                    smallestDist = PVector.dist(pos, entity.pos);
                }
            }
        }
        return bullet;
    }
}
