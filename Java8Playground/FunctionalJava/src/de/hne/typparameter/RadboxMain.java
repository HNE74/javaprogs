package de.hne.typparameter;

import java.util.ArrayList;
import java.util.List;

public class RadboxMain {
	
	List<Fahrradbox<?>> radgarage = new ArrayList<>();
	
	public static void main(String args[]) {
		
		Fahrradbox<Fahrrad> box = new Fahrradbox<>();
		Fahrrad rad = new Mountainbike();
		parkeRad(box, rad);
		holeRad(box);
		
		rad = new Fahrrad();
		parkeRad(box, rad);
		holeRad(box);
		
		rad = new AllMountainbike();
		parkeRad(box, rad);
		holeRad(box);			
	}
	
	public static void parkeRad(Fahrradbox<? super Fahrrad> radbox, Fahrrad rad) {
		radbox.setRad(rad);
	}
	
	public static Fahrrad holeRad(Fahrradbox<? extends Fahrrad> radbox) {
		return radbox.getRad();
	}
}
