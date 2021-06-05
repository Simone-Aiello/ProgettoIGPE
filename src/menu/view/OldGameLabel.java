package menu.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class OldGameLabel extends JLabel {

	private static final long serialVersionUID = 8109040877782424111L;

	public OldGameLabel(String text, int size) {
		super(text, SwingConstants.CENTER);
		setText(text);
		setForeground(Color.WHITE);		
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] fonts = ge.getAllFonts();
		for (Font f : fonts) {
			if (f.getName().equals("Kongtext Regular")) {
				this.setFont(new Font(f.getName(), Font.PLAIN, size));
			}
		}

	}

	

}
