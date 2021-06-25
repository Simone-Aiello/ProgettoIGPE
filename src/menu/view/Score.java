package menu.view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import menu.MenuSettings;

public class Score extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6537776026302652951L;
	private JLabel nameLabel;
	private JLabel pointLabel;
	private JLabel rankLabel;

	public Score(String rank, String name, String points) {
		this.setLayout(new GridLayout(1, 3));
		this.setBackground(Color.BLACK);
		this.nameLabel = new OldGameLabel(name, MenuSettings.LABEL_TEXT_SIZE);
		this.pointLabel = new OldGameLabel(points, MenuSettings.LABEL_TEXT_SIZE);
		try {
			int rk = Integer.parseInt(rank);
			if (rk % 10 == 1 && rk != 11) {
				rank += "st";
			} else if (rk % 10 == 2 && rk != 12) {
				rank += "nd";
			} else if (rk % 10 == 3 && rk != 13) {
				rank += "rd";
			} else {
				if (!rank.equals("..."))
					rank += "th";
			}
		} catch (NumberFormatException e) {
		}
		this.rankLabel = new OldGameLabel(rank, MenuSettings.LABEL_TEXT_SIZE);
		this.nameLabel.setBackground(Color.BLACK);
		this.pointLabel.setBackground(Color.BLACK);
		this.rankLabel.setBackground(Color.BLACK);
		this.add(rankLabel);
		this.add(nameLabel);
		this.add(pointLabel);
	}

	public void changeColor(Color color) {
		this.setBackground(color);
	}
}
