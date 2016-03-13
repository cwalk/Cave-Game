package caveGame;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.Timer;

public class PuzzleTwo extends Canvas implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private static final int width = 720;
	private static final int height = 360;
	
	private static final String switches = "Switches : ";
	private static final String solvedHint = "Puzzle is Solved";
	private static final String returnHint = "Press ESC to return";
	private static final String inputHint = "Select the switches and use ENTER to turn ON/OFF";
	
	private boolean solved;
	private boolean A, B, C;
	
	private GameStateHandler state;
	private Timer timer;
	private ImageIcon gates;
	private Rectangle selector;

	public PuzzleTwo(GameStateHandler state) {
		this.state = state;
		this.timer = new Timer(CaveGame.GLOBALTIMER*150, this);
		this.gates = new ImageIcon("./img/comb25.gif");
		this.selector = new Rectangle(471+GameContainer.XOFF, 281+GameContainer.YOFF, 65, 39);
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
			g2d.drawImage(gates.getImage(), (state.getContainer().getWidth()-gates.getIconWidth())/2, 75, gates.getIconWidth(), gates.getIconHeight(), null);
			g2d.drawString(inputHint, (state.getContainer().getWidth()-g2d.getFontMetrics(eightBit).stringWidth(inputHint))/2, 300);
			g2d.drawString(switches, (state.getContainer().getWidth()-gates.getIconWidth())/2, 332);
			g2d.drawString(returnHint, (state.getContainer().getWidth() - g2d.getFontMetrics(eightBit).stringWidth(returnHint))/2, height+32);
			
			g2d.setStroke(new BasicStroke(3f));
			g2d.drawRect(selector.x, selector.y, selector.width, selector.height);
			
			if (A) {
				g2d.setColor(new Color(191, 78, 22));
			} else {
				g2d.setColor(Color.gray);
			}
			g2d.drawString("A", (state.getContainer().getWidth()-gates.getIconWidth())/2 + g2d.getFontMetrics(eightBit).stringWidth(switches) + 64, 332);
			
			if (B) {
				g2d.setColor(new Color(191, 78, 22));
			} else {
				g2d.setColor(Color.gray);
			}
			g2d.drawString("B", (state.getContainer().getWidth()-gates.getIconWidth())/2 + g2d.getFontMetrics(eightBit).stringWidth(switches) + 128, 332);
			
			if (C) {
				g2d.setColor(new Color(191, 78, 22));
			} else {
				g2d.setColor(Color.gray);
			}
			g2d.drawString("C", (state.getContainer().getWidth()-gates.getIconWidth())/2 + g2d.getFontMetrics(eightBit).stringWidth(switches) + 192, 332);
			
		} else {
			g2d.drawString(solvedHint, (state.getContainer().getWidth() - g2d.getFontMetrics(eightBit).stringWidth(solvedHint))/2, 70);
			g2d.drawString(returnHint, (state.getContainer().getWidth() - g2d.getFontMetrics(eightBit).stringWidth(returnHint))/2, height);
		}	
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(!A && !B && C && !solved) {
			solved = true;
			System.out.println("p2 solved" + state.getGame().getBoard().getP2TriggerIndex());
			state.getGame().getBoard().getImgBlocks().remove(state.getGame().getBoard().getP2TriggerIndex());
			state.getGame().getBoard().getImgBlocks().remove(state.getGame().getBoard().getP2TriggerIndex());
			state.getGame().getBoard().getRecBlocks().remove(state.getGame().getBoard().getP2TriggerIndex());
			state.getGame().getBoard().getRecBlocks().remove(state.getGame().getBoard().getP2TriggerIndex());
			this.timer.stop();
		}
		
	}
	
	public void reset() {
		A = false;
		B = false;
		C = false;
		selector.setBounds(471 + GameContainer.XOFF, selector.y, selector.width, selector.height);
	}
	
	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (!solved) {
				if (selector.x == 471 + GameContainer.XOFF) {
					selector.setBounds(601 + GameContainer.XOFF, selector.y, selector.width, selector.height);
				} else if (selector.x == 537 + GameContainer.XOFF) {
					selector.setBounds(471 + GameContainer.XOFF, selector.y, selector.width, selector.height);
				} else if (selector.x == 601 + GameContainer.XOFF) {
					selector.setBounds(537 + GameContainer.XOFF, selector.y, selector.width, selector.height);
				}				
			}
		}
		
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (!solved) {
				if (selector.x == 471 + GameContainer.XOFF) {
					selector.setBounds(537 + GameContainer.XOFF, selector.y, selector.width, selector.height);
				} else if (selector.x == 537 + GameContainer.XOFF) {
					selector.setBounds(601 + GameContainer.XOFF, selector.y, selector.width, selector.height);
				} else if (selector.x == 601 + GameContainer.XOFF) {
					selector.setBounds(471 + GameContainer.XOFF, selector.y, selector.width, selector.height);
				}				
			}
		}
		
	}
	
	public void keyReleased(KeyEvent e) {
		
	}
	
	public void keyTyped(KeyEvent e) {
		
		if (e.getKeyChar() == KeyEvent.VK_ENTER) {
			if (!solved) {
				if (selector.x == 471 + GameContainer.XOFF) {
					A = !A;
				} else if (selector.x == 537 + GameContainer.XOFF) {
					B = !B;
				} else if (selector.x == 601 + GameContainer.XOFF) {
					C = !C;
				}				
			}
		}
		
		//if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
		if (e.getKeyChar() == KeyEvent.VK_SPACE) {
			reset();
			this.timer.stop();
			state.getGame().setTriggerTwo(false);
		}
		
	}
	
	public Timer getTimer() {
		return this.timer;
	}

}
