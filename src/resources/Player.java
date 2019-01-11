package resources;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

import game.Direction;
import map.GameMap;

public class Player {

	private GameMap map;
	
	private Direction face;
	
	private Animation playerAnimation;
	
	private int x;
	
	private int y;
	
	private int nextX;
	
	private int nextY;
	
	private float speed;
	
	private int movingTime;
	
	private int duringTime;
	
	private boolean isMoving;
	
	public Player(GameMap map, int x, int y) {
		this.map = map;
		this.x = x;
		this.y = y;
		init();
	}
	
	public void init() {
		speed = 2.0f;
		movingTime = 0;
		duringTime = 200;
		isMoving = false;
		setFace(Direction.SOUTH);
	}
	
	public Direction getFace() {
		return this.face;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public void setDuringTime(int duringTime) {
		this.duringTime = duringTime;
	}
	
	public void setFace(Direction face) {
		if (isMoving) {
			return;
		}
		if (this.face != face) {
			setPlayerAnimation(face);
		}
		this.face = face;
	}
	
	private void setPlayerAnimation(Direction face) {
		if (Direction.NORTH == face) {
			playerAnimation = new Animation(Resources.getSprite("human"), 0, 3, 2, 3, true, 100, false);
		} else if (Direction.SOUTH == face) {
			playerAnimation = new Animation(Resources.getSprite("human"), 0, 0, 2, 0, true, 100, false);
		} else if (Direction.EAST == face) {
			playerAnimation = new Animation(Resources.getSprite("human"), 0, 1, 2, 1, true, 100, false);
		} else if (Direction.WEST == face) {
			playerAnimation = new Animation(Resources.getSprite("human"), 0, 2, 2, 2, true, 100, false);
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean isMoving() {
		return this.isMoving;
	}
	
	public void move() {
		movingTime = 0;
		isMoving = true;
		if (Direction.NORTH == face) {
			nextX = x;
			nextY = y - 1;
		} else if (Direction.SOUTH == face) {
			nextX = x;
			nextY = y + 1;
		} else if (Direction.EAST == face) {
			nextX = x - 1;
			nextY = y;
		} else if (Direction.WEST == face) {
			nextX = x + 1;
			nextY = y;
		}
		playerAnimation.setSpeed(speed);
		playerAnimation.restart();
	}
	
	public void update(int delta) {
		if (isMoving) {
			movingTime += delta * speed;
			if (movingTime >= duringTime) {
				arrivalTarget();
			}
			playerAnimation.update(delta);
		} else {
			playerAnimation.stopAt(1);
		}
	}
	
	private void arrivalTarget() {
		x = nextX;
		y = nextY;
		movingTime = 0;
		isMoving = false;
	}
	
	public void draw(Graphics g) {
		float realX = map.getGridWidth() * x;
		float realY = map.getGridHeight() * y;
		if (isMoving) {
			float progress = (float) movingTime / (float) duringTime;
			if (Direction.NORTH == face) {
				realY -= map.getGridHeight() * progress;
			} else if (Direction.SOUTH == face) {
				realY += map.getGridHeight() * progress;
			} else if (Direction.EAST == face) {
				realX -= map.getGridWidth() * progress;
			} else if (Direction.WEST == face) {
				realX += map.getGridWidth() * progress;
			}
		}
		playerAnimation.draw(realX, realY, map.getGridWidth(), map.getGridHeight());
	}
	
}
