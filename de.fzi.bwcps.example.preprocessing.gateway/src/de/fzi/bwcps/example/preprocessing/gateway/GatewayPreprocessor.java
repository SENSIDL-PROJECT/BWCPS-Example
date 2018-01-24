package de.fzi.bwcps.example.preprocessing.gateway;

import java.util.Arrays;

import de.fzi.bwcps.example.preprocessing.DataPipe;
import de.fzi.bwcps.example.preprocessing.MeasuredData;
import de.fzi.bwcps.example.preprocessing.factory.Preprocessable;
import de.fzi.sensidl.GalileoGen2.GalileoGen2Data;

public class GatewayPreprocessor extends Preprocessable<String,MeasuredData<GalileoGen2Data>> {

	private final DataDeserializer deserializer;
	private final DataPipe<MeasuredData<GalileoGen2Data>> outputPipe;
	private final DataPipe<String> inputDataToDeserialized;
	
	public GatewayPreprocessor() {
		
		this.inputDataToDeserialized = new DataPipe<String>();
		this.outputPipe = new DataPipe<MeasuredData<GalileoGen2Data>>();
		
		this.deserializer = new DataDeserializer(inputDataToDeserialized, outputPipe);
		
	}

	@Override
	public MeasuredData<GalileoGen2Data> process(String data) {
		
		clearInputPipe();
		addToInputPipe(data);
		applyPreprocessors();
		return getResult();
		
	}
	
	private void clearInputPipe() {
		
		inputDataToDeserialized.clear();
		
	}
	
	private void addToInputPipe(String data) {
		
		inputDataToDeserialized.add(data);
		
	}

	private void applyPreprocessors() {
		
		Arrays.asList(deserializer).forEach(preprocessor -> preprocessor.apply());
		
	}

	public MeasuredData<GalileoGen2Data> getResult() {
		
		if (outputPipe.getData().isEmpty()) {
			
			throw new RuntimeException("There is no output");
			
		}
		
		return outputPipe.getData().get(0);
		
	}

}
