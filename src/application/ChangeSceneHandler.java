package application;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import application.view.TopLayerGameView;
import menu.view.GenericMessagePanel;


public class ChangeSceneHandler {
	
	public static HashMap<String, JPanel> scenes;
	private static JFrame window;
	private static JPanel currentScene = null;
	private static ChangeSceneHandler instance = null;
	
	public void init(JFrame w) {
		scenes = new HashMap<String, JPanel>();
		window = w;
		readFont(new File(getClass().getResource("/application/resources/font/oldGameFont.ttf").getPath()));	
	}
	
	public static ChangeSceneHandler getInstance() {
		if(instance == null)
			instance = new ChangeSceneHandler();
		return instance;
	}
	
	public static void add(String name, JPanel scene) {
		scenes.put(name, scene);
	}
	
	public static void setCurrentScene(String current) {
		if(currentScene != null)
			window.remove(currentScene);
		currentScene = scenes.get(current);
		window.add(currentScene, BorderLayout.CENTER);
		currentScene.requestFocus();
		currentScene.setFocusable(true);

		SwingUtilities.updateComponentTreeUI(window);
	}
	
	private static void readFont(File fontFile) {
		try {
			Font oldGameFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
			GraphicsEnvironment localGraphicEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();			
			localGraphicEnv.registerFont(oldGameFont);

		} catch (Exception e) {
			System.out.println("error while loading the font");
		}
	}

	public static void showMessage(String string) {
		GenericMessagePanel p = (GenericMessagePanel) scenes.get("messagePanel");
		p.setText(string);
		setCurrentScene("messagePanel");
	}

	public static void setFrameUndecorated(boolean b) {
		window.dispose();
		window.setUndecorated(b);
		window.setVisible(true);
	}

	public static void setTopBar(TopLayerGameView topView) {
		window.add(topView,BorderLayout.NORTH);
		SwingUtilities.updateComponentTreeUI(window);
	}
}
