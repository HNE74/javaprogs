package de.hne.radarwidget;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * Radar Widget.
 * 
 * @author 057530
 */
public class RadarWidget extends Canvas {

	List<RadarObject> objects = new ArrayList<RadarObject>();
	Display display = Display.getCurrent();

	double rangeDiameter = 100.0;
	int horMargin = 5;
	int verMargin = 5;
	int diameter = 400;
	int objSize = 10;
	int course = 0;

	private Color marginColor = display.getSystemColor(SWT.COLOR_BLACK);
	private Color screenColor = display.getSystemColor(SWT.COLOR_BLUE);
	private Color objectColor = display.getSystemColor(SWT.COLOR_YELLOW);
	private Color objectTagColor = display.getSystemColor(SWT.COLOR_YELLOW);

	public RadarWidget(Composite parent, int style) {
		super(parent, style);

		addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent paintEvent) {
				GC gc = paintEvent.gc;
				paintRadar(gc);
			}
		});
	}

	/**
	 * Paints the radar.
	 * 
	 * @param gc
	 */
	private void paintRadar(GC gc) {
		gc.setClipping(0, 0, diameter + horMargin * 2, diameter + horMargin * 2);
		gc.setBackground(marginColor);
		gc.fillRoundRectangle(0, 0, diameter + horMargin * 2, diameter + verMargin * 2, 10, 10);
		gc.setBackground(screenColor);
		gc.fillOval(horMargin, verMargin, diameter, diameter);
		gc.setForeground(objectColor);
		gc.drawLine(horMargin, verMargin + diameter / 2, horMargin + diameter, verMargin + diameter / 2);
		gc.drawLine(horMargin + diameter / 2, verMargin, horMargin + diameter / 2, verMargin + diameter);
		paintObjectsOntoRadar(gc);
	}

	/**
	 * Paints objects onto radar.
	 * 
	 * @param gc
	 */
	private void paintObjectsOntoRadar(GC gc) {
		
		
		for (RadarObject obj : objects) {
			
	    	double rad = Math.toRadians(course);			
    		double newXPos = obj.getxPos() * Math.cos(rad) - obj.getyPos() * Math.sin(rad);
    		double newYPos = obj.getxPos() * Math.sin(rad) + obj.getyPos() * Math.cos(rad);			
			
			// Calculate distance to object
			double distance = Math.sqrt(Math.pow(obj.getxPos(), 2) + Math.pow(obj.getyPos(), 2));
			if (distance > rangeDiameter / 2) {
				continue;
			}
			
			// Transform object coordinates to screen coordinates
			int xDraw = (int) (diameter / rangeDiameter * newXPos + diameter / 2) + horMargin;
			int yDraw = (int) (diameter / rangeDiameter * newYPos);
			yDraw = diameter / 2 - yDraw + verMargin;

			gc.setBackground(objectColor);
			gc.fillRectangle((xDraw - objSize / 2), (yDraw - objSize / 2), objSize, objSize);

			int fontHeight = fetchFontHeight();
			if (fontHeight > 0) {
				gc.setForeground(objectTagColor);
				gc.setBackground(screenColor);
				String txt = obj.getTag() + "\nDist:" + distance + "\nSpeed:" + obj.getSpeed() + "\nCrs:"
						+ calcAngle(xDraw, yDraw);
				gc.drawText(txt, xDraw, yDraw - fontHeight * 5 - objSize / 2 - 1);
			}
		}
	}

	private int fetchFontHeight() {
		FontData[] data = this.getFont().getFontData();
		if (data != null && data.length > 0) {
			return data[0].getHeight() + 1;
		}

		return 0;
	}

	/**
	 * Adds an object to the radar.
	 * 
	 * @param object
	 */
	public void addRadarObject(RadarObject object) {
		objects.add(object);
	}

	/**
	 * Removes an object from the radar.
	 * 
	 * @param object
	 */
	public void removeRadarObject(RadarObject object) {
		objects.remove(object);
	}

	/**
	 * Calculates the angle of a point related to the center of the radar.
	 * 
	 * @param xPos
	 * @param yPos
	 * @return
	 */
	public int calcAngle(int xPos, int yPos) {

		double dx = xPos - (diameter / 2 + horMargin);
		double dy = yPos - (diameter / 2 + verMargin);
		double adx = Math.abs(dx);
		double ady = Math.abs(dy);

		if (dy == 0 && dx == 0) {
			return 0;
		} else if (dy == 0 && dx > 0) {
			return 90;
		} else if (dy == 0 && dx < 0) {
			return 270;
		} else if (dy > 0 && dx == 0) {
			return 180;
		} else if (dy < 0 && dx == 0) {
			return 0;
		}

		double rwinkel = Math.atan(ady / adx);
		double dWinkel = 0;

		if (dx < 0 && dy < 0) // 1. Quartal Winkkel von 270° - 359°
		{
			dWinkel = 270 + Math.toDegrees(rwinkel);
		} else if (dx < 0 && dy > 0) // 2. Quartal Winkkel von 180° - 269°
		{
			dWinkel = 270 - Math.toDegrees(rwinkel);
		} else if (dx > 0 && dy > 0) // 3. Quartal Winkkel von 90° - 179°
		{
			dWinkel = 90 + Math.toDegrees(rwinkel);
		} else if (dx > 0 && dy < 0) // 4. Quartal Winkkel von 0° - 89°
		{
			dWinkel = 90 - Math.toDegrees(rwinkel);
		}

		int iWinkel = (int) dWinkel;

		if (iWinkel == 360) {
			iWinkel = 0;
		}

		return iWinkel;
	}
	
    /**
     * @param objectList
     * @param degrees
     */
    private void turnRadarObjects(List<RadarObject> objectList, int degrees) {    	
    	double rad = Math.toRadians(degrees);    	
    	for(RadarObject obj : objectList) {
    		double newXPos = obj.getxPos() * Math.cos(rad) - obj.getyPos() * Math.sin(rad);
    		double newYPos = obj.getxPos() * Math.sin(rad) + obj.getyPos() * Math.cos(rad);
   		    obj.setxPos(newXPos);
   		    obj.setyPos(newYPos);
    	}    	    
    }	

	// Accessor methods

	public double getRangeDiameter() {
		return rangeDiameter;
	}

	public void setRangeDiameter(double rangeDiameter) {
		this.rangeDiameter = rangeDiameter;
	}

	public int getDiameter() {
		return diameter;
	}

	public void setDiameter(int diameter) {
		this.diameter = diameter;
	}

	public Color getMarginColor() {
		return marginColor;
	}

	public void setMarginColor(Color marginColor) {
		this.marginColor = marginColor;
	}

	public Color getScreenColor() {
		return screenColor;
	}

	public void setScreenColor(Color screenColor) {
		this.screenColor = screenColor;
	}

	public Color getObjectColor() {
		return objectColor;
	}

	public void setObjectColor(Color objectColor) {
		this.objectColor = objectColor;
	}

	public int getObjSize() {
		return objSize;
	}

	public void setObjSize(int objSize) {
		this.objSize = objSize;
	}
	
	public int getCourse() {
		return course;
	}

	public void setCourse(int course) {
		this.course = course;
	}	
}
