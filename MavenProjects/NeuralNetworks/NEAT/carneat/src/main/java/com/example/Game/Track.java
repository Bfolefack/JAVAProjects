package com.example.Game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;
import java.util.TreeMap;
import java.awt.geom.Point2D;
import java.io.Serializable;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Track implements Serializable {
    int width;
    int height;
    ArrayList<PVector> track;
    public ArrayList<LinearFormula> checkPoints;
    TrackType[][] grid;
    public static final int trackWidth = GameConfig.TRACK_WIDTH;
    public static TreeMap<Integer, float[][]> loadedTracks;
    transient PImage gridDisplay;
    private PVector lowestCheckPoint;
    public int lowestCheckPointIndex;
    public int randomCheckPointIndex;

    public enum TrackType {
        GRASS, ROAD, CHECKPOINT, WALL
    }

    public Track(PApplet p) {
        width = p.width;
        height = p.height;
        grid = new TrackType[width][height];
        initializeGrid();
        track = generateTrack();
        generateGrid(p);
        // generateObstacles(75);
        generateImage(p);
        randomCheckPointIndex = (int) (Math.random() * checkPoints.size());
    }

    private void generateObstacles(int num) {
        for (int i = 0; i < num; i++) {
            int size = (int) (Math.random() * (trackWidth / 4));
            PVector pos = new PVector((int) (Math.random() * width), (int) (Math.random() * height));
            if (grid[(int) pos.x][(int) pos.y] == TrackType.ROAD) {
                for (int j = -size; j < size; j++) {
                    for (int l = -size; l < size; l++) {
                        if (pos.x + j >= 0 && pos.x + j < width && pos.y + l >= 0 && pos.y + l < height)
                            if (new PVector(j, l).mag() < size)
                                if (grid[(int) pos.x + j][(int) pos.y + l] == TrackType.ROAD || grid[(int) pos.x + j][(int) pos.y + l] == TrackType.CHECKPOINT)
                                    grid[(int) pos.x + j][(int) pos.y + l] = TrackType.WALL;
                    }
                }
            }
        }
    }

    private void initializeGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = TrackType.GRASS;
            }
        }
    }

    public void display(PApplet p) {
        p.image(gridDisplay, 0, 0, p.width, p.height);
    }

    public ArrayList<PVector> generateTrack() {
        ArrayList<PVector> hull = convexHull();
        for (int i = 0; i < hull.size() - 1; i++) {
            PVector p = offset(hull.get(i), hull.get(i + 1));
            if (p != null) {
                hull.add(i + 1, p);
                i++;
            } else {
                continue;
            }
        }
        List<Point2D> points = new ArrayList<Point2D>();
        for (PVector vector : hull) {
            points.add(new Point2D.Double(vector.x, vector.y));
        }

        CatmullRomSpline crs = CatmullRomSpline.create(points, 100, 1, true);
        ArrayList<PVector> track = new ArrayList<PVector>();

        for (Point2D p2D : crs.getInterpolatedPoints()) {
            track.add(new PVector((float) p2D.getX(), (float) p2D.getY()));
        }

        return track;
    }

    public ArrayList<PVector> convexHull() {
        HashSet<PVector> points = new HashSet<PVector>();

        for (int i = 0; i < Math.random() * 15 + 15; i++) {
            points.add(new PVector(
                    (trackWidth * 2) + (float) Math.random() * (GameConfig.WIDTH - trackWidth * 4),
                    (trackWidth * 2) + (float) Math.random() * (GameConfig.HEIGHT - trackWidth * 4)));
        }

        HashSet<PVector> hitList = new HashSet<PVector>();
        for (PVector pVector : points) {
            for (PVector pVector2 : points) {
                if (PVector.dist(pVector, pVector2) < 50 && pVector != pVector2) {
                    hitList.add(pVector2);
                }
            }
        }
        points.removeAll(hitList);

        PVector yMax = new PVector(0, 0);
        for (PVector pVector : points) {
            if (pVector.y > yMax.y) {
                yMax = pVector;
            }
        }
        points.remove(yMax);

        TreeMap<Float, PVector> CCWOrdered = new TreeMap<>();

        for (PVector pVector : points) {
            CCWOrdered.put(PVector.angleBetween(new PVector(1, 0), PVector.sub(yMax, pVector)), pVector);
        }

        Stack<PVector> path = new Stack<PVector>();
        ArrayList<PVector> CCWOrderedArr = new ArrayList<>(CCWOrdered.values());
        CCWOrderedArr.add(0, yMax);

        if (CCWOrderedArr.size() < 3) {
            return convexHull();
        }
        for (int i = 0; i < 3; i++) {
            path.push(CCWOrderedArr.get(i));
        }

        PVector nextToTop = CCWOrderedArr.get(1);
        for (int i = 3; i < CCWOrderedArr.size(); i++) {
            path.push(CCWOrderedArr.get(i));
            while (path.size() > 1 && orientation(nextToTop, path.peek(), CCWOrderedArr.get(i)) != 2) {
                path.pop();
                PVector temp = path.pop();
                nextToTop = path.peek();
                path.push(temp);
            }
            path.push(CCWOrderedArr.get(i));
        }

        return new ArrayList<>(path);
    }

    private void generateGrid(PApplet p) {
        int checkPointWidth = 2;

        for (int i = 0; i < track.size(); i++) {
            // System.out.println(i);
            PVector step = track.get(i);
            step.set(step.x, step.y);
            // System.out.println(step);
            for (int j = -trackWidth / 2; j < trackWidth / 2; j++) {
                for (int k = -trackWidth / 2; k < trackWidth / 2; k++) {
                    if (inBounds(PVector.add(step, new PVector(j, k)))) {
                        if (PVector.dist(new PVector(j, k), new PVector(0, 0)) < trackWidth / 2) {
                            grid[(int) step.x + j][(int) step.y + k] = TrackType.ROAD;
                        }
                    }
                }
            }
        }

        for (int i = 2; i < grid.length - 2; i++) {
            for (int j = 2; j < grid[0].length - 2; j++) {
                for (int k = -2; k < 3; k++) {
                    if (grid[i][j] == TrackType.WALL)
                        continue;
                    for (int l = -2; l < 3; l++) {
                        if (grid[i + k][j + l] != grid[i][j] && grid[i + k][j + l] != TrackType.WALL) {
                            grid[i][j] = TrackType.WALL;
                            continue;
                        }
                    }
                }
            }
        }

        PVector lastStep = new PVector(0, 0);
        checkPoints = new ArrayList<LinearFormula>();
        int checkPointInterval = 5;
        float checkPointSpacing = 5;
        for (int i = 0; i < track.size() - 1; i += checkPointInterval) {
            PVector step = track.get(i);
            step.set(step.x, step.y);
            if (PVector.dist(step, lastStep) > trackWidth / checkPointSpacing
                    && (PVector.dist(step, track.get(0)) > trackWidth / checkPointSpacing || i == 0)) {
                LinearFormula lf;
                if (i > 0)
                    lf = LinearFormula.getNormal(track.get(i - 1).x, track.get(i - 1).y, step.x, step.y);
                else
                    lf = LinearFormula.getNormal(track.get(i + 1).x, track.get(i + 1).y, step.x, step.y);
                lastStep = step;
                checkPoints.add(lf);
                if (Math.abs(lf.m) < 1) {
                    for (int j = -trackWidth; j < trackWidth; j++) {
                        for (int k = -trackWidth; k < trackWidth; k++) {
                            fillWithX(trackWidth, checkPointWidth, step, lf, j);
                        }
                    }
                } else {
                    for (int j = -trackWidth; j < trackWidth; j++) {
                        for (int k = -trackWidth; k < trackWidth; k++) {
                            fillWithY(trackWidth, checkPointWidth, step, lf, k);
                        }
                    }
                }
            }
        }
        generateImage(p);
    }

    private void generateImage(PApplet p) {
        gridDisplay = p.createImage(width, height, PApplet.RGB);
        for (

                int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                switch (grid[i][j]) {
                    case GRASS:
                        gridDisplay.set(i, j, p.color(0, 155, 0));
                        break;
                    case ROAD:
                        gridDisplay.set(i, j, p.color(135));
                        break;
                    case CHECKPOINT:
                        gridDisplay.set(i, j, p.color(255, 155, 50));
                        break;
                    case WALL:
                        gridDisplay.set(i, j, p.color(0));
                        break;

                    default:
                        break;
                }
            }
        }
        gridDisplay.updatePixels();
    }

    private void fillWithY(int trackWidth, int checkPointWidth, PVector step, LinearFormula lf, int k) {
        int x;
        int y;
        x = (int) lf.getX(step.y + k);
        y = (int) step.y + k;
        if (x < step.x + trackWidth && x > step.x - trackWidth)
            for (int l = -checkPointWidth / 2; l < checkPointWidth / 2; l++) {
                for (int m = -checkPointWidth / 2; m < checkPointWidth; m++) {
                    if (inBounds(new PVector(x + l, y + m))) {
                        if (grid[x + l][y + m] == TrackType.ROAD)
                            grid[x + l][y + m] = TrackType.CHECKPOINT;
                    }
                }
            }
    }

    private void fillWithX(int trackWidth, int checkPointWidth, PVector step, LinearFormula lf, int j) {
        int x;
        int y;
        x = (int) step.x + j;
        y = (int) lf.getY(step.x + j);
        if (y < step.y + trackWidth && y > step.y - trackWidth)
            for (int l = -checkPointWidth / 2; l < checkPointWidth / 2; l++) {
                for (int m = -checkPointWidth / 2; m < checkPointWidth; m++) {
                    if (inBounds(new PVector(x + l, y + m))) {
                        if (grid[x + l][y + m] == TrackType.ROAD)
                            grid[x + l][y + m] = TrackType.CHECKPOINT;
                    }
                }
            }
    }

    public boolean inBounds(PVector p) {
        return p.x >= 0 && p.x < width && p.y >= 0 && p.y < height;
    }

    public PVector offset(PVector v1, PVector v2) {
        float difficulty = 5f;
        PVector v = PVector.add(v1, v2).div(2);
        v.add(PVector.sub(v1, v2).rotate((float) Math.PI / 2).div(difficulty).mult((float) (Math.random() * 2 - 1)));
        if (v.x < width / 3f || v.x > width * 2 / 3f || v.y < height / 3f || v.y > height * 2 / 3f)
            return offset(v1, v2, 1);
        return v;
    }

    public PVector offset(PVector v1, PVector v2, int depth) {
        float difficulty = 5f;
        PVector v = PVector.add(v1, v2).div(2);
        v.add(PVector.sub(v1, v2).rotate((float) Math.PI / 2).div(difficulty).mult((float) (Math.random() * 2 - 1)));
        if (depth > 60)
            return null;
        if (v.x < width / 3f || v.x > width * 2 / 3f || v.y < height / 3f || v.y > height * 2 / 3f)
            return offset(v1, v2, depth + 1);
        return v;
    }

    int orientation(PVector p, PVector q, PVector r) {
        float val = (q.y - p.y) * (r.x - q.x) -
                (q.x - p.x) * (r.y - q.y);

        if (val == 0)
            return 0; // collinear
        return (val > 0) ? 1 : 2; // clock or counterclock wise
    }

    public PVector lowestCheckPoint() {
        if (lowestCheckPoint == null) {
            lowestCheckPoint = new PVector(0, 0);
            for (LinearFormula linearFormula : checkPoints) {
                if (linearFormula.originPoint.y > lowestCheckPoint.y) {
                    lowestCheckPoint = linearFormula.originPoint;
                    lowestCheckPointIndex = checkPoints.indexOf(linearFormula);
                }
            }
        }
        return lowestCheckPoint.copy();
    }

    public PVector randomCheckPoint() {
        return checkPoints.get(randomCheckPointIndex).originPoint.copy();
    }
}