package caveGame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener{
	
	public synchronized void keyReleased(KeyEvent e) {
								
		if (CaveGame.gameState.getState(GameStateHandler.MENU)) {
			CaveGame.gameState.getMenu().keyReleased(e);
		} else if (CaveGame.gameState.getState(GameStateHandler.HIGHSCORE)) {
			CaveGame.gameState.getScore().keyReleased(e);
		} else if (CaveGame.gameState.getState(GameStateHandler.NEWGAME)) {
			if (CaveGame.gameState.getState(GameStateHandler.STORY)) {
				CaveGame.gameState.getStory().keyReleased(e);
			} else if (CaveGame.gameState.getState(GameStateHandler.LEVEL)) {
				CaveGame.gameState.getLevel().keyReleased(e);
			} else if(CaveGame.gameState.getState(GameStateHandler.GAMING)) {
				CaveGame.gameState.getGame().keyReleased(e);				
			}
		}
		
	}
	
	public synchronized void keyPressed(KeyEvent e) {

		if (CaveGame.gameState.getState(GameStateHandler.MENU)) {
			CaveGame.gameState.getMenu().keyPressed(e);
		}

		if (CaveGame.gameState.getState(GameStateHandler.HIGHSCORE)) {
			CaveGame.gameState.getScore().keyPressed(e);
		}

		if (CaveGame.gameState.getState(GameStateHandler.NEWGAME)) {
			
			if (CaveGame.gameState.getState(GameStateHandler.STORY)) {
				CaveGame.gameState.getStory().keyPressed(e);
			}

			if (CaveGame.gameState.getState(GameStateHandler.LEVEL)) {
				CaveGame.gameState.getLevel().keyPressed(e);
			}

			if(CaveGame.gameState.getState(GameStateHandler.GAMING)) {
				CaveGame.gameState.getGame().keyPressed(e);
			}
			
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (CaveGame.gameState.getState(GameStateHandler.MENU)) {
			//CaveGame.gameState.getMenu().keyTyped(e);
		}

		if (CaveGame.gameState.getState(GameStateHandler.HIGHSCORE)) {
			//CaveGame.gameState.getScore().keyTyped(e);
		}

		if (CaveGame.gameState.getState(GameStateHandler.NEWGAME)) {
			
			if (CaveGame.gameState.getState(GameStateHandler.STORY)) {
				//CaveGame.gameState.getStory().keyTyped(e);
			}

			if (CaveGame.gameState.getState(GameStateHandler.LEVEL)) {
				//CaveGame.gameState.getLevel().keyTyped(e);
			}

			if(CaveGame.gameState.getState(GameStateHandler.GAMING)) {
				CaveGame.gameState.getGame().keyTyped(e);
			}
			
		}
	}

}
