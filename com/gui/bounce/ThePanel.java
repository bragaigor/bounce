package com.gui.bounce;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class ThePanel extends JPanel {
	
	private static final long serialVersionUID = -1196245635658787998L;
	private int framesPerSecond = 60;
	private final double INTERVAL_CONST = 1000.0 / framesPerSecond;
	// TODO: Update to ArrayList or HashMap
	private TheCircle circleList[];
	private int currentFrame = 0;
	private long previousTime = 0;
	private int totalSeconds = 0;
	private int framesPerSec = 0;
	private int frameCount[];
	private boolean frameCountCheck[]; // Keeps track of frames that have already been counted
	private List<Integer> framesPerIteration;
	private boolean warmup;
	
	public ThePanel(int xFrameSize, int yFrameSize) {
		super();
		repaint();
		this.circleList = new TheCircle[1000];
		this.frameCount = new int[framesPerSecond];
		this.frameCountCheck = new boolean[framesPerSecond + 1];
		this.framesPerIteration = new ArrayList<>();
		for (int i = 0; i < 700; i++) {
			circleList[i] = TheCircle.newInstance(xFrameSize, yFrameSize, Color.green);
		}
		for (int i = 700; i < 900; i++) {
			circleList[i] = TheCircle.newInstance(xFrameSize, yFrameSize, Color.blue);
		}
		for (int i = 900; i < circleList.length; i++) {
			circleList[i] = TheCircle.newInstance(xFrameSize, yFrameSize, Color.red);
		}
		for(int i = 0; i < this.frameCountCheck.length; i++) {
			this.frameCountCheck[i] = false; 
		}
		previousTime = System.currentTimeMillis();
		warmup = false;
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2=(Graphics2D) g;
		super.paint(g);
		
//		g2.setColor(Color.red);
		for (int i = 0; i < circleList.length; i++) {
			circleList[i].updatePosition(g2);
//			g2.fill(circleList[i]);
		}
		
	}
	
	public void justPaint() {
		while(true) {
			long currentTime = System.currentTimeMillis();
			long deltaT = currentTime - previousTime;
			int index = (int)(deltaT / INTERVAL_CONST);
//			System.out.println(deltaT);
			if (warmup && index < this.frameCount.length && !this.frameCountCheck[index + 1]) { // (this.totalSeconds == this.frameCount[index])) {
//				System.out.println("index: " + index + ", deltaT: " + deltaT);
				this.frameCountCheck[index + 1] = true; 
				this.frameCountCheck[index] = false; 
				this.frameCount[index]++;
				currentFrame++;
				framesPerSec++;
				repaint();
			}
			
			// One second has elapsed
			if (deltaT > 1000) {
				if(warmup) {
					this.totalSeconds++;
					System.out.println("Seconds ellapsed: " + totalSeconds + " seconds");
					System.out.println("Frames in last second: " + framesPerSec + " frames");
					System.out.println("Total frames: " + currentFrame + " frames");
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
			}
	//		currentFrame++;
	//		framesPerSec++;
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
		System.out.println("More info???");
		System.out.println("-------------------------------------------");
	}

}
