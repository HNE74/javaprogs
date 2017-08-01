package de.hne.gameframework;

import java.awt.image.ImageObserver;
import java.util.List;

/**
 * Interface the GameEngine implements.
 * 
 * @author Heiko Nolte / based on tutorial classes by Alexander Hristov
 * @since August 2008
 */
public interface Stage extends ImageObserver {

	public static final int PLAY_HEIGHT = 500; 
	public static final int SPEED=10;
	public ImageCache getImageCache();
	public SoundCache getSoundCache();
	public void addActor(Actor a);
	public List getActors();
	public Player getPlayer();
	public void gameOver();
}
