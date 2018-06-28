package de.fzi.bwcps.example.dataprocessing.sensor;

import java.util.Optional;
import java.util.function.Predicate;

import de.fzi.bwcps.example.preprocessing.DataPipe;
import de.fzi.bwcps.example.preprocessing.MeasuredData;
import de.fzi.bwcps.example.preprocessing.MultipleInputSingleOutputFilter;
import de.fzi.bwcps.example.sensor.plantower.gen.PlantowerData;

public class RawToPlantowerDataConverter extends MultipleInputSingleOutputFilter<MeasuredData<Object>,MeasuredData<PlantowerData>> {

	private final static String SEPERATOR = ";";
	private final static String CHECKSUM = "CHECKSUM";
	private final static String SENSOR1_ID = "PMSx0031";
	private final static String SENSOR2_ID = "PMSx0032";
	private final static String SENSOR3_ID = "PMSx0033";
	private final static String LIGHT_ID = "Light";
	
	public RawToPlantowerDataConverter(DataPipe<MeasuredData<Object>> input, DataPipe<MeasuredData<PlantowerData>> output) {
		
		super(input, output);
		
	}

	@Override
	public void apply() {
		
		MeasuredData<Object> checksum = getMeasurementWith(CHECKSUM).orElseThrow(() -> new RuntimeException());
		MeasuredData<Object> sensor1 = getMeasurementWith(SENSOR1_ID).orElseThrow(() -> new RuntimeException());
		MeasuredData<Object> sensor2 = getMeasurementWith(SENSOR2_ID).orElseThrow(() -> new RuntimeException());
		MeasuredData<Object> sensor3 = getMeasurementWith(SENSOR3_ID).orElseThrow(() -> new RuntimeException());
		MeasuredData<PlantowerData> result = createMeasuredDataStructure(checksum, sensor1, sensor2, sensor3);
		
		addResultToOutput(result);
		
	}

	private MeasuredData<PlantowerData> createMeasuredDataStructure(MeasuredData<Object> checksum, MeasuredData<Object> sensor1, MeasuredData<Object> sensor2, MeasuredData<Object> sensor3) {
		
		String id = SENSOR1_ID.concat(SEPERATOR).concat(LIGHT_ID);
		float check = (float) checksum.getMeasuredData();
		float s1 = (float) sensor1.getMeasuredData();
		float s2 = (float) sensor2.getMeasuredData();
		float s3 = (float) sensor3.getMeasuredData();
		
		return new MeasuredData<PlantowerData>(id, new PlantowerData(check, s1, s2, s3));
		
	}

	private Optional<MeasuredData<Object>> getMeasurementWith(String targetId) {
		
		return getInputData().stream().filter(isMeasurementWith(targetId))
									  .findFirst();
		
	}

	private Predicate<MeasuredData<Object>> isMeasurementWith(String targetId) {
		
		return measurement -> measurement.getId().equals(targetId);
	
	}

}
