package edu.cuny.lehman.main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Walls extends Sprite
{

	Image image;
	
	public Walls(String name, int x, int y, int w, int h)
	{
		super(x, y, w, h);
		image = Toolkit.getDefaultToolkit().createImage(name);
	}



	public void draw(Graphics g) 
	{
		//g.drawImage(image, x, y, w, h, null);
		g.drawImage(image, super.x, super.y, null);
		rect.draw(g);
	}
}