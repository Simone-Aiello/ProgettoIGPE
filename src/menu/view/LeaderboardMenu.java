package menu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

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
		backToMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ChangeSceneHandler.setCurrentScene("initialMenu");
				backToMenu.setBackground(Color.BLACK);
			}
		});
		add(score,BorderLayout.NORTH);
		add(backToMenu,BorderLayout.SOUTH);
		add(scroll,BorderLayout.CENTER);
		
	}
	public void cleanScore() {
		this.remove(scroll);
		s = new ScoreBoard();
		JScrollPane newScroll = new JScrollPane(s); 
		newScroll.setBorder(BorderFactory.createEmptyBorder());
		this.add(newScroll);
		
	}
	public void addScore(Score score2) {
		s.addScore(score2);
		
	}
	public void revalidateScores() {
		s.revalidateScores();		
	}

}
