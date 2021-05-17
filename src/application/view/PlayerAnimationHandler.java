package application.view;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import javax.imageio.ImageIO;


public class PlayerAnimationHandler {
	public static final int IDLE_LEFT = 0;
	public static final int IDLE_RIGHT = 1;
	public static final int WALK_LEFT = 2;
	public static final int WALK_RIGHT = 3;
	public static final int JUMP_LEFT = 4;
	public static final int JUMP_RIGHT = 5;
	public static final int FALL_LEFT = 6;
	public static final int FALL_RIGHT = 7;
	
	private HashMap<Integer, PlayerAnimation> animations;
	private PlayerAnimation currentAnimation;
	public PlayerAnimationHandler() {
		animations = new HashMap<Integer, PlayerAnimation>();
		animations.put(WALK_LEFT, new PlayerAnimation(getResources("LeftMovement")));
		animations.put(WALK_RIGHT, new PlayerAnimation(getResources("RightMovement")));	
		animations.put(IDLE_LEFT, new PlayerAnimation(getResources("IdleLeft")));
		animations.put(IDLE_RIGHT, new PlayerAnimation(getResources("IdleRight")));
		animations.put(FALL_LEFT, new PlayerAnimation(getResources("FallLeft")));
		animations.put(FALL_RIGHT, new PlayerAnimation(getResources("FallRight")));	
		animations.put(JUMP_LEFT, new PlayerAnimation(getResources("JumpLeft")));
		animations.put(JUMP_RIGHT, new PlayerAnimation(getResources("JumpRight")));
		currentAnimation = animations.get(IDLE_RIGHT);	
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
