package com.example.Game;

import java.util.HashSet;

import com.example.ArenaManager;
import com.example.Game.Entities.Bullet;
import com.example.Game.Entities.Players.Defender;
import com.example.Game.Entities.Players.Hunter;
import com.example.NEAT.Population.Actors.DefenderActor;
import com.example.NEAT.Population.Actors.HunterActor;
import com.example.NEAT.Utils.NetworkDisplay;

import processing.core.PApplet;
import processing.core.PVector;

public class Arena implements Comparable<Arena> {
    public float x, y, width, height;
    DefenderActor defender1, defender2;
    HunterActor hunter;
    int ticks;

    NetworkDisplay defender1Display, defender2Display, hunterDisplay;

    HashSet<Bullet> bullets;
    public PVector target;
    public boolean over;
    public Double fitness;

    public Arena(DefenderActor d1, DefenderActor d2, HunterActor h) {
        width = 800;
        height = 800;
        x = 250;
        y = 0;
        defender1 = d1;
        defender2 = d2;
        hunter = h;
        d1.arena = this;
        d2.arena = this;
        h.arena = this;
        d1.defender = new Defender(width - 100, 100);
        d2.defender = new Defender(width - 100, height - 100);
        hunter.hunter = new Hunter(100, height / 2);
        d1.defender.facing = -(float) Math.PI;
        d2.defender.facing = -(float) Math.PI;
        target = new PVector(width - 25, height / 2);
        bullets = new HashSet<Bullet>();
        fitness = d1.ancestorFitness + d2.ancestorFitness + h.ancestorFitness;
    }

    public void update() {
        if (!over) {
            ticks++;
            defender1.act();
            defender2.act();
            hunter.act();
            for (Bullet b : bullets) {
                b.move();
            }
            cullOutOfBoundsBullets();
            if (defenderLoses()) {
                over = true;
                defender1.lose(1);
                defender2.lose(1);
                hunter.win(2 - ((double) ticks / ArenaManager.maxTicks));
            } else if (hunterLoses()) {
                over = true;
                defender1.win(2 - ((double) ticks / ArenaManager.maxTicks));
                defender2.win(2 - ((double) ticks / ArenaManager.maxTicks));
                hunter.lose(1);
            } else if (tie()) {
                over = true;
                defender1.lose(0.5);
                defender2.lose(0.5);
                hunter.lose(0.5);
            }
        }
    }

    private void cullOutOfBoundsBullets() {
        HashSet<Bullet> toRemove = new HashSet<Bullet>();
        for (Bullet b : bullets) {
            if (b.pos.x < 0 || b.pos.x > width || b.pos.y < 0 || b.pos.y > height) {
                toRemove.add(b);
            }
        }
        bullets.removeAll(toRemove);
    }

    public boolean tie() {
        return false;
    }

    private boolean hunterLoses() {
        if (hunter.hunter.pos.x > width || hunter.hunter.pos.x < 0 || hunter.hunter.pos.y > height
                || hunter.hunter.pos.y < 0) {
            hunter.lose(1);
            hunter.rounds += 5;
            return true;
        }
        for (Bullet b : bullets) {
            if (PVector.dist(b.pos, hunter.hunter.pos) < hunter.hunter.size) {
                b.shooter.kill();
                return true;
            }
        }
        if (PVector.dist(defender1.defender.pos, hunter.hunter.pos) < defender1.defender.size) {
            defender1.kill();
            return true;
        }
        if (PVector.dist(defender2.defender.pos, hunter.hunter.pos) < defender2.defender.size) {
            defender2.kill();
            return true;
        }
        return false;
    }

    private boolean defenderLoses() {
        if (defender1.defender.pos.x > width || defender1.defender.pos.x < 0 || defender1.defender.pos.y > height
                || defender1.defender.pos.y < 0) {
            defender1.lose(1);
            defender1.rounds += 5;
            return true;
        }
        if (defender2.defender.pos.x > width || defender2.defender.pos.x < 0 || defender2.defender.pos.y > height
                || defender2.defender.pos.y < 0) {
            defender2.lose(1);
            defender2.rounds += 5;
            return true;
        }
        if (PVector.dist(hunter.hunter.pos, target) < hunter.hunter.size) {
            return true;
        }
        return false;
    }

    public void draw(PApplet p) {
        p.noStroke();
        if (defender1Display == null)
            initializeDisplays();

        defender1Display.updateGenome(defender1.brain);
        defender2Display.updateGenome(defender2.brain);
        hunterDisplay.updateGenome(hunter.brain);

        // createBullet(new PVector(width/2, height/2), -1);
        p.pushMatrix();
        p.translate(x, y);
        for (Bullet b : bullets) {
            b.draw(p);
        }
        defender1.draw(p);
        defender2.draw(p);
        hunter.draw(p);
        p.fill(255, 255, 0);
        p.ellipse(target.x, target.y, 30, 30);
        defender1Display.display(p, width + 10, 150);
        defender2Display.display(p, width + 10, 450);
        hunterDisplay.display(p, -210, 300);
        p.noFill();
        p.stroke(0);
        p.strokeWeight(2);
        p.rect(0, 0, width, height);
        p.popMatrix();
        // System.out.println(defender1.defender.pos.x + " " +
        // defender1.defender.pos.y);
    }

    private void initializeDisplays() {
        defender1Display = new NetworkDisplay(200, 200);
        defender2Display = new NetworkDisplay(200, 200);
        hunterDisplay = new NetworkDisplay(200, 200);
    }

    public DefenderActor getDefender1() {
        return defender1;
    }

    public DefenderActor getDefender2() {
        return defender2;
    }

    public HunterActor getHunter() {
        return hunter;
    }

    public HashSet<Bullet> getBullets() {
        return bullets;
    }

    public void createBullet(PVector pos, float facing, DefenderActor shooter) {
        Bullet b = new Bullet(pos, facing, shooter);
        bullets.add(b);
    }

    public Bullet getNearestBullet(PVector pos) {
        float smallestDist = Float.MAX_VALUE;
        Bullet nearestBullet = null;
        for (Bullet b : bullets) {
            float dist = PVector.dist(pos, b.pos);
            if (dist < smallestDist) {
                smallestDist = dist;
                nearestBullet = b;
            }
        }
        if (nearestBullet == null) {
            return new Bullet(new PVector(0, 0), 0, null);
        }
        return nearestBullet;
    }

    @Override
    public int compareTo(Arena o) {
        int out = fitness.compareTo(o.fitness);
        return out == 0 ? 1 : out;
    }
}
