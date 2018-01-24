package de.fzi.bwcps.example.preprocessing.gateway;

import java.io.BufferedReader;
import java.io.StringReader;

import de.fzi.bwcps.example.galileogen2.gen.GalileoGen2Data;
import de.fzi.bwcps.example.galileogen2.gen.GalileoGen2Utility;
import de.fzi.bwcps.example.preprocessing.DataPipe;
import de.fzi.bwcps.example.preprocessing.MeasuredData;
import de.fzi.bwcps.example.preprocessing.SingleInputSingleOutputFilter;

public class DataDeserializer extends SingleInputSingleOutputFilter<String,MeasuredData<GalileoGen2Data>> {

	public DataDeserializer(DataPipe<String> input, DataPipe<MeasuredData<GalileoGen2Data>> output) {
		
		super(input, output);

	}

	@Override
	public void apply() {

		GalileoGen2Data deserializedData = GalileoGen2Utility.unmarshalJSON(createBufferedReader(), 
																  			new GalileoGen2Data());		
		
		addResultToOutput(new MeasuredData<GalileoGen2Data>("Placeholder", deserializedData));
		
	}

	private BufferedReader createBufferedReader() {

		StringReader in = new StringReader(getInputData());
		return new BufferedReader(in);
		
	}

}
