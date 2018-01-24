package de.fzi.bwcps.example.preprocessing.sensor;

import de.fzi.bwcps.example.preprocessing.DataPipe;
import de.fzi.bwcps.example.preprocessing.MeasuredData;
import de.fzi.bwcps.example.preprocessing.SingleInputSingleOutputFilter;
import de.fzi.sensidl.GalileoGen2.GalileoGen2Data;
import de.fzi.sensidl.GalileoGen2.GalileoGen2Utility;

public class DataSerializer extends SingleInputSingleOutputFilter<MeasuredData<GalileoGen2Data>, String> {

	public DataSerializer(DataPipe<MeasuredData<GalileoGen2Data>> input, DataPipe<String> output) {
		
		super(input, output);
		
	}

	@Override
	public void apply() {
		
		GalileoGen2Data measurement = getInputData().getMeasuredData();
		String result = GalileoGen2Utility.marshalJSON(measurement);
		
		addResultToOutput(result);
		
		
	}

}
