package de.fzi.bwcps.example.dataprocessing.sensor;

import java.util.Optional;
import java.util.function.Predicate;

import de.fzi.bwcps.example.galileogen2.gen.GalileoGen2Data;
import de.fzi.bwcps.example.preprocessing.DataPipe;
import de.fzi.bwcps.example.preprocessing.MeasuredData;
import de.fzi.bwcps.example.preprocessing.MultipleInputSingleOutputFilter;

public class RawToGalileoGen2DataConverter extends MultipleInputSingleOutputFilter<MeasuredData<Object>,MeasuredData<GalileoGen2Data>> {

	private final static String SEPERATOR = ";";
	private final static String TEMPERATURE_ID = "Temperature";
	private final static String LIGHT_ID = "Light";
	
	public RawToGalileoGen2DataConverter(DataPipe<MeasuredData<Object>> input, DataPipe<MeasuredData<GalileoGen2Data>> output) {
		
		super(input, output);
		
	}

	@Override
	public void apply() {
		
		MeasuredData<Object> temperature = getMeasurementWith(TEMPERATURE_ID).orElseThrow(() -> new RuntimeException());
		MeasuredData<Object> light = getMeasurementWith(LIGHT_ID).orElseThrow(() -> new RuntimeException());
		MeasuredData<GalileoGen2Data> result = createMeasuredDataStructure(temperature, light);
		
		addResultToOutput(result);
		
	}

	private MeasuredData<GalileoGen2Data> createMeasuredDataStructure(MeasuredData<Object> temperature, MeasuredData<Object> light) {
		
		String id = TEMPERATURE_ID.concat(SEPERATOR).concat(LIGHT_ID);
		short t = (short) temperature.getMeasuredData();
		double l = (double) light.getMeasuredData();
		
		return new MeasuredData<GalileoGen2Data>(id, new GalileoGen2Data(t, l));
		
	}

	private Optional<MeasuredData<Object>> getMeasurementWith(String targetId) {
		
		return getInputData().stream().filter(isMeasurementWith(targetId))
									  .findFirst();
		
	}

	private Predicate<MeasuredData<Object>> isMeasurementWith(String targetId) {
		
		return measurement -> measurement.getId().equals(targetId);
	
	}

}
