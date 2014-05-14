package edu.cuny.lehman.elements;

import java.awt.Graphics;

import edu.cuny.lehman.tools.Animation;
import edu.cuny.lehman.tools.Sprite;

public class DoorActivator extends Sprite
{
	Animation animation;
	boolean isPressed = false;
	Door[] door;
	
	public DoorActivator(int x, int y, int w, int h, Door[] door) {
		super(x, y, w, h);
		this.door = door;
		
		animation = new Animation("activator",2);
		
	}

	@Override
	public void draw(Graphics g) {
		if(isPressed){
			g.drawImage(animation.staticImage(1), x, y, null);
			//rect.draw(g);
		}else{
			g.drawImage(animation.staticImage(0), x, y, null);
			//rect.draw(g);
		}
	}

	public void set(int x, int y) {
		super.x = x;
		super.y = y;
		rect.x = x;
		rect.y = y;
	}
	
	public boolean isPressed(){
		return isPressed;
	}

	public void setActive() {
		isPressed = true;
	}
	
	public void setDisabled(){
		isPressed = false;
	}

} 