package caveGame;

public class GameStateHandler implements Runnable{
	
	public static final int MENU = 1, HIGHSCORE = 2, NEWGAME = 3, STORY = 4, LEVEL = 5, GAMING = 6, EXIT = 0;
	
	private boolean isMenu;
	private boolean isHighScore;
	private boolean isNewGame;
	private boolean isStory;
	private boolean isStoryRead;	
	private boolean isLevelMap;
	private boolean isInGame;
	private GameContainer container;
	private GameMenu menu;
	private HighScore score;
	private LevelHandler level;
	private StoryBoard story;
	private GameLevel game;
	private Thread t;
	private SoundHandler sh;

	public GameStateHandler(GameContainer gc) {
		container = gc;
		sh = new SoundHandler();
		initMenu();
		initHighScore();
		initNewGame();
		initLevelMap();
		t = new Thread(this);
	}
	
	public void start() {		
		t.run();
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (isMenu) {
				
				/*-------------------Play title-----------------*/
				if (sh.getClip() == null || !sh.getClip().isActive())
					sh.playSound("title.aif");
				
				if (!menu.getTimer().isRunning()) {
					/*--------------Stop title clip--------------*/
					sh.getClip().stop();
					container.removeAll();
					container.add(menu);
					menu.getTimer().start();
				}								
			} else if (isHighScore) {
				if (!score.getTimer().isRunning()) {
					container.removeAll();
					container.add(score);
					score.getTimer().start();
				}
			} else if (isNewGame) {
												
				if (isStory || !isStoryRead) {
					isStoryRead = true;
					isStory = true;
					if (!story.getTimer().isRunning()) {
						/*--------------Stop title clip--------------*/
						sh.getClip().stop();
						container.removeAll();
						container.add(story);
						story.reset();
						story.getTimer().start();						
					} 
				} else if (isLevelMap) {				
					if (!level.getTimer().isRunning()) {
						container.removeAll();
						container.add(level);
						level.getTimer().start();
					}
				} else if (isInGame || isStoryRead) {
					if (!isInGame) {
						isInGame = true;
					}
					
					/*-------------------Play game-play-----------------*/
					if (sh.getClip() != null && !sh.getClip().isActive())
						sh.playSound("gp.aif");
					
					if (game == null) {
						game = level.getCurrentGame();
					}
					
					if (!game.getTimer().isRunning()) {
						/*--------------Stop title clip--------------*/
						sh.getClip().stop();
						container.removeAll();
						container.add(game);
						game.reset();
						game.getTimer().start();
						game.getClock().getTimer().start();
					}
				}
			}
		}
	}
	
	public void reset() {
		isHighScore = false;
		isInGame = false;
		isLevelMap = false;
		isMenu = false;
		isNewGame = false;
		isStory = false;
	}
	
	public void setState(int state) {
		
		if (state == MENU) {
			reset();
			isMenu = true;
		} else if (state == HIGHSCORE) {
			reset();
			isHighScore = true;
		} else if (state == NEWGAME) {
			reset();
			isNewGame = true;
			
		} else if (state == STORY) {
			reset();
			isNewGame = true;
			isStory = true;
		} else if (state == LEVEL) {
			reset();
			isNewGame = true;
			isLevelMap = true;
		} else if (state == GAMING) {
			reset();
			isNewGame = true;
			isInGame = true;
		} else {
			System.exit(0);
		}		
	}
	
	public boolean getState(int state) {		
		if (state == MENU)	return isMenu;
		if (state == HIGHSCORE)	return isHighScore;
		if (state == NEWGAME) return isNewGame;
		if (state == STORY) return isStory;
		if (state == LEVEL) return isLevelMap;
		if (state == GAMING) return isInGame;
		return false;
	}
	
	public GameMenu getMenu() {
		return this.menu;
	}
	
	public HighScore getScore() {
		return this.score;
	}
	
	public StoryBoard getStory() {
		return this.story;
	}
	
	public GameContainer getContainer() {
		return this.container;
	}
	
	public LevelHandler getLevel() {
		return this.level;
	}
	
	public GameLevel getGame() {
		return this.game;
	}
	
	public SoundHandler getSound() {
		return this.sh;
	}
	
	public void initMenu() {
		menu = new GameMenu(this);
	}
	
	public void initHighScore() {
		score = new HighScore(this);
	}
	
	public void initNewGame() {
		story = new StoryBoard(this);
	}
	
	public void initLevelMap() {
		level = new LevelHandler(this);
	}
}
