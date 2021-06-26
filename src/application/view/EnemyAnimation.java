package application.view;

import java.awt.Image;
import java.util.HashMap;

import application.model.Enemy;
import application.model.Entity;

public class EnemyAnimation {
	private HashMap<Integer, AnimationHandler> animations;
	public EnemyAnimation() {
	}
	public void changeCurrentAnimation(Integer index,Enemy enemy) {
		int xState = ((Entity) enemy).getXState();
		int yState = ((Entity) enemy).getYState();
		animations.get(index).changeCurrentAnimation(xState,yState);
	}
	public Image getCurrentImage(int index) {
		if(animations.containsKey(index)) {
			return animations.get(index).getCurrentImage();
		}
		return null;
	}
	public void clear() {
		animations.clear();
	}
	public void setMap(HashMap<Integer, AnimationHandler> animations) {
		this.animations = animations;
	}
}
