package de.hne.ufomenace;

/**
 * Utility class
 * @author Heiko Nolte
 * @since August 2008
 */
public class Utils {

	/**
	 * Returns a random integer figure from 0 to max.
	 * @param max
	 * @return int
	 */
	public static int RandomNumber(int max)
	{
		return new Double(Math.random() * max).intValue();
	}
}
