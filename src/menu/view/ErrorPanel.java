package menu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import application.ChangeSceneHandler;
import application.GameStarter;
import application.Settings;
import menu.MenuSettings;

public class ErrorPanel extends JPanel{
	
	private static final long serialVersionUID = 4387334406525363926L;
	private OldGameButton ok;
	private OldGameLabel label;
	private OldGameLabel title;
	public ErrorPanel() {
		label = new OldGameLabel("", MenuSettings.LABEL_TEXT_SIZE);
		title = new OldGameLabel("ERROR...", MenuSettings.ERROR_SIZE);
		title.setPreferredSize(new Dimension(Settings.WINDOW_WIDTH,130));
		ok = new OldGameButton("OK", MenuSettings.MULTIPLAYER_BUTTON_TEXT_SIZE);
		ok.setPreferredSize(new Dimension(Settings.WINDOW_WIDTH,130));
		
		this.setLayout(new BorderLayout());
		this.setBackground(Color.BLACK);
		this.add(label,BorderLayout.CENTER);
		this.add(ok,BorderLayout.SOUTH);
		
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ChangeSceneHandler.returnToPreviousScene();
				ok.setBackground(Color.BLACK);
				
			}
		});
	}
	public void setText(String text) {
		text = text.replaceAll("\n", "<br>");
		label.setText("<html><div style='text-align: center;'>" + text + "</div></html>");
	}

}
