package caveGame;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

public class PuzzleOne extends Canvas implements ActionListener{
	
	private enum ColorType {
		R(Color.red), G(Color.green), B(Color.blue), Y(Color.yellow);
		private Color color;
		
		private ColorType(Color color) {
			this.color = color;
		}
		
		public Color getColor() {
			return this.color;
		}		
	}

	private static final long serialVersionUID = 1L;
	
	private static final int width = 720;
	private static final int height = 360;
	private static final int sequenceSize = 5;
	private static final int sequenceSpeed = 1000;
	
	private static final String puzzleTitle = "Color Sequence";
	private static final String solvedHint = "Puzzle is Solved";
	private static final String returnHint = "Press ESC to return";	
	private static final String startHint = "Press ENTER to start";
	private static final String failHint = "You failed";
	private static final String retryHint = "Press ENTER to try again";
	private static final String sequence = "Sequence :";
	private static final String rule1 = "Memorize the colors shown on the screen";
	private static final String rule2 = "Enter the corresponding color sequence";
	private static final String rule3 = "Using R / G / B / Y on the keyboard";
	
	private boolean solved, start, done, fail;
	private int count, timeSpent, tempTime, last=-99;
	
	private ArrayList<Color> colorSequence;
	private ArrayList<Color> solution;
	private ArrayList<Character> playerIn;
	private Color color;
	private Timer timer;
	
	private GameStateHandler state;

	public PuzzleOne(GameStateHandler state) {
		this.state = state;
		this.playerIn = new ArrayList<Character>();
		this.solution = new ArrayList<Color>();
		this.colorSequence = new ArrayList<Color>();
		this.color = Color.black;
		this.count = 0;
		this.timer = new Timer(sequenceSpeed, this);
	}
	
	public void render(Graphics2D g2d) {
		
		g2d.setColor(Color.black);
		g2d.fillRect((state.getContainer().getWidth() - width)/2, 50, width, height);
		
		g2d.setColor(new Color(191, 78, 22));
		float dash[] = {5.0f, 11.0f, 15.0f, 11.0f};
		g2d.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10.0f, dash, 0.0f));
		g2d.drawRect((state.getContainer().getWidth() - width)/2, 50, width, height);
		
		Font eightBit = null;
		try {
			InputStream font = new FileInputStream(new File("./font/8_BIT_WONDER.TTF"));
			eightBit = Font.createFont(Font.TRUETYPE_FONT, font);
		} catch(Exception e) {
			e.printStackTrace();
		}
		eightBit = eightBit.deriveFont(Font.TRUETYPE_FONT, 15);
		g2d.setFont(eightBit);
		g2d.setColor(new Color(191, 78, 22));
		
		if (!solved) {
			if (!start) {
				g2d.drawString(puzzleTitle, (state.getContainer().getWidth() - g2d.getFontMetrics(eightBit).stringWidth(puzzleTitle))/2, 70);
				g2d.drawString(startHint, (state.getContainer().getWidth() - g2d.getFontMetrics(eightBit).stringWidth(startHint))/2, height);
				g2d.drawString(returnHint, (state.getContainer().getWidth() - g2d.getFontMetrics(eightBit).stringWidth(returnHint))/2, height+32);
				g2d.drawString(rule1, (state.getContainer().getWidth() - g2d.getFontMetrics(eightBit).stringWidth(rule1))/2, height/2 - 32);
				g2d.drawString(rule2, (state.getContainer().getWidth() - g2d.getFontMetrics(eightBit).stringWidth(rule2))/2, height/2);
				g2d.drawString(rule3, (state.getContainer().getWidth() - g2d.getFontMetrics(eightBit).stringWidth(rule3))/2, height/2 + 32);				
			} else {
				int offset = 0;
				int startX = (state.getContainer().getWidth() - 400)/2;
				int length = g2d.getFontMetrics(eightBit).stringWidth(sequence);
				g2d.drawString(sequence, startX, 290);
				if (!playerIn.isEmpty()) {
					for (Character c : playerIn) {
						offset+=20;
						g2d.drawString(c.toString(), startX + length + offset, 290);
					}
				}				
				if (fail) {
					g2d.drawString(failHint, (state.getContainer().getWidth() - g2d.getFontMetrics(eightBit).stringWidth(failHint))/2, 322);
					g2d.drawString(retryHint, (state.getContainer().getWidth() - g2d.getFontMetrics(eightBit).stringWidth(retryHint))/2, 354);
					g2d.drawString(returnHint, (state.getContainer().getWidth() - g2d.getFontMetrics(eightBit).stringWidth(returnHint))/2, 386);
				}
				g2d.setColor(color);
				g2d.fillRect((state.getContainer().getWidth() - 400)/2, 75, 400, 200);				
			}			
		} else {
			g2d.drawString(solvedHint, (state.getContainer().getWidth() - g2d.getFontMetrics(eightBit).stringWidth(solvedHint))/2, 70);
			g2d.drawString(returnHint, (state.getContainer().getWidth() - g2d.getFontMetrics(eightBit).stringWidth(returnHint))/2, height);
		}
			
	}
	
	public void reset() {
		colorSequence.clear();
		solution.clear();
		playerIn.clear();
		done = false;
		fail = false;
		color = Color.black;
		count = 0;
	}
	
	public void actionPerformed(ActionEvent e) {
		tempTime += sequenceSpeed;
		if (playerIn.size() == sequenceSize && !solved && !fail) {
			if (solution.equals(colorSequence)) {
				if (!solved) {
					this.timeSpent = tempTime;
				}
				this.solved = true;
				state.getGame().getBoard().getImgBlocks().remove(state.getGame().getBoard().getP1TriggerIndex());
				state.getGame().getBoard().getImgBlocks().remove(state.getGame().getBoard().getP1TriggerIndex());
				state.getGame().getBoard().getImgBlocks().remove(state.getGame().getBoard().getP1TriggerIndex());
				state.getGame().getBoard().getRecBlocks().remove(state.getGame().getBoard().getP1TriggerIndex());
				state.getGame().getBoard().getRecBlocks().remove(state.getGame().getBoard().getP1TriggerIndex());
				state.getGame().getBoard().getRecBlocks().remove(state.getGame().getBoard().getP1TriggerIndex());
				this.start = false;
			} else {
				this.fail = true;
			}
		}
		int n;
		if (count < sequenceSize) {
			while(true){
				Random r = new Random();
				n = r.nextInt(ColorType.values().length);
				if(n != last)
					break;
			}
			last = n;
			color = ColorType.values()[n].getColor();
			colorSequence.add(color);
			count++;
		} else {
			color = Color.black;
			done = true;
		}
	}
	
	public void keyPressed(KeyEvent e) {
		
	}
	
	public void keyReleased(KeyEvent e) {
		
	}
	
	public void keyTyped(KeyEvent e) {
		
		if (e.getKeyChar() == KeyEvent.VK_ENTER) {
			if (!start) {
				start = true;
				this.timer.start();
			} 

			if (start && fail){
				reset();
			}
		}
		
		//if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
		if (e.getKeyChar() == KeyEvent.VK_SPACE) {
			reset();
			start = false;
			count = 0;
			this.timer.stop();
			state.getGame().setTriggerOne(false);
		}
		
		if (start) {		
			char c = e.getKeyChar();
			try {
				if (playerIn.size() < sequenceSize && done) {
					solution.add(ColorType.valueOf(Character.toString(Character.toUpperCase(c))).getColor());
					playerIn.add(c);
				}
			} catch (Exception ex) {
				//ex.printStackTrace();
				System.out.println(ex.toString());
			}
		}		
	}
	
	public boolean isSolved() {
		return this.solved;
	}
	
	public int getTimeSpent() {
		return this.timeSpent;
	}
}
