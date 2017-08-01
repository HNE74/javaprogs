package de.hne.chaseevade;

public class MVector {

	public double x;
	public double y;
	public double z;
	
	/**
	 * Substracts the end from the begin vector.
	 * @param beginVector
	 * @param endVector
	 * @return MVector
	 */
	public static MVector SubstractVectors(MVector beginVector, MVector endVector)
	{
		MVector result = new MVector();
		result.x = beginVector.x - endVector.x;
		result.y = beginVector.y - endVector.y;
		result.z = beginVector.z - endVector.z;
		
		return result;
	}
	
	/**
	 * Substracts the provided end vector from the current one.
	 * @param endVector
	 * @return MVector
	 */
	public MVector substractVector(MVector endVector)
	{
		return SubstractVectors(this, endVector);
	}

	/**
	 * Adds the end to the begin vector.
	 * @param beginVector
	 * @param endVector
	 * @return MVector
	 */
	public static MVector AddVectors(MVector beginVector, MVector endVector)
	{
		MVector result = new MVector();
		result.x = beginVector.x + endVector.x;
		result.y = beginVector.y + endVector.y;
		result.z = beginVector.z + endVector.z;
		
		return result;
	}
	
	/**
	 * Adds the provided end vector to the current one.
	 * @param endVector
	 * @return MVector
	 */
	public MVector AddVector(MVector endVector)
	{
		return AddVectors(this, endVector);
	}
	
	/**
	 * Calculates the magnitude of the distance between the to
	 * provided vectors.
	 * @param beginVector
	 * @param endVector
	 * @return double
	 */
	public static double FetchMagnitude(MVector beginVector, MVector endVector)
	{
		double xLength = beginVector.x - endVector.x;
		double yLength = beginVector.y - endVector.y;
		double zLength = beginVector.z - endVector.z;
		
		return Math.sqrt(xLength * xLength + yLength * yLength + zLength * zLength);
	}
	
	/**
	 * Calculates the magnitude of the distance between the current 
	 * and the provided vector.
	 * @param endVector
	 * @return double
	 */
	public double fetchMagnitude(MVector endVector)
	{
		double xLength = x - endVector.x;
		double yLength = y - endVector.y;
		double zLength = z - endVector.z;
		
		return Math.sqrt(xLength * xLength + yLength * yLength + zLength * zLength);
	}
	
	/**
	 * Returns the normalized vector for provided vector.
	 * @param vector
	 * @return double
	 */
	public static MVector normalizeVector(MVector vector)
	{
		final double tol = 0.001;
		double m = Math.sqrt(vector.x * vector.x + 
				             vector.y * vector.y +
				             vector.z * vector.z);
		if (m<=tol) m = 1.0;
		
		MVector result = new MVector();
		result.x = vector.x /= m;
		if(Math.abs(result.x) < tol) result.x = 0.0;
		result.y = vector.y /= m;
		if(Math.abs(result.y) < tol) result.y = 0.0;
		result.z = vector.z /= m;
		if(Math.abs(result.z) < tol) result.z = 0.0;
	
		return result;
	}
	
	/**
	 * Returns normalized vector from current vector.
	 * @return MVector
	 */
	public MVector normalize()
	{
		return MVector.normalizeVector(this);
	}
	
	/**
	 * Returns the component of a vector with the provided magnitude.
	 * Which component is returned depends on the provided angle.
	 * @param magnitude
	 * @param angle
	 * @return double
	 */
	public static double FetchComponent(double magnitude, double angle)
	{
		return magnitude * Math.cos(Math.toRadians(angle));
	}
	
	/**
     * Transfors earth fixed x component of vector to body fixed x
     * component of vector.
     * @param xComp
     * @param yComp
     * @return double
     */
	public static double FetchLocalXComponent(MVector vector, double angle)
    {
    	return vector.x * Math.cos(Math.toRadians(angle)) + 
    	       vector.y * Math.sin(Math.toRadians(angle));
    }

    /**
     * Transfors earth fixed x component of vector to body fixed x
     * component of vector.
     * @param xComp
     * @param yComp
     * @return double
     */
	public static double FetchLocalYComponent(MVector vector, double angle)
    {
    	return -vector.x * Math.sin(Math.toRadians(angle)) + 
    	        vector.y * Math.cos(Math.toRadians(angle));
    }
	
	/**
	 * Returns vector components as string.
	 */
	public String toString()
	{
		StringBuffer txt = new StringBuffer();
		txt.append("\nx: " + x);
		txt.append("\ny " + y);
		txt.append("\nz: " + z);
		
		return txt.toString();
	}
	
}

