package com.example.NEAT.Population;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.nio.channels.CancelledKeyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

import com.example.NEAT.Network.Genes.Genome;
import com.example.NEAT.Utils.Config;

public class Population<E extends Actor> implements Iterable<E> {
    public TreeSet<Species<E>> species;
    public HashSet<E> actors;
    double bestFitness;
    double highScoreFitness;
    int populationSize;
    public int generation;
    public E best;
    public int epoch;
    boolean save = Config.save;

    public Population(Collection<E> founders) {
        populationSize = founders.size();
        species = new TreeSet<>();
        actors = new HashSet<>();
        actors.addAll(founders);
        sortSpecies(actors);
    }

    public Population(String saveLoc) {
        Collection<E> founders = loadPopulation(saveLoc);
        populationSize = founders.size();
        species = new TreeSet<>();
        actors = new HashSet<>();
        actors.addAll(founders);
        //sortSpecies(actors);
    }

    public void act() {
        actors.remove(null);
        for (E e : actors) {
            e.act();
        }
    }

    public E alive() {
        actors.remove(null);
        for (E e : actors) {
            if (e.alive) {
                return e;
            }
        }
        return null;
    }

    public void generation() {
        generation++;
        calculateFitness();
        for (E e : actors) {
            e.normalizeFitness();
        }
        sortSpecies(actors);
        TreeSet<Species<E>> good = new TreeSet<>();
        int preCull = species.size();
        for (Species s : species) {
            if (s.members.map.size() > 1 && s.stale < 15) {
                good.add(s);
                // System.out.println(s.members.map.size());
            }
            if (species.size() <= 2)
                good.add(s);
        }
        species = good;
        int postCull = species.size();
        System.out.println("Culled " + (preCull - postCull) + " species");
        bestFitness = 0;
        for (Species s : species) {
            s.cull();
            s.calcFitValues();
            if (s.bestGenerationFitness >= bestFitness) {
                bestFitness = s.bestGenerationFitness;
                if (s.getFittest() != null)
                    best = (E) s.getFittest();
            }
            s.fitSharing();
        }
        if(generation > 100){
            if(bestFitness > 1E9)
            CarActor.MAX_TIME_ON_CHECKPOINT *= 1;
        }

        if (bestFitness > highScoreFitness) {
            highScoreFitness = bestFitness;
            
            if (save) {
                savePopulation();
            }
        }
        System.out.println("GENERATION: " + generation);
        System.out.println("BEST FITNESS: " + bestFitness);
        System.out.println("HIGHSCORE FITNESS: " + highScoreFitness);
        System.out.println("SPECIES: " + species.size());
        HashSet<E> children = new HashSet<>();

        for (Species s : species) {
            children.addAll(s.reproduce(s.size() * (Config.inbreedingPercentage * 2)));
        }

        while (children.size() < populationSize) {
            children.add(((Species<E>) Config.getRandomObject(species))
                    .reproduce((Species<E>) Config.getRandomObject(species)));
        }
        actors = children;
        // for (Species spec : species) {
        //     spec.members.remove(spec.representative);
        // }
    }

    public void epoch(){
        if(epoch >= Config.epochsPerBatch){
            epoch = 1;
            generation();
            return;
        }
        calculateFitness();
        for (E e : actors) {
            e.epoch();
        }
        epoch++;
   }

    private void calculateFitness() {
        for (E e : actors) {
            e.calculateFitness();
        }
    }

    public void sortSpecies(Collection<E> members) {
        for (Species s : species) {
            s.clear();
        }
        for (E member : members) {
            boolean added = false;
            for (Species<E> s : species) {
                if (s.add(member)) {
                    added = true;
                    break;
                }
            }
            if (!added)
                species.add(new Species<E>(member));
        }
    }

    public void savePopulation() {
        int count = 0;
        String saveLoc = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Timestamp(System.currentTimeMillis()));
        try {
            Path path = Paths.get("src\\data\\Networks\\" + saveLoc + "\\");
            Files.createDirectories(path);
            for (E e : actors) {
                File f = new File("src\\data\\Networks\\" + saveLoc + "\\" + count + ".neat");
                System.out.println(f.getAbsolutePath());
                f.createNewFile();
                FileOutputStream FOS = new FileOutputStream(f);
                ObjectOutputStream OOS = new ObjectOutputStream(FOS);
                OOS.writeObject(e);
                OOS.flush();
                OOS.close();
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Collection<E> loadPopulation(String saveLoc) {
        HashSet<E> load = new HashSet<>();
        int count = 0;
        File f = null;
        try {
            f = new File("src\\data\\Networks\\" + saveLoc + "\\" + count + ".neat");
            while (true) {
                FileInputStream FIS = new FileInputStream(f);
                ObjectInputStream OIS = new ObjectInputStream(FIS);
                E o = null;
                o = (E) OIS.readObject();
                load.add(o);
                OIS.close();

                f = new File("src\\data\\Networks\\" + saveLoc + "\\" + count + ".neat");
                count++;
            }
        } catch (FileNotFoundException e) {
            if (load.size() == 0)
                System.out.println("FILES IN " + f.toString().substring(0, f.toString().length() - 7) + " NOT FOUND");
        } catch (StreamCorruptedException e) {
            System.out.println("FILE " + f + " IS NOT A NETWORK");
        } catch (ClassNotFoundException e) {
            System.out.println("FILE " + f + " IS NOT CORRECT NETWORK TYPE");
        } catch (IOException e) {
            System.out.println("UNKNOWN IO EXCEPTION:");
            System.out.println(e);
        } catch (Exception e) {
            System.out.println("UNKNOWN EXCEPTION:");
            System.out.println(e);
        } finally {
            load.remove(null);
            System.out.println("LOADED " + load.size() + " ACTORS");
            System.out.println();
        }
        return load;
    }

    public void display() {
        actors.remove(null);
        for (E e : actors) {
            e.display();
        }
    }

    @Override
    public Iterator<E> iterator() {
        return actors.iterator();
    }
}
