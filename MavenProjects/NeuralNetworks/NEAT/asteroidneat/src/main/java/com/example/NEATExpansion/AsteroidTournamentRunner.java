package com.example.NEATExpansion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import com.example.NEAT.Population.Population;

import processing.core.PApplet;
import processing.core.PConstants;

public class AsteroidTournamentRunner {
    public Population<AsteroidActor> pop;
    public HashSet<AsteroidTournamentRound> tournament;
    private AsteroidTournamentRound activeWar;
    public int round;

    public AsteroidTournamentRunner(int populationSize) {
        HashSet<AsteroidActor> actors = new HashSet<>();
        for (int i = 0; i < populationSize; i++) {
            actors.add(new AsteroidActor(false));
        }
        pop = new Population<>(actors);
        refreshTournament(pop.actors);
    }

    private void refreshTournament(Collection<AsteroidActor> actorCollection) {
        tournament = new HashSet<>();
        ArrayList<AsteroidActor> tempList = new ArrayList<>(actorCollection);
        while (tempList.size() > 1) {
            tournament.add(new AsteroidTournamentRound(tempList.remove((int) (Math.random() * tempList.size())),
                    tempList.remove((int) (Math.random() * tempList.size()))));
        }
    }

    public void generation() {
        round = 0;
        activeWar = null;
        pop.generation();
        refreshTournament(pop.actors);
    }

    public void update() {
        boolean done = true;
        for (AsteroidTournamentRound aaw : tournament) {
            if (!aaw.over) {
                done = false;
                activeWar = aaw;
                break;
            }
        }
        if (!done) {
            for (AsteroidTournamentRound aaw : tournament) {
                aaw.update();
            }
        } else {
            if (round < 5) {
                HashSet<AsteroidActor> temp = new HashSet<AsteroidActor>();
                round++;
                // for (AsteroidTournamentRound aaw : tournament) {
                //     AsteroidActor winner = aaw.getWinner();
                //     winner.setMaxRound(round);
                //     temp.add(winner);
                // }
                refreshTournament(pop.actors);
            } else {
                // for (AsteroidTournamentRound aaw : tournament) {
                //     aaw.getWinner().champion();
                // }
                generation();
            }
        }
    }

    public void display(PApplet sketch) {
        if (activeWar != null)
            activeWar.display(sketch);
        sketch.textSize(20);
        sketch.textAlign(PConstants.CENTER, PConstants.TOP);
        sketch.text(round, sketch.width / 2, 0);
    }
}
