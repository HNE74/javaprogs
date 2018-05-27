package de.hne.dungeongenerator.renderer;

import de.hne.dungeongenerator.data.Dungeon;

/**
 * Definition of dungeon renderer.
 * @author hnema
 */
public interface IDungeonRenderer {

	/**
	 * Renders the passed dungeon.
	 * @param dungeon
	 */
	void render(Dungeon dungeon);

}
