package edu.cuny.lehman.main;

public class pathFinder 
{
	public static int[][] findPath(int startX, int startY, int endX, int endY, boolean[][] walkable)
	{
		int length = walkable[0].length;
	    int width = walkable.length;

	    /* now we initialize an int array and fill it with -1 */
	    int[][] distance = new int[width][length];
	    for(int i=0; i < distance.length; i++) {
	        for(int j=0; j < distance[i].length; j++) {
	            distance[i][j] = -1;
	        }
	    }
	    
	    distance[endX][endY] = 0;
	    int steps = 1;
	    
	    while(distance[startX][startY] == -1)
	    {
	    	for(int i=0; i < distance.length; i++) 
	    	{
	            for(int j=0; j < distance[i].length; j++) 
	            {
	            	if(distance[i][j] == steps - 1) 
	            	{
	                    /* check if the Tile right from the current Tile is valid */
	                    if(i < width - 1) 
	                    {
	                        /* check if that Tile is not marked and if it is walkable*/
	                        if(distance[i+1][j] == -1 && walkable[i+1][j]) 
	                        {
	                            /* not marked & walkable, so we can mark it */
	                            distance[i+1][j] = steps;
	                        }
	                    }

	                    /* check if the Tile left from the current Tile is valid */
	                    if(i > 0) 
	                    {
	                        /* check if that Tile is not marked and if it is walkable*/
	                        if(distance[i-1][j] == -1 && walkable[i-1][j]) 
	                        {
	                            /* not marked & walkable, so we can mark it */
	                            distance[i-1][j] = steps;
	                        }
	                    }

	                    /* check if the Tile underneath the current Tile is valid */
	                    if(j < length - 1) 
	                    {
	                        /* check if that Tile is not marked and if it is walkable*/
	                        if(distance[i][j+1] == -1 && walkable[i][j+1]) 
	                        {
	                            /* not marked & walkable, so we can mark it */
	                            distance[i][j+1] = steps;
	                        }
	                    }

	                    /* check if the Tile above the current Tile is valid */
	                    if(j > 0) 
	                    {
	                        /* check if that Tile is not marked and if it is walkable*/
	                        if(distance[i][j-1] == -1 && walkable[i][j-1]) 
	                        {
	                            /* not marked & walkable, so we can mark it */
	                            distance[i][j-1] = steps;
	                        }
	                    }
	                }
	            }
	    	}
	    	steps++;
	    }
	    
	    int[][] path = new int[steps][2];
	    /* we start with the start point */
	    path[0][0] = startX;
	    path[0][1] = startY;

	    int i = 0;
	    /* now we go on until we hit the end point */
	    //  path[i][0] != endX && path[i][1] != endY (did not work)
	    while(i < steps -1) {
	        /* get current tile coordinates */
	        int x = path[i][0];
	        int y = path[i][1];
	        /* we have everything from the current tile so increment i */
	        i++;
	        
	        /* check if the field right from the current field is one step away */
	        if(distance[x+1][y] == distance[x][y] - 1) {
	            /* that tile is one step away, so take it into the path */
	            path[i][0] = x+1;
	            path[i][1] = y;
	        }

	        /* check if the field left from the current field is one step away */
	        else if(distance[x-1][y] == distance[x][y] - 1) {
	            /* that tile is one step away, so take it into the path */
	            path[i][0] = x-1;
	            path[i][1] = y;
	        }

	        /* check if the field underneath the current field is one step away */
	        else if(distance[x][y+1] == distance[x][y] - 1) {
	            /* that tile is one step away, so take it into the path */
	            path[i][0] = x;
	            path[i][1] = y+1;
	        }

	        /* check if the field above the current field is one step away */
	        else if(distance[x][y-1] == distance[x][y] - 1) {
	            /* that tile is one step away, so take it into the path */
	            path[i][0] = x;
	            path[i][1] = y-1;
	        }
	    }
	    return path;
	}
}
