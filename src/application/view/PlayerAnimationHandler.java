package application.view;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import javax.imageio.ImageIO;

import application.model.Utilities;


public class PlayerAnimationHandler implements AnimationHandler {	
	private HashMap<Integer, PlayerAnimation> animationsPlayerOne = null;
	private HashMap<Integer, PlayerAnimation> animationsPlayerTwo = null;
	private PlayerAnimation currentPlayerOneAnimation;
	private PlayerAnimation currentPlayerTwoAnimation;
	
	//Quando parte l'animazione della bolla voglio che finisca anche se il player cade/salta
	private boolean playerOneShooting = false;
	private boolean playerTwoShooting = false;
	
	public PlayerAnimationHandler() {
		animationsPlayerOne = new HashMap<Integer, PlayerAnimation>();
		String pathPlayerOne = "/application/resources/Player/";
		animationsPlayerOne.put(Utilities.MOVE_LEFT, new PlayerAnimation(getResources(pathPlayerOne + "LeftMovement/")));
		animationsPlayerOne.put(Utilities.MOVE_RIGHT, new PlayerAnimation(getResources(pathPlayerOne + "RightMovement/")));	
		animationsPlayerOne.put(Utilities.IDLE_LEFT, new PlayerAnimation(getResources(pathPlayerOne + "IdleLeft/")));
		animationsPlayerOne.put(Utilities.IDLE_RIGHT, new PlayerAnimation(getResources(pathPlayerOne + "IdleRight/")));
		animationsPlayerOne.put(Utilities.FALL_LEFT, new PlayerAnimation(getResources(pathPlayerOne + "FallLeft/")));
		animationsPlayerOne.put(Utilities.FALL_RIGHT, new PlayerAnimation(getResources(pathPlayerOne + "FallRight/")));	
		animationsPlayerOne.put(Utilities.JUMP_LEFT, new PlayerAnimation(getResources(pathPlayerOne + "JumpLeft/")));
		animationsPlayerOne.put(Utilities.JUMP_RIGHT, new PlayerAnimation(getResources(pathPlayerOne + "JumpRight/")));
		animationsPlayerOne.put(Utilities.SHOOT_LEFT, new PlayerAnimation(getResources(pathPlayerOne + "ShootLeft/")));
		animationsPlayerOne.put(Utilities.SHOOT_RIGHT, new PlayerAnimation(getResources(pathPlayerOne + "ShootRight/")));
		currentPlayerOneAnimation = animationsPlayerOne.get(Utilities.IDLE_RIGHT);	
	}
	public void loadPlayerTwoImages() {
		animationsPlayerTwo = new HashMap<Integer, PlayerAnimation>();
		String pathPlayerTwo = "/application/resources/PlayerTwo/";
		animationsPlayerTwo.put(Utilities.MOVE_LEFT, new PlayerAnimation(getResources(pathPlayerTwo + "LeftMovement/")));
		animationsPlayerTwo.put(Utilities.MOVE_RIGHT, new PlayerAnimation(getResources(pathPlayerTwo + "RightMovement/")));	
		animationsPlayerTwo.put(Utilities.IDLE_LEFT, new PlayerAnimation(getResources(pathPlayerTwo + "IdleLeft/")));
		animationsPlayerTwo.put(Utilities.IDLE_RIGHT, new PlayerAnimation(getResources(pathPlayerTwo + "IdleRight/")));
		animationsPlayerTwo.put(Utilities.FALL_LEFT, new PlayerAnimation(getResources(pathPlayerTwo + "FallLeft/")));
		animationsPlayerTwo.put(Utilities.FALL_RIGHT, new PlayerAnimation(getResources(pathPlayerTwo + "FallRight/")));	
		animationsPlayerTwo.put(Utilities.JUMP_LEFT, new PlayerAnimation(getResources(pathPlayerTwo + "JumpLeft/")));
		animationsPlayerTwo.put(Utilities.JUMP_RIGHT, new PlayerAnimation(getResources(pathPlayerTwo + "JumpRight/")));
		animationsPlayerTwo.put(Utilities.SHOOT_LEFT, new PlayerAnimation(getResources(pathPlayerTwo + "ShootLeft/")));
		animationsPlayerTwo.put(Utilities.SHOOT_RIGHT, new PlayerAnimation(getResources(pathPlayerTwo + "ShootRight/")));
		currentPlayerTwoAnimation = animationsPlayerTwo.get(Utilities.IDLE_RIGHT);
	}
	@Override
	public void changeCurrentAnimation(int playerXState,int playerYState) {
		if(playerOneShooting) {
			if(playerXState == Utilities.IDLE_RIGHT ||playerXState == Utilities.MOVE_RIGHT) {
				currentPlayerOneAnimation = animationsPlayerOne.get(Utilities.SHOOT_RIGHT);
			}			
			else {
				currentPlayerOneAnimation = animationsPlayerOne.get(Utilities.SHOOT_LEFT);
			}
			if(currentPlayerOneAnimation.hasReachedEnd()) playerOneShooting = false;
		}
		else {
			switch(playerYState) {
			case Utilities.Y_IDLE:
				currentPlayerOneAnimation = animationsPlayerOne.get(playerXState);
				break;
			case Utilities.JUMPING:
				if(playerXState == Utilities.IDLE_RIGHT ||playerXState == Utilities.MOVE_RIGHT) {
					currentPlayerOneAnimation = animationsPlayerOne.get(Utilities.JUMP_RIGHT);
				}
				else {
					currentPlayerOneAnimation = animationsPlayerOne.get(Utilities.JUMP_LEFT);
				}
				break;
			case Utilities.FALLING:
				if(playerXState == Utilities.IDLE_RIGHT ||playerXState == Utilities.MOVE_RIGHT) currentPlayerOneAnimation = animationsPlayerOne.get(Utilities.FALL_RIGHT);
				else currentPlayerOneAnimation = animationsPlayerOne.get(Utilities.FALL_LEFT);
				break;
			case Utilities.SHOOT:
				playerOneShooting = true;
				if(playerXState == Utilities.IDLE_RIGHT ||playerXState == Utilities.MOVE_RIGHT) {
					currentPlayerOneAnimation = animationsPlayerOne.get(Utilities.SHOOT_RIGHT);
				}
				else {
					currentPlayerOneAnimation = animationsPlayerOne.get(Utilities.SHOOT_LEFT);
				}
				break;	
			default:
				break;
			}			
		}
		currentPlayerOneAnimation.update();
	}
	@Override
	public Image getCurrentImage() {
		return currentPlayerOneAnimation.getCurrentImage();
	}
	public Image getPlayerTwoImage() {
		return currentPlayerTwoAnimation.getCurrentImage();
	}
	public void changeCurrentPlayerTwoAnimation(int playerXState,int playerYState) {
		if(playerTwoShooting) {
			if(playerXState == Utilities.IDLE_RIGHT ||playerXState == Utilities.MOVE_RIGHT) {
				currentPlayerTwoAnimation = animationsPlayerTwo.get(Utilities.SHOOT_RIGHT);
			}
			else {
				currentPlayerTwoAnimation = animationsPlayerTwo.get(Utilities.SHOOT_LEFT);
			}
			if(currentPlayerTwoAnimation.hasReachedEnd()) playerTwoShooting = false;
		}
		else {
			switch(playerYState) {
				case Utilities.Y_IDLE:
					currentPlayerTwoAnimation = animationsPlayerTwo.get(playerXState);
				break;
				case Utilities.JUMPING:
					if(playerXState == Utilities.IDLE_RIGHT ||playerXState == Utilities.MOVE_RIGHT) {
						currentPlayerTwoAnimation = animationsPlayerTwo.get(Utilities.JUMP_RIGHT);
					}
					else {
						currentPlayerTwoAnimation = animationsPlayerTwo.get(Utilities.JUMP_LEFT);
					}
				break;
				case Utilities.FALLING:
					if(playerXState == Utilities.IDLE_RIGHT ||playerXState == Utilities.MOVE_RIGHT) currentPlayerTwoAnimation = animationsPlayerTwo.get(Utilities.FALL_RIGHT);
					else currentPlayerTwoAnimation = animationsPlayerTwo.get(Utilities.FALL_LEFT);
				break;
				case Utilities.SHOOT:
					playerTwoShooting = true;
					if(playerXState == Utilities.IDLE_RIGHT ||playerXState == Utilities.MOVE_RIGHT) {
						currentPlayerTwoAnimation = animationsPlayerTwo.get(Utilities.SHOOT_RIGHT);
					}
					else {
						currentPlayerTwoAnimation = animationsPlayerTwo.get(Utilities.SHOOT_LEFT);
					}
					break;	
				default:
					break;
			}
		}
		currentPlayerTwoAnimation.update();
	}
}
