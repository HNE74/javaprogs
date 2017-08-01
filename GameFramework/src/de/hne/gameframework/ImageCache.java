package de.hne.gameframework;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * Cache for images.
 * 
 * @author Heiko Nolte / based on tutorial classes by Alexander Hristov
 * @since August 2008
 */
public class ImageCache extends ResourceCache implements ImageObserver {

	/**
	 * Creates the image cache using the provided path.
	 * 
	 * @param path
	 */
	public ImageCache(String path) {
		super(path);
	}

	/**
	 * Loads image from provided url using java image IO.
	 * 
	 * @param url
	 * @return Object
	 */
	protected Object loadResource(URL url) {
		try {
			return ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
			return null;
		}
	}

	/**
	 * A compatible image is an image in memory having characteristics which
	 * suite the current video mode. Hence it can be drawn fast.
	 * 
	 * @param width
	 * @param height
	 * @param transparency
	 * @return BufferedImage
	 */
	protected BufferedImage createCompatible(int width, int height,
			int transparency) {
		GraphicsConfiguration gc = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		BufferedImage compatible = gc.createCompatibleImage(width, height,
				transparency);
		return compatible;
	}

	/**
	 * Fetches the image from the resource cache an makes it compatible.
	 * 
	 * @param name
	 * @return BufferedImage
	 */
	public BufferedImage fetchImage(String name) {
		BufferedImage loaded = (BufferedImage) getResource(name);
		BufferedImage compatible = createCompatible(loaded.getWidth(), loaded
				.getHeight(), Transparency.BITMASK);
		Graphics g = compatible.getGraphics();
		g.drawImage(loaded, 0, 0, this);
		return compatible;
	}

	/**
	 * Call back method prescribed by ImageObserver interface.
	 */
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int w,
			int h) {
		return (infoflags & (ALLBITS | ABORT)) == 0;
	}
}
