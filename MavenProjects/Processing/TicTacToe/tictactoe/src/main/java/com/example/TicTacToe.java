package com.example;

import processing.core.PApplet;

/**
 * Hello world!
 *
 */
public class TicTacToe extends PApplet
{
	TicTacToeBoard board;
    public static void main(String[] passedArgs) {
		String[] processingArgs = {"MySketch"};
		TicTacToe mySketch = new TicTacToe();
		PApplet.runSketch(processingArgs, mySketch);
    }

	@Override
	public void settings(){
		size(300, 400);
		board = new TicTacToeBoard();
	}

	@Override
	public void setup(){
		background(255);
	}

	@Override
	public void draw(){
		background(255);
		board.drawGrid(this);
	}

	@Override
	public void mousePressed(){
		board.placePiece(mouseX, mouseY);
	}
}
