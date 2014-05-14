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

import edu.cuny.lehman.elements.Door;
import edu.cuny.lehman.elements.DoorActivator;
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
	boolean nextLevel = false;

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
	int score;
	int door;
	int doorEnteredX;
	int doorEnteredY;
	int CurrentLevel;
	String level;

	//create Menu to select Level
	JOptionPane jp = new JOptionPane();
	JPanel panel = new JPanel();   

	// pause menu
	JOptionPane jp1 = new JOptionPane();

	// Sound variable set up
	AudioClip clip;


	//exit door
	Door[] exit = new Door[2];
	DoorActivator activator = new DoorActivator(32000, 32000, 32, 32, exit);
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
		if(!nextLevel)
		{
			preGameSetUp();
		}

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
		setSize(1200, 700);
		offScreen = createImage(1200, 700);
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
				
				checkIfLevelComplete();

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
		Font normal = g.getFont();
		g.setFont(normal);
		
		g.setColor(Color.black);
		background.draw(g);
		thief.draw(g);

		for(Treasure chest: chests)
			chest.draw(g);

		for(Guards guard: guards)
			guard.draw(g);

		for(Walls wall: walls)
			wall.draw(g);
		/*
		g.setColor(Color.GREEN);
		exit.draw(g);
		
		g.setColor(Color.black);
		*/
		for(Door doorExit : exit)
			doorExit.draw(g);
		
		activator.draw(g);
		// display score 
		Font big = new Font("SanSerif", Font.BOLD, 20);
		g.setFont(big);
		g.fillRect(1000, 0, 200, 700);
		g.setColor(Color.white);
		g.drawString("Score: " + score, 1010, 100);
		g.drawString("Level: " + CurrentLevel, 1010, 150);
		// instructions to play
		g.drawString("To move use ", 1010, 200);
		g.drawString("up, dn, lt, rt", 1010, 220);
		
		g.drawString("To Pause press G", 1010, 300);
		
		g.drawString("To Interact", 1010, 400);
		g.drawString("press E", 1010, 420);
		
//		g.drawString("To open chest", 1010, 450);
//		g.drawString("press E", 1010, 470);
		
		
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
		int countExit = 0;

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
					exit[countExit] = new Door(i*32, j*32, 32, 32);
					exitIsHere = 1;
					countExit++;
				}
				else if(map.map[i][j] == "R")
				{
					//activator = new DoorActivator(i*32, j*32, 32, 32, exit);
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
			for(Door doorExit: exit)
				doorExit.set(32000, 32000);
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
						score++;
					}
				}

			}
			
			if(thief.hasCollidedWith(activator.rect)){
				activator.setActive();
				doorIsOpen = true;
				for(Door doorExit : exit)
					doorExit.set(32000, 32000);
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
		checkCollisionWithDoors();
		checkCollisionWithWalls();
		checkCollisionWithGuards();
	}

	private void checkCollisionWithDoors() {
		for(Door doorExit : exit){
			if(doorExit.rect.hasCollidedWith(thief.rect))
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
	
	public void checkIfLevelComplete()
	{
		for(Door doorExit : exit)
			if(thief.rect.hasCollidedWith(doorExit.phaseRect) && activator.isPressed())
			{
				LevelComplete();
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
	
		CurrentLevel = cbox.getSelectedIndex() + 1;
		level = "Level" + CurrentLevel;
		gameover = false;
	}

	public void pauseMenu(String title)
	{
		Object[] options1 = {"Play Again", "Show Score", "Exit"};

		int m  = jp1.showOptionDialog(null,
				"Score:" + thief.items.size(),
				title,
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				options1,
				null);

		rGPressed = false;

		if(m == 0)
		{
			makeAllNull();
			nextLevel = false;
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
		chests = new ArrayList<Treasure>();
		doorIsOpen = false;
	}
	
	
	public void NextLevel()
	{
		CurrentLevel++;
		level = "Level" + CurrentLevel;
	}
	
	public void LevelComplete()
	{
		Object[] options1 = {"Play Again", "Next Level", "Exit"};

		int m  = jp1.showOptionDialog(null,
				"Number of Chest:" + thief.items.size(),
				"Level Complete ",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				options1,
				null);

		rGPressed = false;

		if(m == 0)
		{
			makeAllNull();
			nextLevel = false;
			initStart();
		}
		
		if(m == 1)
		{
			NextLevel();
			makeAllNull();
			nextLevel = true;
			initStart();
		}
		
		if(m == 2)
		{
			System.exit(0);
		}
	}
}


