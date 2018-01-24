/*
*File:							GalileoGen2Utility.java
*Version:						
*Generate at:					22/01/2018 18:07:39
*Further generated artifacts:	GalileoGen2Data.java
*/

package de.fzi.sensidl.GalileoGen2;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import com.google.gson.Gson;

/**
 * Data transfer object for GalileoGen2Utility
 *
 * @generated
 */
public class GalileoGen2Utility {
	
	
	/**
	 * Alternative method responsible for serializing JSON
	 * 
	 * @return Json String
	 */
	public static String marshalJSON(Object elementToMarshall) { 
		Gson gson = new Gson();
		
		if (elementToMarshall instanceof GalileoGen2Data) {
			return gson.toJson((GalileoGen2Data) elementToMarshall);
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
	public static GalileoGen2Data marshalGalileoGen2DataByteArray() {
		//SENSIDL_TODO: implement Method
		return null;
	}
}
