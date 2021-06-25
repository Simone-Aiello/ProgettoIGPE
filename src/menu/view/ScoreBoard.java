package menu.view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class ScoreBoard extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9112033860820958900L;
	private List<Score> scoreboard;
	public ScoreBoard() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBackground(Color.BLACK);
		scoreboard = new ArrayList<Score>();
	}
	public void addScore(Score s) {
		scoreboard.add(s);
		this.add(s);
	}
	
	public void cleanScore() {
		scoreboard.clear();
	}
	
	public void revalidateScores() {
		this.revalidate();
	}
}
