package menu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import application.ChangeSceneHandler;
import application.GameStarter;
import menu.MenuSettings;

public class InitialMenu extends JPanel{

	private static final long serialVersionUID = 1L;
	private OldGameButton multiplayer;
	private OldGameButton singlePlayer;
	//nel menu iniziale c'è solo la scelta tra single player e multi player
	
	public InitialMenu() {
		this.setBackground(Color.BLACK);
		this.setLayout(new BorderLayout());
		
		JPanel center = new JPanel();
		center.setBackground(Color.BLACK);
		BoxLayout centerLayout = new BoxLayout(center,BoxLayout.LINE_AXIS);
		center.setLayout(centerLayout);
		
		singlePlayer = new OldGameButton("SINGLE PLAYER", MenuSettings.INITIAL_ITEM_SIZE);
		singlePlayer.setPreferredSize(new Dimension(400, 100));
		singlePlayer.setMaximumSize(singlePlayer.getPreferredSize());
		center.add(singlePlayer);
		
		multiplayer = new OldGameButton("MULTIPLAYER", MenuSettings.INITIAL_ITEM_SIZE);
		multiplayer.setPreferredSize(new Dimension(400, 100));
		multiplayer.setMaximumSize(multiplayer.getPreferredSize());
		center.add(multiplayer);
		
		addListeners();

		OldGameLabel select = new OldGameLabel("SELECT GAME MODE", MenuSettings.INITIAL_ITEM_SIZE);
		select.setPreferredSize(new Dimension(800, 200));		
		
		this.add(select, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		
	}
	
	private void addListeners() {
		singlePlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameStarter.startGame(true,null);
			}
		});
		
		multiplayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ChangeSceneHandler.setCurrentScene("multiplayerMenu");				
			}
			
		});
	}
}
