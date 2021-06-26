package menu.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class OldGameLabel extends JLabel {

	private static final long serialVersionUID = 8109040877782424111L;

	public OldGameLabel(String text, int size) {
		super(text, SwingConstants.CENTER);
		setText(text);
		setForeground(Color.WHITE);		
		
		this.setFont(new Font("Kongtext Regular", Font.PLAIN, size));
	}

	

}
