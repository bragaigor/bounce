package com.gui.bounce;

import java.awt.Graphics2D;

public class DrawWorker implements Runnable {
	
	private TheCircle circleList[];
	private Graphics2D g;

	public DrawWorker(TheCircle circleList[], Graphics2D g) {
		this.circleList = circleList;
		this.g = g;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < circleList.length; i++) {
			circleList[i].updatePosition();
			this.g.fill(circleList[i]);
		}
		
	}
}
