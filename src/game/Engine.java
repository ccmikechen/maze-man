package game;


import java.io.File;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import resources.Resources;
import states.FileChooserState;
import states.MazeGameState;
import states.MenuState;
import states.RandomMapSizeState;

public class Engine extends StateBasedGame {
	
	static {
		File lib = new File("lib");
		if (lib.exists()) {
			System.setProperty("org.lwjgl.librarypath", lib.getAbsolutePath());
		}
	}
	
	public static final String GAME_TITLE = "MAZE MAN";
	
	public Engine() {
		super(GAME_TITLE);
		
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		new Resources();
		intGameStates(container);
		this.addState(new MenuState());
		this.addState(new RandomMapSizeState());
		this.addState(new MazeGameState());
		this.addState(new FileChooserState());
	}

	private void intGameStates(GameContainer container) {
		container.setMaximumLogicUpdateInterval(60);
		container.setTargetFrameRate(60);
		container.setAlwaysRender(true);
		container.setShowFPS(false);
		container.setVSync(true);
	}
	
	public static void main(String[] args) {
		
		System.out.println(Engine.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		
		try {
			Game game = new Engine();
			AppGameContainer app = new AppGameContainer(game);
			app.setDisplayMode(Window.WIDTH, Window.HEIGHT, false);
			app.start();
		} catch (SlickException e) {
			
		}
	}
	
}
