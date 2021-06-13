package menu.view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import menu.MenuSettings;

public class Score extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6537776026302652951L;
	private JLabel nameLabel;
	private JLabel pointLabel;
	public Score(String name, String points) {
		//super();
		this.setLayout(new GridLayout(1,2));
		this.nameLabel = new OldGameLabel(name, MenuSettings.LABEL_TEXT_SIZE);
		this.pointLabel = new OldGameLabel(points, MenuSettings.LABEL_TEXT_SIZE);
		this.nameLabel.setBackground(Color.BLACK);
		this.pointLabel.setBackground(Color.BLACK);
		this.add(nameLabel);
		this.add(pointLabel);
	}
}
