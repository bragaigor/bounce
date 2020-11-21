package com.gui.bounce;

import java.awt.Color;

public class TheRedCircle extends TheCircle {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2171630938539342995L;
	
	private final int DUMMY_SIZE = 1024 * 64; 
	private double dummyArray[];

	public TheRedCircle(int xLimit, int yLimit, int x, int y, Color color) {
		super(xLimit, yLimit, x, y, 16, color);
		dummyArray = new double[DUMMY_SIZE];
		for (int i = 0; i < dummyArray.length; i++) {
			dummyArray[i] = 5.5;
		}
	}

}
