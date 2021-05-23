package application.view;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
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
	private Socket socket;
	private ObjectInputStream reader;
	private GameModel model = null;
	public GameView() {
			playerAnimation = new PlayerAnimationHandler();
			this.setBackground(Color.BLACK);
	}
	public void setSocket(Socket s) {
		try {
			this.socket = s;
			reader = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void paintComponent(Graphics g) {
		if(model == null) return;
		super.paintComponent(g);
		g.setColor(Color.CYAN);
		//
		List<Tile> tiles = model.getTiles();
		for(Tile t : tiles) {
			g.fillRect(t.x, t.y, t.width, t.height);
		}
		int x = model.getPlayer().getX();
		int y = model.getPlayer().getY();
		g.drawImage(playerAnimation.getCurrentImage(), x, y, Settings.PLAYER_DIMENSION, Settings.PLAYER_DIMENSION, null);
		//Visualizing hitbox for debug purposes, to be removed
		int dim = (int) model.getPlayer().getHitbox().getHeight();
		int hx = model.getPlayer().getHitbox().x;
		int hy = model.getPlayer().getHitbox().y;
		g.setColor(Color.RED);
		g.drawRect(hx, hy, dim, dim);
	}
	public void update() {
		try {
			model = (GameModel) reader.readObject();
			int playerXState = model.getPlayer().getXState();
			int playerYState = model.getPlayer().getYState();
			playerAnimation.changeCurrentAnimation(playerXState,playerYState);
			repaint();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
}