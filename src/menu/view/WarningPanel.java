package menu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import application.ChangeSceneHandler;
import application.GameStarter;
import application.Settings;
import menu.MenuSettings;

public class WarningPanel extends JPanel{
	
	private static final long serialVersionUID = 4387334406525363926L;
	private OldGameButton yesButton;
	private OldGameButton noButton;
	private OldGameLabel label;
	private OldGameLabel title;
	private String nextScene;
	

	public WarningPanel() {
		label = new OldGameLabel("", MenuSettings.LABEL_TEXT_SIZE);
		title = new OldGameLabel("WARNING...", MenuSettings.ERROR_SIZE);
		title.setPreferredSize(new Dimension(Settings.WINDOW_WIDTH,130));
		
		yesButton = new OldGameButton("YES", MenuSettings.MULTIPLAYER_BUTTON_TEXT_SIZE);
		yesButton.setPreferredSize(new Dimension(Settings.WINDOW_WIDTH/2,130));
		
		noButton = new OldGameButton("NO", MenuSettings.MULTIPLAYER_BUTTON_TEXT_SIZE);
		noButton.setPreferredSize(new Dimension(Settings.WINDOW_WIDTH/2,130));
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setPreferredSize(new Dimension(Settings.WINDOW_WIDTH,130));
		buttonsPanel.setMaximumSize(getPreferredSize());
		buttonsPanel.setLayout(new GridLayout(1,2));
		buttonsPanel.add(yesButton);
		buttonsPanel.add(noButton);
		
		this.setLayout(new BorderLayout());
		this.setBackground(Color.BLACK);
		this.add(label,BorderLayout.CENTER);
		this.add(buttonsPanel,BorderLayout.SOUTH);
		
		yesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GameStarter.resetAll();
				ChangeSceneHandler.setCurrentScene(nextScene);
				yesButton.setBackground(Color.BLACK);
				
			}
		});
		
		noButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ChangeSceneHandler.returnToPreviousScene();
				noButton.setBackground(Color.BLACK);
				
			}
		});
	}
	
	public void setNextScene(String nextScene) {
		this.nextScene = nextScene;
	}
	
	public void setText(String text) {
		text = text.replaceAll("\n", "<br>");
		label.setText("<html><div style='text-align: center;'>" + text + "</div></html>");
	}

}
