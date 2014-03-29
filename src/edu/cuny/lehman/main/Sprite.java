package edu.cuny.lehman.main;

import java.awt.*;


public abstract class Sprite 
{
	int x, y, w, h;
	
	int dir;
	Animation[] animation;
	boolean moving = false;
	Rect rect;
	
	final static int UP = 0;
	final static int DN = 1;
	final static int RT = 2;
	final static int LT = 3;
	
	public Sprite(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		rect = new Rect(x, y, w, h);
	}

	public void moveUpBy(int dy)
	{
		y -= dy;
		dir = UP;
		rect.moveUpBy(dy);
		moving = true;
	}
	public void moveDownBy(int dy)
	{
		y += dy;
		dir = DN;
		rect.moveDownBy(dy);
		moving = true;
	}
	public void moveLeftBy(int dx)
	{
		x -= dx;
		dir = LT;
		rect.moveLeftBy(dx);
		moving = true;
	}
	public void moveRightBy(int dx)
	{
		x += dx;
		dir = RT;
		rect.moveRightBy(dx);
		moving = true;
	}
	
	
	public boolean hasCollidedWith(Rect r)
	{
		return ((  x +   w >= r.x)  	&& 
				(r.x + r.w >=   x) 		&&
				(  y +   h >= r.y) 		&& 
				(r.y + r.h >=   y));
	}
	
	public abstract void draw(Graphics g);
}
