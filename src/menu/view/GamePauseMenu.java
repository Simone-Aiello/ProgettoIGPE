package menu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import application.ChangeSceneHandler;
import application.GameStarter;
import application.Settings;
import menu.MenuSettings;

public class GamePauseMenu extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9071467518278510627L;
	private OldGameButton quitButton;
	private OldGameButton resumeButton;
	private OldGameLabel messageLabel;
	private String alertText;
	public GamePauseMenu(){
		this.setBackground(Color.BLACK);
		this.setLayout(new BorderLayout());
		quitButton = new OldGameButton("QUIT", MenuSettings.MULTIPLAYER_BUTTON_TEXT_SIZE);
		quitButton.setPreferredSize(new Dimension(Settings.WINDOW_WIDTH,130));
		quitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int res = JOptionPane.showConfirmDialog(null, alertText);
				if(res == JOptionPane.YES_OPTION) {
					GameStarter.resetAll();
					ChangeSceneHandler.setCurrentScene("initialMenu");
				}
			}
		});
		
		resumeButton = new OldGameButton("RESUME", MenuSettings.MULTIPLAYER_BUTTON_TEXT_SIZE);
		resumeButton.setPreferredSize(new Dimension(Settings.WINDOW_WIDTH,130));
		resumeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GameStarter.pauseGame(false);
			}
		});
		
		JPanel bottom = new JPanel();
		bottom.setLayout(new GridLayout(1,2));
		bottom.add(resumeButton);
		bottom.add(quitButton);
		messageLabel = new OldGameLabel("Game paused",MenuSettings.LABEL_TEXT_SIZE);
		messageLabel.setBackground(Color.BLACK);
		this.add(messageLabel,BorderLayout.CENTER);
		this.add(bottom,BorderLayout.SOUTH);
	}
	public void setLabelText(String txt) {
		messageLabel.setText(txt);
	}
	public void setAlertText(String string) {
		alertText = string;
	}
}
