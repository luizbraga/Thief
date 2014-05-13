package edu.cuny.lehman.elements;

import java.awt.Graphics;
import java.awt.Image;

import edu.cuny.lehman.tools.Animation;
import edu.cuny.lehman.tools.Sprite;
import edu.cuny.lehman.tools.pathFinder;

public class Guards extends Sprite{

	Image image;
	//Animation[] animation;
	String[] pose = {"up", "dn", "rt", "lt"};
	public boolean patrol = false;
	public boolean chasing = false;
	
	public int[][] path;
	public boolean[][] walkable;
	private int step = 0;
	private int startx;
	private int starty;
	public int endx;
	public int endy;
	
	
	public Guards(String name, int x, int y, int w, int h, int count, int size)
	{
		super(x, y, w, h);
		animation = new Animation[size];
		for(int i = 0; i < animation.length; i++)
		{
			animation[i] = new Animation(name+"_"+pose[i], count);
		}
		startx = x/32;
		starty = y/32;
	}

  public void patrol(int tx, int ty)
  {
	  if(patrol && step > -1)
	  {
		  if(step >= path.length)
		  {
			  endx = startx;
			  endy = starty;
			  startx = x/32;
			  starty = y/32;
			  path = pathFinder.findPath(startx, starty, endx, endy, walkable);
			  step= -100;
		  }
		  else
		  {
		  if(x < path[step][0]*32)
			  this.moveRightBy(1);
		  else if(x > path[step][0]*32)
 			 moveLeftBy(1);
 		  else if(y < path[step][1]*32)
 			 moveDownBy(1);
 		  else if(y > path[step][1]*32)
 			 moveUpBy(1);
		  
		  if(x ==  path[step][0]*32 && y ==  path[step][1]*32)
			  step++;
		  
		  see(tx, ty);
		  }
	  }
	  else if(step == -80)
	  {
		  step++;
		  this.moveDownBy(0);
	  }
	  else if(step == -60)
	  {
		  step++;
		  this.moveLeftBy(0);
	  }
	  else if(step == -40)
	  {
		  step++;
		  this.moveUpBy(0);
	  }
	  else if(step == -20)
	  {
		  step++;
		  this.moveRightBy(0);
	  }
	  else
	  {
		  step++;
	  }
  }
  
  public void see(int tx, int ty)
  {
	  int xt = x - tx;
	  int yt = y - ty;
	  int currentX = x/32;
	  int currentY = y/32;
	  
	  if(dir == 0)
	  {
		  if(xt*xt + yt*yt < 100*100 && ty < y	&&
			walkable[currentX][currentY -1]		&&
			walkable[currentX-1][currentY -1]   &&
			walkable[currentX+1][currentY -1])
		  {
			  
			  chasing = true;
			  patrol = false;
		  }
	  }
	  if(dir == 1)
	  {
		  if(xt*xt + yt*yt < 100*100 && ty > y	&&
			walkable[currentX][currentY +1]		&&
			walkable[currentX-1][currentY +1]   &&
			walkable[currentX+1][currentY +1])
		  {
			  chasing = true;
			  patrol = false;
		  }
	  }
	  if(dir == 2)
	  {
		  if(xt*xt + yt*yt < 100*100 && tx > x  &&
			walkable[currentX+1][currentY]		&&
			walkable[currentX+1][currentY-1]    &&
			walkable[currentX+1][currentY+1])
		  {
			  chasing = true;
			  patrol = false;
		  }
	  }
	  if(dir == 3)
	  {
		  if(xt*xt + yt*yt < 100*100 && tx < x  &&
			walkable[currentX-1][currentY]		&&
			walkable[currentX-1][currentY-1]    &&
			walkable[currentX-1][currentY+1])
		  {
			  chasing = true;
			  patrol = false;
		  }
	  }
  }
   public void chase(int tx, int ty)
   {
	   if(chasing)
	   {
		   if((x-tx)*(x-tx) + (y-ty)*(y-ty) < 128*128)
		   {
			   if(tx > x && walkable[(x/32)+1][y/32])
				   moveRightBy(2);
			   if(tx < x && walkable[(x/32)-1][y/32])
				   moveLeftBy(2);
			   if(ty > y && walkable[x/32][(y/32)+1])
				   moveDownBy(2);
			   if(ty < y && walkable[x/32][(y/32)-1])
				   moveUpBy(2);
		   }
		   
		   else
		   {
			   chasing = false;
			   patrol = true;
			   path = null;
			   
			   path = pathFinder.findPath(x/32, y/32, endx, endy, walkable);
			   step = -100;
		   }
	   }
   }

   public void adjustPath()
   {
	   path = null;
	   path = pathFinder.findPath(x/32, y/32, endx, endy, walkable); 
	   step = 0;
   }
   
   public void catchThief()
   {
	   // this should restart level
   }

	public void draw(Graphics g) 
	{
		if(moving)
		{
			g.drawImage(animation[dir].currentImage(), x+3, y, w-5, h, null);
		}
		else
		{
			g.drawImage(animation[dir].staticImage(0), x+3, y, w-5, h, null);
	
		}
		
		//rect.draw(g);
		moving = false;
	}
} 