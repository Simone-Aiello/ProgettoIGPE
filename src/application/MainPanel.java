package application;

import javax.swing.JFrame;

import menu.view.ErrorPanel;
import menu.view.GamePauseMenu;
import menu.view.GenericMessagePanel;
import menu.view.InitialMenu;
import menu.view.LeaderboardMenu;
import menu.view.LoginMenu;
import menu.view.MultiplayerMenu;
import menu.view.StartView;

public class MainPanel {
	
	
	public static void main(String[] args) {
		JFrame f = new JFrame("BubbleBobble");
		f.setSize(Settings.WINDOW_WIDTH,Settings.WINDOW_HEIGHT);
		f.setLocationRelativeTo(null);
		//f.setUndecorated(true);
		f.setResizable(false);
		ChangeSceneHandler.getInstance().init(f);
		
		//GameView view = new GameView();	
		StartView startScene= new StartView();
		InitialMenu initialMenu = new InitialMenu();
		LoginMenu loginMenu = new LoginMenu();
		MultiplayerMenu multiplayerMenu = new MultiplayerMenu();
		GenericMessagePanel messagePanel = new GenericMessagePanel();
		LeaderboardMenu leaderboards = new LeaderboardMenu();
		GamePauseMenu pauseMenu = new GamePauseMenu();
		ErrorPanel errorMessagePanel = new ErrorPanel();
		//ChangeSceneHandler.add("game", view);
		ChangeSceneHandler.add("start", startScene);
		ChangeSceneHandler.add("initialMenu", initialMenu);
		ChangeSceneHandler.add("loginMenu", loginMenu);
		ChangeSceneHandler.add("multiplayerMenu", multiplayerMenu);
		ChangeSceneHandler.add("messagePanel", messagePanel);
		ChangeSceneHandler.add("leaderboard", leaderboards);
		ChangeSceneHandler.add("pause", pauseMenu);
		ChangeSceneHandler.add("errorMessagePanel", errorMessagePanel);
		ChangeSceneHandler.setCurrentScene("start");
		//ChangeSceneHandler.setCurrentScene("start");
		
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
