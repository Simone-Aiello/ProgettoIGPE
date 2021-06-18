package application.view;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import application.model.Utilities;

public class FoodImage {
	private HashMap<Integer,Image> images;
	public FoodImage() {
		try {
			images = new HashMap<Integer,Image>();
			images.put(Utilities.CAKE,ImageIO.read(getClass().getResourceAsStream("/application/resources/Food/Cake.png")));
			images.put(Utilities.EGG,ImageIO.read(getClass().getResourceAsStream("/application/resources/Food/Egg.png")));
			images.put(Utilities.HAMBURGER,ImageIO.read(getClass().getResourceAsStream("/application/resources/Food/Hamburger.png")));
		} catch (IOException e) {
			System.out.println("Food images not found");
		}
	}
	public Image getImage(int foodType) {
		return images.get(foodType);
	}
}
