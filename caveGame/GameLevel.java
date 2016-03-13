package caveGame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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

public class GameLevel extends Canvas implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	
	private boolean[] something = new boolean[5];
	private static final String loseHint = "You Lose";
	private static final String winHint = "You escaped";
	private static final String congHint = "Congratulations";
	private static final String triggerHint = "Press E to use";
	private static final String newHighScore = "New High Score";
	private static final String highScoreHint = "You Score ";
	private static final String enterInitHint = "Enter your name";
	private static final String returnHint = "Press ENTER back to main menu";	

	private Timer timer;	
	private Player player;
	private GameBoard board;
	private String playerInfo;
	private PuzzleOne puzzleOne;
	private PuzzleTwo puzzleTwo;
	private GameClock gameClock;
	private GameStateHandler state;
	private ArrayList<String> levelInfo;
	
	private int alignX;
	private long score, winDelay;
	private float saturation;
	private double xoff, yoff, jumpHeight;
	private String playerInitial;
	
	Batman batman;
	ArrayList<String> batVal = CaveGame.fh.decrypt("./data/bats.cave");
	private ArrayList<Batman> bat = new ArrayList<Batman>();

	private boolean jump, ground, win, lose, done;
	private boolean puzzleOneTriggered, puzzleTwoTriggered, onTrigger_1, onTrigger_2, highScore;
	
	public GameLevel(GameStateHandler state, String levelData) {
		this.state = state;
		this.levelInfo = CaveGame.fh.decrypt(levelData);		
		this.playerInfo = levelInfo.remove(0);
		this.timer = new Timer(CaveGame.GLOBALTIMER, this);
		this.gameClock = new GameClock();
		initMap();
	}
	
	public void render(Graphics2D g2d) {
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		Font eightBit = null;
		try {
			InputStream font = new FileInputStream(new File("./font/8_BIT_WONDER.TTF"));
			eightBit = Font.createFont(Font.TRUETYPE_FONT, font);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		board.render(g2d);		
		gameClock.render(g2d);
		
		if (win) {
			eightBit = eightBit.deriveFont(Font.TRUETYPE_FONT, 70);
			g2d.setFont(eightBit);
			g2d.setColor(Color.orange);			
			if (winDelay < 3000) {
				g2d.drawString(congHint, (state.getContainer().getWidth()-g2d.getFontMetrics(eightBit).stringWidth(congHint))/2, state.getContainer().getHeight()/2);
			} else if (winDelay >= 3000 && winDelay < 6000) {
				g2d.drawString(winHint, (state.getContainer().getWidth()-g2d.getFontMetrics(eightBit).stringWidth(winHint))/2, state.getContainer().getHeight()/2);
			} else if (winDelay > 6000) {
				if (highScore) {
					eightBit = eightBit.deriveFont(Font.TRUETYPE_FONT, 70);
					g2d.setFont(eightBit);
					alignX = (state.getContainer().getWidth()-g2d.getFontMetrics(eightBit).stringWidth(newHighScore))/2;
					g2d.drawString(newHighScore, alignX, state.getContainer().getHeight()/2);
					
					eightBit = eightBit.deriveFont(Font.TRUETYPE_FONT, 45);
					g2d.setFont(eightBit);
					g2d.drawString(highScoreHint + Long.toString(score) + " " + playerInitial, alignX, state.getContainer().getHeight()/3*2);
					
					if (winDelay % 50 == 0 && playerInitial.length() < 4) {
						g2d.fillRect(alignX + g2d.getFontMetrics(eightBit).stringWidth(highScoreHint + Long.toString(score) + " " + playerInitial), state.getContainer().getHeight()/3*2, 40, 2);
					}
					
					if (playerInitial.length() == 3 && !done) {
						state.getScore().addNewHighScore(playerInitial, score);
						done = true;
						playerInitial = "";
					}
				}				
				eightBit = eightBit.deriveFont(Font.TRUETYPE_FONT, 30);
				g2d.setFont(eightBit);
				g2d.drawString(enterInitHint, (state.getContainer().getWidth()-g2d.getFontMetrics(eightBit).stringWidth(enterInitHint))/2, state.getContainer().getHeight()/6*5);
			}
		}
		winDelay += CaveGame.GLOBALTIMER;
		//System.out.println(winDelay);
		if (winDelay > 10000) {
			winDelay = 6010;
		}
		
		if (lose) {
			eightBit = eightBit.deriveFont(Font.TRUETYPE_FONT, 120);
			g2d.setFont(eightBit);
			g2d.setColor(new Color(Color.HSBtoRGB(21/360, saturation, (float) 0.78)));
			if (saturation > 0.1)
				saturation -= 0.005;
			g2d.drawString(loseHint, (state.getContainer().getWidth()-g2d.getFontMetrics(eightBit).stringWidth(loseHint))/2, state.getContainer().getHeight()/2);
			
			if (saturation < 0.1) {
				eightBit = eightBit.deriveFont(Font.TRUETYPE_FONT, 30);
				g2d.setFont(eightBit);
				//g2d.setColor(new Color(191, 78, 22, 200));
				g2d.setColor(new Color(200, 253, 255, 200));
				g2d.drawString(returnHint, (state.getContainer().getWidth()-g2d.getFontMetrics(eightBit).stringWidth(returnHint))/2, state.getContainer().getHeight()/3*2);
			}
		}
		
		if(win || lose) {
			xoff = 0;
			yoff = 0;
		}
		
		player.render(g2d, something[0], something[2], something[1], something[3], something[4], yoff);
		
		
		if (puzzleOneTriggered) {
			puzzleOne.render(g2d);
		} else if (puzzleTwoTriggered) {
			puzzleTwo.getTimer().start();
			puzzleTwo.render(g2d);
		} else if (onTrigger_1 || onTrigger_2) {
			Rectangle currentTrigger;
			if (onTrigger_1) {
				currentTrigger = board.getP1Trigger();
			} else {
				currentTrigger = board.getP2Trigger();
			}			
			eightBit = eightBit.deriveFont(Font.TRUETYPE_FONT, 15);
			g2d.setFont(eightBit);
			g2d.setColor(new Color(191, 78, 22));
			int width = g2d.getFontMetrics(eightBit).stringWidth(triggerHint);
			g2d.drawString(triggerHint, currentTrigger.x + (currentTrigger.width-width)/2, currentTrigger.y-10);
		}
		
//		batman.spawnBats(g2d, player, bat, batTrack);
			
		if(player.getDist() >= 768 ){
			bat.get(0).setActive();
			bat.get(0).render(g2d);
		}
		
		if(player.getDist() >= 2144){
			bat.get(1).setActive();
			bat.get(2).setActive();
			bat.get(1).render(g2d);
			bat.get(2).render(g2d);
		}


		if(player.getDist() >= 9472){
			bat.get(3).setActive();
			bat.get(4).setActive();
			bat.get(5).setActive();
			bat.get(6).setActive();
			bat.get(3).render(g2d);
			bat.get(4).render(g2d);
			bat.get(5).render(g2d);
			bat.get(6).render(g2d);
		}
		
		if(player.getDist() >= 11712){
			bat.get(7).setActive();
			bat.get(7).render(g2d);
		}

		if(player.getDist() >= 12480){
			bat.get(8).setActive();
			bat.get(8).render(g2d);
		}
		
		if(player.getDist() >= 14400){
			bat.get(9).setActive();
			bat.get(10).setActive();
			bat.get(9).render(g2d);
			bat.get(10).render(g2d);
		}
		
		
		if(player.getDist() >= 15584){
			bat.get(11).setActive();
			bat.get(12).setActive();
			bat.get(11).render(g2d);
			bat.get(12).render(g2d);
		}

		if(player.getDist() >= 16480){
			bat.get(13).setActive();
			bat.get(13).render(g2d);
		}
//		
//		if(player.getDist() >= 16180){
//			bat.get(16).setActive();
//			bat.get(16).render(g2d);
//		}	
			
		
		/*------------------Getting Mouse position---------------------*/
//		PointerInfo pointer = MouseInfo.getPointerInfo();
//		int mouseX = pointer.getLocation().x - state.getContainer().getFrame().getLocationOnScreen().x;
//		int mouseY = pointer.getLocation().y - state.getContainer().getFrame().getLocationOnScreen().y;	
//		g2d.setColor(new Color(255, 255, 255, 255));
//		g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
//		g2d.drawString("Mouse X at: " + mouseX + " Mouse Y at: " + mouseY, 4, 32);	
		/*------------------------------------------------------------*/
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (done) {
			try {
				Thread.sleep(1500);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			this.timer.stop();
			state.setState(GameStateHandler.MENU);
			done = false;
		}
		
		if (!win && !lose) {
			if (player.getX() > 928) {	//928 final? 350 for debug
				win();
			} else if (player.isDead() || !gameClock.getTimer().isRunning()) {
				lose();
			}
		}
		
		yoff = jumpHeight + (0.3 * timer.getDelay());	//Lower multiplier lower gravity, Higher multiplier is high;
														//Ideal number is 0.3 or 0.4 at bounce point (shown below) at 33
		
		if (jumpHeight < 0)
			jumpHeight += 3;
		
		Rectangle[] batRects = new Rectangle[bat.size()];
		for(int i = 0; i < bat.size(); i++){
			batRects[i] = bat.get(i).batBlock();
		}
		
//		Rectangle rBat = batman.batBlock();
		Rectangle rPlayer = player.getPlayerBlock();
		ArrayList<Rectangle> rBlocks = board.getRecBlocks();
		boolean checkBlock = false;
		
		for(int i = 0; i < batRects.length; i++){
			
			if (batRects[i].intersects(rPlayer)){
				player.goDie();
			}
		}
		
		
		if(!player.isDead()){
			for(int i = 0; i < bat.size(); i++)
				if(bat.get(i).getActive())
					bat.get(i).xLoc -= (Math.abs(xoff) * 2) + 5;
		}
		
		for (Rectangle r : rBlocks) {
				
			if (r.intersects(rPlayer.x, rPlayer.y, 4, rPlayer.height-5)) {
				checkBlock = true;
				player.isBlock = true;
				player.isBlockLeft = true;	
			}

			if (r.intersects(rPlayer.x + rPlayer.width - 8, rPlayer.y, 4, rPlayer.height-5)) {
				checkBlock = true;
				player.isBlock = true;
				player.isBlockRight = true;	
			}

			if (r.intersects(rPlayer.x+10, rPlayer.y + rPlayer.height, rPlayer.width-20, 1)) {
				player.setBounds(player.getX(),r.y-33);	//Bounce point range from 33 - 36, original @ 33
														//Increase bounce point should slightly increase the gravity multiplier
				ground = true;		
				jump = false;
			}
			
			if (r.intersects(rPlayer.x+10, rPlayer.y, rPlayer.width-20, 1)) {
				player.setBounds(player.getX(),r.y+32);	
			}
		}

		
		if (board.getP1Trigger().intersects(rPlayer)) {
			onTrigger_1 = true;
		} else {
			onTrigger_1 = false;
		}
		
		if (board.getP2Trigger().intersects(rPlayer)) {
			onTrigger_2 = true;
		} else {
			onTrigger_2 = false;
		}
		
		if (!checkBlock) {
			player.isBlock = false;
			player.isBlockLeft = false;
			player.isBlockRight = false;
		}
		
		for(int i = 0; i < bat.size(); i++)
			bat.get(i).setBounds((int)bat.get(i).xLoc,(int) bat.get(i).yLoc);
		
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
 		
		if (!lose && !win) {		
			if (puzzleOneTriggered) {
				puzzleOne.keyPressed(e);			
			} else if (puzzleTwoTriggered) {
				puzzleTwo.keyPressed(e);
			} else {
				if (e.getKeyCode() == KeyEvent.VK_LEFT && !lose && !win) {
					something[0] = true;
					something[2] = false;
					something[4] = true;
					if (!player.isBlockLeft || player.isBlockRight) {
						xoff = -1 * player.getRunSpeed();
						player.setBounds((int) (player.getX()+xoff), player.getY());
					}
				}
				
				if (e.getKeyCode() == KeyEvent.VK_RIGHT && !lose && !win) {
					something[2] = true;
					something[0] = false;
					something[4] = true;
					if (!player.isBlockRight || player.isBlockLeft) {
						xoff = 1 * player.getRunSpeed();
						player.setBounds((int) (player.getX()+xoff), player.getY());
					}
				}
				
				if (e.getKeyCode() == KeyEvent.VK_UP && !lose && !win) {
					if (ground && !jump) {
						something[1] = true;
						jump = true;
						ground = false;
						jumpHeight = -25;
					}
				}
				
				if (e.getKeyCode() == KeyEvent.VK_SHIFT && !lose && !win) {
						something[3] = true;
				}			
			}		
		}
	}
	
	public void keyReleased(KeyEvent e) {
		
		if (!lose && !win) {		
			if (puzzleOneTriggered) {
				puzzleOne.keyReleased(e);			
			} else if (puzzleTwoTriggered) {
				puzzleTwo.keyReleased(e);			
			} else {
				if (e.getKeyCode() == KeyEvent.VK_LEFT && !lose && !win) {
					something[4] = false;
					something[3] = false;
				}
				
				if (e.getKeyCode() == KeyEvent.VK_RIGHT && !lose && !win) {
					something[4] = false;
					something[3] = false;
				}
				
				if (e.getKeyCode() == KeyEvent.VK_UP && !lose && !win) {
					something[1] = false;		
				}	
				
				if (e.getKeyCode() == KeyEvent.VK_SHIFT && !lose && !win) {
					something[3] = false;
				}			
			}
		}
	}
	
	public void keyTyped(KeyEvent e) {
		
		if (!lose && !win) {
			if (puzzleOneTriggered) {
				puzzleOne.keyTyped(e);
			} else if (puzzleTwoTriggered) {
				puzzleTwo.keyTyped(e);
			} else {
				if (e.getKeyChar() == 'e' && onTrigger_1) {
					puzzleOneTriggered = true;
				}
				
				if (e.getKeyChar() == 'e' && onTrigger_2) {
					puzzleTwoTriggered = true;
				}
			}
		} else {
			if (lose) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					this.timer.stop();	
					state.setState(GameStateHandler.MENU);
				}
			} else if (win) {
				if (Character.isLetter(e.getKeyChar()) && playerInitial.length() < 3) {
					playerInitial = playerInitial + Character.toString(e.getKeyChar());
				}
			}
		}
	}
	
	public void initMap() {
		ground = true;
		player = new Player(state, playerInfo);
		board = new GameBoard(state, levelInfo);
		puzzleOne = new PuzzleOne(state);
		puzzleTwo = new PuzzleTwo(state);
		state.getContainer().add(board);
		state.getContainer().add(player);
		state.getContainer().add(puzzleOne);
		state.getContainer().add(gameClock);
		for(String batValue: batVal){
			String[] bv = batValue.split(" ");
			bat.add(new Batman(Integer.parseInt(bv[0]), Integer.parseInt(bv[1])));
		}
	}
	
	public void win() {
		
		if (!win) {
			win = true;
			winDelay = 0;
			score = (long) (38191.8 * Math.pow(0.999993, 420000-getClock().getTimeSpent()));	//Testing
			highScore = state.getScore().isHighScore(score);
			playerInitial = "";
			gameClock.getTimer().stop();
		}
	}
	
	public void lose() {
		
		if (!lose) {
			saturation = (float) 0.78;
			lose = true;
			gameClock.getTimer().stop();
		}					
	}
	
	public void setTriggerOne(boolean b) {
		puzzleOneTriggered = b;
	}
	
	public void setTriggerTwo(boolean b) {
		puzzleTwoTriggered = b;
	}
	
	public void reset() {
		
		for(int i = 0 ; i < something.length; i++)
			something[i] = false;
		
		ground = true;
		win = false;
		lose = false;
		xoff = 0;
		yoff = 0;
		gameClock.reset();
		bat.clear();
		for(String batValue: batVal){
			String[] bv = batValue.split(" ");
			bat.add(new Batman(Integer.parseInt(bv[0]), Integer.parseInt(bv[1])));
		}
		board.reset(levelInfo);
		player.reset(playerInfo);
		puzzleOneTriggered = false;
		puzzleTwoTriggered = false;
		puzzleOne = new PuzzleOne(state);
		puzzleTwo = new PuzzleTwo(state);
	}
	
	public Timer getTimer() {
		return this.timer;
	}
	
	public GameBoard getBoard() {
		return this.board;
	}
	
	public GameStateHandler getState() {
		return this.state;
	}
	
	public GameClock getClock() {
		return this.gameClock;
	}

}
