package com.example;

import com.example.Abstracts.Piece;

import java.awt.Color;
import processing.core.PApplet;
import processing.core.PConstants;

public class TicTacToePiece extends Piece {
    public TicTacToePiece(boolean isX, int x, int y){
        super((x/100) * 100, (y/100) * 100, 100, 100, isX ? "X" : "O", isX ? Color.RED : Color.GREEN);
    }

    @Override
    public void draw(PApplet p){
        p.fill(255);
        p.stroke(getColor().getRGB());
        p.rect(getX() + 10, getY() + 10, getWidth() - 20, getHeight() - 20);
        p.fill(getColor().getRGB());
        p.textAlign(PConstants.CENTER, PConstants.CENTER);
        p.textSize(50);
        p.text(getName(), getX() + getWidth() / 2, getY() + getHeight() / 2);
    }
}
