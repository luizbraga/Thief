package edu.cuny.lehman.main;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class Main extends Applet implements KeyListener, Runnable
{
	boolean[] pressed = new boolean[256];
	Thread thread;

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
	ImageLayer background = new ImageLayer("combo.gif");

	Image offScreen;
	Graphics offScreen_g;

	Thief thief;
	//Initialize the applet
	public void init()
	{
		requestFocus();
		offScreen = createImage(1000, 700);
		offScreen_g = offScreen.getGraphics();
		requestFocus ();
		addKeyListener(this);

		thread= new Thread(this);
		thread.start();

	}

	//Component initialization
	public void run()
	{
		while (true)

		{

			if(pressed[KeyEvent.VK_UP])   thief.moveUpBy(10);
			if(pressed[KeyEvent.VK_DOWN]) thief.moveDownBy(10);
			if(pressed[KeyEvent.VK_LEFT])  background.moveLeftBy(5);
			if(pressed[KeyEvent.VK_RIGHT]) background.moveRightBy(5);

			repaint();

			try
			{

				Thread.sleep(15);
			}

			catch(Exception x) {};

		}

	}

	public void Paint(Graphics g)

	{
		background.draw(g);
		thief.draw(g);

	}

	public void update(Graphics g)
	{

		offScreen_g.clearRect(0,0,1000,700);  //to clear the screen - no need if the background covers the whole screen.
		paint(offScreen_g);
		g.drawImage(offScreen, 0,0,null);
	}


	public void keyPressed(int code)
	{
		if(code == _UP)  upPressed = true;
		if(code == _DN)  dnPressed = true;
		if(code == _LT)  ltPressed = true;
		if(code == _RT)  rtPressed = true;
	}


	public void keyReleased(int code)
	{
		if(code == _UP)  upPressed = false;
		if(code == _DN)  dnPressed = false;
		if(code == _LT)  ltPressed = false;
		if(code == _RT)  rtPressed = false;
	}


	public final void keyPressed(KeyEvent e)
	{
		keyPressed(e.getKeyCode());
	}


	public final void keyReleased(KeyEvent e)
	{
		keyReleased(e.getKeyCode());
	}

	public final void keyTyped(KeyEvent e) {   }


}


