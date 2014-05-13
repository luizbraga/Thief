package edu.cuny.lehman.elements;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import edu.cuny.lehman.tools.Sprite;

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
		g.drawImage(image, x, y, null);
		rect.draw(g);
	}
}