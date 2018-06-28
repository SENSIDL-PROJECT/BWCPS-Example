/*
*File:							PlantowerUtility.java
*Version:						
*Generate at:					22/05/2018 10:59:13
*Further generated artifacts:	PlantowerData.java
*/

package de.fzi.bwcps.example.sensor.plantower.gen;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Data transfer object for PlantowerUtility
 *
 * @generated
 */
public class PlantowerUtility {
	
	/**
	 * Converts a big endian float into a little endian float
	 *	
	 * @param num the float to convert
	 * @return float the converted float
	 *
	 */
	public static float convertToLittleEndian(float num) {
		byte[] bytes = new byte[4];
		ByteBuffer.wrap(bytes).putFloat(num);
		return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat();
	}
	
	/**
	 * Converts a big endian double into a little endian double
	 *	
	 * @param num the double to convert
	 * @return double the converted double
	 *
	 */
	public static double convertToLittleEndian(double num) {
		byte[] bytes = new byte[8];
		ByteBuffer.wrap(bytes).putDouble(num);
		return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getDouble();
	}
	
	/**
	 * Converts a big endian byte into a little endian byte
	 *	
	 * @param num the byte to convert
	 * @return byte the converted byte
	 *
	 */
	public static byte convertToLittleEndian(byte num) {
		return num;
	}
	
	/**
	 * Converts a big endian short into a little endian short
	 *	
	 * @param num the short to convert
	 * @return short the converted short
	 *
	 */
	public static short convertToLittleEndian(short num) {
		return Short.reverseBytes(num);
	}
	
	/**
	 * Converts a big endian int into a little endian int
	 *	
	 * @param num the int to convert
	 * @return int the converted int
	 *
	 */
	public static int convertToLittleEndian(int num) {
		return Integer.reverseBytes(num);
	}
	
	/**
	 * Converts a big endian long into a little endian long
	 *	
	 * @param num the long to convert
	 * @return long the converted long
	 *
	 */
	public static long convertToLittleEndian(long num) {
		return Long.reverseBytes(num);
	}
	
	/**
	 * Converts a big endian String into a little endian String
	 *	
	 * @param str the String to convert
	 * @return String the converted String
	 *
	 */
	public static String convertToLittleEndian(String str) {
		//SENSIDL_TODO: implement Method
		return str;
	}
	
	/**
	 * Converts a big endian boolean into a little endian boolean
	 *	
	 * @param bool the boolean to convert
	 * @return boolean the converted boolean
	 *
	 */
	public static boolean convertToLittleEndian(boolean bool) {
		//SENSIDL_TODO: implement Method
		return bool;
	}
	
	/**
	 * Alternative method responsible for serializing JSON
	 * 
	 * @return Json String
	 */
	public static String marshalJSON(Object elementToMarshall) { 
		Gson gson = new Gson();
		
		if (elementToMarshall instanceof PlantowerData) {
			// use little endianness
			((PlantowerData) elementToMarshall).convertAllToLittleEndian();
			return gson.toJson(((PlantowerData) elementToMarshall));
		}
		
		return null;
	}
	
	/**
	 * Alternative method responsible for deserializing the received
	 * JSON-formatted L stage from sensor.
	 * 
	 * @param dataset
	 *            the dataset to unmarshall incoming from sensor side in a JSON
	 *            format
	 * @return T unmarshalled T structure
	 */
	public static <T> T unmarshalJSON(BufferedReader dataset, T obj) { 
		
		Gson gson = new Gson();
		BufferedReader br = dataset;
		obj = (T) gson.fromJson(br, obj.getClass());
		// use little endianness 
		if (obj instanceof PlantowerData) {
			((PlantowerData) obj).convertAllToLittleEndian();
		}
		
		return obj;
	}
	
	/**
	 * Method responsible for deserializing the received byte array
	 * representation of L from sensor.
	 * 
	 * @param dataset
	 *            the dataset to unmarshall incoming from sensor side as a byte
	 *            array
	 * @return T unmarshalled T structure
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static <T> T unmarshalByteArray(byte[] dataset) throws IOException, ClassNotFoundException {
		
		ByteArrayInputStream in = new ByteArrayInputStream(dataset);
		ObjectInputStream ois = null;
		ois = new ObjectInputStream(in);
		Object o = ois.readObject();
		T unmarshalledObject = (T) o; // SENSIDL_TODO: Ensure the type conversion is valid
		in.close();
		if (in != null) {
			ois.close();
		}
		return unmarshalledObject;
	}
	
	/**
	 * Method responsible for serializing Byte-Array
	 */
	public static PlantowerData marshalPlantowerDataByteArray() {
		//SENSIDL_TODO: implement Method
		return null;
	}
}
