package menu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import application.ChangeSceneHandler;
import menu.MenuSettings;

public class StartView extends JPanel implements MouseListener{

	private static final long serialVersionUID = -66985563538395434L;
	private OldGameLabel clickToStart;
	private JLabel logoLabel;
	private FlashingLabel fl;
	
	public StartView() {
		addMouseListener(this);
		setBackground(Color.BLACK);
		setLayout(new BorderLayout());
		try {
			Image logo = ImageIO.read(getClass().getResourceAsStream("/menu/resources/logo.png"));
			logo = logo.getScaledInstance(600, 289, Image.SCALE_SMOOTH);
			logoLabel = new JLabel(new ImageIcon(logo));
			add(logoLabel);
		} catch (IOException e) {
			System.out.println("error while loading logo");
		}		
		clickToStart = new OldGameLabel("CLICK TO START", MenuSettings.INITIAL_ITEM_SIZE);
		clickToStart.setPreferredSize(new Dimension(100, 100));
		clickToStart.setMaximumSize(clickToStart.getPreferredSize());
		add(clickToStart, BorderLayout.SOUTH);
		
		fl = new FlashingLabel(clickToStart);
		fl.start();
		}

	@Override
	public void mouseClicked(MouseEvent e) {
		ChangeSceneHandler.setCurrentScene("initialMenu");
		fl.interrupt();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		ChangeSceneHandler.setCurrentScene("initialMenu");
		fl.interrupt();
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
