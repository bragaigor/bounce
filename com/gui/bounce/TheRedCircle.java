package com.gui.bounce;

import java.awt.Color;

/**
 * 
 * @author braga
 *
 * @description: A ball of size 1024 * 256 * 8 = 2M bytes == 32 red circles == 1024 green circles
 */
public class TheRedCircle extends TheCircle {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2171630938539342995L;
	
	private final int DUMMY_SIZE = 1024 * 256; 
	private double dummyArray[];

	public TheRedCircle(int xLimit, int yLimit, int x, int y, Color color, ThePanel attachedPanel) {
		super(xLimit, yLimit, x, y, 16, color, attachedPanel);
		dummyArray = new double[DUMMY_SIZE];
		for (int i = 0; i < dummyArray.length; i++) {
			dummyArray[i] = 5.5;
		}
	}

}
