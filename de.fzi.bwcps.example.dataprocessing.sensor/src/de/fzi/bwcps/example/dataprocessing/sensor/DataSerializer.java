package de.fzi.bwcps.example.dataprocessing.sensor;

import de.fzi.bwcps.example.preprocessing.DataPipe;
import de.fzi.bwcps.example.preprocessing.MeasuredData;
import de.fzi.bwcps.example.preprocessing.SingleInputSingleOutputFilter;
import de.fzi.bwcps.example.sensor.plantower.gen.PlantowerData;
import de.fzi.bwcps.example.sensor.plantower.gen.PlantowerUtility;

public class DataSerializer extends SingleInputSingleOutputFilter<MeasuredData<PlantowerData>, String> {

	public DataSerializer(DataPipe<MeasuredData<PlantowerData>> input, DataPipe<String> output) {
		
		super(input, output);
		
	}

	@Override
	public void apply() {
		
		PlantowerData measurement = getInputData().getMeasuredData();
		String result = PlantowerUtility.marshalJSON(measurement);
		
		addResultToOutput(result);
		
		
	}

}
