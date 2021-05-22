package application.view;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import javax.imageio.ImageIO;

import application.model.PlayerSettings;


public class PlayerAnimationHandler {	
	private HashMap<Integer, PlayerAnimation> animations;
	private PlayerAnimation currentAnimation;
	public PlayerAnimationHandler() {
		animations = new HashMap<Integer, PlayerAnimation>();
		animations.put(PlayerSettings.MOVE_LEFT, new PlayerAnimation(getResources("LeftMovement")));
		animations.put(PlayerSettings.MOVE_RIGHT, new PlayerAnimation(getResources("RightMovement")));	
		animations.put(PlayerSettings.IDLE_LEFT, new PlayerAnimation(getResources("IdleLeft")));
		animations.put(PlayerSettings.IDLE_RIGHT, new PlayerAnimation(getResources("IdleRight")));
		animations.put(PlayerSettings.FALL_LEFT, new PlayerAnimation(getResources("FallLeft")));
		animations.put(PlayerSettings.FALL_RIGHT, new PlayerAnimation(getResources("FallRight")));	
		animations.put(PlayerSettings.JUMP_LEFT, new PlayerAnimation(getResources("JumpLeft")));
		animations.put(PlayerSettings.JUMP_RIGHT, new PlayerAnimation(getResources("JumpRight")));
		currentAnimation = animations.get(PlayerSettings.IDLE_RIGHT);	
	}
	private ArrayList<Image> getResources(String folder) {
		ArrayList<Image> images = new ArrayList<Image>();
		try {
			String path = "/application/resources/Player/" + folder + "/";
			File f = new File(getClass().getResource(path).getPath());
			ArrayList<File> listOfResources = new ArrayList<File>();
			for (File r : f.listFiles()) {
				listOfResources.add(r);				
			}
			Collections.sort(listOfResources, new Comparator<File>() {
				public int compare(File o1, File o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
			for (File img : listOfResources) {				
				images.add(ImageIO.read(getClass().getResourceAsStream(path + img.getName())));
			}			
		}catch(IOException e) {
			System.out.println("CANNOT LOAD RESOURCES");
		}
		return images;
	}
	public void changeCurrentAnimation(int playerXState,int playerYState) {
		switch(playerYState) {
			case PlayerSettings.Y_IDLE:
				currentAnimation = animations.get(playerXState);
			break;
			case PlayerSettings.JUMPING:
				if(playerXState == PlayerSettings.IDLE_RIGHT ||playerXState == PlayerSettings.MOVE_RIGHT) currentAnimation = animations.get(PlayerSettings.JUMP_RIGHT);
				else currentAnimation = animations.get(PlayerSettings.JUMP_LEFT);
			break;
			case PlayerSettings.FALLING:
				if(playerXState == PlayerSettings.IDLE_RIGHT ||playerXState == PlayerSettings.MOVE_RIGHT) currentAnimation = animations.get(PlayerSettings.FALL_RIGHT);
				else currentAnimation = animations.get(PlayerSettings.FALL_LEFT);
			break;
			
		}
		currentAnimation.update();
	}
	
	public PlayerAnimation getCurrentAnimation() {
		return currentAnimation;
	}
	
	public HashMap<Integer, PlayerAnimation> getAnimations(){
		return animations;
	}
	
	public Image getCurrentImage() {
		return currentAnimation.getCurrentImage();
	}
}
