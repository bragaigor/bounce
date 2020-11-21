package com.gui.bounce;

import java.awt.Color;

public class TheBlueCircle extends TheCircle {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1680999877853596045L;
	
	private final int DUMMY_SIZE = 1024; 
	private double dummyArray[];

	public TheBlueCircle(int xLimit, int yLimit, int x, int y, Color color) {
		super(xLimit, yLimit, x, y, 12, color);
		dummyArray = new double[DUMMY_SIZE];
		for (int i = 0; i < dummyArray.length; i++) {
			dummyArray[i] = 5.5;
		}
	}
}
