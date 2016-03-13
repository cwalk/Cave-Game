package caveGame;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Player extends Canvas{
	
	private static final long serialVersionUID = 1L;

	protected boolean isGay;
	private boolean isRight, isLeft, isWalk, isRun, isAlive;
	public boolean isBlock, isBlockLeft, isBlockRight;
	
	private GameStateHandler state;
	private String info;
	private String name;
	private int x, y;
	private int dist;
	private double xoff, yoff, runSpeed;
	private Rectangle r;
	private ImageIcon icon;
	
	public Player(GameStateHandler state, String info) {
		this.state = state;
		this.info = info;
		this.isGay = true;
		initPlayer();
	}	
	
	public void initPlayer() {
		isAlive = true;
		String[] s = info.split(" ");
		this.name = s[0];
		this.x = Integer.parseInt(s[1]) + GameContainer.XOFF;
		this.y = Integer.parseInt(s[2]) + GameContainer.YOFF;
		this.icon = CaveGame.images.get(23).getIcon();
		r = new Rectangle(this.x, this.y, icon.getIconWidth(), icon.getIconHeight());
		this.dist = this.x;
	}
	
	public void reset(String info) {
		xoff = 0;
		yoff = 0;
		this.info = info;
		initPlayer();
	}
	
	public void render(Graphics2D g2d, boolean left, boolean right, boolean jump, boolean run, boolean walk, double yOff) {

		
		yoff = yOff;
		xoff = 0; 
		isLeft = left;
		isRight = right;
		isRun = run;
		isWalk = walk;
		runSpeed = 1.8;
		
		if(isRun){
			runSpeed = 2.5;
		}
		
		if(isLeft && isWalk){
			xoff = -2;
		}
		
		if(isRight && isWalk){
			xoff = 2;
		}
		
		if (isLeft) {
			
			if(!isRun && !isWalk)
				icon = CaveGame.images.get(22).getIcon();
			if(isRun && isWalk)
				icon = CaveGame.images.get(18).getIcon();
			if(!isRun && isWalk)
				icon = CaveGame.images.get(16).getIcon();
			
		} else if (isRight) {
			if(!isRun && !isWalk)
				icon = CaveGame.images.get(23).getIcon();
			if(isRun && isWalk)
				icon = CaveGame.images.get(19).getIcon();
			if(!isRun && isWalk)
				icon = CaveGame.images.get(17).getIcon();
		}
		
		if (!isBlockLeft && isBlockRight) {
			xoff = -1 * runSpeed;
		}
		
		if (isBlockLeft && !isBlockRight) {
			xoff = runSpeed;
		}
		
		if (isAlive) {
			
			if (!isBlock) {
				if (x < 0) {
					x = 0;
				}
				
				if (state.getGame().getBoard().getLastBlock().x > state.getContainer().getWidth()-32-GameContainer.XOFF) {
					if (x < 400 ) {
						x += xoff * runSpeed;
					}
					else {
						x = 400;
						state.getGame().getBoard().increment((int)(xoff * runSpeed));
					}
				} else {
						x += xoff * runSpeed;
				}
					
			}
			y += yoff;
		}
		
		r.setBounds(this.x, this.y, icon.getIconWidth(), icon.getIconHeight());		
		
		System.out.println(dist);
		if(isBlockLeft == false && isBlockRight == false)
			dist += xoff*runSpeed;
		
		g2d.drawImage(icon.getImage(), this.x, this.y, icon.getIconWidth(), icon.getIconHeight(), null);
		
		if (y > 600) {
			isAlive = false;
		}
//		
	}
	
	public void setBounds(int x, int y) {
		this.x = x;
		this.y = y;
		r.setBounds(this.x, this.y, icon.getIconWidth(), icon.getIconHeight());
	}
	
	public ImageIcon getIcon() {
		return this.icon;
	}
	
	public Rectangle getPlayerBlock() {
		return this.r;
	}

	public String getName() {
		return this.name;
	}
	
	public GameStateHandler getState() {
		return this.state;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public boolean isDead() {
		return !this.isAlive;
	}
	
	public void goDie() {
		isAlive = false;
	}
	
	public double getRunSpeed(){
		return this.runSpeed;
	}

	public int getDist(){
		return this.dist;
	}
}
