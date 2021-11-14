package com.example.Utils;

import processing.core.PApplet;
import processing.core.PVector;

import static com.example.Utils.NavTree.Direction.LEFT;
import static com.example.Utils.NavTree.Direction.RIGHT;
import static com.example.Utils.NavTree.Direction.TOP;

import java.util.Collection;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import javax.print.attribute.standard.MediaSize.NA;

import static com.example.Utils.NavTree.Direction.BOTTOM;

public class NavTree implements Comparable<NavTree> {
    NavTree[] children;

    float gCost; // Grid Cost
    float hCost; // Heuristic Cost
    float cost; // Cells Individual Cost

    NavTree parentTree;
    NavTree pathGiver;

    boolean parent;
    boolean divided;

    boolean active;
    boolean spent;

    int x;
    int y;
    int w;
    int h;

    boolean highlighted;

    public NavTree(GridQuadTree input) {
        parent = input.parent;
        divided = input.divided;
        children = new NavTree[4];
        cost = input.value;
        x = input.x;
        y = input.y;
        w = input.w;
        h = input.h;
        if (divided) {
            for (int i = 0; i < 4; i++) {
                children[i] = new NavTree(this, input.subdivisions[i]);
            }
        }
    }

    private NavTree(NavTree p, GridQuadTree input) {
        parentTree = p;
        parent = input.parent;
        divided = input.divided;
        children = new NavTree[4];
        cost = input.value;
        x = input.x;
        y = input.y;
        w = input.w;
        h = input.h;
        if (divided) {
            for (int i = 0; i < 4; i++) {
                children[i] = new NavTree(p, input.subdivisions[i]);
            }
        }
    }

    public void display(PApplet sketch) {
        if (highlighted) {
            sketch.fill(255, 175, 175);
            sketch.rect(x, y, w, h);
            sketch.fill(0);
            // sketch.text(gCost + "\n" + hCost, center().x, center().y);
        }
        if (divided) {
            for (NavTree nt : children) {
                nt.display(sketch);
            }
        }
    }

    public PVector center() {
        return new PVector(x + w / 2, y + h / 2);
    }

    public Path getPath(PVector s, PVector e) {
        if (parent) {
            NavTree start = getTree(s);
            NavTree end = getTree(e);
            if (start == null || end == null || start == end) {
                return null;
            }
            TreeSet<NavTree> active = new TreeSet<>();
            resetAll(e);
            start.gCost = 0;
            start.spent = true;
            start.active = true;
            active.addAll(getNeighbors(start));
            while (true) {
                if(active.size() > 0){
                    NavTree nt = active.pollFirst();
                    nt.spent = true;
                    if (active.contains(end))
                        break;
                    active.addAll(getNeighbors(nt));
                } else {
                    break;
                }
            }
            NavTree t = end;
            Stack<PVector> pStack = new Stack<>();
            while(t != null && pStack.size() < 10000){
                pStack.push(t.center());
                t = t.pathGiver;
            }
            return new Path(pStack);
        }
        return null;
    }

    @Override
    public int compareTo(NavTree n) {
        return ((Float) ((gCost + hCost))).compareTo((n.gCost + n.hCost));
    }

    public void resetAll(PVector target) {
        highlighted = false;
        spent = false;
        active = false;
        gCost = Float.MAX_VALUE;
        hCost = PVector.dist(new PVector(x + w / 2, y + h / 2), target);
        if (divided) {
            for (NavTree nt : children) {
                nt.resetAll(target);
            }
        }
    }

    private Set<NavTree> getNeighbors(NavTree tree) {
        HashSet<NavTree> neighbors = new HashSet<>();

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0)
                    continue;
                NavTree t = getTree(getNeighborVec(i, j, tree), tree.w);
                if (t != null)
                    if (!t.divided) {
                        neighbors.add(t);
                    } else {
                        neighbors.addAll(t.getEdgeTrees(i, j));
                    }
            }
        }

        Set<NavTree> toKill = new HashSet<NavTree>();
        for (NavTree nt : neighbors) {
            nt.highlighted = true;
            float f = PVector.dist(center(), nt.center()) * tree.cost + tree.gCost;
            if (f < nt.gCost) {
                nt.gCost = f;
                nt.pathGiver = tree;
            }
            if (nt.spent || nt.active) {
                toKill.add(nt);
            }
        }

        neighbors.removeAll(toKill);
        return neighbors;
    }

    private PVector getNeighborVec(int i, int j, NavTree tree) {
        return new PVector(tree.x + tree.w / 2, tree.y + tree.h / 2).add(new PVector(tree.w * i, tree.h * j));
    }

    private Set<NavTree> getEdgeTrees(int i, int j) {
        HashSet<NavTree> edge = new HashSet<>();
        String code = i + "," + j;
        switch (code) {
        case "1,1":
            return getEdgeTrees(new DoubleDir(TOP, LEFT));
        case "0,1":
            return getEdgeTrees(new DoubleDir(TOP, TOP));
        case "-1,1":
            return getEdgeTrees(new DoubleDir(TOP, RIGHT));
        case "1,0":
            return getEdgeTrees(new DoubleDir(LEFT, LEFT));
        case "-1,0":
            return getEdgeTrees(new DoubleDir(RIGHT, RIGHT));
        case "1,-1":
            return getEdgeTrees(new DoubleDir(BOTTOM, LEFT));
        case "0,-1":
            return getEdgeTrees(new DoubleDir(BOTTOM, BOTTOM));
        case "-1,-1":
            return getEdgeTrees(new DoubleDir(BOTTOM, RIGHT));
        }
        return null;
    }

    private Set<NavTree> getEdgeTrees(DoubleDir dd) {
        if (!divided) {
            HashSet<NavTree> t = new HashSet<>();
            t.add(this);
            return t;
        }
        if (dd.dir1 == dd.dir2) {
            switch (dd.dir1) {
            case LEFT:
                if (divided) {
                    Set<NavTree> t = children[0].getEdgeTrees(dd);
                    t.addAll(children[2].getEdgeTrees(dd));
                    return t;
                }
                break;
            case RIGHT:
                if (divided) {
                    Set<NavTree> t = children[1].getEdgeTrees(dd);
                    t.addAll(children[3].getEdgeTrees(dd));
                    return t;
                }
                break;
            case TOP:
                if (divided) {
                    Set<NavTree> t = children[0].getEdgeTrees(dd);
                    t.addAll(children[1].getEdgeTrees(dd));
                    return t;
                }
                break;
            case BOTTOM:
                if (divided) {
                    Set<NavTree> t = children[2].getEdgeTrees(dd);
                    t.addAll(children[3].getEdgeTrees(dd));
                    return t;
                }
                break;
            }
        } else if (dd.contains(TOP) && dd.contains(LEFT)) {
            return children[0].getEdgeTrees(dd);
        } else if (dd.contains(TOP) && dd.contains(RIGHT)) {
            return children[1].getEdgeTrees(dd);
        } else if (dd.contains(BOTTOM) && dd.contains(LEFT)) {
            return children[2].getEdgeTrees(dd);
        } else if (dd.contains(BOTTOM) && dd.contains(RIGHT)) {
            return children[3].getEdgeTrees(dd);
        }

        return null;
    }

    public NavTree getTree(PVector pos) {
        if (overlaps((int) pos.x, (int) pos.y)) {
            if (divided) {
                for (NavTree nt : children) {
                    if (nt.overlaps((int) pos.x, (int) pos.y)) {
                        return nt.getTree(pos);
                    }
                }
            }
            return this;
        }
        return null;
    }

    public NavTree getTree(PVector pos, int size) {
        if (overlaps((int) pos.x, (int) pos.y)) {
            if (divided && w > size) {
                for (NavTree nt : children) {
                    if (nt.overlaps((int) pos.x, (int) pos.y)) {
                        return nt.getTree(pos, size);
                    }
                }
            }
            return this;
        }
        return null;
    }

    public boolean overlaps(int i, int j) {
        if (i > x && i < x + w && j > y && j < y + h) {
            return true;
        }
        return false;
    }

    public enum Direction {
        LEFT, RIGHT, TOP, BOTTOM
    }

    public class DoubleDir implements Comparable<DoubleDir> {
        Direction dir1;
        Direction dir2;

        DoubleDir(Direction d1, Direction d2) {
            dir1 = d1;
            dir2 = d2;
        }

        @Override
        public int compareTo(DoubleDir dd) {
            if ((dd.dir1 == dir1 && dd.dir2 == dd.dir2) || (dd.dir2 == dir1 && dd.dir1 == dd.dir2)) {
                return 0;
            }
            return -1;
        }

        public boolean contains(Direction d) {
            if (dir1 == d || dir2 == d) {
                return true;
            }
            return false;
        }

    }
}
