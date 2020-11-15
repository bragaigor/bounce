package com.gui.bounce;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class ThePanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = -1196245635658787998L;
	private Timer tm = new Timer((int)(1000/60),this); // Simulate 60 frames per second
	// TODO: Update to ArrayList or HashMap
	private TheCircle circleList[];
	private int currentFrame = 0;
	private long previousTime = 0;
	private int totalSeconds = 0;
	private int framesPerSec = 0;
	
	public ThePanel(int xFrameSize, int yFrameSize) {
		super();
		repaint();
		circleList = new TheCircle[1000];
		for (int i = 0; i < circleList.length; i++) {
			circleList[i] = TheCircle.newInstance(xFrameSize, yFrameSize);
		}
		previousTime = System.currentTimeMillis();
		tm.start();
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2=(Graphics2D) g;
		super.paint(g);
		
		g2.setColor(Color.red);
		for (int i = 0; i < circleList.length; i++) {
			circleList[i].updatePosition();
			g2.fill(circleList[i]);
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		long currentTime = System.currentTimeMillis();
		long deltaT = currentTime - previousTime;
		// One second has elapsed
		if (deltaT > 1000) {
			previousTime = currentTime;
			totalSeconds++;
			System.out.println("Seconds ellapsed: " + totalSeconds + " seconds");
			System.out.println("Frames in last second: " + framesPerSec + " frames");
			System.out.println("Total frames: " + currentFrame + " frames");
			System.out.println("###################################################");
			framesPerSec = 0;
		}
		repaint();
		currentFrame++;
		framesPerSec++;
	}

}
