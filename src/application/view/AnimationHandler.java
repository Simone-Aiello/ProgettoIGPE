package application.view;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.imageio.ImageIO;

public interface AnimationHandler {
	 public default ArrayList<Image> getResources(String pathToFolder) {
		ArrayList<Image> images = new ArrayList<Image>();
		try {
			File f = new File(getClass().getResource(pathToFolder).getPath());
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
				images.add(ImageIO.read(getClass().getResourceAsStream(pathToFolder + img.getName())));
			}			
		}catch(IOException e) {
			System.out.println("CANNOT LOAD RESOURCES");
		}
		return images;
	}
		public void changeCurrentAnimation(int entityXState,int entityYState);
		public Image getCurrentImage();
}
