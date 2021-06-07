package application.net.server;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ServerStarter {

	public static void main(String[] args) {
		JTextArea area = new JTextArea();
		Server server = new Server(area);
		JFrame f = new JFrame();
		JButton start = new JButton("Start");
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(2, 1));
		f.setSize(400,400);
		f.add(p);
		p.add(start);
		p.add(new JScrollPane(area));
		start.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				Thread t = new Thread(server);
				t.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {}	
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
