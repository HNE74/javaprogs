package de.hne.typparameter;

public class Mountainbike extends Fahrrad {
	
	enum Reifengroesse { 
		KLEIN, MITTEL, GROSS
	};

	private Reifengroesse reifengroesse;

	public Reifengroesse getReifengroesse() {
		return reifengroesse;
	}

	public void setReifengroesse(Reifengroesse reifengroesse) {
		this.reifengroesse = reifengroesse;
	}
}
