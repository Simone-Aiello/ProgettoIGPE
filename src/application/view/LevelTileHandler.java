package application.view;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class LevelTileHandler {
	private ArrayList<Image> images;
	public LevelTileHandler() {
		try {
			images = new ArrayList<Image>();
			images.add(ImageIO.read(getClass().getResourceAsStream("/application/resources/level/Tile1.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Image getTile(int index) {
		return images.get(index);
	}
}
