/*
*File:							GalileoGen2Data.java
*Version:						
*Generate at:					22/01/2018 18:07:39
*Further generated artifacts:	GalileoGen2Utility.java
*/

package de.fzi.bwcps.example.galileogen2.gen;
 
 
/**
 * Data transfer object for GalileoGen2Data
 *
 * @generated
 */
public class GalileoGen2Data {
	
	private static final long serialVersionUID = 1L;
	/*
	 * Unit: °
	 */
	private java.lang.Short temperature;
	
	/*
	 * Unit: cd
	 */
	private java.lang.Double light;
	
	
	/**
	 * Constructor for the Data transfer object
	 */
	public GalileoGen2Data(java.lang.Short temperature, java.lang.Double light) {
		this.temperature = temperature;
		this.light = light;
	}
	
	/**
	 * empty constructor for the Data transfer object
	 */
	public GalileoGen2Data() {
	
	}
	
	
	/**
	 * @return the temperature
	 */
	public java.lang.Short getTemperature() {
		return this.temperature;
	}
	/**
	 * @param temperature  
	 *            the temperature to set
	 */
	public void setTemperature(java.lang.Short temperature) {
		
		this.temperature = temperature;
	} 
	
	/**
	 * @return the light
	 */
	public java.lang.Double getLight() {
		return this.light;
	}
	/**
	 * @param light  
	 *            the light to set
	 */
	public void setLight(java.lang.Double light) {
		
		this.light = light;
	} 
	
	
}
