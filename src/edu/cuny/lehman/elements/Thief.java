package edu.cuny.lehman.elements;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import edu.cuny.lehman.tools.Animation;
import edu.cuny.lehman.tools.Sprite;

public class Thief extends Sprite{
	
	Image image;
	Animation[] animation;
	String[] pose = {"up", "dn", "rt", "lt"};
	
	public ArrayList<Treasure> items = new ArrayList<Treasure>();
	
	public Thief(String name, int x, int y, int w, int h, int count, int size)
	{
		super(x, y, w, h);
		animation = new Animation[size];
		for(int i = 0; i < animation.length; i++)
		{
			animation[i] = new Animation(name+"_"+pose[i], count);
		}
	}
	public void set(int x, int y)
	{
		this.x = x;
		this.y = y;
		rect.x = x;
		rect.y = y;
	}
	public void openTreasureBox()
	{
	
	}
	

	public void moveTreasure()
	{

	}
	
	public void draw(Graphics g)
	{
		if(moving)
		{
			g.drawImage(animation[dir].currentImage(), x+3, y, w-6, h, null);
		}
		else
		{
			g.drawImage(animation[dir].staticImage(0), x+3, y, w-6, h, null);
	
		}
		//rect.draw(g);
		//g.drawRect(x + w/2, y, w, h);
		moving = false;
	}


   
} 