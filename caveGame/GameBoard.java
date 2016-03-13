package caveGame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.Timer;

public class GameBoard extends Canvas{
	
	private static final long serialVersionUID = 1L;
	
	private Timer timer;
	private GameStateHandler state;
	private ArrayList<Images> iBlocks;
	private ArrayList<Rectangle> rBlock;
	private ArrayList<String> info;
	
	private int p1TriggerIndex = 0;
	private int p2TriggerIndex = 0;
	
	private Rectangle p1Trigger;
	private Rectangle p2Trigger;
	private Rectangle lastBlock;

	@SuppressWarnings("unchecked")
	public GameBoard(GameStateHandler state, ArrayList<String> info) {
		this.info = (ArrayList<String>) info.clone();
		this.state = state;		
		this.iBlocks = new ArrayList<Images>();
		this.rBlock = new ArrayList<Rectangle>();
		initBlocks();
	}
	
	public void initBlocks() {
		String ss = this.info.remove(0);
		p1Trigger = new Rectangle(Integer.parseInt(ss.split(" ")[0]), Integer.parseInt(ss.split(" ")[1]), Integer.parseInt(ss.split(" ")[2]), Integer.parseInt(ss.split(" ")[3]));
		ss = this.info.remove(0);
		p2Trigger = new Rectangle(Integer.parseInt(ss.split(" ")[0]), Integer.parseInt(ss.split(" ")[1]), Integer.parseInt(ss.split(" ")[2]), Integer.parseInt(ss.split(" ")[3]));
		
		int idx = 0;
		for (String string : this.info) {
			String[] s = string.split(" ");
			int index = Integer.parseInt(s[0]);
			int x = Integer.parseInt(s[1]) + GameContainer.XOFF;
			int y = Integer.parseInt(s[2]) + GameContainer.YOFF;
			
			Images i = new Images(CaveGame.images.get(index).getImage(), x, y);
			iBlocks.add(i);
			Rectangle r = new Rectangle(x, y, i.getImage().getWidth(), i.getImage().getHeight());
			rBlock.add(r);
			
			if (index == 15 && x == 3712 + GameContainer.XOFF && p1TriggerIndex == 0) {
				p1TriggerIndex = idx;
			}
			if (index == 15 && x == 8416 + GameContainer.XOFF && p2TriggerIndex == 0) {
				p2TriggerIndex = idx-3;
			}
			idx++;
		}
		lastBlock = rBlock.get(rBlock.size()-1);
	}
	
	@SuppressWarnings("unchecked")
	public void reset(ArrayList<String> info) {
		this.info = (ArrayList<String>) info.clone();
		this.iBlocks = new ArrayList<Images>();
		this.rBlock = new ArrayList<Rectangle>();
		initBlocks();
	}
	
	public void render(Graphics2D g2d) {
		
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, state.getContainer().getWidth(), state.getContainer().getHeight());
		
		/*----------------------Draw level blocks-----------------------*/
		for (Images img : iBlocks) {
			if (img.getX() >= -32 && img.getX() <= 1024) {
				g2d.drawImage(img.getImage(), null, img.getX(), img.getY());
			}
		}
		/*--------------------------------------------------------------*/
		
		/*-----------------------Draw console---------------------------*/
		ImageIcon console = CaveGame.images.get(24).getIcon();
		g2d.drawImage(console.getImage(), (int)p1Trigger.getX(), (int)p1Trigger.getY(), null);
		g2d.drawImage(console.getImage(), (int)p2Trigger.getX(), (int)p2Trigger.getY(), null);
		/*--------------------------------------------------------------*/
		
		/*----------------------Draw block frames, only need for debug-----------------------*/
//		for (Rectangle r : rBlock) {
//			g2d.setColor(Color.white);
//			g2d.setStroke(new BasicStroke(1.5f));
//			g2d.drawRect((int)r.getX(), (int)r.getY(), (int)r.getWidth(), (int)r.getHeight());
//		}
		/*-----------------------------------------------------------------------------------*/
				
	}
	
	public synchronized void increment(int x) {
		for (int i = 0; i < iBlocks.size(); i++) {
			if (iBlocks.get(i).getX() > -32) {
				iBlocks.get(i).setX(iBlocks.get(i).getX() - x);
				rBlock.get(i).setBounds(iBlocks.get(i).getX(), iBlocks.get(i).getY(), 32, 32);
			}
		}
		
		p1Trigger.setBounds(p1Trigger.x - x, p1Trigger.y, p1Trigger.width, p1Trigger.height);
		p2Trigger.setBounds(p2Trigger.x - x, p2Trigger.y, p2Trigger.width, p2Trigger.height);
	}
	
	public void keyPressed(KeyEvent e) {
		
	}
	
	public void keyReleased(KeyEvent e) {
		
	}
	
	public Timer getTimer() {
		return this.timer;
	}
	
	public ArrayList<Rectangle> getRecBlocks() {
		return this.rBlock;
	}
	
	public ArrayList<Images> getImgBlocks() {
		return this.iBlocks;
	}
	
	public Rectangle getP1Trigger() {
		return this.p1Trigger;
	}
	
	public Rectangle getP2Trigger() {
		return this.p2Trigger;
	}
	
	public Rectangle getLastBlock() {
		return this.lastBlock;
	}
	
	public int getP1TriggerIndex() {
		return this.p1TriggerIndex;
	}
	
	public int getP2TriggerIndex() {
		return this.p2TriggerIndex;
	}
}
