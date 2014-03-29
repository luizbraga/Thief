package edu.cuny.lehman.main;

import java.awt.Graphics;

public class Treasure extends Sprite{
	
	Animation[] animation;
	String[] pose = {"up", "dn", "rg", "lt"};

	boolean opened = false;
	int count = 0;

	public Treasure(int x, int y, String name, int count, int size, int dir) {
		super(x, y, 32, 32);
		this.dir = dir;
		
		animation = new Animation[size];
		for(int i = 0; i < animation.length; i++)
	          animation[i] = new Animation(name+"_"+pose[i], count);

	}

	public void open(){
		if(opened == false){
			opened = true;
		}
	}

	@Override
	public void draw(Graphics g) {
		
		if(opened){
			if(count == 3){
				g.drawImage(animation[dir].staticImage(3),x, y, null);
			}else{
				count++;
				g.drawImage(animation[dir].currentImage(),x, y, null);
			}
		}
		else {
			g.drawImage(animation[dir].staticImage(0),x, y, null);
		}
		
		rect.draw(g);
	}
	
	


}