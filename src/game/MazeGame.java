package game;

import java.util.LinkedList;

import org.newdawn.slick.Graphics;

import map.GameMap;
import resources.Player;

public class MazeGame {
	
	private static final int MANUAL_MODE = 1;
	
	private static final int AUTO_MODE = 2;
	
	private static MazeGame game;
	
	private GameMap map;
	
	private Player player;
	
	private int mode;
	
	private LinkedList<Direction> solutionPath;
	
	public static MazeGame getGame() {
		if (game == null) {
			game = new MazeGame();
		}
		return game;
	}
	
	private MazeGame() {	
	}
	
	public void init() {
		player = new Player(map, 
				map.getStartLocation().getX(), 
				map.getStartLocation().getY());
		this.mode = MANUAL_MODE;
	}
	
	public void render(Graphics g) {
		map.draw(g);
		player.draw(g);
		if (isAutoMode()) {
			g.drawString("Auto Mode", 0, 0);
		}
	}

	public void update(int delta) {
		if (isAutoMode()) {
			autoMove();
		}
		player.update(delta);
	}
	
	public boolean isMovable() {
		return (!(player.isMoving())) && (mode == MANUAL_MODE);
	}
	
	public void setPlayerFace(Direction face) {
		player.setFace(face);
	}
	
	public boolean isNextPassable() {
		if (Direction.NORTH == player.getFace()) {
			return map.isPassable(player.getX(), player.getY() - 1);
		} else if (Direction.SOUTH == player.getFace()) {
			return map.isPassable(player.getX(), player.getY() + 1);
		} else if (Direction.EAST == player.getFace()) {
			return map.isPassable(player.getX() - 1, player.getY());
		} else {
			return map.isPassable(player.getX() + 1, player.getY());
		}
	}
	
	public void setMap(GameMap map) {
		this.map = map;
	}
	
	public void startAutoMode() {
		solutionPath = new LinkedList<Direction>();
		if (!(searchPath(map.getOriginArray(), player.getX(), player.getY(), solutionPath))) {
			return;
		}
		this.mode = AUTO_MODE;
		player.setSpeed(8.0f);
		
						
	}
	
	public void stopAutoMode() {
		this.mode = MANUAL_MODE;
		player.setSpeed(2.0f);
	}
	
	public boolean isAutoMode() {
		return this.mode == AUTO_MODE;
	}
	
	public void autoMove() {
		if (player.isMoving()) {
			return;
		}
		if ((solutionPath == null) || (solutionPath.size() == 0)) {
			stopAutoMode();
			return;
		}
		player.setFace(solutionPath.removeFirst());
		player.move();
	}
	
	public void move() {
		if (player.isMoving()) {
			return;
		}
		player.move();
	}

	private boolean searchPath(int[][] mapArray, int x, int y, LinkedList<Direction> pathList) {
		if ((x == map.getTargetLocation().getX()) && 
				(y == map.getTargetLocation().getY())) {
			return true;
		}
		mapArray[x][y] = 4;
		if (isPassable(mapArray, x, y - 1)) {
			pathList.add(Direction.NORTH);
			if (searchPath(mapArray, x, y - 1, pathList)) {
				return true;
			}
		}
		if (isPassable(mapArray, x, y + 1)) {
			pathList.add(Direction.SOUTH);
			if (searchPath(mapArray, x, y + 1, pathList)) {
				return true;
			}
		}
		if (isPassable(mapArray, x - 1, y)) {
			pathList.add(Direction.EAST);
			if (searchPath(mapArray, x - 1, y, pathList)) {
				return true;
			}
		}
		if (isPassable(mapArray, x + 1, y)) {
			pathList.add(Direction.WEST);
			if (searchPath(mapArray, x + 1, y, pathList)) {
				return true;
			}
		}
		if (pathList.size() > 0) {
			pathList.removeLast();
		}
		return false;
	}
	
	private boolean isPassable(int[][] array, int x, int y) {
		return (array[x][y] != 1) && (array[x][y] != 4);
	}
	
}
