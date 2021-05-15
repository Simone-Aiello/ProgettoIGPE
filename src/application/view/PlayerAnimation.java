package application.view;

import java.awt.Image;
import java.util.ArrayList;


public class PlayerAnimation {
	private int currentIndex;
	private Image currentImage;
	private ArrayList<Image> images;
	public PlayerAnimation(ArrayList<Image> resources) {
		this.currentIndex = 0;
		this.images = resources;
		if(images.size() > 0) this.currentImage = images.get(0);
	}
	public void update() {
		currentIndex++;
		currentIndex %= images.size();
		this.currentImage = images.get(currentIndex);
	}
	public Image getCurrentImage() {
		return currentImage;
	}

}
