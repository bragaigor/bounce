package com.gui.bounce;

import java.awt.geom.Ellipse2D;
import java.util.Random;

public class TheCircle extends Ellipse2D.Double {

	private static final long serialVersionUID = 8531324654598200175L;
	static private Random rand;
	private static final int SIZE = 10;
	private int deltaX, deltaY, size;
	private int xLimit, yLimit;
	private final int BOUNCE_COUNT_LIMIT = 5;
	private final int DELTA_SPEED_LIMIT = 8;
	private int bounce_count;
	
	static {
		rand = new Random();
	}
	
	private TheCircle(int xLimit, int yLimit, int x, int y, int size) {
		super(x, y, size, size);
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
	
	static public TheCircle newInstance(int xLimit, int yLimit, int size) {
		int x = rand.nextInt(xLimit);
		int y = rand.nextInt(yLimit);
		System.out.println("Creating circle at x: " + x + ", y: " + y + ", size: " + size);
		return new TheCircle(xLimit, yLimit, x, y, size);
	}
	
	static public TheCircle newInstance(int xLimit, int yLimit) {
		return newInstance(xLimit, yLimit, SIZE);
	}
	
	public void updatePosition() {
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
		
	}
}
