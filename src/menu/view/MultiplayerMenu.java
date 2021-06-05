package menu.view;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import application.GameStarter;
import application.Settings;
import menu.MenuSettings;

public class MultiplayerMenu extends JPanel{

	private static final long serialVersionUID = -6931717594607499579L;
	private OldGameButton hostGameButton;
	private OldGameTextField roomCodeField;
	private OldGameButton joinButton;
	
	public MultiplayerMenu() {		
		setBackground(Color.BLACK);
		setLayout(new GridLayout(4,1));
		
		OldGameLabel joinExistingRoom = new OldGameLabel("JOIN EXISTING ROOM", MenuSettings.LABEL_TEXT_SIZE);
		itemSettings(joinExistingRoom, Settings.WINDOW_WIDTH, 50);		
		
		add(joinExistingRoom);
		
		JPanel center = new JPanel();
		center.setBackground(Color.BLACK);
		
		roomCodeField = new OldGameTextField(MenuSettings.TEXTFIELD_TEXT_SIZE);
		itemSettings(roomCodeField, 300, 80);
		center.add(roomCodeField);
		
		joinButton = new OldGameButton("JOIN", MenuSettings.MULTIPLAYER_BUTTON_TEXT_SIZE);
		itemSettings(joinButton, 200, 73);
		center.add(joinButton);
		
		joinButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameStarter.joinRequest(roomCodeField.getText());
			}
		});
		
		this.add(center);
		
		OldGameLabel or = new OldGameLabel("-OR-", MenuSettings.LABEL_TEXT_SIZE);
		itemSettings(or, Settings.WINDOW_WIDTH, 50);
		this.add(or);
		
		hostGameButton = new OldGameButton("HOST GAME", MenuSettings.MULTIPLAYER_BUTTON_TEXT_SIZE);
		itemSettings(hostGameButton, 400, 50);
		hostGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameStarter.hostRequest();
			}
		});
		
		add(hostGameButton);		
		
	}
	
	private void itemSettings(Component component, int width, int height) {
		component.setPreferredSize(new Dimension(width, height));
		component.setMaximumSize(component.getPreferredSize());
	}
	

}
