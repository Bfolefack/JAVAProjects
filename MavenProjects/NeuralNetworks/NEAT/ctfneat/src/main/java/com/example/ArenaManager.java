package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.TreeSet;

import com.example.Game.Arena;
import com.example.NEAT.Network.Genes.GeneLibrary;
import com.example.NEAT.Population.Population;
import com.example.NEAT.Population.Actors.DefenderActor;
import com.example.NEAT.Population.Actors.HunterActor;

import processing.core.PApplet;
import processing.core.PConstants;

public class ArenaManager implements Serializable {
    Population<DefenderActor> defenders;
    Population<HunterActor> hunters;

    GeneLibrary huntLibrary;
    GeneLibrary defLibrary;

    transient TreeSet<Arena> arenas;
    transient int epochs = 0;

    public static transient int maxTicks = 450;
    public transient int ticks;
    int uniqueID = (int) (Math.random() * 1000000);

    public ArenaManager(int Population) {
        HashSet<DefenderActor> defenders = new HashSet<DefenderActor>();
        HashSet<HunterActor> hunters = new HashSet<HunterActor>();
        for (int i = 0; i < Population / 3; i++) {
            defenders.add(new DefenderActor(12, 3, true));
            defenders.add(new DefenderActor(12, 3, true));
            hunters.add(new HunterActor(14, 2, true));
        }
        this.defenders = new Population<DefenderActor>(defenders);
        this.hunters = new Population<HunterActor>(hunters);
        this.defenders.generation();
        this.hunters.generation();
        arenas = new TreeSet<Arena>();
        generation();
    }

    private void generation() {
        epochs++;
        if (epochs > 10) {
            epochs = 0;
            System.out.println("\n\nDefenders Generation: ");
            defenders.generation();
            System.out.println("\nHunters Generation: ");
            hunters.generation();
        } else {
            defenders.epoch();
            hunters.epoch();
        }
        ticks = 0;
        defenders.actors.remove(null);
        hunters.actors.remove(null);
        ArrayList<DefenderActor> defenderSet = new ArrayList<DefenderActor>(defenders.actors);
        ArrayList<HunterActor> hunterSet = new ArrayList<HunterActor>(hunters.actors);
        Collections.shuffle(defenderSet);
        Collections.shuffle(hunterSet);
        arenas = new TreeSet<Arena>();
        while (hunterSet.size() >= 1 && defenderSet.size() >= 2) {
            arenas.add(new Arena(defenderSet.remove(0), defenderSet.remove(0), hunterSet.remove(0)));
        }
    }

    public void update() {
        boolean allOver = true;
        for (Arena arena : arenas) {
            if (!arena.over) {
                allOver = false;
                arena.update();
            }
        }
        if (allOver) {
            generation();
        }
        ticks++;
        if (ticks > maxTicks) {
            for (Arena arena : arenas) {
                if (!arena.over) {
                    arena.over = true;
                    arena.tie();
                }
            }
        }
    }

    public void display(PApplet p) {
        p.fill(0);
        p.textSize(20);
        p.textAlign(PConstants.LEFT, PConstants.TOP);
        p.text("Gen: " + hunters.generation, 0, 0);
        p.text("Epoch: " + epochs, 0, 20);
        p.text("Ticks: " + ticks + "/" + maxTicks, 0, 40);
        Arena displayArena = null;
        Object[] arenaArray = arenas.toArray();
        int i = arenaArray.length - 1;
        while (displayArena == null && i > 0) {
            if (!((Arena) arenaArray[i]).over) {
                displayArena = (Arena) arenaArray[i];
            }
            i--;
        }
        if (displayArena != null)
            displayArena.draw(p);
    }

    public void saveArena() {
        huntLibrary = HunterActor.hunterLibrary;
        defLibrary = DefenderActor.defenderLibrary;
        String saveLoc = "G-" + hunters.generation + "--" + uniqueID/*new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Timestamp(System.currentTimeMillis()))*/;
        try {
            Path path = Paths.get("src\\data\\Arenas\\");
            Files.createDirectories(path);
            File f = new File("src\\data\\Arenas\\" + saveLoc + ".arena");
            f.createNewFile();
            FileOutputStream FOS = new FileOutputStream(f);
            ObjectOutputStream OOS = new ObjectOutputStream(FOS);
            OOS.writeObject(this);
            OOS.flush();
            OOS.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArenaManager loadArena(String saveLoc) {
        ArenaManager load = null;
        File f = null;
        try {
            f = new File("src\\data\\Arenas\\" + saveLoc);
            FileInputStream FIS = new FileInputStream(f);
            ObjectInputStream OIS = new ObjectInputStream(FIS);
            load = (ArenaManager) OIS.readObject();
            OIS.close();

        } catch (FileNotFoundException e) {
            if (load == null)
                System.out.println("FILE IN " + f.toString().substring(0, f.toString().length() - 7) + " NOT FOUND");
        } catch (StreamCorruptedException e) {
            System.out.println("FILE " + f + " IS NOT AN ARENA");
        } catch (ClassNotFoundException e) {
            System.out.println("FILE " + f + " IS NOT CORRECT NETWORK TYPE");
        } catch (IOException e) {
            System.out.println("UNKNOWN IO EXCEPTION:");
            System.out.println(e);
        } catch (Exception e) {
            System.out.println("UNKNOWN EXCEPTION:");
            System.out.println(e);
        } finally {
            System.out.println("LOADED ARENA FROM " + f.toString());
            System.out.println();
        }
        load.initialize();
        return load;
    }

    private void initialize() {
        HunterActor.hunterLibrary = this.huntLibrary;
        DefenderActor.defenderLibrary = this.defLibrary;
        System.out.println("\n\nDefenders Generation: ");
        defenders.generation();
        System.out.println("\nHunters Generation: ");
        hunters.generation();
        ticks = 0;
        defenders.actors.remove(null);
        hunters.actors.remove(null);
        ArrayList<DefenderActor> defenderSet = new ArrayList<DefenderActor>(defenders.actors);
        ArrayList<HunterActor> hunterSet = new ArrayList<HunterActor>(hunters.actors);
        Collections.shuffle(defenderSet);
        Collections.shuffle(hunterSet);
        arenas = new TreeSet<Arena>();
        while (hunterSet.size() >= 1 && defenderSet.size() >= 2) {
            arenas.add(new Arena(defenderSet.remove(0), defenderSet.remove(0), hunterSet.remove(0)));
        }
    }

}
