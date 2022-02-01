package com.example.Game;

import java.io.Serializable;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;

public class Game2048 implements Serializable{
    public int[][] board = new int[4][4];
    int squareSize = 20;
    int boardSize = 100;
    int len = squareSize * (board.length + 1) + boardSize * board.length;
    public int score = 0;
    public int dead = 1;

    public Game2048() {
        restart();
    }

    public void restart() {
        board = new int[4][4];
        spawn();
        score = dead = 0;
    }

    void spawn() {
        ArrayList<Integer> xs = new ArrayList<Integer>(), ys = new ArrayList<Integer>();
        for (int j = 0; j < board.length; j++)
            for (int i = 0; i < board[j].length; i++)
                if (board[j][i] == 0) {
                    xs.add(i);
                    ys.add(j);
                }
        int rnd = (int) (Math.random() * xs.size()), y = ys.get(rnd), x = xs.get(rnd);
        board[y][x] = Math.random() < .9 ? 2 : 4;
    }

    public void display(PApplet app) {
        app.noStroke();
        app.fill(150);
        app.textSize(boardSize/2);
        app.rect(0, 0, 500, 500, 10);
        for (int j = 0; j < board.length; j++)
            for (int i = 0; i < board[j].length; i++) {
                app.fill(200);
                app.rect(squareSize + (squareSize + boardSize) * i, squareSize + (squareSize + boardSize) * j, boardSize, boardSize, 5);
            }
            app.textAlign(PConstants.CENTER, PConstants.CENTER);
        for (int j = 0; j < board.length; j++)
            for (int i = 0; i < board[j].length; i++) {
                float x = squareSize + (squareSize + boardSize) * i, y = squareSize + (squareSize + boardSize) * j;
                if (board[j][i] > 0) {
                    float p = (float) (Math.log(board[j][i]) / Math.log(2));
                    app.fill(p/8f * 125);
                    app.rect(x, y, boardSize, boardSize, 5);
                    app.fill(255);
                    app.text("" + board[j][i], x + boardSize/2, y + boardSize/2);
                }
            }
        app.text("score: " + score, 10, 5);
    }

    public boolean moveUp() {
        int[][] newb = go(-1, 0, true);
        if(newb == null){
            return false;
        }else {
            board = newb;
        }
        spawn();
        if(gameOver()) dead = 1;
        return true;
    }

    public boolean moveDown() {
        int[][] newb = go(1, 0, true);
        if(newb == null){
            return false;
        }else {
            board = newb;
        }
        spawn();
        if(gameOver()) dead = 1;
        return true;
    }

    public boolean moveLeft() {
        int[][] newb = go(0, -1, true);
        if(newb == null){
            return false;
        }else {
            board = newb;
        }
        spawn();
        if(gameOver()) dead = 1;
        return true;
    }

    public boolean moveRight() {
        int[][] newb = go(0, 1, true);
        if(newb == null){
            return false;
        }else {
            board = newb;
        }
        spawn();
        if(gameOver()) dead = 1;
        return true;
    }

    boolean gameOver() {
        int[] dx = { 1, -1, 0, 0 }, dy = { 0, 0, 1, -1 };
        boolean out = true;
        for (int i = 0; i < 4; i++)
            if (go(dy[i], dx[i], false) != null)
                out = false;
        return out;
    }

    int[][] go(int dy, int dx, boolean updatescore) {
        int[][] bak = new int[4][4];
        for (int j = 0; j < 4; j++)
            for (int i = 0; i < 4; i++)
                bak[j][i] = board[j][i];
        boolean moved = false;
        if (dx != 0 || dy != 0) {
            int d = dx != 0 ? dx : dy;
            for (int perp = 0; perp < board.length; perp++)
                for (int tang = (d > 0 ? board.length - 2 : 1); tang != (d > 0 ? -1 : board.length); tang -= d) {
                    int y = dx != 0 ? perp : tang, x = dx != 0 ? tang : perp, ty = y, tx = x;
                    if (bak[y][x] == 0)
                        continue;
                    for (int i = (dx != 0 ? x : y) + d; i != (d > 0 ? board.length : -1); i += d) {
                        int r = dx != 0 ? y : i, c = dx != 0 ? i : x;
                        if (bak[r][c] != 0 && bak[r][c] != bak[y][x])
                            break;
                        if (dx != 0)
                            tx = i;
                        else
                            ty = i;
                    }
                    if ((dx != 0 && tx == x) || (dy != 0 && ty == y))
                        continue;
                    else if (bak[ty][tx] == bak[y][x]) {
                        bak[ty][tx] *= 2;
                        if (updatescore)
                            score += bak[ty][tx];
                        moved = true;
                    } else if ((dx != 0 && tx != x) || (dy != 0 && ty != y)) {
                        bak[ty][tx] = bak[y][x];
                        moved = true;
                    }
                    if (moved)
                        bak[y][x] = 0;
                }
        }
        return moved ? bak : null;
    }

    public int maxValue(){
        int out = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if(board[i][j] >= out){
                    out = board[i][j];
                }
            }
        }
        return out;
    }
}
