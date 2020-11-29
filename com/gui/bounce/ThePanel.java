package com.gui.bounce;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

public class ThePanel extends JPanel {
	
	private static final long serialVersionUID = -1196245635658787998L;
	private final int TOTAL_CIRCLES = 100;
	private int framesPerSecond = 60;
	private final double INTERVAL_CONST = 1000.0 / framesPerSecond;
	// TODO: Update to ArrayList or HashMap
//	private TheCircle circleList[];
	private Set<TheCircle> circleList;
	private int currentFrame = 0;
	private long previousTime = 0;
	private int totalSeconds = 0;
	private int framesPerSec = 0;
	private int frameCount[];
	private boolean frameCountCheck[]; // Keeps track of frames that have already been counted
	private List<Integer> framesPerIteration;
	private boolean warmup;
	private int xFrameSize, yFrameSize;
	private boolean greenSwitch, blueSwitch, redSwitch;
	
	public ThePanel(int xFrameSize, int yFrameSize) {
		super();
		repaint();
		this.circleList = new HashSet<>();
		this.frameCount = new int[framesPerSecond];
		this.frameCountCheck = new boolean[framesPerSecond + 1];
		this.framesPerIteration = new ArrayList<>();
		this.xFrameSize = xFrameSize;
		this.yFrameSize = yFrameSize;
		
		for (int i = 0; i < 700; i++) {
			circleList.add(TheCircle.newInstance(Color.green, this));
		}
		for (int i = 700; i < 900; i++) {
			circleList.add(TheCircle.newInstance(Color.blue, this));
		}
		for (int i = 900; i < TOTAL_CIRCLES; i++) {
			circleList.add(TheCircle.newInstance(Color.red, this));
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
		Graphics2D g2=(Graphics2D) g;
		super.paint(g);
		
		for (TheCircle circle : circleList) {
			circle.updatePosition(g2);
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
				if (greenSwitch) {
					circleList.add(TheCircle.newInstance(Color.green, this));
					blueSwitch = !blueSwitch;
				}
				if (blueSwitch) {
					circleList.add(TheCircle.newInstance(Color.blue, this));
					redSwitch = !redSwitch;
				}
				if (redSwitch) {
					circleList.add(TheCircle.newInstance(Color.red, this));
				}
				greenSwitch = !greenSwitch;

				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
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
					System.out.println("Total circles in the paint: " + this.circleList.size());
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
				TheCircle.updateCircleList(this.circleList);
//				if (this.circleList.size() < 800) {
//					TheCircle.addCircles(100, circleList, this);
//				}
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
		System.out.println("Circles who survived: " + this.circleList.size());
		System.out.println("More info???");
		System.out.println("-------------------------------------------");
	}

}
