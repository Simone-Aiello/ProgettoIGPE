package application.view;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import application.model.Bubble;
import application.model.JellEnemy;
import application.model.MageEnemy;
import application.model.RobotEnemy;
import application.model.SpringEnemy;
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
			else if(b.getEnemyContained() instanceof MageEnemy) {
				animations.get(index).setEnemyContained(Utilities.BUBBLED_MAGE);
			}
			else if(b.getEnemyContained() instanceof SpringEnemy) {
				animations.get(index).setEnemyContained(Utilities.BUBBLED_SPRING);
			}
			else if(b.getEnemyContained() instanceof JellEnemy) {
				animations.get(index).setEnemyContained(Utilities.BUBBLED_JELL);
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
	public void clear() {
		animations.clear();
	}
}
