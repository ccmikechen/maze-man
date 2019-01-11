package map;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import org.newdawn.slick.Graphics;

import game.Window;
import resources.Fence;
import resources.Grid;
import resources.Road;

public class GameMap {

	public static class Loader {
		
		public GameMap loadFromArray(int[][] array) throws LoadMapException {
			checkArray(array);
			int width = array[0].length;
			int height = array.length;
			return new GameMap(array, width, height);
		}
		
		public GameMap loadFromFile(File file) throws LoadMapException {
			try {
				checkFile(file);
				int width = getMapWidth(file);
				int height = getMapHeight(file);
				return new GameMap(file, width, height);
			} catch (FileNotFoundException e) {
				throw new LoadMapException(e);
			}	
		}
		
		private void checkArray(int[][] array) throws LoadMapException {
			for (int i = 0; i < array.length; i++) {
				if (array[i].length != array[0].length) {
					throw new LoadMapException("Rows length is not consistent");
				}
			}
		}
		
		private void checkFile(File file) throws LoadMapException, FileNotFoundException {
			Scanner scanner = new Scanner(file);
			String firstLine = scanner.nextLine();
			checkLine(firstLine);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.length() != firstLine.length()) {
					throw new LoadMapException("Rows length is not consistent");
				}
				checkLine(line);
			}
			
		}
		
		private void checkLine(String line) throws LoadMapException {
			for (char c : line.toCharArray()) {
				if (!(Character.isDigit(c))) {
					throw new LoadMapException("Map format is not correct");
				}
			}
		}

		private int getMapWidth(File file) throws FileNotFoundException {
			Scanner scanner = new Scanner(file);
			return scanner.nextLine().length();
		}
		
		private int getMapHeight(File file) throws FileNotFoundException {
			Scanner scanner = new Scanner(file);
			int count = 0;
			while (scanner.hasNextLine()) {
				count++;
				scanner.nextLine();
			}
			return count;
		}
		
	}
	
	private int width;
	
	private int height;
	
	private Grid[][] bgGrids;
	
	private Grid[][] objGrids;
	
	private int[][] originArray;
	
	private Location start;
	
	private Location target;
	
	private GameMap(int width, int height) {
		this.width = width;
		this.height = height;
		bgGrids = new Grid[width][height];
		objGrids = new Grid[width][height];
	}
	
	private GameMap(String path, int width, int height) throws FileNotFoundException, LoadMapException {
		this(new File(path), width, height);
	}
	
	private GameMap(File file, int width, int height) throws FileNotFoundException, LoadMapException {
		this(loadFileToArray(file, width, height), width, height);
	}
	
	private GameMap(int[][] array, int width, int height) throws LoadMapException {
		this(width, height);
		loadMapFromArray(array);
	}
	
	private static int[][] loadFileToArray(File file, int width, int height) throws FileNotFoundException {
		int[][] array = new int[width][height];
		Scanner scanner = new Scanner(file);
		for (int i = 0; i < height; i++) {
			String line = scanner.nextLine();
			for (int j = 0; j < width; j++) {
				array[j][i] = line.charAt(j) - '0';
			}
		}
		return array;
	}
	
	private void loadMapFromArray(int[][] array) throws LoadMapException {
		originArray = deepCopyArray(array);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				bgGrids[i][j] = new Road(this);
				if (array[i][j] == 1) {
					objGrids[i][j] = new Fence(this);	
				} else if (array[i][j] == 2) {
					start = new Location(i, j);
				} else if (array[i][j] == 3) {
					target = new Location(i, j);
				}
			}
		}
		if (start == null) {
			throw new LoadMapException("No start point found");
		}
		if (target == null) {
			throw new LoadMapException("No target point found");
		}
	}
	
	public int[][] getOriginArray() {
		return deepCopyArray(originArray);
	}
	
	private int[][] deepCopyArray(int[][] array) {
		int[][] clone = new int[array.length][];
		for (int i = 0; i < array.length; i++) {
			clone[i] = new int[array[i].length];
			for (int j = 0; j < array[i].length; j++) {
				clone[i][j] = array[i][j];
			}
		}
		return clone;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public boolean isPassable(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) {
			return false;
		}
		return objGrids[x][y] == null;
	}
	
	
	public void draw(Graphics g) {
		drawBackground(g);
		drawObject(g);
	}
	
	private void drawBackground(Graphics g) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				bgGrids[i][j].draw(g, i, j);
			}
		}
	}
	
	private void drawObject(Graphics g) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (objGrids[i][j] != null) {
					objGrids[i][j].draw(g, i, j);
				}
			}
		}
	}
	
	public int getGridWidth() {
		return Window.WIDTH / width;
	}
	
	public int getGridHeight() {
		return Window.HEIGHT / height;
	}
	
	public Location getStartLocation() {
		return this.start;
	}
	
	public Location getTargetLocation() {
		return this.target;
	}
	
}
