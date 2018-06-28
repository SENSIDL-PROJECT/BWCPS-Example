/*
*File:							PlantowerData.java
*Version:						
*Generate at:					22/05/2018 10:59:13
*Further generated artifacts:	PlantowerUtility.java
*/

package de.fzi.bwcps.example.sensor.plantower.gen;
 
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
 
 
/**
 * Data transfer object for PlantowerData
 *
 * @generated
 */
public class PlantowerData {
	
	private static final long serialVersionUID = 1L;
	/*
	 * Unit: g/m
	 */
	private java.lang.Float checksum;
	
	/*
	 * Unit: g/m
	 */
	private java.lang.Float pMSx003_1;
	
	/*
	 * Unit: g/m
	 */
	private java.lang.Float pMSx003_2;
	
	/*
	 * Unit: g/m
	 */
	private java.lang.Float pMSx003_3;
	
	
	/**
	 * Constructor for the Data transfer object
	 */
	public PlantowerData(java.lang.Float checksum, java.lang.Float pMSx003_1, java.lang.Float pMSx003_2, java.lang.Float pMSx003_3) {
		this.checksum = checksum;
		this.pMSx003_1 = pMSx003_1;
		this.pMSx003_2 = pMSx003_2;
		this.pMSx003_3 = pMSx003_3;
	}
	
	/**
	 * empty constructor for the Data transfer object
	 */
	public PlantowerData() {
	
	}
	
	
	/**
	 * Method for checkChecksum
	 * 
	 * @generated
	 */	
	public java.lang.Boolean checkChecksum(){
		float checksum = pMSx003_1 + pMSx003_2 + pMSx003_3;
		return this.checksum == checksum;
	}
	
	/**
	 * @return the checksum
	 */
	public java.lang.Float getChecksum() {
		return this.checksum;
	}
	/**
	 * @param checksum  
	 *            the checksum to set
	 */
	public void setChecksum(java.lang.Float checksum) {
		
		this.checksum = checksum;
	} 
	
	/**
	 * @return the pMSx003_1
	 */
	public java.lang.Float getPMSx0031() {
		return this.pMSx003_1;
	}
	/**
	 * @param pMSx003_1  
	 *            the pMSx003_1 to set
	 */
	public void setPMSx0031(java.lang.Float pMSx003_1) {
		
		this.pMSx003_1 = pMSx003_1;
	} 
	
	/**
	 * @return the pMSx003_2
	 */
	public java.lang.Float getPMSx0032() {
		return this.pMSx003_2;
	}
	/**
	 * @param pMSx003_2  
	 *            the pMSx003_2 to set
	 */
	public void setPMSx0032(java.lang.Float pMSx003_2) {
		
		this.pMSx003_2 = pMSx003_2;
	} 
	
	/**
	 * @return the pMSx003_3
	 */
	public java.lang.Float getPMSx0033() {
		return this.pMSx003_3;
	}
	/**
	 * @param pMSx003_3  
	 *            the pMSx003_3 to set
	 */
	public void setPMSx0033(java.lang.Float pMSx003_3) {
		
		this.pMSx003_3 = pMSx003_3;
	} 
	
	
	
	public void convertAllToLittleEndian(){
		checksum = PlantowerUtility.convertToLittleEndian(checksum);
		pMSx003_1 = PlantowerUtility.convertToLittleEndian(pMSx003_1);
		pMSx003_2 = PlantowerUtility.convertToLittleEndian(pMSx003_2);
		pMSx003_3 = PlantowerUtility.convertToLittleEndian(pMSx003_3);
	}
	
	
}
