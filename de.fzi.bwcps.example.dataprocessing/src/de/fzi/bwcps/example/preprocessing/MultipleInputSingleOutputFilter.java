package de.fzi.bwcps.example.preprocessing;

import java.util.List;

public abstract class MultipleInputSingleOutputFilter<U,T> extends AbstractFilter<U,T> {

	public MultipleInputSingleOutputFilter(DataPipe<U> input, DataPipe<T> output) {
		
		super(input, output);
		
	}

	protected List<U> getInputData() {
		
		return input.getData();
		
	}
	
	protected void addResultToOutput(T result) {
		
		output.clear();
		output.add(result);
		
	}
	
}
