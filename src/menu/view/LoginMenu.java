package menu.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JPasswordField;

import application.ChangeSceneHandler;
import application.model.Utilities;
import application.net.client.DBClient;
import menu.MenuSettings;

public class LoginMenu extends JPanel {

	private static final long serialVersionUID = 485808589714637589L;

	private OldGameTextField usernameField;
	private JPasswordField passwordField;
	private OldGameButton loginButton;
	private OldGameButton signUpButton;
	private OldGameButton goOnWitouthLogInButton;

	public LoginMenu() {

		this.setBackground(Color.BLACK);
		this.setLayout(new GridLayout(5, 1));

		// username
		JPanel usPanel = new JPanel();
		usPanel.setBackground(Color.BLACK);
		OldGameLabel usLabel = new OldGameLabel("USERNAME", MenuSettings.LABEL_TEXT_SIZE);
		usLabel.setPreferredSize(new Dimension(200, 50));
		usernameField = new OldGameTextField(MenuSettings.TEXTFIELD_TEXT_SIZE);
		usernameField.setPreferredSize(new Dimension(200, 50));
		usPanel.add(usLabel);
		usPanel.add(usernameField);
		this.add(usPanel);

		// password
		JPanel passPanel = new JPanel();
		passPanel.setBackground(Color.BLACK);
		OldGameLabel passLabel = new OldGameLabel("PASSWORD", MenuSettings.LABEL_TEXT_SIZE);
		passLabel.setPreferredSize(new Dimension(200, 50));
		passwordField = new JPasswordField();
		passwordField.setPreferredSize(new Dimension(200, 50));
		passPanel.add(passLabel);
		passPanel.add(passwordField);
		this.add(passPanel);

		// login sign up
		JPanel buttons = new JPanel();
		buttons.setBackground(Color.BLACK);
		loginButton = new OldGameButton("LOG IN", MenuSettings.REGISTRATION_BUTTON_TEXT_SIZE);

		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				checkAccess(true);

			}

		});
		itemSettings(loginButton, 200, 80);

		signUpButton = new OldGameButton("SIGN UP", MenuSettings.REGISTRATION_BUTTON_TEXT_SIZE);

		signUpButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				checkAccess(false);

			}

		});
		itemSettings(signUpButton, 200, 80);
		buttons.add(loginButton);
		buttons.add(signUpButton);
		this.add(buttons);

		// proceed without saving your progresses
		OldGameLabel or = new OldGameLabel("-OR-", MenuSettings.LABEL_TEXT_SIZE);
		itemSettings(or, 800, 50);
		this.add(or);

		goOnWitouthLogInButton = new OldGameButton("PROCEED WITHOUT SAVING YOUR PROGRESSES",
				MenuSettings.REGISTRATION_BUTTON_TEXT_SIZE);
		itemSettings(goOnWitouthLogInButton, 800, 50);

		goOnWitouthLogInButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ChangeSceneHandler.setCurrentScene("initialMenu");

			}

		});
		this.add(goOnWitouthLogInButton);

	}

	private void itemSettings(Component component, int width, int height) {
		component.setPreferredSize(new Dimension(width, height));
		component.setMaximumSize(component.getPreferredSize());
	}

	private void checkAccess(boolean login) {
		String username = usernameField.getText();
		char[] passwordChar = passwordField.getPassword();
		String password = new String(passwordChar);
		String res = DBClient.getInstance().authentication(username, password, login);
		if (res.equals(Utilities.SUCCESS_ACCESS))
			ChangeSceneHandler.getInstance().setCurrentScene("initialMenu");
		else if(res.equals(Utilities.USER_ALREADY_EXIST)) {
				ChangeSceneHandler.showErrorMessage("A user with this username already exists. \n Please change username.");
				DBClient.getInstance().reset();
		}else if(res.equals(Utilities.USER_NOT_EXIST)) {
			ChangeSceneHandler.showErrorMessage("It doesn't exist a user with this username. \n Please retry.");
			DBClient.getInstance().reset();
		}else if(res.equals(Utilities.WRONG_PASSWORD)) {
			ChangeSceneHandler.showErrorMessage("Wrong password. \n Please retry.");
			DBClient.getInstance().reset();
		}else {
			ChangeSceneHandler.showErrorMessage("Ops! An error occurred while trying to connect to server.");
			DBClient.getInstance().reset();
		}
	}
}
