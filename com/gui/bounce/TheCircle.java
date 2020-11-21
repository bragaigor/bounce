package com.gui.bounce;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.Random;

abstract class TheCircle extends Ellipse2D.Double {

	private static final long serialVersionUID = 8531324654598200175L;
	public static Random rand;
	private int deltaX, deltaY, size;
	private int xLimit, yLimit;
	private final int BOUNCE_COUNT_LIMIT = 5;
	private final int DELTA_SPEED_LIMIT = 8;
	private int bounce_count;
	private Color myColor;
	
	static {
		rand = new Random();
	}
	
	public TheCircle() {
		// TODO Auto-generated constructor stub
	}
	
	public TheCircle(int xLimit, int yLimit, int x, int y, int size, Color color) {
		super(x, y, size, size);
		this.myColor = color;
		// Set velocity
		deltaX = rand.nextInt(DELTA_SPEED_LIMIT);
		deltaY = rand.nextInt(DELTA_SPEED_LIMIT);
		
		this.size = size;
		this.xLimit = xLimit;
		this.yLimit = yLimit;
		this.bounce_count = 0;
		
		// Set direction
		if (rand.nextInt(2) == 1) {
			deltaX *= -1;
		}
		
		if (rand.nextInt(2) == 1) {
			deltaY *= -1;
		}
	}
	
	
	static public TheCircle newInstance(int xLimit, int yLimit, Color color) {
		int x = rand.nextInt(xLimit);
		int y = rand.nextInt(yLimit);
		//System.out.println("Creating circle at x: " + x + ", y: " + y + ", size: " + size);
		if (color == Color.red) {
			return new TheRedCircle(xLimit, yLimit, x, y, color);
		} else if (color == Color.green) {
			return new TheGreenCircle(xLimit, yLimit, x, y, color);
		} else {
			return new TheBlueCircle(xLimit, yLimit, x, y, color);
		}
	}
	
//	static public TheCircle newInstance(int xLimit, int yLimit, Color color) {
//		return newInstance(xLimit, yLimit, color);
//	}
	
	public Color getColor() {
		return this.myColor;
	}
	
	public void setColor(Color color) {
		this.myColor = color;
	}
	
	public void updatePosition(Graphics2D g) {
		g.setColor(this.myColor);
		if (bounce_count < BOUNCE_COUNT_LIMIT && 
				(((this.x + (this.size / 2)) >= this.xLimit) || ((this.x - (this.size / 2)) <= 0))) {
			deltaX *= -1;
			bounce_count++;
		}
		if (bounce_count < BOUNCE_COUNT_LIMIT && 
				(((this.y + (this.size / 2)) >= this.yLimit) || ((this.y - (this.size / 2)) <= 0))) {
			deltaY *= -1;
			bounce_count++;
		}
		this.x += deltaX;
		this.y += deltaY;
		g.fill(this);
		
	}
}
