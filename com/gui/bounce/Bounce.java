package com.gui.bounce;

import javax.swing.JFrame;

public class Bounce {
	
	public Bounce() {

	}

    public static void main(String[] args) {
    	int xFrameSize = 800;
    	int yFrameSize = 800;
        System.out.println("Hello");
        ThePanel panel = new ThePanel(xFrameSize, yFrameSize);
        JFrame frame = new JFrame();
        frame.add(panel);
        frame.pack();
        frame.setSize(xFrameSize, yFrameSize);
        frame.setTitle("Bounce");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowManager(panel));
        panel.justPaint();
    }

}
