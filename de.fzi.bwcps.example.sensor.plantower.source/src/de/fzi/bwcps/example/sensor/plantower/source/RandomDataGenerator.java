package de.fzi.bwcps.example.sensor.plantower.source;

import java.util.Random;

/**
 * Produces random plantower sensor data.
 * 
 * @author czogalik
 *
 */
public class RandomDataGenerator implements DataFetcher {

	private final static String CHECKSUM_ID = "CHECKSUM";
	private final static String SENSOR1_ID = "PMSx0031";
	private final static String SENSOR2_ID = "PMSx0032";
	private final static String SENSOR3_ID = "PMSx0033";
	private final static int BOUND = 100;

	private float PMSx0031;
	private float PMSx0032;
	private float PMSx0033;

	private final Random random;

	public RandomDataGenerator() {

		random = new Random(System.currentTimeMillis());

	}

	@Override
	public void produce() {
		PMSx0031 = new Float((float) random.nextInt(BOUND));
		PMSx0032 = new Float((float) random.nextInt(BOUND));
		PMSx0033 = new Float((float) random.nextInt(BOUND));
	}

	@Override
	public DataEntry getPMSx0031() {

		return DataEntry.of(SENSOR1_ID, PMSx0031);

	}

	@Override
	public DataEntry getPMSx0032() {

		return DataEntry.of(SENSOR2_ID, PMSx0032);

	}

	@Override
	public DataEntry getPMSx0033() {

		return DataEntry.of(SENSOR3_ID, PMSx0033);

	}

	@Override
	public DataEntry getChecksum() {
		return DataEntry.of(CHECKSUM_ID, PMSx0031 + PMSx0032 + PMSx0033);
	}

}
