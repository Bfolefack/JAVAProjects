package com.example;

import javafx.scene.text.Text;
import processing.core.PApplet;
import processing.core.PConstants;

public class TicTacToeBoard extends Grid {
    boolean isXTurn = true;
    public String winner = "";

    public TicTacToeBoard() {
        super(3, 3);
    }

    public void placePiece(int xPos, int yPos) {
        if (getSpot(xPos / 100, yPos / 100) == null)
            if (setSpot(xPos / 100, yPos / 100, new TicTacToePiece(isXTurn, xPos, yPos))) {
                isXTurn = !isXTurn;
            }
    }

    @Override
    public void drawGrid(PApplet window) {
        for (int i = 0; i < getNumRows(); i++) {
            for (int j = 0; j < getNumCols(); j++) {
                if (getSpot(i, j) != null) {
                    getSpot(i, j).draw(window);
                }
            }
            window.stroke(0);
            window.line(i * 100, 0, i * 100, 300);
            window.line(0, i * 100, 300, i * 100);
        }
        window.line(0, 300, 300, 300);
        window.line(300, 0, 300, 300);
        
        window.textAlign(PConstants.CENTER, PConstants.CENTER);
        if (winner.equals("X")) {
            window.fill(255, 0, 0);
            window.textSize(50);
            window.text("X Wins!", 150, 350);
        } else if (winner.equals("O")) {
            window.fill(0, 255, 0);
            window.textSize(50);
            window.text("O Wins!", 150, 350);
        } else if (isFull()) {
            window.fill(225, 225, 0);
            window.textSize(50);
            window.text("Draw!", 150, 350);
        } else if (isXTurn) {
            window.fill(255, 0, 0);
            window.textSize(50);
            window.text("X's Turn!", 150, 350);
        } else {
            window.fill(0, 255, 0);
            window.textSize(50);
            window.text("O's Turn!", 150, 350);
        }
        checkWinner();
    }

    private boolean isFull() {
        for (int i = 0; i < getNumRows(); i++) {
            for (int j = 0; j < getNumCols(); j++) {
                if (getSpot(i, j) == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public void checkWinner() {
        String[][] grid = new String[getNumRows()][getNumCols()];
        for (int i = 0; i < getNumRows(); i++) {
            for (int j = 0; j < getNumCols(); j++) {
                if (getSpot(i, j) != null) {
                    grid[i][j] = getSpot(i, j).getName();
                } else {
                    grid[i][j] = "";
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            if(grid[i][0] != "" && grid[i][0].equals(grid[i][1]) && grid[i][1].equals(grid[i][2])) {
                winner = grid[i][0];
            }
            if(grid[0][i] != "" && grid[0][i].equals(grid[1][i]) && grid[1][i].equals(grid[2][i])) {
                winner = grid[0][i];
            }
            if(grid[0][0] != "" && grid[0][0].equals(grid[1][1]) && grid[1][1].equals(grid[2][2])) {
                winner = grid[0][0];
            }
            if(grid[0][2] != "" && grid[0][2].equals(grid[1][1]) && grid[1][1].equals(grid[2][0])) {
                winner = grid[0][2];
            }
        }
    }
}
