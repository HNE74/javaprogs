package de.hdi.radarwidget;

/**
 * Object shown by the radar.
 * @author 057530
 */
public class RadarObject {
	
	private String tag;
	private double xPos = 0.0;
	private double yPos = 0.0;
	private double speed = 0.0;
	private int degrees = 0;
	
	public void move() {
		double xd = 0.0;
		double yd = 0.0;
		
		if(degrees == 0) {
			xd = 0;
			yd = speed;
		}
		else if(degrees == 90) {
			xd = speed;
			yd = 0;
		}
		else if(degrees == 180) {
			xd = 0;
			yd = -speed;
		}
		else if(degrees == 270) {
			xd = -speed;
			yd = 0;
		}
		else if(degrees > 0 && degrees < 90) {
			xd = speed / 90 * degrees;
			yd = speed / 90 * (90 - degrees);			
		}		
		else if(degrees > 90 && degrees < 180) {
			xd = speed / 90 * (90 - (degrees-90));						
			yd = -(speed / 90 * (degrees - 90));
		}		
		else if(degrees > 180 && degrees < 270) {
			xd = -(speed / 90 * (degrees - 180));						
			yd = -(speed / 90 * (90 - (degrees-180)));						
		}	
		else if(degrees > 270 && degrees < 360) {
			xd = -(speed / 90 * (90 - (degrees-270)));									
			yd = speed / 90 * (degrees-270);
		}
		
		this.xPos += xd;
		this.yPos += yd;		
	}
	

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public double getxPos() {
		return xPos;
	}

	public void setxPos(double xPos) {
		this.xPos = xPos;
	}

	public double getyPos() {
		return yPos;
	}

	public void setyPos(double yPos) {
		this.yPos = yPos;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getDegrees() {
		return degrees;
	}

	public void setDegrees(int degrees) {
		this.degrees = degrees;
		
		if(this.degrees >= 360) {
			this.degrees = degrees - (360 * (degrees / 360));
		}
	}	
	
}