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
import menu.MenuSettings;

public class GenericMessagePanel extends JPanel {

	private static final long serialVersionUID = 4387334406525363926L;
	private OldGameButton backToMenu;
	private OldGameLabel label;
	public GenericMessagePanel() {
		label = new OldGameLabel("", MenuSettings.LABEL_TEXT_SIZE);
		backToMenu = new OldGameButton("Back to main menu", MenuSettings.MULTIPLAYER_BUTTON_TEXT_SIZE);
		backToMenu.setPreferredSize(new Dimension(200,130));
		this.setLayout(new BorderLayout());
		this.setBackground(Color.BLACK);
		this.add(label,BorderLayout.CENTER);
		this.add(backToMenu,BorderLayout.SOUTH);
		backToMenu.addMouseListener(new MouseAdapter() {
			@Override
			 public void mouseClicked(MouseEvent e) {
				GameStarter.resetAll();
				ChangeSceneHandler.setCurrentScene("initialMenu");
			}
		});
	}
	public void setText(String text) {
		text = text.replaceAll("\n", "<br>");
		label.setText("<html><div style='text-align: center;'>" + text + "</div></html>");
	}
}
/*
String text = "In early March, the city of Topeka, Kansas," + "<br>" +
              "temporarily changed its name to Google..." + "<br>" + "<br>" +
              "...in an attempt to capture a spot" + "<br>" +
              "in Google's new broadband/fiber-optics project." + "<br>" + "<br>" +"<br>" +
              "source: http://en.wikipedia.org/wiki/Google_server#Oil_Tanker_Data_Center";
JLabel label = new JLabel("<html><div style='text-align: center;'>" + text + "</div></html>");
*/
