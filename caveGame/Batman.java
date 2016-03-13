package caveGame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Batman {

	private boolean isActive;
	private Rectangle batShield;
	private ImageIcon icon;
	boolean beginning;
	double xLoc, yLoc;
	double xOrg, yOrg;
	
	
	public Batman(double x, double y) {
		initBat(x, y);
	}
	
	public void initBat(double x, double y){
		this.beginning = true;
		this.icon = CaveGame.images.get(26).getIcon();
		this.xLoc = x;
		this.yLoc = y;
		this.batShield = new Rectangle((int) this.xLoc,(int) this.yLoc, icon.getIconWidth(), icon.getIconHeight());
		this.xOrg = x;
		this.yOrg = y;
	}

	
	public void reset(ArrayList<String> batVal){
		
		
//		xLoc = xOrg;
//		yLoc = yOrg;
//		this.batShield = new Rectangle((int) this.xOrg, (int) this.yOrg, icon.getIconWidth(), icon.getIconHeight());
	}
	
	public Batman(GameStateHandler state) {
		// TODO Auto-generated constructor stub
	}

	public void render(Graphics g2d) {
				
		
		//Move bat from right side of screen to left
		g2d.drawImage(icon.getImage(), (int) xLoc, (int) yLoc, null);
	}
	
	public void setBounds(int x, int y) {
		this.xLoc = x;
		this.yLoc = y;
		batShield.setBounds((int)this.xLoc,(int) this.yLoc, icon.getIconWidth(), icon.getIconHeight());
	}
		
	public Rectangle batBlock(){
		return this.batShield;		
	}
	
	public void setActive(){
		this.isActive = true;
	}
	
	public boolean getActive(){
		return this.isActive;
	}

	public void spawnBats (Graphics2D g2d, Player player, ArrayList<Batman> bat, int spwnNum){
		if(player.getDist() >= 247){
			bat.get(spwnNum).render(g2d);
			spwnNum++;
		}
		
		if(player.getDist() >= 800){
			bat.get(spwnNum).render(g2d);
			bat.get(spwnNum+1).render(g2d);
			spwnNum += 2;
		}
		
		if(player.getDist() >= 9856){
			bat.get(spwnNum).render(g2d);
			bat.get(spwnNum+1).render(g2d);
			spwnNum += 2;
		}
		
		
		if(player.getDist() >= 12992){
			bat.get(spwnNum).render(g2d);
			spwnNum++;
		}
		
		if(player.getDist() >= 10912){
			bat.get(spwnNum).render(g2d);
			bat.get(spwnNum+1).render(g2d);
			spwnNum += 2;
		}

		if(player.getDist() >= 13120){
			bat.get(spwnNum).render(g2d);
			bat.get(spwnNum+1).render(g2d);
			spwnNum += 2;
		}
		
		if(player.getDist() >= 13728){
			bat.get(spwnNum).render(g2d);
			bat.get(spwnNum+1).render(g2d);
			spwnNum += 2;
		}
		
		if(player.getDist() >= 14528){
			bat.get(spwnNum).render(g2d);
			bat.get(spwnNum+1).render(g2d);
			spwnNum += 2;
		}
		
		if(player.getDist() >= 15360){
			bat.get(spwnNum).render(g2d);
			bat.get(spwnNum+1).render(g2d);
			spwnNum += 2;
		}
		
		if(player.getDist() >= 16288){
			bat.get(spwnNum).render(g2d);
			bat.get(spwnNum+1).render(g2d);
			spwnNum += 2;
		}
		
		if(player.getDist() >= 16672){
			bat.get(spwnNum).render(g2d);
			bat.get(spwnNum+1).render(g2d);
			spwnNum += 2;
		}
	}
//	public void checkCollision(Rectangle r, Rectangle br, ArrayList<Batman> bat, int i) {
//
//			
//			if (r.intersects(br.x+11, br.y, br.width-22, 11)){
//				bat.get(i).yLoc += 14;
//			}
//			
//			if (r.intersects(br.x+11, br.y + br.height-11, br.width-22, 11)){
//				bat.get(0).yLoc -= 14;
//			}
//			
//			if (r.intersects(br.x, br.y+11, 11, br.height-22)){
//				if(bat.get(0).xLoc > 0)
//					bat.get(0).xLoc += 14;
//			}
//			
//			if (r.intersects(br.x + br.width-11, br.y+11, 11, br.height-22)){
//				bat.get(0).xLoc -=14;
//			}
//			
///*			if(r.intersects(br.x, br.y, 11, 11)){
////				batman.xLoc += nX +2;
////				batman.yLoc += nY +2;
//				bat.get(i).xLoc += 2;
//				bat.get(i).yLoc += 2;
//			}
//			
//			if(r.intersects(br.batBlock().x + br.batBlock().width-11, br.batBlock().y, 11, 11)){
////				batman.xLoc -= nX +2;
////				batman.yLoc += nY +2;
//				bat.get(i).xLoc -= 2;
//				bat.get(i).yLoc += 2;
//			}
//			
//			if(r.intersects(br.batBlock().x, br.batBlock().y + br.batBlock().height-11, 11, 11)){
////				batman.xLoc += nX +2;
////				batman.yLoc -= nY +2;
//				if(br.xLoc > 0)
//					br.xLoc += 2;
//				br.yLoc -= 2;
//			}
//			
//			if(r.intersects(br.batBlock().x + br.batBlock().width-11, br.batBlock().y + br.batBlock().height-11, 11, 11)){
////				batman.xLoc -= nX +2;
////				batman.yLoc -= nY +2;
//				br.xLoc -= 2;
//				br.yLoc -= 2;
//			}	
//*/ 
//	}
}