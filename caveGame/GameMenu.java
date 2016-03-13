package caveGame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.Timer;


public class GameMenu extends Canvas implements ActionListener{
	
	private boolean isNewGame, isHighScore, isExit;
	private int pointerY, pointerX;
	private static final long serialVersionUID = 1L;
	private Timer timer;
	private GameStateHandler state;

	public GameMenu(GameStateHandler state) {
		this.timer = new Timer(CaveGame.GLOBALTIMER, this);
		this.state = state;	
		this.isNewGame = true;
		this.pointerY = 390;
		this.pointerX = 443 - 32;
	}

	public void render(Graphics2D g2d) {		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, state.getContainer().getWidth(), state.getContainer().getHeight());
		
		/*----------------------------Draw boarder----------------------------*/		
		for (int i = GameContainer.XOFF + 32; i < CaveGame.WIDTH-32; i+=32) {
			g2d.drawImage(CaveGame.images.get(8).getImage(), null, i, GameContainer.YOFF);
			g2d.drawImage(CaveGame.images.get(7).getImage(), null, i, GameContainer.YOFF+544);
		}
		for (int i = GameContainer.YOFF + 32; i < CaveGame.HEIGHT/2; i+=32) {
			g2d.drawImage(CaveGame.images.get(11).getImage(), null, GameContainer.XOFF, i);
			g2d.drawImage(CaveGame.images.get(13).getImage(), null, GameContainer.XOFF+992, i);
		}
		for (int i = GameContainer.YOFF + CaveGame.HEIGHT/2; i < CaveGame.HEIGHT-32; i+=32) {
			g2d.drawImage(CaveGame.images.get(3).getImage(), null, GameContainer.XOFF, i);
			g2d.drawImage(CaveGame.images.get(6).getImage(), null, GameContainer.XOFF+992, i);
		}
		g2d.drawImage(CaveGame.images.get(9).getImage(), null, GameContainer.XOFF, GameContainer.YOFF);
		g2d.drawImage(CaveGame.images.get(9).getImage(), null, GameContainer.XOFF+992, GameContainer.YOFF);
		g2d.drawImage(CaveGame.images.get(0).getImage(), null, GameContainer.XOFF, GameContainer.YOFF+544);
		g2d.drawImage(CaveGame.images.get(0).getImage(), null, GameContainer.XOFF+992, GameContainer.YOFF+544);
		/*--------------------------------------------------------------------*/
		
		/*----------------------------Draw Title----------------------------*/
		ArrayList<String> block = CaveGame.fh.decrypt(CaveGame.MENUDATA);
		for (String s : block) {
			String[] blockInfo = s.split(" ");
			g2d.drawImage(CaveGame.images.get(Integer.parseInt(blockInfo[0])).getImage(), null, GameContainer.XOFF+Integer.parseInt(blockInfo[1]), GameContainer.YOFF+Integer.parseInt(blockInfo[2]));
		}
		/*------------------------------------------------------------------*/
		
		/*----------------------------Draw Option----------------------------*/
		try {
			InputStream font = new FileInputStream(new File("./font/8_BIT_WONDER.TTF"));
			Font eightBit = Font.createFont(Font.TRUETYPE_FONT, font);
			eightBit = eightBit.deriveFont(Font.PLAIN, 17);
			g2d.setFont(eightBit);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		g2d.setColor(new Color(191, 78, 22));
		g2d.drawString("New Game",  GameContainer.XOFF + 443,  GameContainer.YOFF + 407);
		g2d.drawString("High Score", GameContainer.XOFF + 438, GameContainer.YOFF + 439);
		g2d.drawString("Exit", GameContainer.XOFF + 484, GameContainer.YOFF + 503);	
		
		g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
		ImageIcon icon = new ImageIcon("./img/arrow.png");
		//g2d.drawString("���", GameContainer.XOFF + pointerX, GameContainer.YOFF + pointerY);
		g2d.drawImage(icon.getImage(), GameContainer.XOFF + pointerX, GameContainer.YOFF + pointerY, null);
		/*-------------------------------------------------------------------*/		
		
//		/*------------------Getting Mouse position---------------------*/
//		PointerInfo pointer = MouseInfo.getPointerInfo();
//		int mouseX = pointer.getLocation().x - state.getContainer().getFrame().getLocationOnScreen().x;
//		int mouseY = pointer.getLocation().y - state.getContainer().getFrame().getLocationOnScreen().y;	
//		g2d.setColor(new Color(255, 255, 255, 255));
//		g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
//		g2d.drawString("Mouse X at: " + mouseX + " Mouse Y at: " + mouseY, 4, 32);	
//		/*------------------------------------------------------------*/
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		state.getContainer().getFrame().createBufferStrategy(2);
		BufferStrategy bs = state.getContainer().getFrame().getBufferStrategy();
		Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
		if (!bs.contentsLost()) {
			try {
				g2d = (Graphics2D) bs.getDrawGraphics();
				render(g2d);
			} finally {
				g2d.dispose();
			}
		}
		bs.show();
	}
	
	public Timer getTimer() {
		return timer;
	}
	
	public void keyReleased(KeyEvent e) {
		
	}
	
	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (isNewGame) {
				isNewGame = false;
				isHighScore = true;
				isExit = false;
				pointerY = 422;
				pointerX = 438 - 32;
			} else if (isHighScore) {
				isNewGame = false;
				isHighScore = false;
				isExit = true;
				pointerY = 486;
				pointerX = 484 - 32;
			} else if (isExit) {
				isNewGame = true;
				isHighScore = false;
				isExit = false;
				pointerY = 390;
				pointerX = 443 - 32;
			}
		}
		
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (isNewGame) {
				isNewGame = false;
				isHighScore = false;
				isExit = true;
				pointerY = 486;
				pointerX = 484 - 32;
			} else if (isHighScore) {
				isNewGame = true;
				isHighScore = false;
				isExit = false;
				pointerY = 390;
				pointerX = 443 - 32;
			} else if (isExit) {
				isNewGame = false;
				isHighScore = true;
				isExit = false;
				pointerY = 422;
				pointerX = 438 - 32;
			}
		}
		
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (isNewGame) {
				this.timer.stop();
				state.setState(GameStateHandler.NEWGAME);
			} else if (isHighScore) {
				this.timer.stop();
				state.setState(GameStateHandler.HIGHSCORE);
			} else if (isExit) {
				this.timer.stop();
				state.setState(GameStateHandler.EXIT);
			}
		}
	}
}
