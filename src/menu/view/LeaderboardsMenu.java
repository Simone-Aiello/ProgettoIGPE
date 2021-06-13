package menu.view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class LeaderboardsMenu extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9112033860820958900L;
	private List<Score> scoreboard;
	public LeaderboardsMenu() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBackground(Color.BLACK);
		scoreboard = new ArrayList<Score>();
	}
	public void addScore(Score s) {
		scoreboard.add(s);
		this.add(s);
		this.revalidate();
	}
}
