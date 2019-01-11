package resources;

import java.util.Map;
import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Resources {

	private static Map<String, Image> images;
	
	private static Map<String, SpriteSheet> sprites;
	
	private static Map<String, Animation> animations;
	
	public Resources() {
		images = new HashMap<String, Image>();
		sprites = new HashMap<String, SpriteSheet>();
		try {
			sprites.put("outside", loadSprite("res/outside.png", 32, 32));
			sprites.put("human", loadSprite("res/human.png", 32, 32));
		} catch (SlickException e) {
			System.err.println(e);
			System.exit(0);
		}
	}
	
	private Image loadImage(String path) throws SlickException {
		return new Image(path, false, Image.FILTER_NEAREST);
	}
	
	private SpriteSheet loadSprite(String path, int tw, int th) throws SlickException {
		return new SpriteSheet(loadImage(path), tw, th);
	}
	
	public static Image getImage(String getter) {
		return images.get(getter);
	}
	
	public static SpriteSheet getSprite(String getter) {
		return sprites.get(getter);
	}
	
	public static Image getSpriteImage(String getter, int x, int y) {
		return sprites.get(getter).getSprite(x, y);
	}
	
}
