package application.view;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import application.model.Utilities;

public class BubbleAnimation {
	private List<AnimationHandler> animations;
	public BubbleAnimation() {
		animations = new ArrayList<AnimationHandler>();
	}
	public void changeCurrentAnimation(Integer index) {
		if(index >= animations.size()) {
			animations.add(new BubbleAnimationHandler());
		}
		animations.get(index).changeCurrentAnimation(Utilities.IDLE_RIGHT,Utilities.Y_IDLE);
	}
	public Image getCurrentImage(int index) {
		if(index < animations.size()) {
			return animations.get(index).getCurrentImage();			
		}
		return null;
	}
	public void deleteBubble(int index) {
		animations.set(index,null);
	}
	public void removeNullValues() {
		for(int i = 0; i < animations.size();) {
			if(animations.get(i) == null) animations.remove(i);
			else i++;
		}
		
	}
}
