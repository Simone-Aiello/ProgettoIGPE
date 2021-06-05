package menu.view;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.JTextField;

public class OldGameTextField extends JTextField{

	private static final long serialVersionUID = 8109040877782424111L;

	public OldGameTextField(int fontSize) {
		super();
		/*GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] fonts = ge.getAllFonts();
		for (Font f : fonts) {
			if (f.getName().equals("Kongtext Regular")) {
				this.setFont(new Font(f.getName(), Font.PLAIN, fontSize));
			}
		}*/
		this.setFont(new Font("Kongtext Regular", Font.PLAIN, fontSize));
	}



}
