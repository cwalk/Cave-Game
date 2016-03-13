package caveGame;

import java.util.ArrayList;

public class CaveGame {
	
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 576;
	public static final int GLOBALTIMER = 10;
	
	public static final String VERSION = "1.0.4";	
	public static final String IMAGEDATA = "./data/block.cave";
	public static final String MENUDATA = "./data/menu.cave";
	public static final String SCOREDATA = "./data/score.cave";
	public static final String STORYDATA = "./data/story.cave";
	
	public static GameStateHandler gameState;
	public static GameContainer container;
	
	public static FileHandler fh;
	public static ArrayList<Images> images;

	public CaveGame() {
		container = new GameContainer("C A V E v" + VERSION, WIDTH, HEIGHT);
		gameState = new GameStateHandler(container);
		gameState.setState(GameStateHandler.MENU);
		gameState.start();
	}
	
	public static void main(String args[]) {
		fh = new FileHandler();
		//handleFiles();
		initImages();

		new CaveGame();				
	}
 	
 	public static void initImages() {
 		
 		images = new ArrayList<Images>();
 		
 		System.out.println("Initializing images data...");
 		
 		ArrayList<String> datas = fh.decrypt(IMAGEDATA);
 		for (String s : datas) {
 			String[] sa = s.split(" ");
 			images.add(new Images(sa[1], sa[0]));
 		}
 		
 		System.out.println("Successful initialized images data!");
 		
 	}
 	
 	public static void handleFiles() {
 		/*-------Need change null to output----------*/
 		fh.encrypt("./data/data.txt", "./data/data.cave", IMAGEDATA);
 		fh.encrypt("./data/data3.txt", "./data/data.cave", MENUDATA);
 		fh.encrypt("./data/data4.txt", "./data/data.cave", SCOREDATA);
 		fh.encrypt("./data/data5.txt", "./data/data.cave", STORYDATA);
 		fh.encrypt("./data/data7.txt", "./data/data.cave", "./data/cave1.cave");
 		fh.encrypt("./data/bats.txt", "./data/data.cave", "./data/bats.cave");
 	}
		
}
