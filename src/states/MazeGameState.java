package states;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import game.Direction;
import game.MazeGame;

public class MazeGameState extends BasicGameState {

	private StateBasedGame game;
	
	private MazeGame mazeGame;
	
	private Direction currentPressed;
	
	public MazeGameState() {
		
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.game = game;
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		mazeGame = MazeGame.getGame();
		mazeGame.init();
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		mazeGame.render(g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if (mazeGame.isMovable()) {
			if (currentPressed != null) {
				mazeGame.setPlayerFace(currentPressed);
				if (mazeGame.isNextPassable()) {
					mazeGame.move();
				}
			}
		}
		mazeGame.update(delta);
	}

	@Override
	public int getID() {
		return States.GAME;
	}
	
	@Override
	public void keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_ESCAPE:
		case Input.KEY_BACK:
			game.enterState(States.MENU, new FadeOutTransition(), new FadeInTransition());
			break;
		case Input.KEY_SPACE:
			if (mazeGame.isAutoMode()) {
				mazeGame.stopAutoMode();	
			} else {
				mazeGame.startAutoMode();
			}
			break;
		case Input.KEY_UP:
			currentPressed = Direction.NORTH;
			break;
		case Input.KEY_DOWN:
			currentPressed = Direction.SOUTH;
			break;
		case Input.KEY_LEFT:
			currentPressed = Direction.EAST;
			break;
		case Input.KEY_RIGHT:
			currentPressed = Direction.WEST;
			break;
		}
	}
	
	@Override
	public void keyReleased(int key, char c) {
		switch (key) {
		case Input.KEY_UP:
			if (currentPressed == Direction.NORTH) {
				currentPressed = null;
			}
			break;
		case Input.KEY_DOWN:
			if (currentPressed == Direction.SOUTH) {
				currentPressed = null;
			}
			break;
		case Input.KEY_LEFT:
			if (currentPressed == Direction.EAST) {
				currentPressed = null;
			}
			break;
		case Input.KEY_RIGHT:
			if (currentPressed == Direction.WEST) {
				currentPressed = null;
			}
			break;
		}
	}
	
}
