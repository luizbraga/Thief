package edu.cuny.lehman.main;

import java.awt.Graphics;

public class Thief extends Sprite {

	   Animation[] animation;

	   String[] pose = {"up", "dn", "rg", "lt"};


	   public Thief(int x, int y, String name, int count, int size)
	   {
	       super(x, y, 32, 32);
	       
	       animation = new Animation[size];
	       
	       for(int i = 0; i < animation.length; i++)
	          animation[i] = new Animation(name+"_"+pose[i], count);

	   }

	   public void draw(Graphics g)
	   {
	      if(moving)
	      {
	         g.drawImage(animation[dir].currentImage(),x, y,w,h, null);
	      }
	      else
	      {
	         g.drawImage(animation[dir].staticImage(0),x, y,w,h, null);
	      }
	      
	      rect.draw(g);
	      moving = false;
	   }

} 