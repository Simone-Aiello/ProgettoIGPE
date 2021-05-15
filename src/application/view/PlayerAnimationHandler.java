package application.view;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;


public class PlayerAnimationHandler {
	//public static final int FALL = 0;
	public static final int IDLE_LEFT = 0;
	public static final int IDLE_RIGHT = 1;
	public static final int WALK_LEFT = 2;
	public static final int WALK_RIGHT = 3;
	private HashMap<Integer, PlayerAnimation> animations;
	private PlayerAnimation currentAnimation;
	public PlayerAnimationHandler() {
		animations = new HashMap<Integer, PlayerAnimation>();
		animations.put(WALK_LEFT, new PlayerAnimation(getResources("LeftMovement")));
		animations.put(WALK_RIGHT, new PlayerAnimation(getResources("RightMovement")));	
		animations.put(IDLE_LEFT, new PlayerAnimation(getResources("IdleLeft")));
		animations.put(IDLE_RIGHT, new PlayerAnimation(getResources("IdleRight")));
		currentAnimation = animations.get(IDLE_RIGHT);	
	}
	private ArrayList<Image> getResources(String name) {
		ArrayList<Image> images = new ArrayList<Image>();
		try {
			switch(name) {
			case "LeftMovement":
				for(int i = 0; i < 7;i++) {
					Image img = ImageIO.read(getClass().getResourceAsStream("/application/resources/LeftMovement/RunLeft"+i+".png"));
					images.add(img);
				}
				break;
			case "RightMovement":
				for(int i = 0; i < 7;i++) {
					Image img = ImageIO.read(getClass().getResourceAsStream("/application/resources/RightMovement/RunRight"+i+".png"));
					images.add(img);
				}
				break;
			case "IdleLeft":
				for(int i = 0; i < 2;i++) {
					Image img = ImageIO.read(getClass().getResourceAsStream("/application/resources/IdleLeft/Idleleft"+i+".png"));
					images.add(img);
				}
				break;
			case "IdleRight":
				for(int i = 0; i < 2;i++) {
					Image img = ImageIO.read(getClass().getResourceAsStream("/application/resources/IdleRight/IdleRight"+i+".png"));
					images.add(img);
				}
				break;
			}
		}
		catch(IOException e) {
			System.out.println("CANNOT LOAD SOURCES");
		}
		return images;
	}
	public void update() {
		currentAnimation.update();
	}
	public void changeCurrentAnimation(int type) {
		currentAnimation = animations.get(type);
		
	}
	public Image getCurrentImage() {
		return currentAnimation.getCurrentImage();
	}
}
