package application.view;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import application.model.Bubble;
import application.model.RobotEnemy;
import application.model.Utilities;

public class BubbleAnimation {
	private List<BubbleAnimationHandler> animations;
	public BubbleAnimation() {
		animations = new ArrayList<BubbleAnimationHandler>();
	}
	public void changeCurrentAnimation(Integer index,Bubble b) {
		if(index >= animations.size()) {
			animations.add(new BubbleAnimationHandler());
		}
		if(b.getEnemyContained() != null) {
			if(b.getEnemyContained() instanceof RobotEnemy) {
				animations.get(index).setEnemyContained(Utilities.BUBBLED_ROBOT);				
			}
		}
		if(b.isAlive()) animations.get(index).changeCurrentAnimation(Utilities.IDLE_RIGHT,Utilities.Y_IDLE);
	}
	public Image getCurrentImage(int index) {
		if(index < animations.size()) {
			return animations.get(index).getCurrentImage();			
		}
		return null;
	}
}
