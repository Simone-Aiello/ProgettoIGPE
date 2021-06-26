package application.view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class ResourcesLoader implements Runnable {
	private int currentLevel;
	private HashMap<Integer, AnimationHandler> animations;

	public ResourcesLoader() {
		currentLevel = -1;
		this.run();
	}

	@Override
	public void run() {
		try {
			int index = 0;
			animations = new HashMap<Integer, AnimationHandler>();
			currentLevel++;
			BufferedReader in = new BufferedReader(new FileReader(
					getClass().getResource("/application/resources/level/Level" + currentLevel + ".csv").getFile()));
			while (in.ready()) {
				String s = in.readLine();
				String[] v = s.split(",");
				for (int i = 0; i < v.length; i++) {
					if (v[i].equals("2")) {
						animations.put(index++, new RobotEnemyAnimationHandler());
					} else if (v[i].equals("3")) {
						animations.put(index++, new MageAnimationHandler());
					} else if (v[i].equals("4")) {
						animations.put(index++, new SpringAnimationHandler());
					}
					else if(v[i].equals("5")) {
						animations.put(index++, new JellEnemyAnimationHandler());
					}
				}
			}
			in.close();
		} catch (Exception e) {
			animations = null;
			return;
		}
	}
	public HashMap<Integer, AnimationHandler> getAnimations(){
		return animations;
	}
}
