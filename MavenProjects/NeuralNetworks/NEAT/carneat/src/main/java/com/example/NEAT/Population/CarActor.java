package com.example.NEAT.Population;

import com.example.CarNeat;
import com.example.Game.Car;
import com.example.Game.Track;
import com.example.NEAT.Network.Genes.Genome;

import processing.core.PVector;

public class CarActor extends Actor {

    /**
     *
     */
    private static int MAX_IDLE_COUNTER = 90;
    private static int MAX_ALIVE_COUNTER = 10800;
    static int MAX_TIME_ON_CHECKPOINT = 1200;

    public static CarNeat app;
    public static Track nextTrack;

    public Car car;
    int aliveCounter;
    int timeOnCheckpoint;
    int prevCheckpoint;
    int checkpointsPassed;
    int idleCounter;
    boolean diedBeforeTimeout = true;

    public CarActor(Genome b, Track track) {
        super(b);
        car = new Car(15, 15f, 1f, track);
        brain = b;
        prevCheckpoint = car.target;
        car.update(0, 0);
    }

    public CarActor(int inputs, int outputs, boolean r, Track track) {
        super(inputs, outputs, r);
        car = new Car(15, 7.5f, 1f, track);
        brain = new Genome(inputs, outputs, r);
        prevCheckpoint = car.target;
        car.update(0, 0);
    }

    @Override
    public void setInputs(int[] in) {
    }

    @Override
    public void act() {
        if (alive && car.sightPoints != null) {
            alive = car.alive;
            if(car.vel.mag() <= 0) {
                idleCounter++;
            } else {
                idleCounter = 0;
            }
            aliveCounter++;
            int arrLength = car.sightPoints.length * 2 - 2;
            double[] ins = new double[arrLength + 4];
            for (int i = 0; i < arrLength; i += 2) {
                ins[i] = car.sightPoints[i / 2].mag()/Car.sightRange;
                switch (car.sightPointTypes[i / 2]) {
                    case ROAD:
                        ins[i + 1] = 1;
                        break;
                    case GRASS:
                        ins[i + 1] = -1;
                        break;
                    case WALL:
                        ins[i + 1] = -1;
                        break;
                    case CHECKPOINT:
                        ins[i + 1] = 1;
                        break;
                    default:
                        break;
                }
            }
            // ins[arrLength] = car.sightPoints[car.sightPoints.length - 1].mag()/Car.sightRange;
            ins[arrLength] = car.rotation / (Math.PI);
            ins[arrLength + 1] = car.target / ((float) car.track.checkPoints.size());
            ins[arrLength + 2] = (car.vel.mag() / car.maxSpeed) * Math.round((PVector.dot(new PVector(car.vel.x, car.vel.y).normalize(), PVector.fromAngle(car.rotation))));
            ins[arrLength + 3] = PVector.dot(PVector.fromAngle(car.rotation), PVector.fromAngle((float)Math.atan(car.track.checkPoints.get(car.target).m)));
            double[] outs = brain.feedForward(ins);
            car.update((float) outs[0], (float) outs[1]);
            if (car.target != prevCheckpoint) {
                checkpointsPassed++;
                timeOnCheckpoint = 0;
            } else {
                timeOnCheckpoint++;
            }
            prevCheckpoint = car.target;
            if (timeOnCheckpoint > MAX_TIME_ON_CHECKPOINT) {
                alive = false;
            }
            if(idleCounter > MAX_IDLE_COUNTER) {
                alive = false;
            }
            if (aliveCounter > MAX_ALIVE_COUNTER) {
                alive = false;
                diedBeforeTimeout = false;
            }
        }
    }

    @Override
    public void display() {
        if (alive)
            car.display(app);
    }

    @Override
    public Actor breed(Actor b) {
        Genome newBrain = Genome.crossover(brain, b.brain);
        newBrain.mutate();
        return new CarActor(newBrain, nextTrack);
    }

    @Override
    public double calculateFitness() {
        double temp = ((Math.pow((checkpointsPassed/(float)car.track.checkPoints.size()) * 100,3)));
        fitness = temp;
        if(fitness > CarNeat.pop.highScoreFitness)
            System.out.println("AHIJGHKUHKEBUBFBFUGUEF");
        return fitness;
    }

    @Override
    public Actor clone() {
        CarActor r = new CarActor((Genome)brain.copy(), car.track);
        r.fitness = fitness;
        return r;
    }

}
