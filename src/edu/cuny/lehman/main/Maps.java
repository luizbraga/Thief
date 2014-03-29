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
	
	public Maps(String filename) throws IOException
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
					map[x][y] = "A";
					walkable[x][y] = false;
				}
				if(ch == 'B')
				{
					map[x][y] = "B";
					walkable[x][y] = false;
				}
				if(ch == 'C')
				{
					map[x][y] = "C";
					walkable[x][y] = true;
				}
				if(ch == 'D')
				{
					map[x][y] = "D";
					walkable[x][y] = true;
				}
				if(ch == 'T')
				{
					map[x][y] = "T";
					walkable[x][y] = true;
				}
				if(ch == '0')
				{
					map[x][y] = "0";
					walkable[x][y] = true;
				}
				if(ch == '1')
				{
					map[x][y] = "1";
					walkable[x][y] = true;
				}
				if(ch == ' ')
				{
					walkable[x][y] = true;
				}
				if(ch == 'E')
				{
					map[x][y] = "E";
					walkable[x][y] = false;
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
