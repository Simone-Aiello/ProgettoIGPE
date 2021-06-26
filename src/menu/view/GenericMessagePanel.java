package menu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

import application.ChangeSceneHandler;
import application.GameStarter;
import application.Settings;
import menu.MenuSettings;

public class GenericMessagePanel extends JPanel {

	private static final long serialVersionUID = 4387334406525363926L;
	private OldGameButton backToMenu;
	private OldGameLabel label;
	public GenericMessagePanel() {
		label = new OldGameLabel("", MenuSettings.LABEL_TEXT_SIZE);
		backToMenu = new OldGameButton("BACK TO MENU", MenuSettings.MULTIPLAYER_BUTTON_TEXT_SIZE);
		backToMenu.setPreferredSize(new Dimension(Settings.WINDOW_WIDTH,130));
		this.setLayout(new BorderLayout());
		this.setBackground(Color.BLACK);
		this.add(label,BorderLayout.CENTER);
		this.add(backToMenu,BorderLayout.SOUTH);
		backToMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameStarter.resetAll();
				ChangeSceneHandler.setCurrentScene("initialMenu");
				backToMenu.setBackground(Color.BLACK);
				
			}
		});
	}
	public void setText(String text) {
		text = text.replaceAll("\n", "<br>");
		label.setText("<html><div style='text-align: center;'>" + text + "</div></html>");
	}
}
