package application.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JLabel;
import javax.swing.JPanel;

import application.Settings;
import application.SoundsHandler;
import application.controller.GameController;
import application.model.Bubble;
import application.model.Enemy;
import application.model.Entity;
import application.model.Food;
import application.model.GameModel;
import application.model.Tile;
import application.model.Utilities;


public class GameView  extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int currentYStatePlayer1 = Utilities.Y_IDLE;
	private int currentYStatePlayer2 = Utilities.Y_IDLE;
	private PlayerAnimationHandler playerAnimation;
	private EnemyAnimation enemyAnimations;
	private GameModel model = null;
	private GameController controller;
	private BubbleAnimation bubbleAnimations;
	private FoodImage foodImage;
	private JLabel scoreLabel;
	private LevelTileHandler tiles;
	private ResourcesLoader loader;
	private ExecutorService executor;
	private int currentLevel = 0;
	public GameView(JLabel scoreLabel) {
			playerAnimation = new PlayerAnimationHandler();
			enemyAnimations = new EnemyAnimation();
			bubbleAnimations = new BubbleAnimation();
			foodImage = new FoodImage();
			tiles = new LevelTileHandler();
			this.setBackground(Color.BLACK);
			this.scoreLabel = scoreLabel;
			loader = new ResourcesLoader();
			executor = Executors.newSingleThreadExecutor();
			changeResources();
	}
	private void changeResources() {
		bubbleAnimations.clear();
		if(loader.getAnimations() != null) {
			enemyAnimations.setMap(loader.getAnimations());
			executor.submit(loader);
		}
		if(model != null) currentLevel = model.getCurrentLevel();
	}
	public void setController(GameController controller) {
		this.controller = controller;
	}
	//Funzione chiamata dal controller in caso di multiplayer
	public void loadPlayerTwo() {
		playerAnimation.loadPlayerTwoImages();
	}
	private void drawFood(Graphics g,boolean drawHitbox) {
		List<Food> food = model.getFood();
		for(Food f : food) {
			if(f.isAlive()) {
				g.drawImage(foodImage.getImage(f.getType()),f.getX(),f.getY(),Settings.FOOD_DIMENSION,Settings.FOOD_DIMENSION,null);
				if(drawHitbox) g.drawRect(f.getHitbox().x, f.getHitbox().y, f.getHitbox().width, f.getHitbox().height);
			}
		}
	}
	private void drawEnemies(Graphics g,boolean drawHitbox) {
		List<Enemy> enemies = model.getEnemies();
		for(int i = 0; i <enemies.size();i++) {
			Entity entity = (Entity) enemies.get(i);
			if(entity.isAlive()) {
				Image img = enemyAnimations.getCurrentImage(i);
				if(img != null) {
					if(drawHitbox) g.drawRect(entity.getHitbox().x, entity.getHitbox().y, entity.getHitbox().width, entity.getHitbox().height);
					g.drawImage(img, entity.getX(), entity.getY(), Settings.PLAYER_DIMENSION, Settings.PLAYER_DIMENSION, null);				
				}				
			}
		}
	}
	private void drawTiles(Graphics g) {
		List<Tile> tiles = model.getTiles();
		for(Tile t : tiles) {
			g.drawImage(this.tiles.getTile(model.getCurrentLevel()), t.x, t.y, Settings.TILE_WIDHT, Settings.TILE_HEIGHT, null);
		}
	}
	private void drawBubbles(Graphics g,boolean drawHitbox) {
		List<Bubble> bubbles = model.getBubbles();
		for(int i = 0; i < bubbles.size();i++) {
			if(bubbles.get(i).isAlive()) {
				Entity entity = bubbles.get(i);
				Image img = bubbleAnimations.getCurrentImage(i);
				if(drawHitbox) g.drawRect(entity.getHitbox().x,entity.getHitbox().y,entity.getHitbox().width, entity.getHitbox().height);
				g.drawImage(img,entity.getX(),entity.getY(),Settings.PLAYER_DIMENSION,Settings.PLAYER_DIMENSION,null);				
			}
		}
	}
	private void drawPlayerOne(Graphics g,boolean drawHitbox) {
		int x = model.getPlayerOne().getX();
		int y = model.getPlayerOne().getY();
		int newYState = model.getPlayerOne().getYState();

		if(newYState == Utilities.SHOOT|| newYState == Utilities.SHOOT_LEFT || newYState == Utilities.SHOOT_RIGHT) {
			SoundsHandler.playSoundEffect("shootSound"); 
		}
		
		if(newYState == Utilities.JUMPING && (currentYStatePlayer1 == Utilities.Y_IDLE || currentYStatePlayer1 == Utilities.FALLING) )
			SoundsHandler.playSoundEffect("jumpSound");
		currentYStatePlayer1 = newYState;
		
		g.drawImage(playerAnimation.getCurrentImage(), x, y, Settings.PLAYER_DIMENSION, Settings.PLAYER_DIMENSION, null);
		int dim = (int) model.getPlayerOne().getHitbox().getHeight();
		int hx = model.getPlayerOne().getHitbox().x;
		int hy = model.getPlayerOne().getHitbox().y;
		if(drawHitbox) g.drawRect(hx, hy, dim, dim);
	}
	private void drawPlayerTwo(Graphics g,boolean drawHitbox) {
		int playerTwoX = model.getPlayerTwo().getX();
		int playerTwoY = model.getPlayerTwo().getY();
		
		int newYState = model.getPlayerTwo().getYState();
		
		if(newYState == Utilities.SHOOT || newYState == Utilities.SHOOT_LEFT || newYState == Utilities.SHOOT_RIGHT ) 
			SoundsHandler.playSoundEffect("shootSound");

		if(newYState == Utilities.JUMPING && (currentYStatePlayer2 == Utilities.Y_IDLE || currentYStatePlayer2 == Utilities.FALLING))
			SoundsHandler.playSoundEffect("jumpSound");
		currentYStatePlayer2 = newYState;
		
		if(drawHitbox) g.drawRect(model.getPlayerTwo().getX(), model.getPlayerTwo().getY(), Settings.PLAYER_DIMENSION, Settings.PLAYER_DIMENSION);
		g.drawImage(playerAnimation.getPlayerTwoImage(), playerTwoX, playerTwoY, Settings.PLAYER_DIMENSION, Settings.PLAYER_DIMENSION, null);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(model == null) return;
		g.setColor(Color.RED); //Colore delle hitbox in caso servano per debug
		drawTiles(g);
		drawFood(g, true);
		drawEnemies(g,true);
		drawBubbles(g, true);
		drawPlayerOne(g, true);
		if(model.getPlayerTwo() != null) drawPlayerTwo(g, true);
		/*List<Tile> tiles = model.getTiles();
		for(Tile t : tiles) {
			g.drawImage(this.tiles.getTile(model.getCurrentLevel()), t.x, t.y, Settings.TILE_WIDHT, Settings.TILE_HEIGHT, null);
		}*/
		/*int x = model.getPlayerOne().getX();
		int y = model.getPlayerOne().getY();
		g.drawImage(playerAnimation.getCurrentImage(), x, y, Settings.PLAYER_DIMENSION, Settings.PLAYER_DIMENSION, null);
		int dim = (int) model.getPlayerOne().getHitbox().getHeight();
		int hx = model.getPlayerOne().getHitbox().x;
		int hy = model.getPlayerOne().getHitbox().y;
		g.setColor(Color.RED);
		g.drawRect(hx, hy, dim, dim);
		if(model.getPlayerTwo() != null) {
			int playerTwoX = model.getPlayerTwo().getX();
			int playerTwoY = model.getPlayerTwo().getY();
			g.drawRect(model.getPlayerTwo().getX(), model.getPlayerTwo().getY(), Settings.PLAYER_DIMENSION, Settings.PLAYER_DIMENSION);
			g.drawImage(playerAnimation.getPlayerTwoImage(), playerTwoX, playerTwoY, Settings.PLAYER_DIMENSION, Settings.PLAYER_DIMENSION, null);
		}*/
		/*List<Enemy> enemies = model.getEnemies();
		for(int i = 0; i <enemies.size();i++) {
			Entity entity = (Entity) enemies.get(i);
			if(entity.isAlive()) {
				Image img = enemyAnimations.getCurrentImage(i);
				if(img != null) {
					g.drawRect(entity.getHitbox().x, entity.getHitbox().y, Settings.PLAYER_DIMENSION, Settings.PLAYER_DIMENSION);
					g.drawImage(img, entity.getX(), entity.getY(), Settings.PLAYER_DIMENSION, Settings.PLAYER_DIMENSION, null);				
				}				
			}
		}*/
		/*List<Bubble> bubbles = model.getBubbles();
		for(int i = 0; i < bubbles.size();i++) {
			if(bubbles.get(i).isAlive()) {
				Entity entity = bubbles.get(i);
				Image img = bubbleAnimations.getCurrentImage(i);
				g.drawRect(entity.getHitbox().x,entity.getHitbox().y,Settings.PLAYER_DIMENSION, Settings.PLAYER_DIMENSION);
				g.drawImage(img,entity.getX(),entity.getY(),Settings.PLAYER_DIMENSION,Settings.PLAYER_DIMENSION,null);				
			}
		}*/
		/*List<Food> food = model.getFood();
		for(Food f : food) {
			if(f.isAlive()) g.drawImage(foodImage.getImage(f.getType()),f.getX(),f.getY(),Settings.FOOD_DIMENSION,Settings.FOOD_DIMENSION,null);
		}*/
	}
	public void update() {
		model = controller.getModel();
		if (!model.isStarted()) return;
		scoreLabel.setText("" + model.getScore());
		if(currentLevel != model.getCurrentLevel()) {
			/*bubbleAnimations.clear();
			enemyAnimations.clear();
			currentLevel = model.getCurrentLevel();*/
			changeResources();
		}
		int playerXState = model.getPlayerOne().getXState();
		int playerYState = model.getPlayerOne().getYState();
		playerAnimation.changeCurrentAnimation(playerXState, playerYState);
		if(model.getPlayerTwo() != null) {
			int playerTwoXState = model.getPlayerTwo().getXState();
			int playerTwoYState = model.getPlayerTwo().getYState();
			playerAnimation.changeCurrentPlayerTwoAnimation(playerTwoXState, playerTwoYState);
		}
		List<Enemy> enemies = model.getEnemies();
		for (int i = 0; i < enemies.size(); i++) {
			if(((Entity)enemies.get(i)).isAlive()){
				enemyAnimations.changeCurrentAnimation(i, enemies.get(i));				
			}
		}
		List<Bubble> bubbles = model.getBubbles();
		for(int i = 0; i < bubbles.size();i++) {
			bubbleAnimations.changeCurrentAnimation(i,bubbles.get(i));				
		}
		repaint();
	}
}