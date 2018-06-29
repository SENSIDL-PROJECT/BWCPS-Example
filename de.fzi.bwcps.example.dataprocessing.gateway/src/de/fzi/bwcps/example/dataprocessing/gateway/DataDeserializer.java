package de.fzi.bwcps.example.dataprocessing.gateway;

import java.io.BufferedReader;
import java.io.StringReader;

import de.fzi.bwcps.example.preprocessing.DataPipe;
import de.fzi.bwcps.example.preprocessing.SingleInputSingleOutputFilter;
import de.fzi.bwcps.example.sensor.plantower.gen.PlantowerData;
import de.fzi.bwcps.example.sensor.plantower.gen.PlantowerUtility;

public class DataDeserializer extends SingleInputSingleOutputFilter<String, PlantowerData> {

	public DataDeserializer(DataPipe<String> input, DataPipe<PlantowerData> output) {

		super(input, output);

	}

	@Override
	public void apply() {

		PlantowerData result = PlantowerUtility.unmarshalJSON(createBufferedReader(), new PlantowerData());

		addResultToOutput(result);

	}

	private BufferedReader createBufferedReader() {

		StringReader in = new StringReader(getInputData());
		return new BufferedReader(in);

	}

}
