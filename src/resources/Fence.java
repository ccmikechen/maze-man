package resources;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import map.GameMap;

public class Fence implements Grid {

	private GameMap map;
	
	private Image image;

	public Fence(GameMap map) {
		this.map = map;
		image = Resources
				.getSpriteImage("outside", 8, 3)
				.getScaledCopy((int) map.getGridWidth(), (int) map.getGridHeight());
	}
	
	@Override
	public void draw(Graphics g, int x, int y) {
		g.drawImage(image, x * map.getGridWidth(), y * map.getGridHeight());
	}
	
}
