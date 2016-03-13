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
import java.util.Collections;
import java.util.Comparator;

import javax.swing.Timer;

public class HighScore extends Canvas implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private GameStateHandler state;
	private Timer timer;
	private ArrayList<String> list;
	private ArrayList<Score> sList;
	
	public HighScore(GameStateHandler state) {
		this.state = state;
		this.timer = new Timer(CaveGame.GLOBALTIMER, this);
		this.list = CaveGame.fh.decrypt(CaveGame.SCOREDATA);
		this.sList = convertion(list);
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
		
		/*-------------------------------Draw Score List-------------------------*/
		Font eightBit = null;
		try {
			InputStream font = new FileInputStream(new File("./font/8_BIT_WONDER.TTF"));
			eightBit = Font.createFont(Font.TRUETYPE_FONT, font);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		g2d.setColor(new Color(191, 78, 22));
		int ratio = 10, y = 128;
		for (String s : list) {
			eightBit = eightBit.deriveFont(Font.PLAIN, 17 + ratio * 3);
			g2d.setFont(eightBit);
			int width = g2d.getFontMetrics(eightBit).stringWidth(s);			
			g2d.drawString(s, (state.getContainer().getWidth()-width)/2, y);
			y += 32 + ratio*2;
			ratio--;
		}
		
		eightBit = eightBit.deriveFont(Font.PLAIN, 10);
		g2d.setFont(eightBit);
		String message = "Press ESC Return To Main Menu";
		int width = g2d.getFontMetrics(eightBit).stringWidth(message);
		g2d.drawString(message, state.getContainer().getWidth() - width - 40,state.getContainer().getHeight() - 40 - GameContainer.XOFF);
		/*-----------------------------------------------------------------------*/
		
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
	
	public ArrayList<String> getList() {
		return this.list;
	}
	
	public ArrayList<Score> getScoreList() {
		return this.sList;
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
	
	public void keyReleased(KeyEvent e) {
		
	}
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			this.timer.stop();
			state.setState(GameStateHandler.MENU);
		}
	}
	
	public ArrayList<Score> convertion(ArrayList<String> string) {
		
		ArrayList<Score> scoreList = new ArrayList<Score>();
		
		for (String s : string) {
			String name = s.split(" ")[0];
			long score = Long.parseLong(s.split(" ")[1]);
			scoreList.add(new Score(name, score));			
		}
		
		return scoreList;
	}
	
	public void addNewHighScore(String name, long score) {
		sList.add(new Score(name, score));
		list = updateHighScoreList();
		storeNewHighScoreData();
	}
	
	public ArrayList<String> updateHighScoreList() {
		ArrayList<String> newStringList = new ArrayList<String>();
		
		Collections.sort(sList, comp);
		sList.remove(10);
		for (Score s : sList) {
			newStringList.add(s.toString());
		}		
		return newStringList;
	}
	
	static final Comparator<Score> comp = new Comparator<Score>() {
		
        public int compare(Score e1, Score e2) {
        	
            return e1.compareTo(e2);
       }
        
	};
	
	public void storeNewHighScoreData() {
		CaveGame.fh.encrypt(list, "./data/score.cave");
	}
	
	public boolean isHighScore(long newScore) {
		
		return (newScore < sList.get(sList.size()-1).score ? false : true);
		
	}
	
	class Score {
		
		private String name;
		private long score;
		
		public Score(String name, long score) {
			this.name = name;
			this.score = score;
		}
		
		public int compareTo(Score s) {
			 
			if (this.score == s.score)
				return 0;
			
			return (this.score > s.score ? -1 : 1);
		}
		
		public String toString() {
			return name + " " + score;
		}
		
	}

}
