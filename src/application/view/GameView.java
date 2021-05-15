package application.view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

import application.Settings;
import application.model.GameModel;
import application.model.Tile;


public class GameView  extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PlayerAnimationHandler playerAnimation;

	public GameView() {
		playerAnimation = new PlayerAnimationHandler();
		this.setBackground(Color.BLACK);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int x = GameModel.getInstance().getPlayer().getX();
		int y = GameModel.getInstance().getPlayer().getY();
		g.drawImage(playerAnimation.getCurrentImage(), x, y, Settings.PLAYER_DIMENSION, Settings.PLAYER_DIMENSION, null);
		//Visualizing hitbox for debug purposes, to be removed
		int dim = (int) GameModel.getInstance().getPlayer().getHitbox().getHeight();
		int hx = GameModel.getInstance().getPlayer().getHitbox().x;
		int hy = GameModel.getInstance().getPlayer().getHitbox().y;
		g.setColor(Color.RED);
		g.drawRect(hx, hy, dim, dim);
		g.setColor(Color.CYAN);
		//
		List<Tile> tiles = GameModel.getInstance().getTiles();
		for(Tile t : tiles) {
			g.fillRect(t.x, t.y, t.width, t.height);
		}
	}
	public void changeAnimation(int type) {
		playerAnimation.changeCurrentAnimation(type);
	}
	public void update() {
		playerAnimation.update();
		repaint();
	}
}