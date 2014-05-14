package edu.cuny.lehman.elements;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import edu.cuny.lehman.tools.Rect;
import edu.cuny.lehman.tools.Sprite;

public class Door extends Sprite {
	
	Image image;
	public Rect phaseRect;
	
	public Door(int x, int y, int w, int h) {
		super(x, y, w, h);
		image = Toolkit.getDefaultToolkit().createImage("../images/door.png");
		phaseRect = new Rect(x, y, w, h);
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, x, y, null);
	}

	public void set(int x, int y) {
		super.x = x;
		super.y = y;
		rect.x = x;
		rect.y = y;
	}

} 