package states;

import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.sun.corba.se.spi.orbutil.fsm.State;

import game.MazeGame;
import game.Window;
import map.GameMap;
import map.LoadMapException;

public class FileChooserState extends BasicGameState {

	class FileNamePair {
		
		String displayName;
		
		File file;
		
		public FileNamePair(String displayName, File file) {
			this.displayName = displayName;
			this.file = file;
		}
		
	}
	
	private static final String TITLE = "LOAD MAP";
	
	private static int maxDisplayCount = 10;
	
	private File currentDirectory;
	
	private List<FileNamePair> childs;
	
	private int selectedIndex;
	
	private int topDisplayIndex;
	
	private StateBasedGame game;
	
	private TrueTypeFont titleFont;
	
	private TrueTypeFont fileNameDisplayFont;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.game = game;
		this.titleFont = new TrueTypeFont(new Font("DejaVu Sans Mono", Font.BOLD | Font.ITALIC, 60), false);
		this.fileNameDisplayFont = new TrueTypeFont(new Font("DejaVu Sans Mono", 0, 30), false);
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		changeDirectory(new File(".").getAbsoluteFile().getParentFile());
	}
	

	private void changeDirectory(File directory) {
		if (!directory.isDirectory()) {
			return;
		}
		currentDirectory = directory;
		scanChilds();
		selectedIndex = 0;
		topDisplayIndex = 0;
	}
	
	private void scanChilds() {
		childs = new ArrayList<FileNamePair>();
		File parent = currentDirectory.getAbsoluteFile().getParentFile();
		if (parent != null) {
			childs.add(new FileNamePair("../", parent));
		}
		for (File child : currentDirectory.listFiles()) {
			if (!(child.getName().startsWith("."))) {
				if (child.isDirectory()) {
					childs.add(new FileNamePair(child.getName() + "/", child));
				} else {
					childs.add(new FileNamePair(child.getName(), child));
				}	
			}
		}
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// Draw title
		g.setFont(titleFont);
		g.setColor(Color.white);
		g.drawString(TITLE, 20, 20);
		// Draw selections
		float selectionHeight = (Window.HEIGHT * 0.85f) / maxDisplayCount;
		int displayCount = Math.min(maxDisplayCount, childs.size());
		for (int i = 0; i < displayCount; i++) {
			float y = (Window.HEIGHT * 0.15f) + (selectionHeight * i);
			// Draw background of selection rectangle
			int currentIndex = i + topDisplayIndex;
			if (currentIndex == selectedIndex) {
				g.setColor(Color.darkGray);
			} else {
				g.setColor(Color.black);
			}
			g.fillRect(0, y, Window.WIDTH, y + selectionHeight);
			// Draw rectangle border
			g.setColor(Color.white);
			g.setLineWidth(2);
			g.drawRect(0, y, Window.WIDTH, y + selectionHeight);
			// Draw selection name
			g.setFont(fileNameDisplayFont);
			float nameY = y + (selectionHeight - fileNameDisplayFont.getHeight()) / 2;
			g.drawString(childs.get(currentIndex).displayName, 20, nameY);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		return States.FILE_CHOOSER;
	}

	@Override
	public void keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_ESCAPE:
		case Input.KEY_BACK:
			game.enterState(States.MENU, new FadeOutTransition(), new FadeInTransition());
			break;
		case Input.KEY_UP:
			selectUp();
			break;
		case Input.KEY_DOWN:
			selectDown();
			break;
		case Input.KEY_ENTER:
		case Input.KEY_NUMPADENTER:
			handleSelection();
			break;
		}
	}
	
	private void selectUp() {
		if (selectedIndex > 0) {
			selectedIndex--;
			if (selectedIndex < topDisplayIndex) {
				topDisplayIndex--;
			}
		}
	}
	
	private void selectDown() {
		if (selectedIndex < (childs.size() - 1)) {
			selectedIndex++;
			if (selectedIndex >= (topDisplayIndex + maxDisplayCount)) {
				topDisplayIndex++;
			}
		}
	}
	
	private void handleSelection() {
		File selectedFile = childs.get(selectedIndex).file;
		if (selectedFile.isDirectory()) {
			changeDirectory(selectedFile);
		} else {
			try {
				GameMap map = new GameMap.Loader().loadFromFile(selectedFile);
				MazeGame.getGame().setMap(map);
				game.enterState(States.GAME, new FadeOutTransition(), new FadeInTransition());
			} catch (LoadMapException e) {
				System.err.println(e);
			}
		}
	}
	
}
