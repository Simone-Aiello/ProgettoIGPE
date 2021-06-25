package application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import application.model.Utilities;
import application.net.DataBaseClient;
import application.view.TopLayerGameView;
import menu.view.ErrorPanel;
import menu.view.GamePauseMenu;
import menu.view.GenericMessagePanel;

import menu.view.LeaderboardMenu;
import menu.view.Score;
import menu.view.ScoreBoard;
import menu.view.WarningPanel;



public class ChangeSceneHandler {

	private static HashMap<String, JPanel> scenes;

	private static JFrame window;
	private static String currentScene = null;
	private static String previousScene = null;
	private static ChangeSceneHandler instance = null;

	public void init(JFrame w) {
		scenes = new HashMap<String, JPanel>();
		window = w;
		readFont(new File(getClass().getResource("/application/resources/font/oldGameFont.ttf").getPath()));
	}

	public static ChangeSceneHandler getInstance() {
		if (instance == null)
			instance = new ChangeSceneHandler();
		return instance;
	}

	public static void add(String name, JPanel scene) {
		scenes.put(name, scene);
	}

	public static void setCurrentScene(String current) {
		if (currentScene != null)
			window.remove(scenes.get(currentScene));
		previousScene = currentScene;
		currentScene = current;
		window.add(scenes.get(currentScene), BorderLayout.CENTER);
		scenes.get(currentScene).requestFocus();
		scenes.get(currentScene).setFocusable(true);
		SwingUtilities.updateComponentTreeUI(window);


		if (current.equals("start"))
			SoundsHandler.setCurrentSound("menuMusic");

		if (current.equals("game"))
			SoundsHandler.setCurrentSound("gameMusic");

		if ((!current.equals("game")) && SoundsHandler.isGameSoundOn())
			SoundsHandler.setCurrentSound("menuMusic");

		scenes.get(current).requestFocus();
		scenes.get(current).setFocusable(true);
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
		window.add(topView, BorderLayout.NORTH);
	}

	public static void removeTopBar(TopLayerGameView topView) {
		window.remove(topView);
		window.revalidate();
		setFrameUndecorated(false);

	}

	public static void setPauseMode(boolean isSinglePlayer) {
		GamePauseMenu menu = (GamePauseMenu) scenes.get("pause");
		if (isSinglePlayer) {
			menu.setAlertText("Are you sure?\nYour score will still be saved and displayed on the leaderboard");
			menu.setLabelText("Game paused");
		} else {
			menu.setAlertText("Are you sure you want to quit?\n Your friend will also be disconnected from the game");
			menu.setLabelText("Game still in progress");
		}
	}

	public static void showErrorMessage(String text) {
		ErrorPanel p = (ErrorPanel) scenes.get("errorMessagePanel");
		p.setText(text);
		setCurrentScene("errorMessagePanel");
	}
	
	public static void showWarningMessage(String text, String nextScene) {
		WarningPanel p = (WarningPanel) scenes.get("warningPanel");
		p.setNextScene(nextScene);
		p.setText(text);
		setCurrentScene("warningPanel");
	}

	public static void returnToPreviousScene() {
		setCurrentScene(previousScene);
	}

	public static void showLeaderboards(String scores) {
		if (scores.equals(Utilities.STILL_NO_CLASSIFICATION)) {
			showMessage("Still no leaderboard to show");
			return;
		}
		if (scores.equals(Utilities.SERVER_NOT_REACHABLE)) {
			showErrorMessage("Cannot estabilish a connection with the server. Please check your internet connection.");
			return;
		}
		LeaderboardMenu scoreboard = (LeaderboardMenu) scenes.get("leaderboard");
		scoreboard.cleanScore();

		String[] scoresList = scores.split(Utilities.MESSAGE_SEPARATOR);

		boolean userScoreAlreadyShowed = false;
		String username = DataBaseClient.getInstance().getUsername();
		if (username == null)
			userScoreAlreadyShowed = true;

		for (int i = 0; i < scoresList.length; i++) {
			String[] current = scoresList[i].split(" ");
			Score s = new Score("" + (i + 1), current[0], current[1]);

			if (current[0].equals(username)) {
				s.changeColor(new Color(255, 51, 153));
				userScoreAlreadyShowed = true;
				if(i > 15) {
					scoreboard.addScore(new Score("...", "...", "..."));
				}
				scoreboard.addScore(s);
			}
			if (i < 15)
				scoreboard.addScore(s);
			if (i > 15 && userScoreAlreadyShowed)
				break;
		}
		scoreboard.revalidateScores();
		setCurrentScene("leaderboard");
	}
}
