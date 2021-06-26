package menu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import application.ChangeSceneHandler;
import application.Settings;
import menu.MenuSettings;

public class LeaderboardMenu extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3179713163188149316L;
	ScoreBoard s = new ScoreBoard();
	JButton backToMenu = new OldGameButton("BACK TO MENU",MenuSettings.MULTIPLAYER_BUTTON_TEXT_SIZE);
	Score score = new Score("Rank","Username", "High score");
	JScrollPane scroll = new JScrollPane(s);
	public LeaderboardMenu() {
		setBackground(Color.BLACK);
		setLayout(new BorderLayout());
		scroll.setBorder(BorderFactory.createEmptyBorder());
		score.setPreferredSize(new Dimension(Settings.WINDOW_WIDTH, 30));
		backToMenu.setPreferredSize(new Dimension(Settings.WINDOW_WIDTH, 60));
		backToMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ChangeSceneHandler.setCurrentScene("initialMenu");
				backToMenu.setBackground(Color.BLACK);
			}
		});
		add(score,BorderLayout.NORTH);
		add(scroll,BorderLayout.CENTER);
		add(backToMenu,BorderLayout.SOUTH);
		
	}
	public void addScore(Score score2) {
		s.addScore(score2);
	}
	public void revalidateScores() {
		s.revalidateScores();		
	}

}
