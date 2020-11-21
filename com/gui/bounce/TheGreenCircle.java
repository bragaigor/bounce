package com.gui.bounce;

import java.awt.Color;

public class TheGreenCircle extends TheCircle {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2228354230437102574L;
	
	private final int DUMMY_SIZE = 256; 
	private double dummyArray[];

	public TheGreenCircle(int xLimit, int yLimit, int x, int y, Color color) {
		super(xLimit, yLimit, x, y, 8, color);
		dummyArray = new double[DUMMY_SIZE];
		for (int i = 0; i < dummyArray.length; i++) {
			dummyArray[i] = 5.5;
		}
	}
}
