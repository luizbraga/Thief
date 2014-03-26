package edu.cuny.lehman.main;

import java.awt.*;

public class SpriteAnimated extends Sprite
{
   Animation[] animation;

   String[] pose = {"up", "dn", "rg", "lt"};


   public SpriteAnimated(int x, int y, String name, int count, int size)
   {
       super(x, y, 0, 0);

       animation = new Animation[size];


       for(int i = 0; i < animation.length; i++)
          animation[i] = new Animation(name+"_"+pose[i], count);

   }

   public void draw(Graphics g)
   {
      if(moving)
      {
         g.drawImage(animation[dir].currentImage(),x, y, null);
      }
      else
      {
         g.drawImage(animation[dir].staticImage(),x, y, null);
      }

      moving = false;

   }



}