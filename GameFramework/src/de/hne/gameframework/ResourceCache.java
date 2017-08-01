package de.hne.gameframework;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract cache class to load resources.
 * 
 * @author Heiko Nolte / based on tutorial classes by Alexander Hristov
 * @since August 2008
 */
public abstract class ResourceCache {

	protected Map resources; // Holds cached resources
	protected String path; // Resource path

	/**
	 * Creates empty resource cache and set the path (e.g res/) resources can be
	 * found at.
	 */
	public ResourceCache(String path) {
		this.resources = new HashMap();
		this.path = path;
	}

	/**
	 * Loads a resource.
	 * 
	 * @param name
	 * @return Object
	 */
	public Object loadResource(String name) {
		URL url = null;
		url = getClass().getClassLoader().getResource(name);
		return loadResource(url);
	}

	/**
	 * Returns a resource and loads it if necessary.
	 * 
	 * @param name
	 * @return Object
	 */
	protected Object getResource(String name) {
		Object res = this.resources.get(name);
		if (res == null) {
			res = loadResource(this.path + name);
			resources.put(name, res);
		}
		return res;
	}

	/**
	 * Invalidates the ressource cache.
	 */
	public void invalidate() {
		this.resources = null;
	}

	/**
	 * Loads the resource from the provided path. To be implemented by subclass.
	 * 
	 * @param url
	 * @return Object
	 */
	protected abstract Object loadResource(URL url);

}
