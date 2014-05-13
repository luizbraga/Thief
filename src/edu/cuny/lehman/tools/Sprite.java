package edu.cuny.lehman.tools;

import java.awt.*;


public abstract class Sprite 
{
	public int x;
	public int y;
	protected int w;
	protected int h;
	
	public int dir;
	protected Animation[] animation;
	protected boolean moving = false;
	public Rect rect;
	
	private final static int UP = 0;
	private final static int DN = 1;
	private final static int RT = 2;
	private final static int LT = 3;
	
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
	
	public void moveTo(int nx, int ny)
	{
		x += nx;
		rect.x = x;
		y += ny;
		rect.x = y;
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
