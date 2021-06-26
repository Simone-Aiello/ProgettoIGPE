package menu.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;


public class OldGameButton extends JButton{

	private static final long serialVersionUID = -6045656011862520468L;
	
	public OldGameButton(String buttonName, int size) {
		super(buttonName);
		this.setFont(new Font("Kongtext Regular", Font.PLAIN, size));
		
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
