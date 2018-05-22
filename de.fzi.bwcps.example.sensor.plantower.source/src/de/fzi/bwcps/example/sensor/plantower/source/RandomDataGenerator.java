package de.fzi.bwcps.example.sensor.plantower.source;

import java.util.Random;

public class RandomDataGenerator implements DataFetcher {

	private final static String SENSOR1_ID = "PMSx0031";
	private final static String SENSOR2_ID = "PMSx0032";
	private final static String SENSOR3_ID = "PMSx0033";
	private final static int BOUND = 100;
	
	private final Random random;
	
	public RandomDataGenerator() {
		
		random = new Random(System.currentTimeMillis());
		
	}
	
	@Override
	public DataEntry getPMSx0031() {
		
		Float temp = new Float((float) random.nextInt(BOUND)); 
		return DataEntry.of(SENSOR1_ID, temp);
		
	}

	@Override
	public DataEntry getPMSx0032() {
		
		Float temp = new Float((float) random.nextInt(BOUND)); 
		return DataEntry.of(SENSOR2_ID, temp);
		
	}
	
	@Override
	public DataEntry getPMSx0033() {
		
		Float temp = new Float((float) random.nextInt(BOUND)); 
		return DataEntry.of(SENSOR3_ID, temp);
		
	}

}
