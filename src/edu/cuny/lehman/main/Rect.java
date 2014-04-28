package edu.cuny.lehman.main;
import java.awt.Graphics;


public class Rect 
{
int x, y, w, h;
	
	
	public Rect(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void moveBy(int dx, int dy)
	{
		x += dx;
		y += dy;
	}
	
	public void moveUpBy(int dy)
	{
		y -= dy;
	}
	public void moveDownBy(int dy)
	{
		y += dy;
	}
	public void moveLeftBy(int dx)
	{
		x -= dx;
	}
	public void moveRightBy(int dx)
	{
		x += dx;
	}
	
	public boolean hasCollidedWith(Rect r)
	{
		return ((  x +   w >= r.x)  	&& 
				(r.x + r.w >=   x) 		&&
				(  y +   h >= r.y) 		&& 
				(r.y + r.h >=   y));
	}
	public boolean contains(int mx, int my)
	{
		return (x+h <= mx && x > mx && y+w <= my && y > my);
	}
	
	public void draw(Graphics g)
	{
		g.drawRect(x + Origin.x, y + Origin.y, w, h);
	}
}
