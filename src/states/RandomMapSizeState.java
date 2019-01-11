package states;

import java.awt.Font;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import game.MazeGame;
import game.Window;
import map.GameMap;
import map.LoadMapException;
import map.MazeGenerator;

public class RandomMapSizeState extends BasicGameState {

	private int randomMapSize = 0;
	
	private TrueTypeFont font = new TrueTypeFont(new Font("DejaVu Sans Mono", 0, 50), false);

	private StateBasedGame game;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.game = game;
		randomMapSize = 20;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setFont(font);
		drawStringOnCenter(g, "Map Size", Window.WIDTH * 0.5f, Window.HEIGHT * 0.25f);
		drawStringOnCenter(g, String.valueOf(randomMapSize), 
				Window.WIDTH * 0.5f, Window.HEIGHT * 0.5f);
	}
	
	private void drawStringOnCenter(Graphics g, String s, float x, float y) {
		int helfWidth = g.getFont().getWidth(s) / 2;
		int helfHeight = g.getFont().getHeight(s) / 2;
		g.drawString(s, x - helfWidth, y - helfHeight);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
	}

	@Override
	public int getID() {
		return States.RANDOM_MAP_SIZE;
	}

	public void keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_ENTER:
		case Input.KEY_NUMPADENTER:
			GameMap map;
			try {
				map = new GameMap.Loader().loadFromArray(
						new MazeGenerator().generate(randomMapSize, randomMapSize));
				MazeGame.getGame().setMap(map);
				game.enterState(States.GAME, new FadeOutTransition(), new FadeInTransition());
			} catch (LoadMapException e) {
				e.printStackTrace();
			}
			break;
		case Input.KEY_ESCAPE:
		case Input.KEY_BACK:
			game.enterState(States.MENU, new FadeOutTransition(), new FadeInTransition());
			break;
		case Input.KEY_DOWN:
			if (randomMapSize >= 11) {
				randomMapSize -= 10;
			}
			break;
		case Input.KEY_UP:
			if (randomMapSize <= 100) {
				randomMapSize += 10;
			}
			break;
		case Input.KEY_LEFT:
			if (randomMapSize > 1) {
				randomMapSize--;
			}
			break;
		case Input.KEY_RIGHT:
			if (randomMapSize < 100) {
				randomMapSize++;
			}
			break;
		}
	}
	
}
