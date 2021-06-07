package application.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import application.Settings;
import application.controller.GameController;
import application.model.Bubble;
import application.model.Enemy;
import application.model.Entity;
import application.model.GameModel;
import application.model.Tile;


public class GameView  extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PlayerAnimationHandler playerAnimation;
	private EnemyAnimation enemyAnimations;
	private GameModel model = null;
	private GameController controller;
	private BubbleAnimation bubbleAnimations;
	private List<Integer> removableBubbles;
	public GameView() {
			playerAnimation = new PlayerAnimationHandler();
			enemyAnimations = new EnemyAnimation();
			bubbleAnimations = new BubbleAnimation();
			removableBubbles = new LinkedList<Integer>();
			this.setBackground(Color.BLACK);
	}
	public void setController(GameController controller) {
		this.controller = controller;
	}
	//Funzione chiamata dal controller in caso di multiplayer
	public void loadPlayerTwo() {
		playerAnimation.loadPlayerTwoImages();
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(model == null) return;
		g.setColor(Color.CYAN);
		//
		List<Tile> tiles = model.getTiles();
		for(Tile t : tiles) {
			g.fillRect(t.x, t.y, t.width, t.height);
		}
		int x = model.getPlayerOne().getX();
		int y = model.getPlayerOne().getY();
		g.drawImage(playerAnimation.getCurrentImage(), x, y, Settings.PLAYER_DIMENSION, Settings.PLAYER_DIMENSION, null);
		//Visualizing hitbox for debug purposes, to be removed
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
		}
		List<Enemy> enemies = model.getEnemies();
		for(int i = 0; i <enemies.size();i++) {
			Entity entity = (Entity) enemies.get(i);
			Image img = enemyAnimations.getCurrentImage(i);
			g.drawRect(entity.getX(), entity.getY(), Settings.PLAYER_DIMENSION, Settings.PLAYER_DIMENSION);
			if(img != null) {
				g.drawImage(img, entity.getX(), entity.getY(), Settings.PLAYER_DIMENSION, Settings.PLAYER_DIMENSION, null);				
			}
		}
		List<Bubble> bubbles = model.getBubbles();
		for(int i = 0; i < bubbles.size();i++) {
			if(bubbles.get(i).isAlive()) {
				Entity entity = bubbles.get(i);
				Image img = bubbleAnimations.getCurrentImage(i);
				g.drawRect(entity.getHitbox().x,entity.getHitbox().y,Settings.PLAYER_DIMENSION, Settings.PLAYER_DIMENSION);
				g.drawImage(img,entity.getX(),entity.getY(),Settings.PLAYER_DIMENSION,Settings.PLAYER_DIMENSION,null);				
			}
		}
	}
	public void freeBubbleMemory() {
		if(model == null) return;
		List<Integer> removable = model.getRemovableBubbles();
		for(int i : removable) {
			bubbleAnimations.deleteBubble(i);
		}
		bubbleAnimations.removeNullValues();
	}
	public void update() {
		model = controller.getModel();
		if (!model.isStarted()) return;
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
			enemyAnimations.changeCurrentAnimation(i, enemies.get(i));
		}
		List<Bubble> bubbles = model.getBubbles();
		for(int i = 0; i < bubbles.size();i++) {
			if(!bubbles.get(i).isAlive()) {
				removableBubbles.add(i);
			}
			else {
				bubbleAnimations.changeCurrentAnimation(i);				
			}
		}
		repaint();
	}
}