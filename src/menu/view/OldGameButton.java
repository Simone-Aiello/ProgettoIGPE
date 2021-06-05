package menu.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;


public class OldGameButton extends JButton{

	private static final long serialVersionUID = -6045656011862520468L;
	
	public OldGameButton(String buttonName, int size) {
		super(buttonName);
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] fonts = ge.getAllFonts();
		for (Font f : fonts) {
			if (f.getName().equals("Kongtext Regular")) {
				this.setFont(new Font(f.getName(), Font.PLAIN, size));
			}
		}
		
        setForeground(Color.WHITE); //colore del testo bianco
        setBackground(Color.BLACK);
        setOpaque(true);
        setBorderPainted(false);
        //
        setFocusPainted(false);
        //
        
        addMouseListener(new MouseAdapter() {
        	
        	@Override
        	public void mouseEntered(MouseEvent e) {
        		setBackground(new Color(255, 51, 153));
        	}
        	
        	@Override
        	public void mouseExited(MouseEvent e) {
        		setBackground(Color.BLACK);
        	}
        });
        
	}
		
}
