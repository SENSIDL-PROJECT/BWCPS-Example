package de.fzi.bwcps.example.sensor.data;

import java.util.Random;

public class RandomDataGenerator implements DataFetcher {

	private final static String TEMPERATURE_ID = "Temperature";
	private final static String LIGHT_ID = "Light";
	private final static int BOUND = 100;
	
	private final Random random;
	
	public RandomDataGenerator() {
		
		random = new Random(System.currentTimeMillis());
		
	}
	
	@Override
	public DataEntry getTemperature() {
		
		Integer temp = new Integer(random.nextInt(BOUND)); 
		return DataEntry.of(TEMPERATURE_ID, temp);
		
	}

	@Override
	public DataEntry getLight() {
		
		Double light = new Double(random.nextDouble());
		return DataEntry.of(LIGHT_ID, light);
		
	}

}
