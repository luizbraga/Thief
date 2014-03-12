package edu.cuny.lehman.main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;


public class ImageLayer {

	Image image;
	
	public ImageLayer(String file) {
		this.image = Toolkit.getDefaultToolkit().createImage(file);
	}
	
	public void draw(Graphics g){
		g.drawImage(image, 0, 0, null);
	}

	public void moveLeftBy(int i) {
		// TODO Auto-generated method stub
		
	}

	public void moveRightBy(int i) {
		// TODO Auto-generated method stub
		
	}

}
