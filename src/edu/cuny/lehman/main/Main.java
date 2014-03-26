package edu.cuny.lehman.main;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class Main extends Applet implements KeyListener, Runnable
{
	boolean[] isPressed = new boolean[256];
	Thread thread;

	//Image img = Toolkit.getDefaultToolkit().getImage("thief_dn_0.png");

	boolean upPressed = false;
	boolean dnPressed = false;
	boolean ltPressed = false;
	boolean rtPressed = false;

	final int _UP = KeyEvent.VK_UP;
	final int _DN = KeyEvent.VK_DOWN;
	final int _LT = KeyEvent.VK_LEFT;
	final int _RT = KeyEvent.VK_RIGHT;
	final int _A = KeyEvent.VK_A;
	final int _B = KeyEvent.VK_B;
	final int _C = KeyEvent.VK_C;
	final int _D = KeyEvent.VK_D;
	final int _E = KeyEvent.VK_E;
	final int _F = KeyEvent.VK_F;
	final int _G = KeyEvent.VK_G;
	final int _H = KeyEvent.VK_H;
	final int _I = KeyEvent.VK_I;
	final int _J = KeyEvent.VK_J;
	final int _K = KeyEvent.VK_K;
	final int _L = KeyEvent.VK_L;
	final int _0 = KeyEvent.VK_0;
	final int _1 = KeyEvent.VK_1;
	final int _2 = KeyEvent.VK_2;
	final int _3 = KeyEvent.VK_3;
	final int _4 = KeyEvent.VK_4;
	final int _5 = KeyEvent.VK_5;
	final int _6 = KeyEvent.VK_6;
	final int _7 = KeyEvent.VK_7;


	int x = 0;
	int y = 0;


	//load the image
	//ImageLayer background = new ImageLayer("combo.gif");
	SpriteAnimated thief = new SpriteAnimated(100, 100, "thief", 3, 4);

	Image offScreen;
	Graphics offScreen_g;

	//Thief thief;
	//Initialize the applet
	public void init()
	{
		requestFocus();
		offScreen = createImage(1000, 700);
		offScreen_g = offScreen.getGraphics();
		requestFocus ();
		addKeyListener(this);

		thread = new Thread(this);
		thread.start();

	}

	//Component initialization
	public void run()
	{
		while (true)

		{

			if(isPressed[KeyEvent.VK_UP])   thief.moveUpBy(4); 
			if(isPressed[KeyEvent.VK_DOWN]) thief.moveDownBy(4);
			if(isPressed[KeyEvent.VK_LEFT])  thief.moveLeftBy(4);
			if(isPressed[KeyEvent.VK_RIGHT]) thief.moveRightBy(4);

			repaint();

			try
			{

				Thread.sleep(20);
			}

			catch(Exception x) {};

		}

	}

	public void paint(Graphics g)

	{
		//		background.draw(g);
		thief.draw(g);
		//g.drawRect(100, 100, 50, 50);
		//g.drawImage(img, 100, 100, null);

	}

	public void update(Graphics g)
	{

		offScreen_g.clearRect(0,0,1000,700);  //to clear the screen - no need if the background covers the whole screen.
		paint(offScreen_g);
		g.drawImage(offScreen, 0,0,null);
	}


	public void keyPressed(KeyEvent e)
	{
		isPressed[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e)
	{
		isPressed[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {  }


}


