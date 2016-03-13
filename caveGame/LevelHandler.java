package caveGame;

import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Timer;

public class LevelHandler extends Canvas implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private GameStateHandler state;
	private Timer timer;
	private GameLevel currentGame;
	
	public LevelHandler (GameStateHandler state) {
		this.state = state;
		this.timer = new Timer(CaveGame.GLOBALTIMER, this);
		
		//Demo, setting game to level one
		currentGame = new GameLevel(state, "./data/cave1.cave");
		
		
	}
	
	public void render() {
		//No level map UI implemented yet
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.timer.stop();	
		state.setState(GameStateHandler.GAMING);
	}
	
	public Timer getTimer() {
		return this.timer;
	}
	
	public GameLevel getCurrentGame() {
		return currentGame;		
	}
	
	
	public void keyPressed(KeyEvent e) {
		
	}
	
	public void keyReleased(KeyEvent e) {
		
	}

}
