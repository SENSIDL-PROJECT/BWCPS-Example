package de.fzi.bwcps.example.preprocessing;

public abstract class SingleInputSingleOutputFilter<U,T> extends AbstractFilter<U,T> {

	public SingleInputSingleOutputFilter(DataPipe<U> input, DataPipe<T> output) {
		
		super(input, output);
		
	}

	protected U getInputData() {
		
		if (input.getData().isEmpty()) {
			
			throw new RuntimeException("Sensor data is not initialized.");
			
		}
		
		return input.getData().get(0);
		
	}
	
	protected void addResultToOutput(T result) {
		
		output.clear();
		output.add(result);
		
	}
	
}
