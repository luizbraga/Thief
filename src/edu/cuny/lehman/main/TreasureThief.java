package edu.cuny.lehman.main;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TreasureThief extends Applet implements KeyListener, Runnable
{
	//boolean[] pressed = new boolean[256];
	Thread thread;

	boolean upPressed = false;
	boolean dnPressed = false;
	boolean ltPressed = false;
	boolean rtPressed = false;
	boolean ePressed  = false;
	boolean pPressed  = false;

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
	final int _P = KeyEvent.VK_P;
	final int _0 = KeyEvent.VK_0;
	final int _1 = KeyEvent.VK_1;
	final int _2 = KeyEvent.VK_2;
	final int _3 = KeyEvent.VK_3;
	final int _4 = KeyEvent.VK_4;
	final int _5 = KeyEvent.VK_5;
	final int _6 = KeyEvent.VK_6;
	final int _7 = KeyEvent.VK_7;

	private boolean isOver = false;
	private boolean isPaused = false;

	Image offScreen;
	Graphics offScreen_g;

	//load the image
	ImageLayer background = new ImageLayer("../images/background_3.jpg", 0, 0, 1000, 700);

	// creating characters.. 
	Thief thief;// = new Thief("thief", 64, 480, 32, 32, 3, 4);
	Guards[] guard;
	ArrayList<Treasure> chests = new ArrayList<Treasure>();

	ArrayList<Walls> walls = new ArrayList<Walls>();


	Maps map;
	int[] guardStartX, guardStartY;
	int numberOfGuards = 0;

	int door = 0;
	int doorEnteredX;
	int doorEnteredY;
	String level;

	//create Menu to select Level
	JOptionPane jp = new JOptionPane();
	JPanel panel = new JPanel();   
	JComboBox cbox = new JComboBox();

	//Initialize the applet
	public void init()
	{
		preGameSetUp();

		thief = new Thief("thief", 0, 0, 32, 32, 3, 4);
		ReloadMap("0");

		//initial thief location
		doorEnteredX = 3;
		doorEnteredY = 10;
		thief = null;
		thief = new Thief("thief", doorEnteredX*32, (doorEnteredY+1)*32, 32, 32, 3, 4);

		setUpGuards();

		requestFocus();
		// size of the screen
		setSize(1000, 700);
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
		while (!isOver)
		{
			checkUserInput();
			
			inGameLoop();
			
			repaint();
			try
			{
				Thread.sleep(15);
			}catch(Exception x) {};

			
		}

	}

	private void inGameLoop() {
		if(!isPaused){
			moveOtherObjects();
			checkWhereYouAreOnMap();
		}
	}

	public void paint(Graphics g)

	{
		background.draw(g);
		thief.draw(g);


		for(Treasure chest: chests)
			chest.draw(g);

		for(int i=0; i<guard.length; i++)
			guard[i].draw(g);

		for(Walls wall: walls)
			wall.draw(g);

		if(isOver){
			g.drawString("GAME OVER!", 420, 75);
		}
		if(isPaused){
			g.drawString("Paused!", 420, 75);
		}
	}

	public void update(Graphics g)
	{
		//to clear the screen - no need if the background covers the whole screen.
		//offScreen_g.clearRect(0,0,1000,700);  

		paint(offScreen_g);
		g.drawImage(offScreen, 0,0,null);
	}

	public void keyPressed(int code)
	{
		if(code == _UP)  upPressed = true;
		if(code == _DN)  dnPressed = true;
		if(code == _LT)  ltPressed = true;
		if(code == _RT)  rtPressed = true;
		if(code == _E)   ePressed  = true;
		if(code == _P)   pPressed  = true;
	}


	public void keyReleased(int code)
	{
		if(code == _UP)  upPressed = false;
		if(code == _DN)  dnPressed = false;
		if(code == _LT)  ltPressed = false;
		if(code == _RT)  rtPressed = false;
		if(code == _E)   ePressed  = true;
		if(code == _P)   pPressed  = false;
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

	public void ReloadMap(String door)
	{
		chests.clear();
		int lastDoor = Integer.parseInt(door);
		try
		{
			map = new Maps(level, lastDoor);
		}
		catch (IOException ex) 
		{ 
			System.out.println(ex);
		}

		guardStartX = new int[map.numberOfGuards];
		guardStartY = new int[map.numberOfGuards];

		for(int i=0; i<map.width; i++)
		{
			for(int j=0; j<map.height; j++)
			{
				if(map.map[i][j] == "Wall")
				{

					walls.add(new Walls("../images/wall.png", i*32, j*32, 32, 32));
				}
				else if(map.map[i][j] == "Z")
				{
					guardStartX[numberOfGuards] = i*32;
					guardStartY[numberOfGuards] = j*32;
					numberOfGuards++;

				}
				else if(map.map[i][j] == "0" && this.door == 0)
				{
					doorEnteredX = i;
					doorEnteredY = j;
				}
				else if(map.map[i][j] == "1" && this.door == 1)
				{
					doorEnteredX = i;
					doorEnteredY = j;
				}
				else if(map.map[i][j] == "2" && this.door == 2)
				{
					doorEnteredX = i;
					doorEnteredY = j;
				}
				else if(map.map[i][j] == "E")
				{

					if(thief.items.size() != 0){
						for(int index=0;index < thief.items.size(); index++){

							if(thief.items.get(index).id == i+j){
								chests.add(thief.items.get(index));
								index = 0;
							}else{
								chests.add(new Treasure(i+j, i*32, j*32,"chest", 4, 4, 1));
							}
						}
					}else{
						chests.add(new Treasure(i+j, i*32, j*32,"chest", 4, 4, 1));
					}

				}
			}
		}

		if(doorEnteredX*32 >= 1000)
			thief.set((doorEnteredX-1)*32, doorEnteredY*32);
		else if(doorEnteredX*32 <= 0)
			thief.set((doorEnteredX+1)*32, doorEnteredY*32);
		else if(doorEnteredY*32 >= 700)
			thief.set(doorEnteredX*32, (doorEnteredY-1)*32);
		else if(doorEnteredY*32 <= 0)
			thief.set(doorEnteredX*32, (doorEnteredY+1)*32);
		else
			thief.set(doorEnteredX*32, doorEnteredY*32);
	}

	public void checkUserInput()
	{
		
		if(!isPaused){
			if(upPressed)
			{
				thief.moveUpBy(4);
			}
	
			else if(dnPressed)
			{
				thief.moveDownBy(4);
			}
			else if(ltPressed)
			{
				thief.moveLeftBy(4);
			}
			else if(rtPressed)
			{
				thief.moveRightBy(4);
			}
			else if(ePressed)
			{
				for(int i=0; i<chests.size(); i++){
					if(!chests.get(i).opened){
						if(thief.hasCollidedWith(chests.get(i).rect)){
							chests.get(i).open();
							thief.items.add(chests.get(i));
						}
					}
	
				}
				ePressed = false;
			}
		}
		
		if(pPressed){
			if(isPaused){
				isPaused = false;
			}else{
				isPaused = true;
			}
		}
		
		//checkCollisionWithChests();
		checkCollisionWithWalls();
		checkCollisionWithGuards();
	}

	private void checkCollisionWithChests() {
		for(Treasure chest: chests)
		{
			if(chest.rect.hasCollidedWith(thief.rect))
			{
				if(thief.dir == 0)
				{
					thief.y += 4;
					thief.rect.y += 4;
				}
				else if(thief.dir == 1)
				{
					thief.y -= 4;
					thief.rect.y -= 4;
				}
				else if(thief.dir == 2)
				{
					thief.x -= 4;
					thief.rect.x -= 4;
				}
				else if(thief.dir == 3)
				{
					thief.x += 4;
					thief.rect.x += 4;
				}
			}
		}
	}

	public void checkWhereYouAreOnMap()
	{
		String s = map.map[thief.x/32][thief.y/32];

		if(s == "0" || s == "1" || s == "2")
		{
			map = null;
			guard = null;
			walls.clear();
			numberOfGuards = 0;

			ReloadMap(s);
			// set up the guards
			setUpGuards();

			door = Integer.parseInt(s);

		}

	}
	public void moveOtherObjects()
	{
		for(int i=0; i<guard.length; i++)
		{
			if(!guard[i].chasing)
			{
				guard[i].patrol = true;
				guard[i].patrol(thief.x, thief.y);
			}
			else
				guard[i].chase(thief.x, thief.y);
		}
	}

	public void setUpGuards()
	{
		guard = new Guards[map.numberOfGuards];

		for(int i=0; i<guard.length; i++)
		{
			guard[i] = new Guards("guard", guardStartX[i], guardStartY[i], 32, 32, 3, 4);

			guard[i].path = pathFinder.findPath(guardStartX[i]/32, 
					guardStartY[i]/32,
					guardStartX[(i+1)%guard.length]/32, 
					guardStartY[(i+1)%guard.length]/32, 
					map.walkable);

			guard[i].endx = guardStartX[(i+1)%guard.length]/32;
			guard[i].endy = guardStartY[(i+1)%guard.length]/32;
			guard[i].walkable = map.walkable;
		}
	}

	public void checkCollisionWithWalls()
	{
		for(Walls wall: walls)
		{
			if(wall.rect.hasCollidedWith(thief.rect))
			{
				if(thief.dir == 0)
				{
					thief.y += 4;
					thief.rect.y += 4;
				}
				else if(thief.dir == 1)
				{
					thief.y -= 4;
					thief.rect.y -= 4;
				}
				else if(thief.dir == 2)
				{
					thief.x -= 4;
					thief.rect.x -= 4;
				}
				else if(thief.dir == 3)
				{
					thief.x += 4;
					thief.rect.x += 4;
				}
			}
		}
	}

	public void checkCollisionWithGuards()
	{
		for(int i=0; i<guard.length; i++)
		{
			if(guard[i].rect.hasCollidedWith(thief.rect))
			{
				isOver = true;
			}
		}
	}

	public void preGameSetUp()
	{
		cbox.addItem("1");
		cbox.addItem("2");
		cbox.addItem("3");

		jp.showOptionDialog(null, cbox,"Select Level",
				JOptionPane.OK_CANCEL_OPTION,  
				JOptionPane.QUESTION_MESSAGE, null, null, null);

		level = "Level" + cbox.getSelectedItem();
	}
}


