package application.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import application.Settings;
import menu.MenuSettings;
import menu.view.OldGameLabel;

public class TopLayerGameView extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5542460869749235009L;
	
	private OldGameLabel textLabel;
	private OldGameLabel scoreLabel;
	public TopLayerGameView() {
		this.setLayout(new GridLayout(1,2));
		this.setBackground(Color.BLACK);
		this.setPreferredSize(new Dimension(Settings.WINDOW_WIDTH,40));
		textLabel = new OldGameLabel("SCORE: ", MenuSettings.LABEL_TEXT_SIZE);
		scoreLabel = new OldGameLabel("0", MenuSettings.LABEL_TEXT_SIZE);
		//textLabel.setText("SCORE: ");
		textLabel.setAlignmentX(LEFT_ALIGNMENT);
		//textLabel.setForeground(new Color(255, 51, 153));
		textLabel.setHorizontalAlignment(SwingConstants.LEFT);
		scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		//scoreLabel.setText("0");
		scoreLabel.setAlignmentX(RIGHT_ALIGNMENT);
		scoreLabel.setForeground(Color.WHITE);
		this.add(textLabel);
		this.add(scoreLabel);
	}
	public OldGameLabel getScoreLabel() {
		return scoreLabel;
	}
}
