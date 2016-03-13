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

import javax.swing.Timer;

public class StoryBoard extends Canvas implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	private GameStateHandler state;
	private Timer timer;
	private ArrayList<String> story;
	private int iterator, fontHeight;
	
	public StoryBoard(GameStateHandler state) {
		this.state = state;
		this.timer = new Timer(3, this);
		this.story = CaveGame.fh.decrypt(CaveGame.STORYDATA);
		this.iterator = 0;
		this.fontHeight = 0;
	}
	
	public void render(Graphics2D g2d) {		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, state.getContainer().getWidth(), state.getContainer().getHeight());
		
		/*--------------------------Draw Story-------------------------*/
		Font eightBit = null;
		try {
			InputStream font = new FileInputStream(new File("./font/8_BIT_WONDER.TTF"));
			eightBit = Font.createFont(Font.TRUETYPE_FONT, font);
		} catch(Exception e) {
			e.printStackTrace();
		}		
		int yOffset = 0;
		eightBit = eightBit.deriveFont(Font.TRUETYPE_FONT, 30);
		fontHeight = g2d.getFontMetrics(eightBit).getHeight();
		g2d.setFont(eightBit);
		g2d.setColor(Color.white);
		for (String s : story) {
			int width = g2d.getFontMetrics(eightBit).stringWidth(s);
			g2d.drawString(s, (state.getContainer().getWidth() - width)/2, state.getContainer().getHeight()+yOffset-iterator);
			yOffset+=64;
		}
		iterator++;
		/*-------------------------------------------------------------*/
		
		/*--------------------------Draw message-------------------------*/
		String message = "Press Space To Skip";
		eightBit = eightBit.deriveFont(Font.TRUETYPE_FONT, 15);
		g2d.setFont(eightBit);
		int height = g2d.getFontMetrics(eightBit).getHeight();
		int width = g2d.getFontMetrics(eightBit).stringWidth(message);
		g2d.drawString(message, state.getContainer().getWidth() - GameContainer.XOFF - width - 4, state.getContainer().getHeight() - GameContainer.YOFF - (int)1.5*height);
		/*---------------------------------------------------------------*/
		
//		/*------------------Getting Mouse position---------------------*/
//		PointerInfo pointer = MouseInfo.getPointerInfo();
//		int mouseX = pointer.getLocation().x - state.getContainer().getFrame().getLocationOnScreen().x;
//		int mouseY = pointer.getLocation().y - state.getContainer().getFrame().getLocationOnScreen().y;	
//		g2d.setColor(new Color(255, 255, 255, 255));
//		g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
//		g2d.drawString("Mouse X at: " + mouseX + " Mouse Y at: " + mouseY, 4, 32);	
//		/*------------------------------------------------------------*/
	}
	
	public Timer getTimer() {
		return this.timer;
	}
	
	public void reset() {
		iterator = 0;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {	
		
		if (iterator > (fontHeight+32) * story.size() + state.getContainer().getHeight()){
			this.timer.stop();
			state.setState(GameStateHandler.LEVEL);
		}
		
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
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			this.timer.stop();
			state.setState(GameStateHandler.LEVEL);
		}
	}
	
	public void keyReleased(KeyEvent e) {
		
	}
	
}
