package states;

import java.awt.Font;
import java.io.FileNotFoundException;

import org.newdawn.slick.Color;
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

public class MenuState extends BasicGameState {

	public static final String GAME_TITLE = "MAZE MAN";
	
	private String[] menu = new String[] {
			"RANDOM MAP",
			"LOAD MAP",
			"EXIT"
	};
	
	private int selectedIndex;

	private TrueTypeFont titleFont;
	
	private TrueTypeFont menuFont;
	
	private StateBasedGame game;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.game = game;
		selectedIndex = 0;
		titleFont = new TrueTypeFont(new Font("DejaVu Sans Mono", Font.BOLD | Font.ITALIC, 60), false);
		menuFont = new TrueTypeFont(new Font("DejaVu Sans Mono", 0, 50), false);
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		selectedIndex = 0;
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		drawTitle(g);
		drawMenu(g);
	}

	private void drawTitle(Graphics g) {
		g.setColor(Color.white);
		g.setFont(titleFont);
		drawStringOnCenter(g, GAME_TITLE, Window.WIDTH / 2, Window.HEIGHT * 0.2f);
	}
	
	private void drawMenu(Graphics g) {
		g.setFont(menuFont);
		for (int i = 0; i < menu.length; i++) {
			if (i == selectedIndex) {
				g.setColor(Color.yellow);
			} else {
				g.setColor(Color.white);
			}
			drawStringOnCenter(g, menu[i], Window.WIDTH / 2, Window.HEIGHT / 2 + 100 * i);
		}
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
		return States.MENU;
	}
	
	public void keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_UP:
			if (selectedIndex > 0) {
				selectedIndex--;
			}
			break;
		case Input.KEY_DOWN:
			if (selectedIndex < menu.length - 1) {
				selectedIndex++;
			}
			break;
		case Input.KEY_ENTER:
		case Input.KEY_NUMPADENTER:
			handleSelection();
			break;
		}
	}

	private void handleSelection() {
		switch (selectedIndex) {
		case 0:
			game.enterState(States.RANDOM_MAP_SIZE, new FadeOutTransition(), new FadeInTransition());
			break;
		case 1:
			game.enterState(States.FILE_CHOOSER, new FadeOutTransition(), new FadeInTransition());
			break;
		case 2:
			System.exit(0);
		}
	}
	
}
