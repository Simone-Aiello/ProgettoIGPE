package application.view;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;

import application.model.Utilities;

public class MageAnimationHandler implements AnimationHandler {
	private static HashMap<Integer, ArrayList<Image>> animation = null;
	private int index;
	private Image currentImage;
	private int previousState = Utilities.IDLE_LEFT;
	public MageAnimationHandler() {
		if(animation == null) {
			animation = new HashMap<Integer, ArrayList<Image>>();
			String path = "/application/resources/MageEnemy/";
			animation.put(Utilities.MOVE_LEFT, getResources(path + "LeftMovement/"));
			animation.put(Utilities.MOVE_RIGHT, getResources(path + "RightMovement/"));
		}
		index = 0;
	}
	@Override
	public void changeCurrentAnimation(int entityXState, int entityYState) {
		if(entityXState != previousState) {
			index = 0;
			previousState = entityXState;
		}
		currentImage = animation.get(entityXState).get(index);
		index++;
		index%=animation.get(entityXState).size();
	}

	@Override
	public Image getCurrentImage() {
		return currentImage;
	}

}
