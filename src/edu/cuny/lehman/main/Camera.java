package edu.cuny.lehman.main;

public class Camera 
{
	static int x, y;
	
	public static void set(int x, int y)
	{
		Camera.x = x;
		Camera.y = y;
	}
	
	public static void moveLeftBy(int dx)
	{
		x -= dx;
	}
	
	public static void moveRightBy(int dx)
	{
		x += dx;
	}
	
	public static void moveUpBy(int dy)
	{
		y -= dy;
	}
	
	public static void moveDownBy(int dy)
	{
		y += dy;
	}
}
