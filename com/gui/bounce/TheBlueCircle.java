package com.gui.bounce;

import java.awt.Color;

/**
 * 
 * @author braga
 *
 * @description: A ball of size 1024 * 8 * 8 = 64K bytes == 32 green circles
 */
public class TheBlueCircle extends TheCircle {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1680999877853596045L;
	
	private final int DUMMY_SIZE = 1024 * 8; 
	private double dummyArray[];

	public TheBlueCircle(int xLimit, int yLimit, int x, int y, Color color, ThePanel attachedPanel) {
		super(xLimit, yLimit, x, y, 12, color, attachedPanel);
		dummyArray = new double[DUMMY_SIZE];
		for (int i = 0; i < dummyArray.length; i++) {
			dummyArray[i] = 5.5;
		}
	}
}
