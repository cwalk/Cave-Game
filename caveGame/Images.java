package caveGame;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Images{
	
	private BufferedImage img = null;
	private ImageIcon icon = null;
	private int width, height;
	private int x, y;
	private String type;
	private Rectangle r;

	public Images(String file, String type) {
		System.out.println("Reading " + file);
		try {
			img = ImageIO.read(new File(file));
			icon = new ImageIcon(file);
		} catch(Exception e) {
			System.out.println("Error when reading " + file);
			e.printStackTrace();
		}
		this.type = type;
		this.width = img.getWidth();
		this.height = img.getHeight();
	}
	
	public Images(byte[] array, int x, int y) {
		ByteArrayInputStream in = new ByteArrayInputStream(array);
		try {
			this.img = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.width = img.getWidth();
		this.height = img.getHeight();
	}
	
	public Images(BufferedImage image, int x, int y) {
		this.img = image;
		this.x = x;
		this.y = y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}

	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public Rectangle getRectangle() {
		return this.r;
	}
	
	public String getType() {
		return this.type;
	}
	
	public BufferedImage getImage() {
		return this.img;
	}
	
	public ImageIcon getIcon() {
		return this.icon;
	}
	

}
