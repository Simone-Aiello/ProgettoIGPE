package application.view;

import java.awt.Image;
import java.util.List;

public class BubbleAnimationHandler implements AnimationHandler {
	private static List<Image> images = null;
	private int index;
	private Image currentImage;
	public BubbleAnimationHandler() {
		if(images == null) {
			images = getResources("/application/resources/Bubble/");
		}
		index = 0;
	}
	@Override
	public void changeCurrentAnimation(int entityXState, int entityYState) {
		currentImage = images.get(index);
		index++;
		if(index >= images.size()) index = images.size() - 1;
	}

	@Override
	public Image getCurrentImage() {
		return currentImage;
	}

}
