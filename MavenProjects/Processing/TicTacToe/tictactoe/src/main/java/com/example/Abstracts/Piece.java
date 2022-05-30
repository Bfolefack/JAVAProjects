//(c) A+ Computer Science
//www.apluscompsci.com
//Name - 
package com.example.Abstracts;

import java.awt.Color;
import java.awt.Graphics;

import com.example.Interfaces.Nameable;

import processing.core.PApplet;

import java.awt.Font;

public class Piece extends Cell implements Nameable
{
	protected String name;
	private Color color;
	
	public Piece()
	{
		super(5,5,5,5);
		setName("empty");
		setColor(Color.BLUE);		
	}

	public Piece(String n)
	{
		super(5,5,5,5);
		setName(n);
		setColor(Color.BLUE);
	}

	public Piece(int x, int y, String n)
	{
		super(x,y,5,5);
		setName(n);
		setColor(Color.BLUE);
	}

	public Piece(int x, int y, int w, int h, String n)
	{
		super(x,y,w,h);
		setName(n);
		setColor(Color.BLUE);
	}

	public Piece(int x, int y, int w, int h, String n, Color c)
	{
		super(x,y,w,h);
		setName(n);
		setColor(c);
	}

	public void setName(String n)
	{
		name = n;
	}

	public void setColor(Color c)
	{
		color = c;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public String toString()
	{
		return "Name: " + name + "\nColor: " + color + "\nX: " + xPos + "\nY: " + yPos;
	}

	@Override
	public void draw(PApplet window) {
		window.rect(xPos, yPos, width, height);
	}
}