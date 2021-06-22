package menu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import application.ChangeSceneHandler;
import application.GameStarter;
import application.Settings;
import menu.MenuSettings;

public class GenericMessagePanel extends JPanel {

	private static final long serialVersionUID = 4387334406525363926L;
	private OldGameButton backToMenu;
	private OldGameLabel label;

	public GenericMessagePanel(boolean error) {
		label = new OldGameLabel("", MenuSettings.LABEL_TEXT_SIZE);

		if (error)
			backToMenu = new OldGameButton("OK", MenuSettings.MULTIPLAYER_BUTTON_TEXT_SIZE);
		else
			backToMenu = new OldGameButton("BACK TO MENU", MenuSettings.MULTIPLAYER_BUTTON_TEXT_SIZE);
		backToMenu.setPreferredSize(new Dimension(Settings.WINDOW_WIDTH, 130));
		this.setLayout(new BorderLayout());
		this.setBackground(Color.BLACK);
		this.add(label, BorderLayout.CENTER);
		this.add(backToMenu, BorderLayout.SOUTH);

		if (error) {
			backToMenu.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					GameStarter.resetAll();
					ChangeSceneHandler.returnToPreviousScene();
					backToMenu.setBackground(Color.BLACK);
				}
			});
			
		} else {
			backToMenu.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					GameStarter.resetAll();
					ChangeSceneHandler.setCurrentScene("initialMenu");
					backToMenu.setBackground(Color.BLACK);
				}
			});
		}
	}

	public void setText(String text) {
		text = text.replaceAll("\n", "<br>");
		label.setText("<html><div style='text-align: center;'>" + text + "</div></html>");
	}

}
