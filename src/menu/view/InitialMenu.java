package menu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
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
	//nel menu iniziale c'� solo la scelta tra single player e multi player
	
	public InitialMenu() {
		this.setLayout(new GridLayout(4, 1));
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
		//multiplayer.setMaximumSize(multiplayer.getPreferredSize());
		this.add(howToPlay);
		
		leaderboard = new OldGameButton("LEADERBOARD", MenuSettings.INITIAL_ITEM_SIZE);
		leaderboard.setPreferredSize(new Dimension(400, 100));
		//multiplayer.setMaximumSize(multiplayer.getPreferredSize());
		this.add(leaderboard);
		
		addListeners();
		
		
		/*this.setBackground(Color.BLACK);
		this.setLayout(new BorderLayout());
		
		JPanel center = new JPanel();
		center.setBackground(Color.BLACK);
		//BoxLayout centerLayout = new BoxLayout(center,BoxLayout.LINE_AXIS);
		//center.setLayout(centerLayout);
		center.setLayout(new GridLayout(2,2));
		singlePlayer = new OldGameButton("SINGLE PLAYER", MenuSettings.INITIAL_ITEM_SIZE);
		singlePlayer.setPreferredSize(new Dimension(400, 100));
		singlePlayer.setMaximumSize(singlePlayer.getPreferredSize());
		center.add(singlePlayer);
		
		multiplayer = new OldGameButton("MULTIPLAYER", MenuSettings.INITIAL_ITEM_SIZE);
		multiplayer.setPreferredSize(new Dimension(400, 100));
		multiplayer.setMaximumSize(multiplayer.getPreferredSize());
		center.add(multiplayer);
		
		howToPlay = new OldGameButton("HOW TO PLAY", MenuSettings.INITIAL_ITEM_SIZE);
		howToPlay.setPreferredSize(new Dimension(400, 100));
		//multiplayer.setMaximumSize(multiplayer.getPreferredSize());
		center.add(howToPlay);
		
		leaderboard = new OldGameButton("LEADERBOARD", MenuSettings.INITIAL_ITEM_SIZE);
		leaderboard.setPreferredSize(new Dimension(400, 100));
		//multiplayer.setMaximumSize(multiplayer.getPreferredSize());
		center.add(leaderboard);
		addListeners();

		OldGameLabel select = new OldGameLabel("SELECT GAME MODE", MenuSettings.INITIAL_ITEM_SIZE);
		select.setPreferredSize(new Dimension(800, 250));		
		
		this.add(select, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);*/
		
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
		
		howToPlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
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
		
		leaderboard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ChangeSceneHandler.showLeaderboards(DataBaseClient.getInstance().getLeaderboards());
				DataBaseClient.getInstance().reset();
				leaderboard.setBackground(Color.BLACK);
			}
		});
	}
}