package com.gui.bounce;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

abstract class TheCircle extends Ellipse2D.Double {

	private static final long serialVersionUID = 8531324654598200175L;
	public static Random rand;
	private int deltaX, deltaY, size;
	private final int BOUNCE_COUNT_LIMIT = 5;
	private final int DELTA_SPEED_LIMIT = 8;
	private int bounce_count;
	private Color myColor;
	private ThePanel attachedPanel;
	
	static {
		rand = new Random();
	}
	
	public TheCircle() {
		// TODO Auto-generated constructor stub
	}
	
	public TheCircle(int xLimit, int yLimit, int x, int y, int size, Color color,ThePanel attachedPanel) {
		super(x, y, size, size);
		this.myColor = color;
		this.attachedPanel = attachedPanel;
		
		// Set velocity
		deltaX = rand.nextInt(DELTA_SPEED_LIMIT);
		deltaY = rand.nextInt(DELTA_SPEED_LIMIT);
		
		this.size = size;
		this.bounce_count = 0;
		
		// Set direction
		if (rand.nextInt(2) == 1) {
			deltaX *= -1;
		}
		
		if (rand.nextInt(2) == 1) {
			deltaY *= -1;
		}
	}
	
	
	static public TheCircle newInstance(Color color, ThePanel attachedPanel) {
		int x = rand.nextInt(attachedPanel.getXFrameSize());
		int y = rand.nextInt(attachedPanel.getYFrameSize());
		//System.out.println("Creating circle at x: " + x + ", y: " + y + ", size: " + size);
		if (color == Color.red) {
			return new TheRedCircle(attachedPanel.getXFrameSize(), attachedPanel.getYFrameSize(), x, y, color, attachedPanel);
		} else if (color == Color.green) {
			return new TheGreenCircle(attachedPanel.getXFrameSize(), attachedPanel.getYFrameSize(), x, y, color, attachedPanel);
		} else {
			return new TheBlueCircle(attachedPanel.getXFrameSize(), attachedPanel.getYFrameSize(), x, y, color, attachedPanel);
		}
	}
	
	public Color getColor() {
		return this.myColor;
	}
	
	public void setColor(Color color) {
		this.myColor = color;
	}
	
	public static void updateCircleList(Set<TheCircle> circles) {
		for (Iterator<TheCircle> i = circles.iterator(); i.hasNext();) {
			TheCircle circle = i.next();
			if (circle.isOutOfLimits()) {
		        i.remove();
		    }
		}
	}
	
	public static void addCircles(int quantity, Set<TheCircle> circles, ThePanel attachedPanel) {
		int greenQuantity = (int)(quantity * 0.6);
		int blueQuantity = (int)(quantity * 0.3);
		int redQuantity = (int)(quantity * 0.1);
		addGreenCircles(greenQuantity, circles, attachedPanel);
		addBlueCircles(blueQuantity, circles, attachedPanel);
		addRedCircles(redQuantity, circles, attachedPanel);
	}
	
	private static void addRedCircles(int quantity, Set<TheCircle> circles, ThePanel attachedPanel) {
		System.out.println("Adding " + quantity + " more red circles to the panel!");
		for (int i = 0; i < quantity; i++) {
			int x = rand.nextInt(attachedPanel.getXFrameSize());
			int y = rand.nextInt(attachedPanel.getYFrameSize());
			circles.add(new TheRedCircle(attachedPanel.getXFrameSize(), attachedPanel.getYFrameSize(), x, y, Color.red, attachedPanel));
		}
	}
	
	private static void addBlueCircles(int quantity, Set<TheCircle> circles, ThePanel attachedPanel) {
		System.out.println("Adding " + quantity + " more blue circles to the panel!");
		for (int i = 0; i < quantity; i++) {
			int x = rand.nextInt(attachedPanel.getXFrameSize());
			int y = rand.nextInt(attachedPanel.getYFrameSize());
			circles.add(new TheBlueCircle(attachedPanel.getXFrameSize(), attachedPanel.getYFrameSize(), x, y, Color.blue, attachedPanel));
		}
	}
	
	private static void addGreenCircles(int quantity, Set<TheCircle> circles, ThePanel attachedPanel) {
		System.out.println("Adding " + quantity + " more green circles to the panel!");
		for (int i = 0; i < quantity; i++) {
			int x = rand.nextInt(attachedPanel.getXFrameSize());
			int y = rand.nextInt(attachedPanel.getYFrameSize());
			circles.add(new TheGreenCircle(attachedPanel.getXFrameSize(), attachedPanel.getYFrameSize(), x, y, Color.green, attachedPanel));
		}
	}
	
	public boolean isOutOfLimits() {
		return (this.x > attachedPanel.getXFrameSize()) || (this.x < 0) || (this.y > attachedPanel.getYFrameSize()) || (this.y < 0);
	}
	
	public void updatePosition(Graphics2D g) {
		g.setColor(this.myColor);
		if (bounce_count < BOUNCE_COUNT_LIMIT && 
				(((this.x + (this.size / 2)) >= attachedPanel.getXFrameSize()) || ((this.x - (this.size / 2)) <= 0))) {
			deltaX *= -1;
			bounce_count++;
		}
		if (bounce_count < BOUNCE_COUNT_LIMIT && 
				(((this.y + (this.size / 2)) >= attachedPanel.getYFrameSize()) || ((this.y - (this.size / 2)) <= 0))) {
			deltaY *= -1;
			bounce_count++;
		}
		this.x += deltaX;
		this.y += deltaY;
		g.fill(this);
		
	}
}
