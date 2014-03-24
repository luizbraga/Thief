package edu.cuny.lehman.main;

import java.awt.Graphics;

public class Rect {
	
	int x;
	int y;
	int h;
	int w;
	
	public Rect(int x, int y, int h, int w) {
		this.x = x;
		this.y = y;
		this.h = h;
		this.w = w;
	}
	
	public void draw(Graphics g){
		// Draws a rectangle with (x,y) in the center 
		g.drawRect(x, y, w, h);
		
	}
	
	
	/*
	 * Moving Methods
	 */
	
	public void moveUpBy(int dy){y -= dy;}
	public void moveDownBy(int dy){y += dy;}
	public void moveRightBy(int dx){x += dx;}
	public void moveLeftBy(int dx){x -= dx;}
	
	/*
	 * Collision Methods
	 */
	
	public boolean hasCollidedWIth(Rect r){
		return  (x+w >= r.x) &&      // Left
				(r.x + r.w >= x) &&  // Right
				(r.y + r.h >= y) &&  // Top
				(y+h >= r.y);        // Bottom
	}
}
	
