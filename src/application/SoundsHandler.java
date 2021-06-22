package application;

import java.util.HashMap;

public class SoundsHandler {
	private static HashMap<String, SoundEffect> sounds = new HashMap<String, SoundEffect>();
	private static SoundEffect currentSound = null;
	
	public static void addSoundEffect(String name, SoundEffect sound) {
		sounds.put(name, sound);
	}
	
	public static void setCurrentSound(String current) {
		if (currentSound != null) {
			currentSound.stop();
		}
		currentSound = sounds.get(current);
		currentSound.reset();
		currentSound.loop();
	}
	
	public static void playSoundEffect(String sound) {
		sounds.get(sound).restart();
	}
	
	public static SoundEffect getCurrentSound() {
		return currentSound;
	}

	public static boolean isGameSoundOn() {
		if(currentSound == sounds.get("gameMusic"))
			return true;
		return false;
	}

}
