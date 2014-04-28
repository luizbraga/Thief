package edu.cuny.lehman.main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Maps 
{
	String[][] map;
	boolean[][] walkable;
	ArrayList lines = new ArrayList();
	int width = 0;
	int height = 0;
	int numberOfGuards = 0;
	int room = 0;
	String currentLevel;
	
	public Maps(String currentLevel, int room) throws IOException
	{
		this.currentLevel = currentLevel;
		this.room = room; 
		String filename = "../Levels/" + currentLevel + "/" + room + ".txt"; 
		loadFile(filename);
		
	}
	public void loadFile(String filename) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		while(true)
		{
			String line = reader.readLine();
			
			if(line == null)
			{
				reader.close();
				break;
			}
			
			if(!line.startsWith("#"))
			{
				lines.add(line);
				width = Math.max(width, line.length());
			}
		}
		
		height = lines.size();
		map = new String[width][height];
		walkable = new boolean[width][height];
		
		for(int y=0; y<height; y++)
		{
			String line = (String) lines.get(y);
			for(int x=0; x<line.length(); x++)
			{
				char ch = line.charAt(x);
				if(ch == 'A')
				{
					map[x][y] = "Wall";
					walkable[x][y] = false;
				}
				else if(ch == 'B')
				{
					map[x][y] = "Wall";
					walkable[x][y] = false;
				}
				else if(ch == 'Z')
				{
					map[x][y] = "Z";
					walkable[x][y] = true;
					numberOfGuards++;
				}
				else if(ch == 'T')
				{
					map[x][y] = "T";
					walkable[x][y] = true;
				}
				else if(ch == '0')
				{
					map[x][y] = "0";
					walkable[x][y] = true;
				}
				else if(ch == '1')
				{
					map[x][y] = "1";
					walkable[x][y] = true;
				}
				else if(ch == '2')
				{
					map[x][y] = "2";
					walkable[x][y] = true;
				}
				else if(ch == 'E')
				{
					map[x][y] = "E";
					walkable[x][y] = false;
				}
				else
				{
					walkable[x][y] = true;
				}
			}
		}
		reader.close();
	}
	
	public String[][] getMap()
	{
		return map;
	}
}
