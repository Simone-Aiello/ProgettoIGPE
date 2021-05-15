package application.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import application.Settings;
import application.model.GameModel;


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
		g.drawImage(playerAnimation.getCurrentImage(), x, y, Settings.PLAYER_DIMENSION +32, Settings.PLAYER_DIMENSION+32, null);
	}
	public void changeAnimation(int type) {
		playerAnimation.changeCurrentAnimation(type);
	}
	public void update() {
		playerAnimation.update();
		repaint();
	}
}