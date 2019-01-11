package map;

import java.util.ArrayList;
import java.util.List;

public class MazeGenerator {
	
	private static final int UNVISITED = -1;
	
	private static final int VISITED = 0;
	
	private static final int WALL = 1;
	
	private static final int START = 2;
	
	private static final int TARGET = 3;
	
	private int[][] maze;
	
	private int visitedCount;
	
	private int total;
	
	private int width;
	
	private int height;
	
	public int[][] generate(int width, int height) {
		maze = createNewMaze(width, height);
		this.width = width * 2 + 1;
		this.height = height * 2 + 1;
		visitedCount = 0;
		total = width * height;
		int x = (int) (Math.random() * width);
		int y = (int) (Math.random() * height);
		randomKnockDown(x * 2 + 1, y * 2 + 1);
		maze[1][1] = START;
		maze[this.width - 2][this.height - 2] = TARGET;
		return maze;
	}
	
	private int[][] createNewMaze(int width, int height) {
		int[][] maze = new int[width * 2 + 1][height * 2 + 1];
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				maze[i][j] = WALL;
			}
		}
		for (int i = 1; i < maze.length - 1; i += 2) {
			for (int j = 1; j < maze[i].length - 1; j += 2) {
				maze[i][j] = UNVISITED;
			}
		}
		return maze;
	}
	
	private void randomKnockDown(int x, int y) {
		maze[x][y] = VISITED;
		visitedCount++;
		while (visitedCount < total) {
			List<Location> neighbors = getUnvisitedNeighbors(x, y);
			if (neighbors.size() == 0) {
				return;
			}
			Location next = neighbors.get((int) (Math.random() * neighbors.size()));
			int wallX = (x + next.getX()) / 2;
			int wallY = (y + next.getY()) / 2;
			maze[wallX][wallY] = VISITED;
			randomKnockDown(next.getX(), next.getY());
		}
	}
	
	private List<Location> getUnvisitedNeighbors(int x, int y) {
		List<Location> neighbors = new ArrayList<Location>();
		if (((x - 2) > 0) && (maze[x - 2][y] == UNVISITED)) {
			neighbors.add(new Location(x - 2, y));
		}
		if (((y - 2) > 0) && (maze[x][y - 2] == UNVISITED)) {
			neighbors.add(new Location(x, y - 2));
		}
		if (((x + 2) < width) && (maze[x + 2][y] == UNVISITED)) {
			neighbors.add(new Location(x + 2, y));
		}
		if (((y + 2) < height) && (maze[x][y + 2] == UNVISITED)) {
			neighbors.add(new Location(x, y + 2));
		}
		return neighbors;
	}
	
}
