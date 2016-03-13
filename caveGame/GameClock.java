package caveGame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.Timer;

public class GameClock extends Canvas implements ActionListener{

	private static final long serialVersionUID = 1L;

	private int min, sec, msec;
	private double timeSpent = 420000;
	
	private Font clockFont;
	private Timer timer;
	
	public GameClock() {
		this.timer = new Timer(15, this);
		
		try {
			clockFont = Font.createFont(Font.TRUETYPE_FONT, new File("./font/V5PRF___.TTF")).deriveFont(Font.PLAIN, 50);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	public void render(Graphics2D g2d){
		
		g2d.setFont(clockFont);
		g2d.setColor(Color.ORANGE);
		if(min < 10 && sec > 10)
			g2d.drawString("0" + min + ":" + sec + ":" +  msec, 50, 70);
		else if(min < 10 && sec < 10)
			g2d.drawString("0" + min + ":" + "0" +  sec + ":" + msec, 50, 70);
		else if(min >= 10 &&sec < 10)
			g2d.drawString(min + ":" + "0" + sec + ":" + msec, 50, 70);
		else
			g2d.drawString(min + ":" + sec + ":" +  msec, 50, 70);
	}

	@Override
	public void actionPerformed(ActionEvent arg0){
						
		if(timeSpent < 0){
			this.timer.stop();
			timeSpent = 0;
		}

		min = (int) (timeSpent/60000);
		sec = (int) ((timeSpent - 60000 * min)/1000);
		msec = (int) (timeSpent%1000);

		timeSpent-=16;
	}
	
	public void reset() {
		this.timer.stop();
		timeSpent = 420000;
	}
	
	public Timer getTimer() {
		return this.timer;
	}
	
	public double getTimeSpent() {
		return this.timeSpent;
	}
}