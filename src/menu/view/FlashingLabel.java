package menu.view;

import java.awt.Color;

import javax.swing.JLabel;

public class FlashingLabel extends Thread{
	private JLabel label;

	public FlashingLabel(JLabel label) {
		super();
		this.label = label;
	}

	@Override
	public void run() {
		while(true) {			
			try {
				label.setForeground(Color.WHITE);				
				Thread.sleep(400);
				label.setForeground(Color.BLACK);
				Thread.sleep(400);
			} catch (InterruptedException e) {
				return;
			}			
		}
	}
}
