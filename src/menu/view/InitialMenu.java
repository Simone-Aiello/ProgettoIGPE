package menu.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import application.ChangeSceneHandler;
import application.GameStarter;
import application.net.DataBaseClient;
import menu.MenuSettings;

public class InitialMenu extends JPanel{

	private static final long serialVersionUID = 1L;
	private OldGameButton multiplayer;
	private OldGameButton singlePlayer;
	private OldGameButton howToPlay;
	private OldGameButton leaderboard;
	private OldGameButton backToLogin;
	//nel menu iniziale c'� solo la scelta tra single player e multi player
	
	public InitialMenu() {
		this.setLayout(new GridLayout(5, 1));
		this.setBackground(Color.BLACK);
		
		singlePlayer = new OldGameButton("SINGLE PLAYER", MenuSettings.INITIAL_ITEM_SIZE);
		singlePlayer.setPreferredSize(new Dimension(400, 100));
		singlePlayer.setMaximumSize(singlePlayer.getPreferredSize());
		this.add(singlePlayer);
		
		multiplayer = new OldGameButton("MULTIPLAYER", MenuSettings.INITIAL_ITEM_SIZE);
		multiplayer.setPreferredSize(new Dimension(400, 100));
		multiplayer.setMaximumSize(multiplayer.getPreferredSize());
		this.add(multiplayer);
		
		howToPlay = new OldGameButton("HOW TO PLAY", MenuSettings.INITIAL_ITEM_SIZE);
		howToPlay.setPreferredSize(new Dimension(400, 100));
		this.add(howToPlay);
		
		leaderboard = new OldGameButton("LEADERBOARD", MenuSettings.INITIAL_ITEM_SIZE);
		leaderboard.setPreferredSize(new Dimension(400, 100));
		this.add(leaderboard);
		
		backToLogin = new OldGameButton("GO BACK TO LOGIN", MenuSettings.INITIAL_ITEM_SIZE);
		backToLogin.setPreferredSize(new Dimension(400, 100));
		backToLogin.setMaximumSize(backToLogin.getPreferredSize());
		this.add(backToLogin);
		
		addListeners();
		
	}
	
	private void addListeners() {
		singlePlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameStarter.startGame(true,null);
				singlePlayer.setBackground(Color.black);
			}
		});
		
		multiplayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ChangeSceneHandler.setCurrentScene("multiplayerMenu");
				multiplayer.setBackground(Color.black);
			}
			
		});
		
		howToPlay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = "CONTROLS: \n"
						+ "- Press A/D to move left/right\n\n"
						+ "- Press SPACE to jump\n\n"
						+ "- Press J to shoot bubbles\n\n"
						+ "GOAL OF THE GAME: \n"
						+ "- Trap enemies by shooting bubbles at them\n\n"
						+ "- Touch bubbles to kill trapped enemies\n\n"
						+ "- Collect food to increase your score\n\n"
						+ "- Be careful not to touch the enemies, you have only one life";
				ChangeSceneHandler.showMessage(text);
				howToPlay.setBackground(Color.BLACK);
				
			}
		});
		
		leaderboard.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ChangeSceneHandler.showLeaderboards(DataBaseClient.getInstance().getLeaderboards());
				DataBaseClient.getInstance().reset();
				leaderboard.setBackground(Color.BLACK);
				
			}
		});
		
		backToLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ChangeSceneHandler.setCurrentScene("loginMenu");
				DataBaseClient.getInstance().reset();
				DataBaseClient.getInstance().setUsername(null);
				backToLogin.setBackground(Color.BLACK);
				
			}		
		});
	}
}