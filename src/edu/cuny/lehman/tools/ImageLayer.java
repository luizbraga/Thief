package edu.cuny.lehman.tools;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;


public class ImageLayer {

	Image image;
	int x, y, w, h;
	
	public ImageLayer(String file, int x, int y, int w, int h) 
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.image = Toolkit.getDefaultToolkit().createImage(file);
	}
	
	public void draw(Graphics g)
	{
		g.drawImage(image, x, y, w, h, null);
	}

	public void moveLeftBy(int i) {
		// TODO Auto-generated method stub
		
	}

	public void moveRightBy(int i) {
		// TODO Auto-generated method stub
		
	}

}
