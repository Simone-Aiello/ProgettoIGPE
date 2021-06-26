package application.view;

import java.awt.Image;
import java.util.HashMap;
import java.util.List;

import application.model.Utilities;

public class BubbleAnimationHandler implements AnimationHandler {
	private static List<Image> images = null;
	private static HashMap<Integer, List<Image>> bubbledEnemyAnimations = null;
	private int index;
	private Image currentImage;
	private Integer enemyContained = null;
	public BubbleAnimationHandler() {
		if(images == null) {
			images = getResources("/application/resources/Bubble/");
		}
		if(bubbledEnemyAnimations == null) {
			bubbledEnemyAnimations = new HashMap<Integer, List<Image>>();
			bubbledEnemyAnimations.put(Utilities.BUBBLED_ROBOT, getResources("/application/resources/RobotEnemy/Bubbled/"));
			bubbledEnemyAnimations.put(Utilities.BUBBLED_MAGE, getResources("/application/resources/MageEnemy/Bubbled/"));
			bubbledEnemyAnimations.put(Utilities.BUBBLED_SPRING, getResources("/application/resources/SpringEnemy/Bubbled/"));
			bubbledEnemyAnimations.put(Utilities.BUBBLED_JELL, getResources("/application/resources/JellEnemy/Bubbled/"));
		}
		index = 0;
	}
	@Override
	public void changeCurrentAnimation(int entityXState, int entityYState) {
		if(enemyContained != null) {
			List<Image> img = bubbledEnemyAnimations.get(enemyContained);
			index %= img.size();
			currentImage = img.get(index);
			index++;
		}
		else {
			currentImage = images.get(index);
			index++;
			if(index >= images.size()) index = images.size() - 1;			
		}
	}
	@Override
	public Image getCurrentImage() {
		return currentImage;
	}
	public void setEnemyContained(Integer enemyContained) {
		this.enemyContained = enemyContained;
	}
}
