package resources;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import map.GameMap;

public class Road implements Grid {

	private GameMap map;
	
	private Image image;

	public Road(GameMap map) {
		this.map = map;
		image = Resources
				.getSpriteImage("outside", 1, 0)
				.getScaledCopy((int) map.getGridWidth(), (int) map.getGridHeight());
	}
	
	@Override
	public void draw(Graphics g, int x, int y) {
		g.drawImage(image, x * map.getGridWidth(), y * map.getGridHeight());
	}
	
}
