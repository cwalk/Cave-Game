package caveGame;

import java.awt.Component;

import javax.swing.JFrame;

public class GameContainer {
	
	public static int XOFF = 3;
	public static int YOFF = 25;
	
	private static int WIDTH;
	private static int HEIGHT;
	private static String TITLE;
	private JFrame frame;
	
	public GameContainer(String title, int width, int height) {
		WIDTH = width + XOFF*2;	//Set actual drawing stage width to 1024
		HEIGHT = height + 28;	//Set actual drawing stage height to 576
		TITLE = title;	
		initFrame();
	}
	
	public int getWidth() {
		return WIDTH;
	}
	
	public int getHeight() {
		return HEIGHT;
	}
	
	public String getTitle() {
		return TITLE;
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public void initFrame() {
		frame = new JFrame(TITLE);		
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		//frame.setUndecorated(true);
		frame.setResizable(false);
		frame.setVisible(true);	
		frame.addKeyListener(new InputHandler());
	}
	
	public void add(Component component) {
		try {
			this.frame.add(component);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeAll() {
		this.frame.removeAll();
	}
	
	public void remove(Component component) {
		this.frame.remove(component);
	}

}
