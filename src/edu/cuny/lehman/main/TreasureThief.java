package edu.cuny.lehman.main;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.cuny.lehman.elements.Guards;
import edu.cuny.lehman.elements.Thief;
import edu.cuny.lehman.elements.Treasure;
import edu.cuny.lehman.elements.Walls;
import edu.cuny.lehman.tools.ImageLayer;
import edu.cuny.lehman.tools.Maps;
import edu.cuny.lehman.tools.Rect;
import edu.cuny.lehman.tools.pathFinder;


public class TreasureThief extends Applet implements KeyListener, Runnable
{
	
	Thread thread;
	boolean firstTime = true;

	boolean upPressed = false;
	boolean dnPressed = false;
	boolean ltPressed = false;
	boolean rtPressed = false;
	boolean rGPressed = false;

	final int _UP = KeyEvent.VK_UP;
	final int _DN = KeyEvent.VK_DOWN;
	final int _LT = KeyEvent.VK_LEFT;
	final int _RT = KeyEvent.VK_RIGHT;
	
	final int _E = KeyEvent.VK_E;
	final int _G = KeyEvent.VK_G;


	Image offScreen;
	Graphics offScreen_g;

	//load the image
	ImageLayer background = new ImageLayer("../images/background_3.jpg", 0, 0, 1000, 700);

	// creating characters.. 
	Thief thief;
	int first_X_Thief;
	int first_Y_Thief;

	Guards[] guards;
	ArrayList<Treasure> chests = new ArrayList<Treasure>();
	ArrayList<Walls> walls;


	Maps map;
	int[] guardStartX, guardStartY;
	int numberOfGuards;

	int door;
	int doorEnteredX;
	int doorEnteredY;
	String level;

	//create Menu to select Level
	JOptionPane jp = new JOptionPane();
	JPanel panel = new JPanel();   

	// pause menu
	JOptionPane jp1 = new JOptionPane();

	// Sound variable set up
	AudioClip clip;


	//exit door
	Rect exit = new Rect(320000, 320000, 32, 32);
	Rect activator = new Rect(32000, 320000, 32, 32);
	boolean doorIsOpen = false;

	//others
	boolean gameover;
	private boolean ePressed;

	public void init()
	{
		initStart();
	}

	//Initialize the applet
	public void initStart()
	{	
		preGameSetUp();
		gameover = false;
		numberOfGuards = 0;
		door = 0;
		boolean firstTimeStatingThief = true;
		resetKeys();
		
		//thief setup
		thief = new Thief("thief", 0, 0, 32, 32, 3, 4);
		ReloadMap("0");


		if(firstTimeStatingThief){
			//initial thief location
			thief = null;
			thief = new Thief("thief", first_X_Thief*32, (first_Y_Thief)*32, 32, 32, 3, 4);
			firstTimeStatingThief = false;
		}

		setUpGuards();

		requestFocus();
		// size of the screen
		setSize(1000, 700);
		offScreen = createImage(1000, 700);
		offScreen_g = offScreen.getGraphics();

		requestFocus ();
		addKeyListener(this);

		if(firstTime)
		{
			thread = new Thread(this);
			thread.start();
			firstTime = false;
		}
		//setup Sound files
		clip = getAudioClip(getCodeBase(), "../Sound/music.midi");
		clip.loop();

		// to stop clip use -- clip.stop()



	}

	private void resetKeys() {
		upPressed = false;
		dnPressed = false;
		rtPressed = false;
		ltPressed = false;
	}

	//Component initialization
	public void run()
	{

		while (true)

		{
			if(!gameover)
			{
				checkUserInput();

				moveOtherObjects();

				checkWhereYouAreOnMap();

				repaint();
				try
				{
					Thread.sleep(15);
				}

				catch(Exception x) {};
			}
			else
				pauseMenu("Main Menu");
		}

	}

	public void paint(Graphics g)

	{
		background.draw(g);
		thief.draw(g);

		for(Treasure chest: chests)
			chest.draw(g);

		for(Guards guard: guards)
			guard.draw(g);

		for(Walls wall: walls)
			wall.draw(g);

		g.setColor(Color.GREEN);
		exit.draw(g);
		activator.draw(g);
		g.setColor(Color.black);
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

	}


	public void keyReleased(int code)
	{
		if(code == _UP)  upPressed = false;
		if(code == _DN)  dnPressed = false;
		if(code == _LT)  ltPressed = false;
		if(code == _RT)  rtPressed = false;
		if(code == _G)   rGPressed = true;
		if(code == _E)   ePressed  = true;
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
		int exitIsHere = 0;
		int activatorIsHere = 0;
		walls = new ArrayList<Walls>();

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
				else if(map.map[i][j] == "T")
				{
					first_X_Thief = i;
					first_Y_Thief = j;
				}
				else if(map.map[i][j] == "Q" && doorIsOpen == false)
				{
					exit.set(i*32, j*32);
					exitIsHere = 1;
				}
				else if(map.map[i][j] == "R")
				{
					activator.set(i*32, j*32);
					activatorIsHere = 1;
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

		if(exitIsHere == 0)
		{
			exit.set(32000, 32000);
		}

		if(activatorIsHere == 0)
		{
			activator.set(32000, 32000);
		}

		if(doorEnteredX*32 >= 992)
			thief.set((doorEnteredX-1)*32, doorEnteredY*32);
		else if(doorEnteredX*32 <= 0)
			thief.set((doorEnteredX+1)*32, doorEnteredY*32);
		else if(doorEnteredY*32 >= 670)
			thief.set(doorEnteredX*32, (doorEnteredY-1)*32);
		else if(doorEnteredY*32 <= 0)
			thief.set(doorEnteredX*32, (doorEnteredY+1)*32);
		else
			thief.set(doorEnteredX*32, doorEnteredY*32);
	}

	public void checkUserInput()
	{
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
						Treasure collected = chests.get(i);
						thief.items.add(new Treasure(collected.id, collected.x, 
								collected.y, "chest", 4, 4, 1, true));

					}
				}

			}
			ePressed = false;
		}

		// to pause the game
		if(rGPressed == true)
		{
			pauseMenu("Pause");
		}


		/*
	   // testing the exit door
	   if(thief.rect.hasCollidedWith(activator) && rGPressed == true)
	   {
		   exit.set(32000, 32000);
		   doorIsOpen = true;
	   }
		 */
		checkCollisionWithWalls();
		checkCollisionWithGuards();
	}

	public void checkWhereYouAreOnMap()
	{
		String s = map.map[thief.x/32][thief.y/32];

		if(s == "0" || s == "1" || s == "2")
		{
			map = null;
			guards = null;
			walls.clear();
			chests.clear();
			numberOfGuards = 0;

			ReloadMap(s);
			// set up the guards
			setUpGuards();

			door = Integer.parseInt(s);

		}

	}
	public void moveOtherObjects()
	{
		for(int i=0; i<guards.length; i++)
		{
			if(!guards[i].chasing)
			{
				guards[i].patrol = true;
				guards[i].patrol(thief.x, thief.y);
			}
			else
				guards[i].chase(thief.x, thief.y);
		}
	}

	public void setUpGuards()
	{
		guards = new Guards[map.numberOfGuards];

		for(int i=0; i<guards.length; i++)
		{
			guards[i] = new Guards("guard", guardStartX[i], guardStartY[i], 32, 32, 3, 4);

			guards[i].path = pathFinder.findPath(guardStartX[i]/32, 
					guardStartY[i]/32,
					guardStartX[(i+1)%guards.length]/32, 
					guardStartY[(i+1)%guards.length]/32, 
					map.walkable);

			guards[i].endx = guardStartX[(i+1)%guards.length]/32;
			guards[i].endy = guardStartY[(i+1)%guards.length]/32;
			guards[i].walkable = map.walkable;
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
		for(int i=0; i<guards.length; i++)
		{
			if(guards[i].rect.hasCollidedWith(thief.rect))
			{
				//GameOver
				gameover = true;
			}
		}
	}

	public void preGameSetUp()
	{
		JComboBox cbox = new JComboBox();
		cbox.addItem("1");
		cbox.addItem("2");
		cbox.addItem("3");

		jp.showOptionDialog(null, cbox,"Select Level",
				JOptionPane.CLOSED_OPTION,  
				JOptionPane.QUESTION_MESSAGE, null, null, null);


		level = "Level" + cbox.getSelectedItem();
		gameover = false;
	}

	public void pauseMenu(String title)
	{
		Object[] options1 = {"Play Again", "Show Score", "Exit"};

		int m  = jp1.showOptionDialog(null,
				title,
				"Menu",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				options1,
				null);

		rGPressed = false;

		if(m == 0)
		{
			makeAllNull();
			initStart();

		}

		if(m == 2)
		{
			System.exit(0);
		}

		// m is 0, 1 or 2
		// use m to decide what to do next

	}

	public void makeAllNull()
	{
		guards = null;
		map = null;
		guardStartX = null;
		guardStartY = null;
	}
}


