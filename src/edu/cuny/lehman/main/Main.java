package edu.cuny.lehman.main;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends Applet implements KeyListener, Runnable
{
	boolean[] isPressed = new boolean[256];
	Thread thread;

	//Image img = Toolkit.getDefaultToolkit().getImage("thief_dn_0.png");

	boolean upPressed = false;
	boolean dnPressed = false;
	boolean ltPressed = false;
	boolean rtPressed = false;
	boolean ePressed  = false;

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
	ImageLayer background = new ImageLayer("../images/background_3.jpg", 0, 0, 1000, 700);


	Thief thief;
	Guards guard;

	Image offScreen;
	Graphics offScreen_g;

	ArrayList<Treasure> chests = new ArrayList<Treasure>();
	ArrayList<Rect> walls = new ArrayList<Rect>();


	Maps map;
	int startX, startY, endX, endY;
	int[][] path;
	int[] doors = {0, 1, 2};

	//Initialize the applet
	public void init()
	{
		ReloadMap(0);

		// set up the guards
		path = pathFinder.findPath(startX, startY, endX, endY, map.walkable);
		guard.endx = endX;
		guard.endy = endY;
		guard.walkable = map.walkable;
		guard.path = path;

		requestFocus();

		setSize(1000, 700);
		offScreen = createImage(1000, 700);
		offScreen_g = offScreen.getGraphics();

		addKeyListener(this);

		thread = new Thread(this);
		thread.start();

	}

	//Component initialization
	public void run()
	{
		while (true)
		{
			inGameLoop();
			repaint();
			try
			{
				Thread.sleep(20);
			}catch(Exception x) {};
		}

	}

	public void paint(Graphics g) {
		//		background.draw(g);
		background.draw(g);

		thief.draw(g);
		guard.draw(g);
		for(Treasure chest: chests){
			chest.draw(g);
		}
		
		for(Rect wall: walls){
			wall.draw(g);
		}

	}

	public void update(Graphics g) {
		//offScreen_g.clearRect(0,0,1000,700);  //to clear the screen - no need if the background covers the whole screen.
		paint(offScreen_g);
		g.drawImage(offScreen, 0,0,null);
	}

	public boolean wallCollisions()
	{
		// thief collides with anything on screen

		return (
				thief.x < 0 || thief.x + thief.w > 1000 ||
				thief.y < 0 || thief.y + thief.h > 700
				);
	}


	public void keyPressed(int code)
	{
		if(code == _UP)  upPressed = true;
		if(code == _DN)  dnPressed = true;
		if(code == _LT)  ltPressed = true;
		if(code == _RT)  rtPressed = true;
		if(code == _E)   ePressed = true;

	}


	public void keyReleased(int code)
	{
		if(code == _UP)  upPressed = false;
		if(code == _DN)  dnPressed = false;
		if(code == _LT)  ltPressed = false;
		if(code == _RT)  rtPressed = false;
		if(code == _E)   ePressed = true;
	}

	public final void keyPressed(KeyEvent e)
	{
		keyPressed(e.getKeyCode());
	}


	public final void keyReleased(KeyEvent e)
	{
		keyReleased(e.getKeyCode());
	}


	public void keyTyped(KeyEvent e) {  }

	public void inGameLoop(){

		checkUserInput();

		moveOtherObjects();

		checkWhereYouAreOnMap();
	}


	public void ReloadMap(int door)
	{
		try
		{
			map = new Maps("../maps/map"+door+".txt");
		}
		catch (IOException ex) 
		{ 
			System.out.println(ex);
		}


		for(int i=0; i<map.width; i++)
		{
			for(int j=0; j<map.height; j++)
			{
				if(map.map[i][j] == "A" || map.map[i][j] == "B")
				{
					Rect wall = new Rect(i*32, j*32, 32, 32);
					walls.add(wall);
				}
				else if(map.map[i][j] == "C")
				{
					startX = i;
					startY = j;
					guard = new Guards("../images/guard", i*32, j*32, 32, 32, 3, 4);
				}
				else if(map.map[i][j] == "D")
				{
					endX = i;
					endY = j;
				}
				else if(map.map[i][j] == "T")
				{
					thief = new Thief(i*32, j*32,"../images/thief", 3, 4);
				}
				else if(map.map[i][j] == "E")
				{
					chests.add(new Treasure(i*32, j*32,"../images/chest", 4, 4, 1));
				}
			}
		}
	}

	public void checkUserInput()
	{
		if(upPressed)
		{
			if(thief.y%32 != 0)
			{
				thief.moveUpBy(4);
			}
			else if(map.walkable[thief.x/32][thief.y/32 - 1])
			{
				thief.moveUpBy(4);
			}
		}

		if(dnPressed)
		{
			if(thief.y%32 != 0)
			{
				thief.moveDownBy(4);
			}
			else if(map.walkable[thief.x/32][thief.y/32 + 1])
			{
				thief.moveDownBy(4);
			}
		}
		if(ltPressed)
		{
			if(thief.x%32 != 0)
			{
				thief.moveLeftBy(4);
			}
			else if(map.walkable[thief.x/32 - 1][thief.y/32])
			{
				thief.moveLeftBy(4);
			}

		}
		if(rtPressed)
		{
			if(thief.x%32 != 0)
			{
				thief.moveRightBy(4);
			}
			else if(map.walkable[thief.x/32 + 1][thief.y/32])
			{
				thief.moveRightBy(4);
			}
		}

		if(ePressed)
		{
			for(int i=0; i<chests.size(); i++){
				if(!chests.get(i).opened){
					if(thief.hasCollidedWith(chests.get(i).rect)){
						chests.get(i).open();
					}
				}
				
			}
			ePressed = false;
			
		}
	}

	public void checkWhereYouAreOnMap()
	{
		String s = map.map[thief.x/32][thief.y/32];
		if(s == "0" || s == "1")
		{
			map = null;
			guard = null;
			walls.clear();
			chests.clear();
			ReloadMap(Integer.parseInt(s));
			// set up the guards
			path = pathFinder.findPath(startX, startY, endX, endY, map.walkable);
			guard.endx = endX;
			guard.endy = endY;
			guard.walkable = map.walkable;
			guard.path = path;
		}
	}
	public void moveOtherObjects()
	{
		if(!guard.chasing)
		{
			guard.patrol = true;
			guard.patrol(thief.x, thief.y);
		}
		else
			guard.chase(thief.x, thief.y);
	}


}


