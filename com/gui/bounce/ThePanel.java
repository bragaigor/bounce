package com.gui.bounce;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

public class ThePanel extends JPanel {
	
	private static final long serialVersionUID = -1196245635658787998L;
	private final int TOTAL_CIRCLES = 100;
	private int framesPerSecond = 60;
	private final double INTERVAL_CONST = 1000.0 / framesPerSecond;
	private Set<TheCircle> syncCircles;
	private int currentFrame = 0;
	private long previousTime = 0;
	private int totalSeconds = 0;
	private int framesPerSec = 0;
	private int frameCount[];
	private boolean frameCountCheck[]; // Keeps track of frames that have already been counted
	private List<Integer> framesPerIteration;
	private boolean warmup;
	private int xFrameSize, yFrameSize;
	
	public ThePanel(int xFrameSize, int yFrameSize) {
		super();
		repaint();
		this.syncCircles = Collections.synchronizedSet(new HashSet<>());
		this.frameCount = new int[framesPerSecond];
		this.frameCountCheck = new boolean[framesPerSecond + 1];
		this.framesPerIteration = new ArrayList<>();
		this.xFrameSize = xFrameSize;
		this.yFrameSize = yFrameSize;
		
		for (int i = 0; i < 700; i++) {
			syncCircles.add(TheCircle.newInstance(Color.green, this));
		}
		for (int i = 700; i < 900; i++) {
			syncCircles.add(TheCircle.newInstance(Color.blue, this));
		}
		for (int i = 900; i < TOTAL_CIRCLES; i++) {
			syncCircles.add(TheCircle.newInstance(Color.red, this));
		}
		for(int i = 0; i < this.frameCountCheck.length; i++) {
			this.frameCountCheck[i] = false; 
		}
		previousTime = System.currentTimeMillis();
		warmup = false;
	}
	
	public int getXFrameSize() {
		return this.xFrameSize;
	}
	
	public int getYFrameSize() {
		return this.yFrameSize;
	}
	
	@Override
	public void paint(Graphics g) {
		try {
			Graphics2D g2=(Graphics2D) g;
			super.paint(g);
			/* Must synchronize synset to avoid ConcurrentModificationException from paint component */
			synchronized (syncCircles) {
				for (TheCircle circle : syncCircles) {
					circle.updatePosition(g2);
					g2.setColor(circle.myColor);
					g2.fill(circle);
				}
			}
		} catch (ConcurrentModificationException e) {
			System.out.println("ConcurrentModificationException cautch at paint!");
			e.printStackTrace();
		}
	}
	
	public void justPaint() {
		while(true) {
			long currentTime = System.currentTimeMillis();
			long deltaT = currentTime - previousTime;
			int index = (int)(deltaT / INTERVAL_CONST);
			if (warmup && index < this.frameCount.length && !this.frameCountCheck[index + 1]) { // (this.totalSeconds == this.frameCount[index])) {
				this.frameCountCheck[index + 1] = true; 
				this.frameCountCheck[index] = false; 
				this.frameCount[index]++;
				currentFrame++;
				framesPerSec++;
				try {
					if (index >= 0 && index < 28) {
						syncCircles.add(TheCircle.newInstance(Color.green, this));
					}
					if (index >= 28 && index < 54) {
						syncCircles.add(TheCircle.newInstance(Color.blue, this));
					}
					if (index >= 54 && index < 59) {
						syncCircles.add(TheCircle.newInstance(Color.red, this));
					}
				} catch (ConcurrentModificationException e) {
					System.out.println("ConcurrentModificationException cautch at index: " + index);
					e.printStackTrace();
				}

				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				repaint();
			}
			
			// One second has elapsed
			if (deltaT > 1000) {
				if(warmup) {
					this.totalSeconds++;
					System.out.println("Seconds ellapsed: " + totalSeconds + " seconds");
					System.out.println("Frames in last second: " + framesPerSec + " frames");
					System.out.println("Total frames: " + currentFrame + " frames");
					System.out.println("Total circles in the paint: " + this.syncCircles.size());
					this.framesPerIteration.add(framesPerSec);
					this.frameCountCheck[this.frameCountCheck.length - 1] = false;
					for (int i = 0; i < this.frameCount.length; i++) {
						System.out.print(i + ": " + this.frameCount[i] + ", ");
					}
					System.out.println("\n###################################################");
				}
				warmup = true;
				framesPerSec = 0;
				currentTime = System.currentTimeMillis();
				previousTime = currentTime;
				TheCircle.updateCircleList(this.syncCircles);
			}
		}
	}
	
	public void displaySummary() {
		long totalFrames = 0;
		long lostFrames = 0;
		for(int i = 0; i < this.framesPerIteration.size(); i++) {
			totalFrames += this.framesPerIteration.get(i);
			lostFrames += this.framesPerSecond - this.framesPerIteration.get(i);
		}
		
		System.out.println("-------------------------------------------");
		System.out.println("Run summary");
		System.out.println("Total frames: " + totalFrames + " frames");
		System.out.println("Frames lost: " + lostFrames + " lost frames");
		System.out.println("Detailed frames lost info: TODO");
		System.out.println("Seconds ellapsed: " + this.totalSeconds + " seconds");
		System.out.println("Circles who survived: " + this.syncCircles.size());
		System.out.println("More info???");
		System.out.println("-------------------------------------------");
	}

}
